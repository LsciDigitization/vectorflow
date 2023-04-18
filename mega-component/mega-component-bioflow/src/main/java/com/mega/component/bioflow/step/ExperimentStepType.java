package com.mega.component.bioflow.step;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 15:44
 */
public enum ExperimentStepType {
    LOOP, // 循环步骤
    BRANCH, // 分支步骤
    CONDITION, // 条件步骤
    MANUAL, // 人工步骤
    SINGLE, // 单线步骤
    CONVERGE, // 聚合步骤
    START, // 开始步骤
    END, // 结束步骤
    FLOW, // 流程步骤
    STEP, // 通用步骤
}
