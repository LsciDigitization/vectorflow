package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 设备托架
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_device_nest")
public class DeviceNestEntity{

    /**
     * 主键
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
     * 托架组ID
     */
    private Long deviceNestGroupId;


    /**
     * 位置
     */
    private String position;


    /**
     * 索引
     */
    private String nestIndex;


    /**
     * 状态
     */
    private String status;


    /**
     * 耗材组，逗号分割
     */
    private String labwares;


    /**
     * 耗材类型id
     */
    private Long labwareTypeId;


    /**
     * 设备key
     */
    private String deviceKey;



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
