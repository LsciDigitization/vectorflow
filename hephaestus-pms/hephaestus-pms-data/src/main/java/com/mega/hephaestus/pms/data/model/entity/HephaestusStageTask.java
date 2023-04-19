package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import lombok.Data;
import java.util.Date;

/**
 * 任务表
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_experiment_task")
public class HephaestusStageTask {

    /**
     * 自增id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * stageId
     */
    private Long stageId;


    /**
     * 所属实验
     */
    private Long experimentId;

    /**
     * 设备key,多个逗号分割
     */
    private String deviceKey;

    @TableField(exist = false)
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
     * 设备类型
     */
    private String deviceType;


    /**
     * 设备命令
     */
    private String taskCommand;


    /**
     * 设备参数
     */
    private String taskParameter;

    /**
     * 用户参数
     */
//    @TableField(value ="assign_parameters" ,typeHandler = InstanceContextJsonTypeHandler.class)
    private String assignParameters;


    /**
     * 加锁状态
     */
    private Integer lockStatus;

    /**
     * stepKey
     */
    private String stepKey;


    /**
     * 超时时间，单位秒，默认60
     */
    private Integer timeoutSecond;
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
     * 顺序
     */
    private Integer sortOrder;

    /**
     * 到达下一个任务最大间距时间 单位秒 -1 没有限制
     */
    private Long nextTaskExpireDurationSecond;

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
     * 备注
     */
    private String remarks;

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

    /**
     * 执行异常后置task
     */
    private Long onErrorTaskId;


    /**
     * 是否关闭
     */
    private BooleanEnum isClosed;

    /**
     * 是否存储结果 0 否 1是
     */
    private BooleanEnum isStoraged;

    /**
     * 任务类型:
     * 前置任务
     * 任务
     * 后置任务
     * 执行失败的任务
     */
    private String taskAction;
}
