package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;


import java.util.Date;

/**
 * 耗材模板属性
 *
 * @author xianming.hu
 */
@Data
@TableName("vector_labware_template_property")
public class LabwareTemplatePropertyEntity {

    /**
     * 主键ID
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
     * 耗材模板ID
     */
    private Long labwareTemplateId;


    /**
     * 类型
     */
    private String type;


    /**
     * 初始值
     */
    private String initialValue;


    /**
     * 是否加载过
     */
    private Integer isLoaded;

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
