package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/3 21:11
 */
@Data
@TableName("hephaestus_labware_transfer")
public class LabwareTransferEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 记录ID
     */
    private Long processRecordId;
    /**
     * 移液计划ID
     */
    private Long planId;

    /**
     * 起始板ID
     */
    private Long sourcePlate;

    /**
     * 起始孔 ID
     */
    private Long sourceWell;

    /**
     * 目标板ID
     */
    private Long destinationPlate;

    /**
     * 目标孔 ID
     */
    private Long destinationWell;

    /**
     * 液体ID 当transfer_type为sample时 此id 为-1
     */
    private Long liquidId;


    /**
     * 起始样本id  transfer_type为liquid时 此id 为-1
     */
    private Long sourceSampleId;


    /**
     * 目标样本id
     */
    private Long destinationSampleId;

    /**
     * 移液类型 liquid, sample
     */
    private String transferType;
    /**
     * 移液头ID
     */
    private Long pipetteId;

    /**
     * 移液体积
     */
    private Float volume;

    /**
     * 移液时间
     */
    private Date transferTime;


    /**
     *三种移液方式：PARTIAL（部分）、WHOLE（全部）、MIX（混合）
     *
     * 有数据，使用mix 混合转移
     * 无数据，使用partial转移，且保留两个孔位一样的sample_id
     * 无数据，使用whole转移，且源孔的sample_id清空，目标孔的sample_id由源孔的转移过来
     */
    private String sampleTransferMethod;


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
