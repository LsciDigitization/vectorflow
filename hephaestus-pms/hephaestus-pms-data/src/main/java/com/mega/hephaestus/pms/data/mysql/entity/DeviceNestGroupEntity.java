package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 设备托架组
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_device_nest_group")
public class DeviceNestGroupEntity{

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
     * 设备key
     */
    private String deviceKey;
    /**
     * 名称
     */
    private String name;


    /**
     * 描述
     */
    private String description;


    /**
     * 功能允许
     */
    private String labwareHandlerAccessible;


    /**
     * 类型
     */
    private String type;


    /**
     * 布局X
     */
    private String numberX;


    /**
     * 布局Y
     */
    private String numberY;


    /**
     * 布局Z
     */
    private String numberZ;


    /**
     * 序号
     */
    private Integer seqNo;


    /**
     * 需要盖子状态 0 开盖 1 关盖
     */
    private Integer requireLidStatus;


    /**
     * 盖子位置值
     */
    private String lidPositionValue;


    /**
     * 窄边夹持偏移最小值
     */
    private String gripOffsetNMin;


    /**
     * 窄边夹持偏移最大值
     */
    private String gripOffsetNMax;


    /**
     * 宽边夹持偏移最小值
     */
    private String gripOffsetWMin;


    /**
     * 宽边夹持偏移最大值
     */
    private String gripOffsetWMax;


    /**
     * 塔序号
     */
    private Integer towerNo;


    /**
     * 格数
     */
    private Integer gridNo;


    /**
     * 是否能删除
     */
    private Integer canDelete;


    /**
     * 盖子位置id
     */
    private Integer lidPositionId;


    /**
     * 允许盖上或取下盖子
     */
    private Integer allowCoverRemoveLid;


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
