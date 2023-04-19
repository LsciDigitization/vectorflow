package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 设备托架允许耗材类型
 *
 * @author xianming.hu
 */
@Data
@TableName("vector_device_nest_group_allowed_labware_type")
public class DeviceNestGroupAllowedLabwareTypeEntity{

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 项目id
     */
    private Long projectId;


    /**
     * 设备托架组id
     */
    private Long deviceNestGroupId;


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
