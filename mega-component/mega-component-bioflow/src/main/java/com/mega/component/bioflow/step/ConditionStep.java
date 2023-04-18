package com.mega.component.bioflow.step;

import lombok.Data;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 16:43
 */
@Data
public class ConditionStep implements ExperimentStep, ExperimentStepExecute {

    private String id;
    private String name;
    private String description;
    private Condition condition;
    private ExperimentStepExecute nextStep;

    @Override
    public void execute() {
        if (condition.evaluate()) {
            nextStep.execute();
        }
    }

    @Override
    public ExperimentStepType getType() {
        return ExperimentStepType.CONDITION;
    }

}
