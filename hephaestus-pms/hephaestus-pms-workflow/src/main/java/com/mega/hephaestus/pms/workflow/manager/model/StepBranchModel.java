package com.mega.hephaestus.pms.workflow.manager.model;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.List;

/**
 * 步骤分支model
 */
@Data
public class StepBranchModel {

    private Long id;

    /**
     * 步骤名称
     */
    private String name;


    /**
     *  步骤key
     */
    private String stepKey;


    /**
     *  分支key
     */
    private String branchKey;

    /**
     * 是否默认分支 0 否 1
     */
    private boolean isDefault;


    /**
     * 是否瓶颈
     */
    private boolean isBottleneck;

    /**
     * 点位 x,y
     */
    private String point;
    /**
     * 序号
     */
    private Integer sortOrder;

    private List<String> plateKeys;
}
