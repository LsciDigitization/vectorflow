package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 实例板池表
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_labware_plate")
public class LabwarePlateEntity {

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
     * project_plate_type表的 plate_key字段
     */
    private String plateType;


    /**
     * 托架id
     */
    private Long labwareNestId;


    /**
     *  起始位置
     */
    private String startLocation;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    /**
     * 条形码
     */
    private String barCode;

    /**
     * 序号
     */
    private Integer sortOrder;
}
