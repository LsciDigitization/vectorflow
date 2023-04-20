package com.mega.hephaestus.pms.workflow.task.tasktype;

import com.mega.component.bioflow.task.StageType;
import com.mega.component.workflow.models.TaskType;
import com.mega.hephaestus.pms.workflow.task.stageflow.AbstractStageFlowExecutor;

/**
 * stage任务类型
 */
public class StageTaskType implements TaskTypeRegister {

    private AbstractStageFlowExecutor stageFlowExecutor;

    @Override
    public StageType type() {
        return StageType.Stage;
    }

    public TaskType getTaskType() {
        return new TaskType(StageType.Stage.value, "1", true);
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
