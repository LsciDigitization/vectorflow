package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import com.mega.component.mybatis.common.model.BaseFullDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * @author xianming.hu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel("InstanceDTO")
public class HephaestusInstanceDTO extends BaseFullDTO {


    /**
     * 实验实例ID
     */
    @ApiModelProperty("实验实例ID")
    private Long instanceId;


    /**
     * 实验名称
     */
    @ApiModelProperty("实验名称")
    private String instanceTitle;


    /**
     * 实验状态
     */
    @ApiModelProperty("实验状态 ")
    private String instanceStatus;


    /**
     * 实验参数
     */
    @ApiModelProperty("实验参数")
    private String instanceContext;


    /**
     * 所在节点ID
     */
    @ApiModelProperty("所在节点ID")
    private Long activeStageId;


    /**
     * 所在节点状态
     */
    @ApiModelProperty("所在节点状态")
    private String activeStageStatus;

}
