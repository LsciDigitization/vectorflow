package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 硬件配置表
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_inventory_location")
public class InventoryLocationEntity {

    /**
     * 数据编号
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
     * 父id 默认0
     */
    private Long parentId;

    /**
     * 父集合 逗号分隔
     */
    private String parentIds;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 位置类型
     *
     * default
     * 设备级(冰箱)
     * 耗材级别(板)
     * 实验室
     * 车间
     */
    private String locationType;


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
