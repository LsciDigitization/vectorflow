package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 设备驱动表
 *
 * @author xianming.hu
 */
@Data
@TableName("vector_device_driver")
public class DeviceDriverEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 驱动名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 驱动code
     */
    private String driverCode;


    /**
     * 版本
     */
    private String version;





    /**
     * 固件版本
     */
    private String hardwareVersion;


    /**
     * nuc版本
     */
    private String nucVersion;


    /**
     * 厂商
     */
    private String company;


    /**
     * 驱动
     */
    private String driver;


    /**
     * 创建人
     */
    private String createBy;


    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 修改人
     */
    private String updateBy;


    /**
     * 修改时间
     */
    private Date updateTime;



    /**
     * 是否 删除 0 否 1是
     */
    private Integer isDeleted;


    /**
     * 删除人
     */
    private String deleteBy;


    /**
     * 删除时间
     */
    private Date deleteTime;

}
