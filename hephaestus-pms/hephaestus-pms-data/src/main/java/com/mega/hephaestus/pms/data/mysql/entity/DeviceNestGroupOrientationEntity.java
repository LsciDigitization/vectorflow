package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 设备托架⽅向
 *
 * @author xianming.hu
 */
@Data
@TableName("vector_device_nest_group_orientation")
public class DeviceNestGroupOrientationEntity{

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 项目id
     */
    private Integer projectId;


    /**
     * 托架组id
     */
    private Integer deviceNestGroupId;


    /**
     * 处理
     */
    private Integer handlerDeviceId;


    /**
     * 设备key
     */
    private String deviceKey;


    /**
     * 抓方向
     */
    private String gripOrientation;


    /**
     * 角度
     */
    private String angle;


    /**
     *
     */
    private String cursorIndex;

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
