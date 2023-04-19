package com.mega.hephaestus.pms.data.runtime.view;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class HephaestusInstanceTaskView {


    /**
     * 数据编号
     */

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * deviceKey
     */
    private String deviceKey;
    /**
     * 可用deviceKey范围，逗号分割
     */
    private String deviceKeyRange;

    /**
     * 高 High 1
     * 中 Medium 11
     * 低 Low111
     * default 111
     */
    private Integer priority;
    /**
     * 设备NUC ID
     */
    private String deviceId;


    /**
     * 设备类型
     */
    private String deviceType;

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
     * 实例id
     */
    private Long instanceId;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务名称
     */
    private String taskName;

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
     *  修改优先级时间
     */
    private Date updatePriorityTime;

    /**
     * 任务超时时间
     */
    private Integer timeoutSecond;

    /**
     * 到达下一个任务最大间距时间 单位秒 -1 没有限制
     */
    private Long nextTaskExpireDurationSecond;

    /**
     * 任务将要过期的时间 单位秒
     * 正数是还未过期时间
     * 负数为已经过期多久
     */
    @TableField(exist = false)
    private Long willExpireDurationSecond;

    /**
     * 步骤类型
     */
    private String stepKey;

    /**
     * 步骤值
     */
    private Long stepValue;

    /**
     * 实验资源池类型
     */
    private String experimentPoolType;


    /**
     * 任务编号
     */
    private Integer taskNo;

    /**
     * 临时排序字段 数据库不存在  无实际意义
     */
    @TableField(exist = false)
    private Integer transientSort;

    /**
     * 板序号
     */
    private Integer plateNo;

    /**
     * 实验组历史id
     */
    private Long experimentGroupHistoryId;

    private Integer batchNo;
}
