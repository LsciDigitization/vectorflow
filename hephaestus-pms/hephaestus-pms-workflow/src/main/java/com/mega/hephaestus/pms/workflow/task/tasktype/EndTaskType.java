package com.mega.hephaestus.pms.workflow.task.tasktype;

import com.mega.component.bioflow.task.StageType;
import com.mega.component.workflow.models.TaskType;
import com.mega.hephaestus.pms.workflow.task.stageflow.AbstractStageFlowExecutor;

/**
 * end任务类型
 */
public class EndTaskType implements TaskTypeRegister {

    private AbstractStageFlowExecutor stageFlowExecutor;

    @Override
    public StageType type() {
        return StageType.End;
    }

    public TaskType getTaskType() {
        return new TaskType(StageType.End.value, "1", true);
    }

    @Override
    public AbstractStageFlowExecutor getTaskExecutor() {
        return stageFlowExecutor;
    }

    @Override
    public void setTaskExecutor(AbstractStageFlowExecutor taskExecutor) {
        this.stageFlowExecutor = taskExecutor;
    }

}
