package com.mega.component.bioflow.step;

import lombok.Data;

import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 15:42
 */
@Data
public class Flow implements ExperimentStep {

    private String id;
    private String name;
    private String description;
    private List<ExperimentStep> steps;

    @Override
    public ExperimentStepType getType() {
        return ExperimentStepType.FLOW;
    }

}
