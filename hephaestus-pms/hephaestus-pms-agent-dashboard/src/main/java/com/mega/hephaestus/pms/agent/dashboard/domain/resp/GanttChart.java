package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author yinyse
 * @description TODO 甘特图
 * @date 2022/11/30 4:34 PM
 */
@Data
@ApiModel("甘特图数据body")
public class GanttChart {

    // 实验组名称
    @ApiModelProperty(value = "实验组名称")
    private String experimentGroupName;
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
    /**
     * 实例数据
     */
    @ApiModelProperty("实例信息")
    private List<GanttInstanceInfo> instances;

    @Data
    @ApiModel("实例信息")
    public static class GanttInstanceInfo {
        @ApiModelProperty("实例Id")
        private String instanceId;
        /**
         * 实例名称
         */
        @ApiModelProperty("实例名称")
        private String instanceName;
        /**
         * 实验资源池类型
         */
        @ApiModelProperty("板类型")
        private String experimentPoolType;
        /**
         * 实验资源池类型名
         */
        @ApiModelProperty("板类型名称")
        private String experimentPoolTypeName;
        /**
         * 实例任务
         */
        @ApiModelProperty("实例任务")
        private List<GanttTaskInfo> tasks;
        /**
         * 创建时间
         */
        @ApiModelProperty("创建时间")
        private Date createTime;

        @ApiModelProperty("板号")
        private Integer plateNo;

        @ApiModelProperty("批次号")
        private Integer batchNo;

        @ApiModelProperty("序号")
        private Integer sortOrder;
    }

    @AllArgsConstructor
    @Data
    @ApiModel("任务信息")
    public static class GanttTaskInfo {
        @ApiModelProperty("任务id")
        private String taskId;
        /**
         * 任务开始时间
         */
        @ApiModelProperty("任务开始时间")
        private Date startTime;
        /**
         * 任务结束时间
         */
        @ApiModelProperty("任务结束时间")
        private Date endTime;
        /**
         * 任务名称
         */
        @ApiModelProperty("任务名称")
        private String taskName;
        /**
         * 设备KEY
         */
        @ApiModelProperty("设备key")
        private String deviceKey;
        /**
         * 设备类型
         */
        @ApiModelProperty("设备类型")
        private String deviceType;
        /**
         * 任务状态
         */
        @ApiModelProperty("任务状态")
        private String taskStatus;
        /**
         * 创建时间
         */
        @ApiModelProperty("任务创建时间")
        private Date createTime;

        @ApiModelProperty("甘特图颜色")
        private String ganttColor;


    }
}
