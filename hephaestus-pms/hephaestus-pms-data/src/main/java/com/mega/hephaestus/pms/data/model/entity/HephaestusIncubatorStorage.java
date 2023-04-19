package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.enums.StorageModelStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * Co2培育箱存放模型表
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_incubator_storage")
@Deprecated(since = "20221115")
public class HephaestusIncubatorStorage{

    /**
     * 数据编号
     */
    private Long id;


    /**
     * 保温箱位置Key
     */
    private Integer plateKey;


    /**
     * 存储状态：1空闲 2占用 0未知
     */
    private StorageModelStatusEnum storageStatus;


    /**
     * 实验实例ID
     */
    private Long instanceId;


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
