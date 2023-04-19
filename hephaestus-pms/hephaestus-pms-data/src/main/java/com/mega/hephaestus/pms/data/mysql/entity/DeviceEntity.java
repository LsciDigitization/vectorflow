package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 设备表
 *
 * @author xianming.hu
 */
@Data
@TableName("vector_device")
public class DeviceEntity {

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
     * 设备key
     */
    private String deviceKey;

    /**
     *  设备名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;
    /**
     * 设备类型
     */
    private String deviceType;


    /**
     * 设备no
     */
    private String deviceNo;


    /**
     * 资源状态
     */
    private String resourceStatus;


    /**
     * 带板数 逗号分割
     */
    private String resourcePlateNumber;


    /**
     * 是否瓶颈资源 0 否 1是
     */
    private Boolean resourceBottleneck;


    /**
     * 资源颜色
     */
    private String resourceColor;


    /**
     * 设备状态
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
