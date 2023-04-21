package com.mega.hephaestus.pms.agent.dashboard.domain.manager.model;

import lombok.Data;

import java.util.Date;

@Data
public class InstanceLabwareModel {
    /**
     * 主键
     */
    private Long id;


    /**
     * 实验id
     */
    private Long experimentId;


    /**
     * 实验名称
     */
    private String experimentName;

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 流程id
     */
    private Long processId;


    /**
     * process_iteration表id
     */
    private Long iterationId;


    /**
     * project_plate_type表的 plate_key字段
     */
    private String labwareType;


    /**
     * 托架id
     */
    private Long labwareNestId;


    /**
     * 流程记录id
     */
    private Long processRecordId;


    /**
     * 开始位置(设备表key，device_key)
     */
    private String startLocation;


    /**
     * 实例id
     */
    private Long instanceId;


    /**
     * 是否消费 0 未消费 1消费
     */
    private boolean isConsumed;


    /**
     * 消费时间
     */
    private Date consumeTime;


    /**
     * 是否完成 0 未完成 1已完成
     */
    private boolean isFinished;


    /**
     * 完成时间
     */
    private Date finishTime;



    /**
     * 通量号
     */
    private Integer iterationNo;


    /**
     * 条形码
     */
    private String barCode;


    /**
     * 序号
     */
    private Integer sortOrder;
}
