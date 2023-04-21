package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author yinyse
 */
@Data
@ApiModel("实例信息")
public class InstanceTaskInfo {

    @ApiModelProperty("实例id")
    private Long instanceId;

    @ApiModelProperty("实例名称")
    private String instanceTitle;

    @ApiModelProperty("板类型")
    private String poolType;

    @ApiModelProperty("任务信息")
    private List<TaskInfo> taskList;

    @ApiModelProperty("实例状态")
    private int instanceStatus;

    @ApiModelProperty("等待总时长")
    private long waitDurationTotal;

    @ApiModelProperty("运行总时长")
    private long runDurationTotal;

    // 总耗时
    @ApiModelProperty("总耗时")
    private long durationTotal;

    @ApiModelProperty(value = "总耗时格式化",example = "10小时01分钟01秒")
    private String durationTotalFormat;

    @ApiModelProperty("板号")
    private Integer plateNo;

    @ApiModelProperty("批次号")
    private Integer batchNo;

    @Data
    @ApiModel("任务信息")
    public static class TaskInfo{

        @ApiModelProperty("任务名称")
        private String taskName;

        @ApiModelProperty("任务状态")
        private String taskStatus;

        @ApiModelProperty("任务创建时间")
        private Date createTime;

        @ApiModelProperty("任务开始时间")
        private Date startTime;

        @ApiModelProperty("任务结束时间")
        private Date endTime;

        @ApiModelProperty("任务开始时间格式化")
        private String startTimeFormatted;

        @ApiModelProperty("任务结束时间格式化")
        private String endTimeFormatted;

        @ApiModelProperty("等待时长")
        private long waitDuration;

        @ApiModelProperty("运行时长")
        private long runDuration;

        /**
         * 是否是虚拟task
         */
        @ApiModelProperty("是否虚拟task")
        private boolean virtual;

        @ApiModelProperty("任务序号")
        private int taskNo;

        @ApiModelProperty("设备key")
        private String deviceKey;

        @ApiModelProperty("步骤key")
        private String stepKey;
    }
}
