package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * hephaestus_instance_task
 *
 * @author xianming.hu
 */
@Data
@TableName("实例任务DTO")
public class InstanceTaskDTO {

    /**
     * 数据编号
     */
    @ApiModelProperty("数据编号")
    private Long id;


    /**
     * 实例ID
     */
    @ApiModelProperty("实例编号")
    private Long instanceId;
    /**
     * 任务编号
     */
    @ApiModelProperty("任务编号")
    private Integer taskNo;

    /**
     * 板编号
     */
    @ApiModelProperty("板编号")
    private Integer plateNo;

    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String taskName;


    /**
     * deviceKey
     */
    @ApiModelProperty("设备Key")
    private String deviceKey;

    /**
     * 设备类型
     */
    @ApiModelProperty("设备类型")
    private String deviceType;

    /**
     * task状态 await:等待，running：进行，finished：完成，failed：失败
     */
    @ApiModelProperty("任务状态")
    private String taskStatus;

    /**
     * 实验资源池类型
     */
    @ApiModelProperty("板类型")
    private String experimentPoolType;


    /**
     * 任务命令
     */
    @ApiModelProperty("任务命令")
    private String taskCommand;
    /**
     * 将要执行时间
     */
    @ApiModelProperty("将要执行时间")
    private Integer timeoutSecond;
    /**
     * 可用deviceKey范围，逗号分割
     */

    /**
     * 步骤类型
     */
    @ApiModelProperty("步骤类型")
    private String stepKey;

    /**
     * 步骤值
     */
    @ApiModelProperty("步骤值")
    private Long stepValue;

    @ApiModelProperty("可用deviceKey范围，逗号分割")
    private String deviceKeyRange;

}
