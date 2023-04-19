package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 板子信息表实体类
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/3 21:30
 */
@Data
@TableName("plate")
public class PlateEntity {

    /**
     * Plate ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * Plate 分类（如96孔板、384孔板等）
     */
    private String plateCategory;

    /**
     * 板名称
     */
    private String name;

    /**
     * 板key
     */
    private String plateKey;

    /**
     * 板子类型（0：普通板，1：抢头板）
     */
    private Integer plateType;

    /**
     * 行数
     */
    private Integer rows;

    /**
     * 列数
     */
    private Integer columns;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 创建者ID
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updatedAt;

    /**
     * 每孔体积
     */
    private Float wellVolume;

    /**
     * 每孔总容积
     */
    private Float wellTotalVolume;

}
