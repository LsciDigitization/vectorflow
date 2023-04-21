package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yinyse
 */
@Data
@ApiModel("板类型颜色")
public class PlateTypeColorDTO {

    @ApiModelProperty("板Key")
    private String poolKey;

    @ApiModelProperty("板类型")
    private String poolType;

    @ApiModelProperty("板类型名称")
    private String poolTypeName;

    @ApiModelProperty("板颜色")
    private String color;
}
