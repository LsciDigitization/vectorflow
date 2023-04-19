package com.mega.hephaestus.pms.data.runtime.view;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.runtime.enums.ExperimentInstanceStatusEnum;
import com.mega.hephaestus.pms.data.runtime.enums.ExperimentStageStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author xianming.hu
 */
@Data
@ApiModel("instance")
@TableName("实例实验数据")
public class HephaestusInstanceExperimentView {

    /**
     * 自增id
     */
    @ApiModelProperty("自增id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 实验实例ID
     */
    @ApiModelProperty("实验实例ID")
    private String workflowRunId;

    /**
     * 实验起始位置ID
     */
    @ApiModelProperty("实验起始位置ID")
    private Long startStorageId ;

    /**
     * 实验结束位置ID
     */
    @ApiModelProperty("实验结束位置ID")
    private Long endStorageId ;

    /**
     * 实验当前位置ID
     */
    @ApiModelProperty("实验当前位置ID")
    private Long currentStorageId ;

    /**
     * 实验实例ID
     */
    @ApiModelProperty("并联实验ID")
    private Long experimentId ;


    /**
     * 实验名称
     */
    @ApiModelProperty("实验名称")
    private String instanceTitle;


    /**
     * 实验状态 
     */
    @ApiModelProperty("实验状态 ")
    private ExperimentInstanceStatusEnum instanceStatus;


    /**
     * 实验参数
     */
    @ApiModelProperty("实验参数")
//    @TableField(value ="instance_context" ,typeHandler = InstanceContextJsonTypeHandler.class)
    private String instanceContext;


    /**
     * 所在节点ID
     */
    @ApiModelProperty("所在节点ID")
    private Long activeStageId;


    /**
     * 所在节点状态
     */
    @ApiModelProperty("所在节点状态")
    private ExperimentStageStatusEnum activeStageStatus;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date instanceStartTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date instanceEndTime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;


    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)

    private Date createTime;


    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;


    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remarks;

    /**
     * 是否删除 0 否 1是
     */
    @ApiModelProperty("是否删除 0 否 1是")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private BooleanEnum isDeleted;

    /**
     * 删除人
     */
    @ApiModelProperty("删除人")
    private String deleteBy;
    /**
     * 删除时间
     */
    @ApiModelProperty("删除时间")
    private Date deleteTime;


    /**
     * 实验名称
     */
    @ApiModelProperty("实验名称")
    private String experimentName;


    /**
     * 实验描述
     */
    @ApiModelProperty("实验描述")
    private String experimentDescription;

    /**
     * 优先等级 默认50
     */
    @ApiModelProperty("优先等级")
    private Integer priorityLevel;

}
