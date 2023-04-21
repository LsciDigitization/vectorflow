package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yinyse
 */
@Data
@ApiModel
public class ResourceDTO {

    @ApiModelProperty("资源名称")
    private String deviceName;

    @ApiModelProperty("资源Key")
    private String deviceKey;

    @ApiModelProperty("资源类型")
    private String deviceType;

    @ApiModelProperty("类型名称")
    private String deviceTypeName;

    @ApiModelProperty("资源颜色")
    private String color;

    /**
     *  资源带板数 逗号分割:
     *  <p>
     *  2,4,8
     *  </p>
     */
    @ApiModelProperty("资源带板数")
    private String resourcePlateNumber;


    // 是否资源瓶颈，默认都是false
    @ApiModelProperty("是否资源瓶颈")
    private Boolean resourceBottleneck;
}
