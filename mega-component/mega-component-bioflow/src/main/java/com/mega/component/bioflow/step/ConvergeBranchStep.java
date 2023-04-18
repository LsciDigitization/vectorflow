package com.mega.component.bioflow.step;

import lombok.Data;

import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 16:40
 */
@Data
public class ConvergeBranchStep implements ExperimentStep, ExperimentStepExecute {

    private String id;
    private String name;
    private String description;
    private List<ExperimentStep> inputSteps; // 汇聚前的实验步骤
    private ExperimentStep nextStep; // 汇聚后执行的实验步骤

    @Override
    public void execute() {

    }

    @Override
    public ExperimentStepType getType() {
        return ExperimentStepType.CONVERGE;
    }

}
