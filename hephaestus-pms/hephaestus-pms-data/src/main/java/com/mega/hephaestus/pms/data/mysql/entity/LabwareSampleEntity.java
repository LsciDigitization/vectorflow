package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 样品表
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/3 21:27
 */
@Data
@TableName("hephaestus_labware_sample")
public class LabwareSampleEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 样品名称
     */
    private String name;

    /**
     * 样品描述
     */
    private String description;

    /**
     * 样品类型
     */
    private String type;


    /**
     * 孔位总容积
     */
    private Float wellVolume;


    /**
     * 孔位当前总液体容积
     */
    private Float wellTotalVolume;


    /**
     * 样品浓度
     */
    private Float concentration;

    /**
     *附加数据
     */
    private String additionalData;

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
