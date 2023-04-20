package com.mega.hephaestus.pms.workflow.task.stagetask;

import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.component.task.Task;
import com.mega.component.task.executor.SynchronousExecutor;
import com.mega.component.task.task.*;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.model.enums.InstanceTaskStatusEnum;
import com.mega.hephaestus.pms.data.model.service.IHephaestusStageService;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.component.nuc.device.AbstractDevice;
import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.component.nuc.exceptions.DeviceException;
import com.mega.component.nuc.exceptions.StageException;
import com.mega.hephaestus.pms.nuc.workflow.stage.AbstractStage;
import com.mega.hephaestus.pms.workflow.event.StepCreateEvent;
import com.mega.hephaestus.pms.workflow.event.StepEndEvent;
import com.mega.hephaestus.pms.workflow.event.WorkEventPusher;
import com.mega.hephaestus.pms.workflow.exception.DeviceTaskException;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceTaskManager;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;
import com.mega.hephaestus.pms.workflow.task.tasklog.StageTaskLogger;
import com.mega.hephaestus.pms.workflow.work.workstep.Step;
import com.mega.hephaestus.pms.workflow.work.workstep.StepStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.message.FormattedMessage;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * TODO.....
 *  遍历 Stage -> TaskList
 *  ｛
 *      1.通过instance查询实例数据，填充动态参数  ${instance.start_storage.nuc_id} ....${instance.current_storage.nuc_id} .... ${instance.end_storage.nuc_id}
 *      2.解析执行 - before_hook
 *      3.解析替换 - task_parameter 动态参数
 *      4.解析执行 - task_command
 *      3.解析执行 - after_success_hook OR after_fail_hook
 *      4.更新instance表数据 current_storage_model_id
 *  ｝
 */

@RequiredArgsConstructor
@Component
@Slf4j
@Deprecated(since = "20230322")
public class DynamicStageTask extends AbstractStage<DynamicStageTask.RunParameter> {

    private final IHephaestusStageService stageService;
    private final TaskLoggerService stageLoggerService;
    private final WorkEventPusher workEventPusher;

    private final InstanceTaskManager instanceTaskManager;

    @Override
    public void run(RunParameter runParameter) {
        // 获取任务执行容量
        int capacity = runParameter.stageTasks.size();
        if (capacity == 0) {
            return;
        }

        StageTaskLogger stageTaskLogger = new StageTaskLogger(runParameter.instanceId);

        // Stage任务执行的汇总时间，设置总任务的执行超时，设置5个小时，18000秒钟
        final SynchronousExecutor executor = new SynchronousExecutor(18000, capacity);
        executor.setLogger(stageTaskLogger);
        final TaskListener listener = new DefaultTaskListener(stageTaskLogger);
        TaskManager taskManager = TaskManager.getInstance();
        // 添加任务
        for (HephaestusStageTask v : runParameter.stageTasks) {

            taskManager.addTask(new Task(v.getTaskName(), v.getId()) {
                // 任务开始时间
//                private Date startTime;
                // 请求ID
//                private String requestId = "";
                // 任务状态
                private int taskStatus = TaskStatus.S_READY;

//                private Map<String, Object> taskParameterMap = new HashMap<>();

                @Override
                public void runTask() {
                    try {
                        stageTaskLogger.setExperimentId(v.getExperimentId());
                        stageTaskLogger.setStageId(v.getStageId());
                        stageTaskLogger.setTaskId(v.getId());

                        // 检测当前任务是否被执行或完成
                        Optional<InstanceTaskEntity> instanceTaskOptional = instanceTaskManager.getInstanceTask(Long.parseLong(runParameter.instanceId), v.getId());
                        if (instanceTaskOptional.isPresent()) {
                            String taskStatus1 = instanceTaskOptional.get().getTaskStatus();
                            if (taskStatus1.equals(InstanceTaskStatusEnum.Finished.getValue())) {
                                stageLoggerService.info(Long.parseLong(runParameter.instanceId), v, new FormattedMessage("实例 {} 任务 {} {} 已经执行完成，直接跳过", runParameter.instanceId, v.getId(), this.getTaskName()));
                                // 标记任务状态为完成
                                taskStatus = TaskStatus.S_SUCCEED;
                                return;
                            }
                        }

//                        startTime = new Date();
                        // 开始日志
                        stageLoggerService.info(Long.parseLong(runParameter.instanceId), v, new FormattedMessage("开始执行 {} {}", this.getTaskId(), this.getTaskName()));

                        Step step = new Step();
                        if (StringUtils.isNotBlank(v.getStepKey())) {
                            step.setName(v.getTaskName());
                            step.setType(StepTypeEnum.toEnum(v.getStepKey()));
                            step.setStatus(StepStatus.NonStarted);
                            step.setNextStepDurationSecond(v.getNextTaskExpireDurationSecond());
                            step.setStartTime(new Date());
                            workEventPusher.sendStepCreateEvent(new StepCreateEvent(step, runParameter.instanceId));
                        }

                        // 投递设备任务
                        DeviceType deviceType = DeviceTypeEnum.toEnum(v.getDeviceType());
                        InstanceTaskEntity instanceTask = instanceTaskManager.createTask(deviceType, v, Long.parseLong(runParameter.instanceId));

                        // 任务同步阻塞检测
                        TaskSpinWait.ofMax().waitWithThrow(() -> {
                            try {
                                return instanceTaskManager.checkTaskFinished(instanceTask.getId());
                            } catch (DeviceTaskException e) {
                                stageLoggerService.info(Long.parseLong(runParameter.instanceId), v, new FormattedMessage("检测任务状态出现异常 {}", e.getMessage()));
                                return false;
                            }
                        });

                        if (StringUtils.isNotBlank(v.getStepKey())) {
                            step.setEndTime(new Date());
                            step.setStatus(StepStatus.Completed);
                            workEventPusher.sendStepEndEvent(new StepEndEvent(step, runParameter.instanceId));
                        }

                        // 标记任务状态为完成
                        taskStatus = TaskStatus.S_SUCCEED;
                    } catch (DeviceException e) {
                        Object first = Arrays.stream(e.getArgs()).findFirst().orElse("");
                        stageLoggerService.error(Long.parseLong(runParameter.instanceId), v, new FormattedMessage("DeviceException {}", first.toString()));
                        // 抛出异常，由上层捕获保存数据库状态
                        throw new StageException(e.getMessage(), e);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        // 记录日志
                        stageLoggerService.error(Long.parseLong(runParameter.instanceId), v, new FormattedMessage("执行异常 {} {}，异常消息：{}, 出现异常位置：{}", this.getTaskId(), this.getTaskName(), e.getMessage(), e.getClass().getName()));
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
                            stageLoggerService.info(Long.parseLong(runParameter.instanceId), v, new FormattedMessage("保存任务执行状态 {} {}", v.getTaskName(), taskStatus));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void timeOutAction() {
                    stageLoggerService.info(Long.parseLong(runParameter.instanceId), v, new FormattedMessage("执行超时 {} {}", this.getTaskId(), this.getTaskName()));
                }

                @Override
                public void taskFinished() {
                    stageLoggerService.info(Long.parseLong(runParameter.instanceId), v, new FormattedMessage("执行完成 {} {}", this.getTaskId(), this.getTaskName()));
                }
            }, executor, listener);
        }

        executor.start();
    }

    /**
     * 执行任务前操作
     * @param runParameter RunParameter
     * @param deviceLockSet AbstractDevice set
     */
    @Deprecated(since = "20230322")
    public void beforeStageTask(RunParameter runParameter, Set<AbstractDevice> deviceLockSet) {
        // 设备锁为空，忽略处理
        if (deviceLockSet.size() == 0) {
            return;
        }

        // 前置锁加锁
//        retryLockAll(runParameter.instanceId, runParameter.retryTimeOut, (device) -> {
//            // 记录日志
//            stageLoggerService.info(Long.parseLong(runParameter.instanceId), runParameter.stage, new FormattedMessage("设备前置加锁成功 {} {}", device.getDeviceType(), device.getDeviceId()));
//        }, deviceLockSet.toArray(new AbstractDevice[]{}));
    }

    /**
     * 执行任务后操作
     * @param runParameter RunParameter
     * @param allDevices AbstractDevice set
     */
    @Deprecated(since = "20230322")
    public void afterStageTask(RunParameter runParameter, Set<AbstractDevice> allDevices) {
        // 设备锁为空，忽略处理
        if (allDevices.size() == 0) {
            return;
        }

        // 解锁所有设备
//        unlockAll(runParameter.instanceId, (device) -> {
//            // 记录日志
//            stageLoggerService.info(Long.parseLong(runParameter.instanceId), runParameter.stage, new FormattedMessage("设备前置解锁成功 {} {}", device.getDeviceType(), device.getDeviceId()));
//        }, allDevices.toArray(new AbstractDevice[]{}));
    }

    /**
     * 执行任务后操作
     * @param runParameter RunParameter
     * @param allDevices AbstractDevice set
     */
    @Deprecated(since = "20230322")
    public void afterStageTask(RunParameter runParameter, Set<AbstractDevice> allDevices, HephaestusStageTask stageTask) {
        // 设备锁为空，忽略处理
        if (allDevices.size() == 0) {
            return;
        }

        // 解锁所有设备
//        unlockAll(runParameter.instanceId, (device) -> {
//            // 记录日志
//            stageLoggerService.info(Long.parseLong(runParameter.instanceId), stageTask, new FormattedMessage("设备解锁成功 {} {}", device.getDeviceType(), device.getDeviceId()));
//        }, allDevices.toArray(new AbstractDevice[]{}));
    }



    @Data
    public static class RunParameter {
        /**
         * 实例ID
         */
        private String instanceId;
        /**
         * 锁时间，单位毫秒
         */
        private int retryTimeOut = 100 * 1000;


        private List<HephaestusStageTask> stageTasks;

        private String workflowRunId;

        private HephaestusStage stage;

    }
}
