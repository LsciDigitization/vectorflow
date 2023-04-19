package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 孔位信息表实体类
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/3 21:29
 */
@Data
@TableName("hephaestus_labware_well")
public class LabwareWellEntity {

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
     * 所属板子ID
     */
    private Long plateId;

    /**
     * 孔位类型（0：普通孔，1：抢头孔）
     */
    private String wellType;

    /**
     * 孔位编号
     */
    private String wellKey;

    /**
     * 行索引
     */
    private Integer rowIndex;

    /**
     * 列索引
     */
    private Integer columnIndex;

    /**
     * 样品ID，外键关联Sample表，可为NULL，表示该孔位未放置样品
     */
    private Long sampleId;

    /**
     * 液体ID，外键关联Liquid表，可为NULL，表示该孔位未加液体
     */
    private Long liquidId;

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
