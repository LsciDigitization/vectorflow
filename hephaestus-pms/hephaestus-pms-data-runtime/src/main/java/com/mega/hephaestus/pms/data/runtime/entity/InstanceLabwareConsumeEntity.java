package com.mega.hephaestus.pms.data.runtime.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

@Data
@TableName("hephaestus_instance_labware_consume")
public class InstanceLabwareConsumeEntity {
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
     * 实例耗材ID
     */
    private Long instanceLabwareId;

    /**
     * 流程id
     */
    private Long processId;

    /**
     * process_iteration表id
     */
    private Long iterationId;
    /**
     * 实例id
     */
    private Long instanceId;


    /**
     * 板序号
     */
    private Integer iterationNo;


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
