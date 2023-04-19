package com.mega.hephaestus.pms.data.runtime.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 实验步骤
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_instance_step_back")
@Deprecated(since = "20221202废弃")
public class HephaestusInstanceStepBack {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 实验组id
     */
    private Long groupId;

    /**
     * 实验组历史id
     */
    private Long groupHistoryId;

    /**
     * 实验id
     */
    private Long experimentId;

    /**
     * 实例id
     */
    private Long instanceId;
    /**
     * 实例状态
     */
    private int instanceStatus;
    /**
     * 实例步骤总计
     */
    private Long instanceStepNum;

    /**
     * 关键步骤名称
     */
    private String stepName;


    /**
     * 步骤类型
     */
    private String stepType;

    /**
     * 开始时间
     */
    private Date stepStartTime;

    /**
     * 结束时间
     */
    private Date stepEndTime;


    /**
     * step状态
     */
    private int stepStatus;

    /**
     * 到下一个步骤消耗时间，单位秒
     */
    private Long nextStepDurationSecond;



    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
