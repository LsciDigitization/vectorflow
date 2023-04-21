package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mega.component.mybatis.common.model.BaseFullDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author yinyse
 * @description TODO 实例任务返回实体
 * @date 2022/11/25 1:44 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel("instanceTaskSearchResultDTO")
public class HephaestusInstanceTaskSearchResultDTO  extends BaseFullDTO {

    /**
     * 数据编号
     */
    @ApiModelProperty("数据编号")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * deviceKey
     */
    @ApiModelProperty("选中deviceKey")
    private String deviceKey;
    /**
     * 可用deviceKey范围，逗号分割
     */
    @ApiModelProperty("可用deviceKey范围，逗号分割")
    private String deviceKeyRange;

    /**
     * 高 High 1
     * 中 Medium 11
     * 低 Low111
     * default 111
     */
    private Integer priority;


    private String priorityName;


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
    private int timeoutSecond;

    /**
     *
     */
    private String instanceTitle;

}
