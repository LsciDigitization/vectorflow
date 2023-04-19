package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;


import java.util.Date;

/**
 * 设备命令参数
 *
 * @author xianming.hu
 */
@Data
@TableName("vector_device_command_parameter")
public class DeviceCommandParameterEntity {

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
     * 参数名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 命令id
     */
    private Long deviceCommandId;


    /**
     * 参数key
     */
    private String parameterKey;


    /**
     * 参数值
     */
    private String parameterValue;


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
