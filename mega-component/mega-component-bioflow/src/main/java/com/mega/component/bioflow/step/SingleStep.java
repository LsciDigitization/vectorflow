package com.mega.component.bioflow.step;

import lombok.Data;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 16:39
 */
@Data
public class SingleStep implements ExperimentStep, ExperimentStepExecute {

    private String id;
    private String name;
    private String description;
    private ExperimentStep nextStep; // 下一个执行的实验步骤

    @Override
    public void execute() {

    }

    @Override
    public ExperimentStepType getType() {
        return ExperimentStepType.SINGLE;
    }

}
