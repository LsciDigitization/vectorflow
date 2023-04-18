package com.mega.component.bioflow.step;

import lombok.Data;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 16:44
 */
@Data
public class ManualStep implements ExperimentStep, ExperimentStepExecute {

    private String id;
    private String name;
    private String description;
    private String manualInstructions; // 人工操作的指南或说明

    @Override
    public void execute() {
        // 这里不会有任何自动执行的操作，因为它需要人工干预
        // 您可能需要在这里添加用于跟踪和记录人工操作的逻辑
    }

    @Override
    public ExperimentStepType getType() {
        return ExperimentStepType.MANUAL;
    }

}
