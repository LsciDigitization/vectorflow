package com.mega.hephaestus.pms.workflow.task.stagetask;

import com.mega.component.bioflow.task.InstanceId;
import com.mega.component.bioflow.task.StageTaskEntity;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.component.task.Task;
import com.mega.component.task.task.TaskSpinWait;
import com.mega.component.task.task.TaskStatus;
import com.mega.hephaestus.pms.data.model.enums.InstanceTaskStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.exceptions.DeviceException;
import com.mega.component.nuc.exceptions.StageException;
import com.mega.hephaestus.pms.workflow.event.StepCreateEvent;
import com.mega.hephaestus.pms.workflow.event.StepEndEvent;
import com.mega.hephaestus.pms.workflow.exception.DeviceTaskException;
import com.mega.hephaestus.pms.workflow.task.tasklog.StageTaskLogger;
import com.mega.hephaestus.pms.workflow.work.workstep.Step;
import com.mega.hephaestus.pms.workflow.work.workstep.StepStatus;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.FormattedMessage;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/22 15:51
 */
@RequiredArgsConstructor
public class DynamicStageRunnerTask extends Task {
    private final InstanceId instanceId;
    private final StageTaskEntity stageTask;
    private final StageTaskLogger stageTaskLogger;
    private final DynamicStageRunnerTaskResource runnerTaskResource;

    private int taskStatus = TaskStatus.S_READY;

    @Override
    public void runTask() {
        try {
            stageTaskLogger.setExperimentId(stageTask.getExperimentId().getLongId());
            stageTaskLogger.setStageId(stageTask.getStageId().getLongId());
            stageTaskLogger.setTaskId(stageTask.getId().getLongId());

            // 检测当前任务是否被执行或完成
            Optional<InstanceTaskEntity> instanceTaskOptional = runnerTaskResource.getInstanceTaskManager().getInstanceTask(instanceId.getLongId(), stageTask.getId().getLongId());
            if (instanceTaskOptional.isPresent()) {
                String taskStatus1 = instanceTaskOptional.get().getTaskStatus();
                if (taskStatus1.equals(InstanceTaskStatusEnum.Finished.getValue())) {
                    runnerTaskResource.getStageLoggerService().info(instanceId.getLongId(), stageTask, new FormattedMessage("实例 {} 任务 {} {} 已经执行完成，直接跳过", instanceId, stageTask.getId(), this.getTaskName()));
                    // 标记任务状态为完成
                    taskStatus = TaskStatus.S_SUCCEED;
                    return;
                }
            }

            Step step = new Step();
            if (Objects.nonNull(stageTask.getStepKey())) {
                step.setName(stageTask.getTaskName());
                step.setType(StepTypeEnum.toEnum(stageTask.getStepKey().toString()));
                step.setStatus(StepStatus.NonStarted);
                step.setNextStepDurationSecond(stageTask.getNextTaskExpireDurationSecond());
                step.setStartTime(new Date());
                runnerTaskResource.getWorkEventPusher().sendStepCreateEvent(new StepCreateEvent(step, instanceId.getLongId()));
            }

            // 投递设备任务
            DeviceType deviceType = stageTask.getDeviceType();
            InstanceTaskEntity instanceTask = runnerTaskResource.getInstanceTaskManager().createTask(deviceType, stageTask, instanceId.getLongId());

            // 任务同步阻塞检测
            TaskSpinWait.ofMax().waitWithThrow(() -> {
                try {
                    return runnerTaskResource.getInstanceTaskManager().checkTaskFinished(instanceTask.getId());
                } catch (DeviceTaskException e) {
                    runnerTaskResource.getStageLoggerService().info(instanceId.getLongId(), stageTask, new FormattedMessage("检测任务状态出现异常 {}", e.getMessage()));
                    return false;
                }
            });

            if (Objects.nonNull(stageTask.getStepKey())) {
                step.setEndTime(new Date());
                step.setStatus(StepStatus.Completed);
                runnerTaskResource.getWorkEventPusher().sendStepEndEvent(new StepEndEvent(step, instanceId.toString()));
            }

            // 标记任务状态为完成
            taskStatus = TaskStatus.S_SUCCEED;
        } catch (DeviceException e) {
            Object first = Arrays.stream(e.getArgs()).findFirst().orElse("");
            runnerTaskResource.getStageLoggerService().error(instanceId.getLongId(), stageTask, new FormattedMessage("DeviceException {}", first.toString()));
            // 抛出异常，由上层捕获保存数据库状态
            throw new StageException(e.getMessage(), e);
        } catch (RuntimeException e) {
            e.printStackTrace();
            // 记录日志
            runnerTaskResource.getStageLoggerService().error(instanceId.getLongId(), stageTask, new FormattedMessage("执行异常 {} {}，异常消息：{}, 出现异常位置：{}", this.getTaskId(), this.getTaskName(), e.getMessage(), e.getClass().getName()));
            // 标记任务状态为失败
            taskStatus = TaskStatus.S_FAILED;
            // 抛出异常，由上层捕获保存数据库状态
            throw new StageException(e.getMessage(), e);
        } finally {
            try {
                // TODO
                // 保存执行记录
                // saveExperimentStageTaskEntity(runParameter, v, requestId, taskParameterMap, startTime, taskStatus);
                // 记录日志
                runnerTaskResource.getStageLoggerService().info(instanceId.getLongId(), stageTask, new FormattedMessage("保存任务执行状态 {} {}", stageTask.getTaskName(), taskStatus));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void timeOutAction() {
        runnerTaskResource.getStageLoggerService().info(instanceId.getLongId(), stageTask, new FormattedMessage("执行超时 {} {}", this.getTaskId(), this.getTaskName()));
    }

    @Override
    public void taskFinished() {
        runnerTaskResource.getStageLoggerService().info(instanceId.getLongId(), stageTask, new FormattedMessage("执行完成 {} {}", this.getTaskId(), this.getTaskName()));
    }
}
