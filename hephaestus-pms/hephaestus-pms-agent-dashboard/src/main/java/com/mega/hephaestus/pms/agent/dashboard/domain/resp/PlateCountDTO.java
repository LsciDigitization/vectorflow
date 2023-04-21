package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("板统计DTO")
public class PlateCountDTO {

    @ApiModelProperty("总数")
    private long total;

    @ApiModelProperty("完成总数")
    private long finishedTotal;

    @ApiModelProperty("消费总数")
    private long consumedTotal;

    /**
     * 开始时间 取自该实验的所有任务的最早时间
     */
    @ApiModelProperty("开始时间")
    private Date startTime;
    /**
     * 结束时间 取自该实验的所有任务的最晚时间
     */
    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty(value = "开始时间格式化", example = "HH:mm:ss")
    private String startTimeFormat;
    /**
     * 结束时间 取自该实验的所有任务的最晚时间
     */
    @ApiModelProperty("结束时间")
    private String endTimeFormat;

    // 持续时间
    @ApiModelProperty(value = "持续时间格式化", example = "HH:mm:ss")
    private String durationTimeFormat;
}
