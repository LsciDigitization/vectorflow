package com.mega.hephaestus.pms.workflow.manager.model;

import com.mega.component.bioflow.task.ExperimentId;
import com.mega.component.bioflow.task.ProjectId;
import lombok.Data;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/30 16:46
 */
@Data
public class StepConditionModel {
    private Long id;

    /**
     * 项目id
     */
    private ProjectId projectId;

    /**
     * 实验id
     */
    private ExperimentId experimentId;

    /**
     * 步骤key
     */
    private String stepKey;


    /**
     * 耗材类型
     */
    private String labwareType;


    /**
     * 耗材数
     */
    private Integer labwareCount;


    /**
     * 分支key
     */
    private String branchKey;

    /**
     * 耗材到节点类型
     * 途径、
     * 开始(从旋转板栈取)、
     * 动态获取(工作站发起 枪头)、
     * 预置(类似于 工作站内提前放置板子)
     */
    private String labwareArriveType;
}
