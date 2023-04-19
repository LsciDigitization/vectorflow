package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 耗材表
 *
 * @author xianming.hu
 */
@Data
@TableName("vector_labware")
public class LabwareEntity {

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
     * 耗材key
     */
    private String labwareKey;


    /**
     * 耗材类型
     */
    private String labwareType;


    /**
     * 耗材颜色
     */
    private String labwareColor;


    /**
     * 初始化托架
     */
    private Integer initialNestId;


    /**
     * 当前托架
     */
    private Integer currentNestId;


    /**
     * 耗材模板Id
     */
    private Integer labwareTemplateId;


    /**
     * 类别
     */
    private String category;


//    /**
//     *
//     */
//    private String index;


    /**
     * 条码
     */
    private String barcode;


    /**
     * 是否有盖
     */
    private Integer hasLid;


    /**
     * 是否带盖
     */
    private Integer withLid;


    /**
     * 移动速度
     */
    private Double moveSpeed;


    /**
     * 是否使用
     */
    private String isUsed;


    /**
     * 名称
     */
    private String displayName;


    /**
     * 描述
     */
    private String description;


    /**
     * 是否坏的
     */
    private String isSpoiled;


    /**
     * 是否删除
     */
    private String isCanceled;


    /**
     * 是否完成
     */
    private String isRunFinished;


    /**
     * 是否手动搬出
     */
    private String isMoveOutMannualy;


    /**
     * 是否平衡
     */
    private String isBalance;


    /**
     * 重量
     */
    private Double weight;


    /**
     * 状态
     */
    private String status;


    /**
     * 失败状态
     */
    private String errorStatus;


    /**
     *
     */
    private Integer relatedLabwareId;

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
