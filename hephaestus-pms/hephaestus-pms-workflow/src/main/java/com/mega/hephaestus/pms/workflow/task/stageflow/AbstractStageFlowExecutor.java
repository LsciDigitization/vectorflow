package com.mega.hephaestus.pms.workflow.task.stageflow;

import com.google.common.collect.Maps;
import com.mega.component.bioflow.task.*;
import com.mega.component.workflow.WorkflowManager;
import com.mega.component.workflow.executor.TaskExecution;
import com.mega.component.workflow.executor.TaskExecutionStatus;
import com.mega.component.workflow.executor.TaskExecutor;
import com.mega.component.workflow.models.ExecutableTask;
import com.mega.component.workflow.models.TaskExecutionResult;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.component.nuc.device.DeviceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractStageFlowExecutor implements TaskExecutor {

    public AbstractStageFlowExecutor() {

    }

    public AbstractStageFlowExecutor(int latchQty) {

    }

    @Override
    public TaskExecution newTaskExecution(WorkflowManager workflowManager, ExecutableTask task) {
        return () -> {
            try {
                return doRun(task);
            } catch (Exception e) {
                log.info("中断异常 {}", e.getMessage());
                return executionFailedResult();
            }
        };
    }

    protected TaskExecutionResult executionFailedResult() {
        return new TaskExecutionResult(TaskExecutionStatus.FAILED_STOP, "failed", resultData());
    }

    protected TaskExecutionResult executionSuccessResult() {
        return new TaskExecutionResult(TaskExecutionStatus.SUCCESS, "success", resultData());
    }

    protected Map<String, String> resultData() {
        return Maps.newHashMap();
    }

    public abstract TaskExecutionResult doRun(ExecutableTask task) throws InterruptedException;

    protected StageTaskEntity toStageTaskEntity(HephaestusStageTask x) {
        StageTaskEntity taskEntity = new StageTaskEntity();
        taskEntity.setId(new StageTaskId(x.getId()));
        taskEntity.setStageId(new StageId(x.getStageId()));
        taskEntity.setExperimentId(new ExperimentId(x.getExperimentId()));
        taskEntity.setDeviceType(DeviceTypeEnum.toEnum(x.getDeviceType()));
        taskEntity.setDeviceName(x.getDeviceName());
        taskEntity.setTaskName(x.getTaskName());
        taskEntity.setTaskDescription(x.getTaskDescription());
        taskEntity.setTaskCommand(x.getTaskCommand());
        taskEntity.setTaskParameter(x.getTaskParameter());
//                                taskEntity.setTaskAction();
        taskEntity.setLockStatus(x.getLockStatus());
        if (StringUtils.isNotEmpty(x.getStepKey())) {
            taskEntity.setStepKey(StepKey.toEnum(x.getStepKey()));
        }
        taskEntity.setTimeoutSecond(x.getTimeoutSecond());
        taskEntity.setNextTaskExpireDurationSecond(x.getNextTaskExpireDurationSecond());
        taskEntity.setOnErrorTaskId(x.getOnErrorTaskId());
        taskEntity.setSortOrder(x.getSortOrder());
        return taskEntity;
    }

    protected StageEntity toStageEntity(HephaestusStage v) {
        StageEntity stageEntity = new StageEntity();
        stageEntity.setId(new StageId(v.getId()));
        stageEntity.setStageName(v.getStageName());
        stageEntity.setStageDescription(v.getStageDescription());
        stageEntity.setExperimentId(new ExperimentId(v.getExperimentId()));
        stageEntity.setSortOrder(v.getSortOrder());
        stageEntity.setPriorityLevel(v.getPriorityLevel());
        stageEntity.setStageType(StageType.toEnum(v.getStageType()));
        stageEntity.setIsSkip(v.getIsSkip() == 1);
        return stageEntity;
    }

    protected StageEntity toStageEntity(HephaestusStage v, List<HephaestusStageTask> stageTasks) {
        StageEntity stageEntity = new StageEntity();
        stageEntity.setId(new StageId(v.getId()));
        stageEntity.setStageName(v.getStageName());
        stageEntity.setStageDescription(v.getStageDescription());
        stageEntity.setExperimentId(new ExperimentId(v.getExperimentId()));
        stageEntity.setSortOrder(v.getSortOrder());
        stageEntity.setPriorityLevel(v.getPriorityLevel());
        stageEntity.setStageType(StageType.toEnum(v.getStageType()));
        stageEntity.setIsSkip(v.getIsSkip() == 1);

        List<StageTaskEntity> newStageTasks = stageTasks.stream()
                .map(this::toStageTaskEntity)
                .collect(Collectors.toList());

        stageEntity.setTasks(newStageTasks);

        return stageEntity;
    }


}
