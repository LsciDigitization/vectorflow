package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 实例板池
 *
 * @author xianming.hu
 */
@Data
@ApiModel("实例板动态DTO")
public class InstancePlateDynamicDTO {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;


    @ApiModelProperty("实例Id")
    private Long instanceId;


    /**
     * 板序号
     */
    @ApiModelProperty("板序号")
    private Integer plateNo;

    /**
     * 状态
     *    3、已完成--->isFinished ==1 && isConsumed ==1
     *    2、已消费--->(isFinished ==null || isFinished ==0) && isConsumed ==1
     *    1、未消费--->(isFinished ==null || isFinished ==0) && isConsumed ==0
     */
    @ApiModelProperty("状态 1、未消费 2、已消费 3、已完成")
    private Integer transientStatus;

    @ApiModelProperty("消费/完成时间")
    private Date transientTime;


    @ApiModelProperty("板类型名称")
    private String transientExperimentPoolTypeName;

    // 实验资源池类型
    @ApiModelProperty("板类型")
    private String experimentPoolType;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
