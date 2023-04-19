package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 流程耗材关系
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_process_labware")
public class ProcessLabwareEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 实验ID
     */
    private Long experimentId;


    /**
     * 流程id
     */
    private Long processId;


    /**
     * project_plate_type表的 plate_key字段
     */
    private String labwareType;


    /**
     *  是否主入口
     */
    private BooleanEnum IsMain;

    /**
     * 序号
     */
    private Integer sortOrder;
    /**
     * 开始位置(设备表key，device_key)
     */
    private String startLocation;


    /**
     * 结束位置(设备表key，device_key)
     */
    private String endLocation;


    /**
     * 失败位置(设备表key，device_key)
     */
    private String errorToLocation;


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
