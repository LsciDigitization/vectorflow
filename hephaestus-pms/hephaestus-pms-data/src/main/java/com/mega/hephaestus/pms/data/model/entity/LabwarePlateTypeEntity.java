package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import java.util.Date;
import lombok.Data;

/**
 * 实验资源组
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_labware_plate_type")
public class LabwarePlateTypeEntity {

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
     * 板key
     */
    private String plateKey;

    /**
     *  类型: 普通板 抢头板
     */
    private String plateType;

    /**
     * 分类（如96孔板、384孔板等）
     */
    private String plateCategory;
    /**
     * 板名称
     */
    private String plateName;


    /**
     * 行数
     */
    private Integer rows;

    /**
     * 列数
     */
    private Integer cols;

    /**
     * 长度
     */
    private Integer length;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 厚度
     */
    private Integer thickness;

    private String plateColor;
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
