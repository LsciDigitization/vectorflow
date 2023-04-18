package com.mega.component.bioflow.step;

import lombok.Data;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 15:45
 */
@Data
public class LoopStep implements ExperimentStep, ExperimentStepExecute {

    private String id;
    private String name;
    private String description;
    private ExperimentStepExecute loopStep;
    private int loopCount;

    @Override
    public ExperimentStepType getType() {
        return ExperimentStepType.LOOP;
    }

    @Override
    public void execute() {
        for (int i = 0; i < loopCount; i++) {
            loopStep.execute();
        }
    }

}
