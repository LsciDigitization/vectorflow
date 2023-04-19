package com.mega.hephaestus.pms.data.runtime.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.runtime.enums.ExperimentInstanceStatusEnum;
import com.mega.hephaestus.pms.data.runtime.enums.ExperimentStageStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 *
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_instance")
public class InstanceEntity {

    /**
     * 自增id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 实验实例ID
     */
    private String workflowRunId;

    /**
     * 实验起始位置ID
     */
    private Long startStorageId ;

    /**
     * 实验结束位置ID
     */
    private Long endStorageId ;

    /**
     * 实验当前位置ID
     */
    private Long currentStorageId ;

    /**
     * 实验实例ID
     */
    private Long experimentId ;


    /**
     * 实验名称
     */
    private String instanceTitle;


    /**
     * 实验状态
     */
    private ExperimentInstanceStatusEnum instanceStatus;


    /**
     * 实验参数
     */
    private String instanceContext;

    /**
     * step
     */
    private String currentStep;

    /**
     * step总计
     */
    private Long currentStepTotal;


    /**
     * 所在节点ID
     */
    private Long activeStageId;


    /**
     * 所在节点状态
     */
    private ExperimentStageStatusEnum activeStageStatus;

    /**
     * 开始时间
     */
    private Date instanceStartTime;

    /**
     * 结束时间
     */
    private Date instanceEndTime;


    /**
     * 实验组id
     */
    private Long processId;
    /**
     * 流程Id
     */
    private Long processRecordId;

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
