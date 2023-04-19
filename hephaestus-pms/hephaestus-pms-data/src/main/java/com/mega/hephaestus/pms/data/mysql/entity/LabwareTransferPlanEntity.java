package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/3 21:12
 */
@Data
@TableName("hephaestus_labware_transfer_plan")
public class LabwareTransferPlanEntity {

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
     * 移液计划名称
     */
    private String name;

    /**
     * 移液计划描述
     */
    private String description;


    /**
     * 起始板类型
     * 对应plateType表的key
     */
    private String sourcePlateType;


    /**
     * 目标板类型
     * 对应plateType表的key
     */
    private String destinationPlateType;

    /**
     * 步骤key
     */
    private String stepKey;


    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     *三种移液方式：PARTIAL（部分）、WHOLE（全部）、MIX（混合）
     *
     * 有数据，使用mix 混合转移
     * 无数据，使用partial转移，且保留两个孔位一样的sample_id
     * 无数据，使用whole转移，且源孔的sample_id清空，目标孔的sample_id由源孔的转移过来
     */
    private String sampleTransferMethod;

    /**
     * 移液类型 liquid, sample
     */
    private String transferType;

    /**
     * 移液头ID
     */
    private Long pipetteId;

    /**
     * 枪头数量
     */
    private Integer pipetteCount;

    /**
     * 液体ID
     */
    private Long liquidId;

    /**
     * 移液体积
     */
    private Float volume;
    /**
     *  孔范围
     *   A string of well ranges, e.g. "A1, B1:B3, C1:C3,D1"
      */
    private String wellRange;

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
