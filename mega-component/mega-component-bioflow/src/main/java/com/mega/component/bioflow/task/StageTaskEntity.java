package com.mega.component.bioflow.task;

import com.mega.component.nuc.device.DeviceType;
import lombok.Data;


/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/23 11:37
 */
@Data
public class StageTaskEntity {

    /**
     * 任务ID
     */
    private StageTaskId id;

    /**
     * 阶段ID，所属阶段
     */
    private StageId stageId;

    /**
     * 实验ID，所属实验
     */
    private ExperimentId experimentId;

    /**
     * 设备类型
     */
    private DeviceType deviceType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务描述
     */
    private String taskDescription;

    /**
     * 设备命令
     */
    private String taskCommand;

    /**
     * 设备参数
     */
    private String taskParameter;

    /**
     * 任务动作类型
     */
    private TaskAction taskAction;

    /**
     * 加锁状态
     */
    private Integer lockStatus;

    /**
     * stepKey
     */
    private StepKey stepKey;

    /**
     * 超时时间，单位秒，默认60
     */
    private Integer timeoutSecond = 60;

    /**
     * 到达下一个任务最大间距时间 单位秒 -1 没有限制
     */
    private Long nextTaskExpireDurationSecond = -1L;

    /**
     * 执行异常后置task
     */
    private Long onErrorTaskId;

    /**
     * 执行前HOOK参数
     */
    private String beforeHook;

    /**
     * 执行后成功HOOK参数
     */
    private String afterSuccessHook;

    /**
     * 执行后失败HOOK参数
     */
    private String afterFailHook;

    /**
     * 排序
     */
    private Integer sortOrder;


}
