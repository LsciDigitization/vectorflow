package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("通量统计")
public class InstancePlateNoCountDTO {
    @ApiModelProperty("板号")
    private String plateNo;

    @ApiModelProperty("总量")
    private Integer total;

    @ApiModelProperty("完成量")
    private Integer finishedTotal;

    @ApiModelProperty("完成率")
    private Double completionRate;
}
