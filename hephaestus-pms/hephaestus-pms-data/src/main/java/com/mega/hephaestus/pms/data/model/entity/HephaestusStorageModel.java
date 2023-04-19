package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.enums.DeviceTypeEnum;
import com.mega.hephaestus.pms.data.model.enums.StorageModelStatusEnum;
import com.mega.hephaestus.pms.data.model.enums.StorageTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * 存放模型表
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_storage_model")
@Deprecated(since = "20221115")
public class HephaestusStorageModel {

    /**
     * 数据编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 板型 1：384孔板 2：96孔板
     */
    @TableField(exist = false)
    private StorageTypeEnum storageType;

    /**
     * 设备ID
     */
    private Integer deviceId;



    /**
     * 板位组ID
     */
    private String nestGroup;


    /**
     * 板位ID
     */
    private String nest;


    /**
     * 方向：1Narrow 2ReverseNarrow
     */
    private Integer gripOrientation;




    /**
     * 存储状态：1空闲 2占用 0未知
     */
    @TableField(exist = false)
    private StorageModelStatusEnum storageStatus;


    /**
     * 工作流实例ID
     */
    @TableField(exist = false)
    private String instanceId;



    /**
     * 硬件类型：0 Unknown 1 Incubator 2 Carousel
     */
    @TableField(exist = false)
    private DeviceTypeEnum deviceType;


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
