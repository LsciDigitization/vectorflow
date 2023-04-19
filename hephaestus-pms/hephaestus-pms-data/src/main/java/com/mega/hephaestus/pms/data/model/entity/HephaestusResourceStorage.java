package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 存储设备表
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_resource_storage")
public class HephaestusResourceStorage {

    /**
     * 数据编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 存储资源名称
     */
    private String storageName;

    /**
     * 存储资源KEY
     */
    private String storageKey;

    /**
     * 存储资源类型，同设备类型
     */
    private String storageDeviceType;

    /**
     * 设备ID
     */
    private String deviceKey;

    /**
     * 存储位状态
     */
    private String storageStatus;
    /**
     * 资源组id
     */
    private Long resourceGroupId;
    /**
     * 资源池ID
     */
    private Long poolId;


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
     * 备注
     */
    private String remarks;

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
