package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 设备类型表
 *
 * @author xianming.hu
 */
@Data
@TableName("vector_device_type")
public class DeviceTypeEntity {

    /**
     * 主键
     */
    private Long id;


    /**
     * 项目id
     */
    private Long projectId;


    /**
     * 类型名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 设备类型(离心机、机械臂)
     */
    private String deviceType;


    /**
     * 设备模型(存储类、操作类)
     */
    private String deviceModel;


    /**
     * 驱动code
     */
    private String driverCode;


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
