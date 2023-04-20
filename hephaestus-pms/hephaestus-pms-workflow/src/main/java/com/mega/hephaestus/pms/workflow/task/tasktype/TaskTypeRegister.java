package com.mega.hephaestus.pms.workflow.task.tasktype;

import com.mega.component.bioflow.task.StageType;
import com.mega.component.workflow.models.TaskType;
import com.mega.hephaestus.pms.workflow.task.stageflow.AbstractStageFlowExecutor;

public interface TaskTypeRegister {

    StageType type();

    TaskType getTaskType();

    AbstractStageFlowExecutor getTaskExecutor();

    void setTaskExecutor(AbstractStageFlowExecutor taskExecutor);

}
