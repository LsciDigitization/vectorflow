package com.mega.hephaestus.pms.data.runtime.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;

import java.util.Date;

/**
 * 实例板池表
 *
 * @author xianming.hu
 */
@Data

@TableName("hephaestus_instance_iteration_consume")
public class InstanceIterationConsumeEntity {

    /**
     * 主键
     */
    private Long id;


    /**
     * 通量id
     */
    private Long iterationId;


    /**
     * 实例id
     */
    private Long instanceId;


    /**
     * 流程记录id
     */
    private Long processRecordId;


    /**
     * 开始时间
     */
    private Date startTime;


    /**
     * 是否消费 0 未消费 1消费
     */
    private BooleanEnum isConsumed;


    /**
     * 消费时间
     */
    private Date consumeTime;


    /**
     * 是否完成 0 未完成 1已完成
     */
    private BooleanEnum isFinished;


    /**
     * 完成时间
     */
    private Date finishTime;

}
