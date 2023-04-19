package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/3 21:15
 */
/**
 * 移液头表
 */
@Data
@TableName("hephaestus_labware_pipette")
public class LabwarePipetteEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 移液头名称
     */
    private String name;

    /**
     * 移液头描述
     */
    private String description;

    /**
     * 最小容积
     */
    private Float minVolume;

    /**
     * 最大容积
     */
    private Float maxVolume;


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
