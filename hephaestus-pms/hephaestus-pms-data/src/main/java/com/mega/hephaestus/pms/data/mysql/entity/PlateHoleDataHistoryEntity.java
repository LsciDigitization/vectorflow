package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;


/**
 * 孔位数据
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_plate_hole_data_history")
public class PlateHoleDataHistoryEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     *  holeDataId
     */
    private Long holeDataId;

    /**
     * 项目ID
     */
    private Long projectId;


    /**
     * 板类型
     */
    private String plateType;


    /**
     * 板id
     */
    private Long instancePlateId;


    /**
     * 容量
     */
    private Integer capacity;


    /**
     * 浓度
     */
    private Integer concentration;


    /**
     * 数据
     */
    private String data;


    /**
     * 数据类型
     */
    private String dataType;


    /**
     * 版本
     */
    private Integer version;


    /**
     * 孔key，规则为 行_列
     */
    private String holeKey;

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



}
