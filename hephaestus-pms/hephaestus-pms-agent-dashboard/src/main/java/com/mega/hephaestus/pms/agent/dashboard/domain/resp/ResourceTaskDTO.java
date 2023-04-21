package com.mega.hephaestus.pms.agent.dashboard.domain.resp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设备任务表
 *
 * @author xianming.hu
 */
@Data
@ApiModel("资源任务")
public class ResourceTaskDTO {


    @ApiModelProperty("主键")
    private Long id;


    @ApiModelProperty("实例Id")
    private Long instanceId;
    /**
     * deviceKey
     */
    @ApiModelProperty("deviceKey")
    private String deviceKey;

    /**
     * 设备类型
     */
    @ApiModelProperty("设备类型")
    private String deviceType;

    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String taskName;


    /**
     * task状态 await:等待，running：进行，finished：完成，failed：失败
     */
    @ApiModelProperty("任务状态")
    private String taskStatus;


    /**
     * 任务命令
     */
    @ApiModelProperty("任务命令")
    private String taskCommand;

    /**
     * 任务运行超时时间
     */
    @ApiModelProperty("任务运行超时时间")
    private Integer taskTimeoutSecond;

}
