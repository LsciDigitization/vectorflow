package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * step
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_flow_step_condition")
public class FlowStepConditionEntity {

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
     *  实验id
     */
    private Long experimentId;

    /**
     * 步骤key
     */
    private String stepKey;


    /**
     *  耗材类型
     */
    private String labwareType;


    /**
     *  耗材数
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
