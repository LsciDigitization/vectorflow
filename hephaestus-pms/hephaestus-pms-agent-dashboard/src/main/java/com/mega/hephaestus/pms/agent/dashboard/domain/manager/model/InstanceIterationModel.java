package com.mega.hephaestus.pms.agent.dashboard.domain.manager.model;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

@Data
public class InstanceIterationModel {

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
     * 实例id
     */
    private Long instanceId;

    /**
     *  流程记录id
     */
    private Long processRecordId;


    /**
     * 是否消费 0 否 1 是
     */
    private BooleanEnum isConsumed;

    /**
     * 消费时间
     */
    private Date consumeTime;

    /**
     * 是否完成 0 否 1 是
     */
    private BooleanEnum isFinished;

    /**
     * 完成时间
     */
    private Date finishTime;
}
