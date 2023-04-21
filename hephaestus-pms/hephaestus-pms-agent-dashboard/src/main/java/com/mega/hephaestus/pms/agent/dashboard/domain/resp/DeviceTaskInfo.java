package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author yinyse
 * @description TODO
 * @date 2022/11/21 4:32 PM
 */
@Getter
@Data
public class DeviceTaskInfo {

    private String deviceType;
    //private String deviceName;
    private List<DeviceRunningInfo>  deviceList;
    private List<DeviceAwaitInfo>  awaitTasks;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeviceRunningInfo {
        private String deviceKey;
        private String deviceName;
        private String deviceStatus;
        private List<TaskInfo> runningTasks;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class TaskInfo{
        private Long instanceId;
        private String taskStatus;
        private Date startTime;
        private Date endTime;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class DeviceAwaitInfo {
        private Long id;
        private Long instanceId;
        private String taskStatus;
        private Integer priority;
        private Long  taskId;
        private String taskCommand;
        private int timeoutSecond;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public Integer getDeviceListSize() {
        return deviceList.size();
    }

    public Integer getAwaitTasksSize() {
        return awaitTasks.size();
    }
}
