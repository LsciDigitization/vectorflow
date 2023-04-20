package com.mega.hephaestus.pms.workflow.task.taskexecutor;

import com.google.common.collect.Maps;
import com.mega.component.workflow.models.ExecutableTask;
import com.mega.component.workflow.models.TaskExecutionResult;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.workflow.event.InstanceFinishedEvent;
import com.mega.hephaestus.pms.workflow.task.taskbuild.SpecialMeta;
import com.mega.hephaestus.pms.workflow.task.stageflow.AbstractStageFlowExecutor;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.FormattedMessage;

import java.util.Map;
import java.util.Optional;


@Slf4j
public class EndTaskExecutor extends AbstractStageFlowExecutor
{
    private final EndTaskExecutorResource endTaskExecutorResource;
    private final TaskLoggerService stageLoggerService;

    public EndTaskExecutor(int latchQty, EndTaskExecutorResource endTaskExecutorResource, TaskLoggerService stageLoggerService) {
        super(latchQty);
        this.endTaskExecutorResource = endTaskExecutorResource;
        this.stageLoggerService = stageLoggerService;
    }

    public TaskExecutionResult doRun(ExecutableTask task) throws InterruptedException
    {
        log.info("Stage ExecutableTask print: {}", task);

        Map<String, String> metaData = task.getMetaData();
        SpecialMeta specialMeta = new SpecialMeta(metaData);
        String instanceIdString = specialMeta.getMetaData(SpecialMeta.SpecialMetaKeyEnum.EXPERIMENT_INSTANCE_ID);
        Long instanceId = Long.parseLong(instanceIdString);

        stageLoggerService.info(instanceId, new FormattedMessage("Stage instance id {}", instanceId));
        stageLoggerService.info(instanceId, new FormattedMessage("Stage step: {}, type: {} task starting.", task.getTaskId().getId(), task.getTaskType().getType()));

        Optional<InstanceEntity> instanceOptional = endTaskExecutorResource.getExperimentExecutorManager().getInstance(instanceId);
        instanceOptional.ifPresent(hephaestusInstance -> {
            System.out.println(hephaestusInstance);
            endTaskExecutorResource.getWorkEventPusher().sendInstanceFinishedEvent(new InstanceFinishedEvent(instanceId));
        });

        stageLoggerService.info(instanceId, new FormattedMessage("Stage {} task over.", task.getTaskId().getId()));

        return executionSuccessResult();
    }

    @Override
    protected Map<String, String> resultData() {
        Map<String, String> resultData = Maps.newHashMap();
        resultData.put("one", "1");
        resultData.put("two", "2");
        return resultData;
    }

}
