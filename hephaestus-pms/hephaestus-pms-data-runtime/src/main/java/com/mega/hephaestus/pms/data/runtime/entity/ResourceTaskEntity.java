package com.mega.hephaestus.pms.data.runtime.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 设备任务表
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_resource_task")
public class ResourceTaskEntity {

    /**
     * 数据编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 记录ID
     */
    private Long processRecordId;

    /**
     * deviceKey
     */
    private String deviceKey;



    /**
     * 设备NUC ID
     */
    private String deviceId;


    /**
     * 设备类型
     */
    private String deviceType;



    /**
     * stepKey
     */
    private String stepKey;
    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 任务名称
     */
    private String taskName;
    /**
     * task状态 await:等待，running：进行，finished：完成，failed：失败
     */
    private String taskStatus;
    /**
     * 任务命令
     */
    private String taskCommand;

    /**
     * 任务参数
     */
    private String taskParameter;


    /**
     * 任务请求id
     */
    private String taskRequestId;

    /**
     * 异常信息
     */
    private String taskErrorMessage;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 终止时间
     */
    private Date endTime;

    /**
     *实验ID
     */
    private Long experimentId;

    /**
     * 实例ID
     */
    private Long instanceId;

    /**
     *  阶段ID
     */
    private Long stageId;

    /**
     * 阶段名称
     */
    private String stageName;




    /**
     * 响应内容
     */
    private String taskResponseMessage;

    /**
     * 耗时
     */
    private Integer taskDuration;

    /**
     * 任务运行超时时间
     */
    private Integer taskTimeoutSecond;
}
