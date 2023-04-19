package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 实验资源
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_resource")
public class ResourceEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 实验组id
     */
    private Long projectId;
    /**
     * deviceKey
     */
    private String deviceKey;

    /**
     * 设备类型
     */
    private String deviceType;

    /***
     * 资源状态
     *  idle:空闲,running:运行
     */
    private String resourceStatus;

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


    private String resourceColor;
}
