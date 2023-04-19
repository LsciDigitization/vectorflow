package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
//import com.mega.hephaestus.pms.data.model.enums.ExperimentStageStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * Stage
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_experiment_stage")
public class HephaestusStage {

    /**
     * 自增id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * stage名称
     */
    private String stageName;


    /**
     * stage描述
     */
    private String stageDescription;


    /**
     * 所属实验
     */
    private Long experimentId;


    /**
     * 序号
     */
    private Integer sortOrder;

    /**
     * 优先等级 默认50
     */
    private Integer priorityLevel;


    /**
     *  stage类型  开始、执行、等待、判断、结束
     */
    private String stageType;
    /**
     * 是否允许跳过 默认 0 否
     */
    private Integer isSkip;

    /**
     * 耗材到节点类型
     * 途径、
     * 开始(从旋转板栈取)、
     * 动态获取(工作站发起 枪头)、
     * 预置(类似于 工作站内提前放置板子)
     */
    private String labwareArriveType;

    /**
     * 策略
     * 1、满足其一即可
     * 2、全部需要满足
     */
    private String strategy;

    /**
     * step key
     */
    private String stepKey;

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
     * 备注
     */
    private String remarks;

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
