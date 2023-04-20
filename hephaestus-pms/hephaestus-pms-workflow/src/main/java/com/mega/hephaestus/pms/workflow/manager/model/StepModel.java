package com.mega.hephaestus.pms.workflow.manager.model;

import com.mega.component.bioflow.task.ProjectId;
import lombok.Data;

import java.util.List;

@Data
public class StepModel {


    private Long id;


    /**
     * 步骤名称
     */
    private String name;


    /**
     *  描述
     */
    private String description;


    /**
     *  步骤key
     */
    private String stepKey;


    /**
     *  项目id
     */
    private ProjectId projectId;

    /**
     * 是否瓶颈资源
     */
    private boolean resourceBottleneck;


    /**
     * 序号
     */
    private Integer sortOrder;


    private List<StepConditionModel> conditions;

    private List<StepBranchModel> branchs;
}
