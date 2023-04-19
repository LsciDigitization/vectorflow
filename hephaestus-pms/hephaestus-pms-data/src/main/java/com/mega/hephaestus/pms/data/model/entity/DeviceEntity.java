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
@TableName("hephaestus_device")
public class DeviceEntity {

    /**
     * 数据编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * deviceKey
     */
    private String deviceKey;
    /**
     * 设备NUC ID
     */
    private String deviceId;


    /**
     * 设备名称
     */
    private String deviceName;


    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 模型类型
     */
    private String deviceModel;

    /**
     * 子型号
     */
    private String deviceTypeFamily;


    /**
     * 硬件状态：1离线 2就绪 3繁忙 4挂起 5错误 6未知
     */
    private String deviceStatus;


    /**
     * 接口地址
     */
    private String serverAddress;

    /**
     * 接口代理地址
     */
    private String serverProxyAddress;



    /**
     * 回调地址
     */
    private String callbackAddress;


    /**
     * 描述
     */
    private String description;

    /***
     *  DISABLE("disable", "禁用"),
     *     NORMAL("normal", "正常"),
     *     LOCK("lock", "锁定"),
     */
    private String status;

    /***
     * 资源状态
     *  idle:空闲,running:运行
     */
    private String resourceStatus;
    /**
     * 备注
     */
    private String remarks;


    /**
     *  资源带板数 逗号分割:
     *  <p>
     *  2,4,8
     *  </p>
     */
    private String resourcePlateNumber;


    // 是否资源瓶颈，默认都是false
    private Boolean resourceBottleneck;


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


    /**
     * 甘特图颜色
     */
    private String ganttColor;


    /**
     * 项目ID
     */
    private Long projectId;
}
