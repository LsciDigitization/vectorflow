package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 实验组关系表
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_experiment_group_pool")
@Deprecated(since = "已修改为hephaestus_process_labware")
public class HephaestusExperimentGroupPool {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 实验id
     */
    private Long experimentId;


    /**
     * 实验组id
     */
    private Long experimentGroupId;

    /**
     * 耗材存储设备类型
     */
    private String storagePoolType;

    /**
     * 耗材数量数量
     */
    private Integer storagePoolSize;



    /**
     * 设备表key
     */
    private String deviceKey;

    /**
     * 设备type
     */
    private String deviceType;

    /**
     * 序号
     */
    private Integer sortOrder;

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
     * 备注
     */
    private String remarks;

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
