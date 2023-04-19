package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;


import java.util.Date;

/**
 * 耗材模板
 *
 * @author xianming.hu
 */
@Data
@TableName("vector_labware_template")
public class LabwareTemplateEntity{

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 项目ID
     */
    private Long projectId;


    /**
     * 名称
     */
    private String name;


    /**
     * 描述
     */
    private String description;


    /**
     * 耗材类型ID
     */
    private Long labwareTypeId;


    /**
     * 是否有盖 0 无 1有
     */
    private Integer hasLid;


    /**
     * 移动速度
     */
    private Double moveSpeed;


    /**
     * 是否系统内置
     */
    private Integer isSystem;


    /**
     * 模板类型
     */
    private String labwareTemplateType;


    /**
     *
     */
    private Integer processDesignId;


    /**
     *
     */
    private Integer relatedLabwareTemplateId;

    /**
     *
     */
    private String workstationGripOffsetStarSerial;

    /**
     *
     */
    private Integer transpositionId;

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
