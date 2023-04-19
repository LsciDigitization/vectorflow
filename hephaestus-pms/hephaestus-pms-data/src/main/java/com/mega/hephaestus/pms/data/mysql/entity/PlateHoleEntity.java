package com.mega.hephaestus.pms.data.mysql.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;


/**
 * 孔位表
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_plate_hole")
public class PlateHoleEntity {

    /**
     * 主键
     */
    private Long id;


    /**
     * 项目id
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
     * 第几行
     */
    private Integer numberOfRows;


    /**
     * 位数和
     */
    private Long numberOfTotals;


    /**
     * 第1列(hole_data表Id)
     */
    private Long holeColumn1;


    /**
     * 第2列(hole_data表Id)
     */
    private Long holeColumn2;


    /**
     * 第3列(hole_data表Id)
     */
    private Long holeColumn3;


    /**
     * 第4列(hole_data表Id)
     */
    private Long holeColumn4;


    /**
     * 第5列(hole_data表Id)
     */
    private Long holeColumn5;


    /**
     * 第6列(hole_data表Id)
     */
    private Long holeColumn6;


    /**
     * 第7列(hole_data表Id)
     */
    private Long holeColumn7;


    /**
     * 第8列(hole_data表Id)
     */
    private Long holeColumn8;


    /**
     * 第9列(hole_data表Id)
     */
    private Long holeColumn9;


    /**
     * 第10列(hole_data表Id)
     */
    private Long holeColumn10;


    /**
     * 第11列(hole_data表Id)
     */
    private Long holeColumn11;


    /**
     * 第12列(hole_data表Id)
     */
    private Long holeColumn12;


    /**
     * 第13列(hole_data表Id)
     */
    private Long holeColumn13;


    /**
     * 第14列(hole_data表Id)
     */
    private Long holeColumn14;


    /**
     * 第15列(hole_data表Id)
     */
    private Long holeColumn15;


    /**
     * 第16列(hole_data表Id)
     */
    private Long holeColumn16;


    /**
     * 第17列(hole_data表Id)
     */
    private Long holeColumn17;


    /**
     * 第18列(hole_data表Id)
     */
    private Long holeColumn18;


    /**
     * 第19列(hole_data表Id)
     */
    private Long holeColumn19;


    /**
     * 第20列(hole_data表Id)
     */
    private Long holeColumn20;


    /**
     * 第21列(hole_data表Id)
     */
    private Long holeColumn21;


    /**
     * 第22列(hole_data表Id)
     */
    private Long holeColumn22;


    /**
     * 第23列(hole_data表Id)
     */
    private Long holeColumn23;


    /**
     * 第24列(hole_data表Id)
     */
    private Long holeColumn24;

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
