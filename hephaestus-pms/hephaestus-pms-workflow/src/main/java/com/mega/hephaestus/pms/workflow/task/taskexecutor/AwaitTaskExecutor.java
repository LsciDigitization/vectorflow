package com.mega.hephaestus.pms.workflow.task.taskexecutor;

import com.mega.component.bioflow.task.InstanceId;
import com.mega.component.bioflow.task.StageEntity;
import com.mega.component.workflow.models.ExecutableTask;
import com.mega.component.workflow.models.TaskExecutionResult;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.workflow.task.stageflow.AbstractStageFlowExecutor;
import com.mega.hephaestus.pms.workflow.task.stagetask.*;
import com.mega.hephaestus.pms.workflow.task.taskbuild.SpecialMeta;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.FormattedMessage;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 等待节点
 */
@Slf4j
public class AwaitTaskExecutor extends AbstractStageFlowExecutor {

    private final AwaitTaskExecutorResource awaitTaskExecutorResource;

    private final TaskLoggerService stageLoggerService;

    public AwaitTaskExecutor(int latchQty, AwaitTaskExecutorResource awaitTaskExecutorResource, TaskLoggerService stageLoggerService) {
        super(latchQty);
        this.awaitTaskExecutorResource = awaitTaskExecutorResource;
        this.stageLoggerService = stageLoggerService;
    }

    @Override
    public TaskExecutionResult doRun(ExecutableTask task) throws InterruptedException {
        log.info("Stage ExecutableTask print: {}", task);

        Map<String, String> metaData = task.getMetaData();
        SpecialMeta specialMeta = new SpecialMeta(metaData);
        String instanceIdString = specialMeta.getMetaData(SpecialMeta.SpecialMetaKeyEnum.EXPERIMENT_INSTANCE_ID);
        Long instanceId = Long.parseLong(instanceIdString);

        stageLoggerService.info(instanceId, new FormattedMessage("Stage instance id {}", instanceId));
        stageLoggerService.info(instanceId, new FormattedMessage("Stage step: {}, type: {} task starting.", task.getTaskId().getId(), task.getTaskType().getType()));

        Optional<InstanceEntity> instanceOptional = awaitTaskExecutorResource.getExperimentExecutorManager().getInstance(instanceId);
        System.out.println(instanceOptional);

        AtomicReference<HephaestusStage> currentStage = new AtomicReference<>();
        try {
            instanceOptional.ifPresentOrElse(hephaestusInstance -> {

                // 查找当前stage阶段数据
                Optional<HephaestusStage> stagesOptional = awaitTaskExecutorResource.getExperimentExecutorManager().getStage(hephaestusInstance, task.getTaskId().getId());
                stagesOptional.ifPresentOrElse(v -> {
                    currentStage.set(v);

                    stageLoggerService.info(instanceId, v, new FormattedMessage("Experiment instance {} of stage {}", instanceId, v));

                    List<HephaestusStageTask> stageTasks = awaitTaskExecutorResource.getExperimentStageTaskManager().getStageTasks(hephaestusInstance.getExperimentId(), task.getTaskId().getId());
                    StageEntity stageEntity = toStageEntity(v, stageTasks);

//                    DynamicStageTask.RunParameter runParameter = new DynamicStageTask.RunParameter();
//                    runParameter.setInstanceId(instanceId.toString());
//                    runParameter.setStageTasks(stageTasks);
//                    runParameter.setWorkflowRunId(task.getRunId().getId());
//                    runParameter.setStage(v);
//                    // 运行阶段子任务
//                    // 设置线程池
//                    awaitTaskExecutorResource.getDynamicStageTask().setDeviceLockExecutor(awaitTaskExecutorResource.getDeviceLockExecutor());
//                    awaitTaskExecutorResource.getDynamicStageTask().run(runParameter);

                    DynamicStage.RunParameter runParameter = new DynamicStage.RunParameter();
                    runParameter.setInstanceId(new InstanceId(instanceId));
                    runParameter.setStage(stageEntity);
                    // 运行阶段子任务
                    // 设置线程池
                    awaitTaskExecutorResource.getDynamicStage().setDeviceLockExecutor(awaitTaskExecutorResource.getDeviceLockExecutor());
                    awaitTaskExecutorResource.getDynamicStage().run(runParameter);

                }, () -> stageLoggerService.info(instanceId, new FormattedMessage("未找到instance {} stage {}", instanceId, task.getTaskId().getId())));

            }, () -> stageLoggerService.info(instanceId, new FormattedMessage("未找到instanceId {}", instanceId)));
        } catch (RuntimeException e) {
            stageLoggerService.error(instanceId, new FormattedMessage("Stage {} run error: {}", task.getTaskId().getId(), e.getMessage()));
            // 失败后更新任务状态，stage执行失败，保存数据
            awaitTaskExecutorResource.getExperimentInstanceManager().failStage(instanceId, currentStage.get().getId());
            return executionFailedResult();
        } finally {
            stageLoggerService.info(instanceId, new FormattedMessage("Stage {} task over.", task.getTaskId().getId()));
        }

        // stage执行完成，保存数据
        awaitTaskExecutorResource.getExperimentInstanceManager().endStage(instanceId, currentStage.get().getId());

        return executionSuccessResult();
    }

}
