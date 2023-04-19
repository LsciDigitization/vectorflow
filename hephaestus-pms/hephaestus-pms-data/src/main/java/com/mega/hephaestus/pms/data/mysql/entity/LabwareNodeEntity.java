package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;


import java.util.Date;

/**
 * 耗材节点
 *
 * @author xianming.hu
 */
@Data
@TableName("vector_labware_node")
public class LabwareNodeEntity{

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 项目ID
     */
    private Long projectId;


    /**
     * 节点ID
     */
    private Long nodeId;


    /**
     *
     */
    private Integer labwareTemplateId;


    /**
     *
     */
    private String piecesforEachBatch;


    /**
     *
     */
    private String locationType;


    /**
     *
     */
    private String labwareFilter;


    /**
     *
     */
    private String showLabwareFilter;


    /**
     *
     */
    private Date maximumWaitingTime;


    /**
     *
     */
    private Date spoiledTime;


    /**
     *
     */
    private String displayLabwareTemplateName;


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
