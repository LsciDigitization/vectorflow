package com.mega.hephaestus.pms.workflow.task.taskexecutor;

import com.mega.component.workflow.models.ExecutableTask;
import com.mega.component.workflow.models.TaskExecutionResult;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.workflow.task.stageflow.AbstractStageFlowExecutor;
import com.mega.hephaestus.pms.workflow.task.taskbuild.SpecialMeta;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.FormattedMessage;

import java.util.Map;
import java.util.Optional;

/**
 * 判断节点，网关执行器
 */
@Slf4j
public class GatewayExecutor extends AbstractStageFlowExecutor {

    private final GatewayTaskExecutorResource gatewayTaskExecutorResource;

    private final TaskLoggerService stageLoggerService;

    public GatewayExecutor(int latchQty, GatewayTaskExecutorResource gatewayTaskExecutorResource, TaskLoggerService stageLoggerService) {
        super(latchQty);
        this.gatewayTaskExecutorResource = gatewayTaskExecutorResource;
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

        Optional<InstanceEntity> instanceOptional = gatewayTaskExecutorResource.getExperimentExecutorManager().getInstance(instanceId);
        System.out.println(instanceOptional);

        stageLoggerService.info(instanceId, new FormattedMessage("Stage {} task over.", task.getTaskId().getId()));

        return executionSuccessResult();
    }


}
