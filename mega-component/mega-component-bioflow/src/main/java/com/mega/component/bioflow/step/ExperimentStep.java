package com.mega.component.bioflow.step;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 15:39
 */
public interface ExperimentStep {

    String getId();

    String getName();

    String getDescription();

    ExperimentStepType getType();

}
