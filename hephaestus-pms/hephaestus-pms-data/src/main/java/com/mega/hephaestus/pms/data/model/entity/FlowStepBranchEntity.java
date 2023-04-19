package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * step 分支
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_flow_step_branch")
public class FlowStepBranchEntity {

    /**
     * 自增id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     *  项目id
     */
    private Long projectId;

    /**
     *  流程id
     */
    private Long processId;
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
     *  分支key
     */
    private String branchKey;
    /**
     *  板key
     */
    private String plateKey;

    /**
     * 板序号
     */
    private Integer plateNo;

    /**
     * 板索引
     * plateKey + "-"+plateNo
     */
    private String plateIndex;

    /**
     * 合板顺序
     */
    private Integer plateSortOrder;

    /**
     * 是否默认分支 0 否 1
     */
    private BooleanEnum isDefault;


    /**
     * 是否瓶颈
     */
    private BooleanEnum isBottleneck;

    /**
     * 点位 x,y
     */
    private String point;
    /**
     * 序号
     */
    private Integer sortOrder;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;


    /**
     * 是否删除 0 否 1是
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private BooleanEnum isDeleted;



    /**
     * 删除人
     */
    private String deleteBy;
    /**
     * 删除时间
     */
    private Date deleteTime;
}
