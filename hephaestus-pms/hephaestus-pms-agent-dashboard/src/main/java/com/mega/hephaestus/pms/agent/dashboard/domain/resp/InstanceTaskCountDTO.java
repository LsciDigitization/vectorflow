package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yinyse
 */
@Data
@ApiModel("实例任务统计DTO")
public class InstanceTaskCountDTO {


    @ApiModelProperty("总数")
    private long total;

    @ApiModelProperty("完成总数")
    private long finishedTotal;

    @ApiModelProperty("运行数量")
    private long runningTotal;
}
