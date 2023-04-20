package com.mega.hephaestus.pms.workflow.manager.model;

import lombok.Data;

import java.util.Date;

@Data
public class ProcessIterationModel {

    /**
     * 主键
     */
    private Long id;


    /**
     * 项目id
     */
    private Long projectId;


    /**
     * 流程id
     */
    private Long processId;


    /**
     * 通量编号
     */
    private Integer iterationNo;


    /**
     *  消费表状态
     */
    private Long consumeId;

    /**
     * 实例耗材ID
     */
    private Long instanceLabwareId;

    /**
     * 实例id
     */
    private Long instanceId;

    /**
     *  流程记录id
     */
    private Long processRecordId;


    /**
     * 是否消费
     */
    private boolean isConsumed;

    /**
     * 消费时间
     */
    private Date consumeTime;

    /**
     * 是否完成
     */
    private boolean isFinished;

    /**
     * 完成时间
     */
    private Date finishTime;
}
