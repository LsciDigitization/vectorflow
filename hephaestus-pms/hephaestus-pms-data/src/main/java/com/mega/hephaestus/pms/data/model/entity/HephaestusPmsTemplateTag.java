package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 模板标签关系表
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_pms_template_tag")
public class HephaestusPmsTemplateTag{

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 模板id
     */
    private Long templateId;


    /**
     * 标签id
     */
    private Long tagId;


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
