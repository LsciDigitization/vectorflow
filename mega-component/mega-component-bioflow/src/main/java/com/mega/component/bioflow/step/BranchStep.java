package com.mega.component.bioflow.step;

import lombok.Data;

import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 15:40
 */
@Data
public class BranchStep implements ExperimentStep, ExperimentStepExecute {

    private String id;
    private String name;
    private String description;
    private Condition condition;
    private List<ExperimentStepExecute> stepsWhenConditionMet;
    private List<ExperimentStepExecute> stepsWhenConditionNotMet;

    @Override
    public void execute() {
        if (condition.evaluate()) {
            for (ExperimentStepExecute step : stepsWhenConditionMet) {
                step.execute();
            }
        } else {
            for (ExperimentStepExecute step : stepsWhenConditionNotMet) {
                step.execute();
            }
        }
    }

    @Override
    public ExperimentStepType getType() {
        return null;
    }

}
