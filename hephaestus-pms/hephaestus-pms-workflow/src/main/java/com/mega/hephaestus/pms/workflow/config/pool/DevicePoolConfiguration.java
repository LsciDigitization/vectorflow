package com.mega.hephaestus.pms.workflow.config.pool;

import com.mega.component.workflow.models.TaskType;
import com.mega.hephaestus.pms.workflow.device.devicebottleneck.DeviceBottleneckPool;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceBusPool;
import com.mega.hephaestus.pms.workflow.device.deviceclient.DeviceClientPool;
import com.mega.hephaestus.pms.workflow.device.devicedaemon.DeviceDaemonPool;
import com.mega.hephaestus.pms.workflow.device.deviceschedule.DeviceSchedulePool;
import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskPool;
import com.mega.hephaestus.pms.workflow.task.tasktype.TaskTypePool;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlatePool;
import com.mega.hephaestus.pms.workflow.work.workstart.WorkStartPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevicePoolConfiguration {

    @Bean
    public DeviceDaemonPool deviceDaemonPool() {
        return new DeviceDaemonPool();
    }

    @Bean
    public DeviceBottleneckPool deviceBottleneckPool() {
        return new DeviceBottleneckPool();
    }

    @Bean
    public DeviceBusPool deviceBusPool() {
        return new DeviceBusPool();
    }

    @Bean
    public DeviceClientPool deviceClientPool() {
        return new DeviceClientPool();
    }

    @Bean
    public DeviceTaskPool deviceTaskPool() {
        return new DeviceTaskPool();
    }

    @Bean
    public DeviceSchedulePool deviceSchedulePool() {
        return new DeviceSchedulePool();
    }

    @Bean
    public WorkPlatePool workPlatePool() {
        return new WorkPlatePool();
    }

    @Bean
    public WorkStartPool workStartPool() {
        return new WorkStartPool();
    }

    @Bean
    public TaskTypePool taskTypePool() {
        return new TaskTypePool();
    }


    //======
    @Bean("stageTaskType")
    public TaskType stageTaskType() {
        return createTaskType("stage");
    }

    @Bean("dynamicTaskType")
    public TaskType dynamicTaskType() {
        return createTaskType("dynamic");
    }

    @Bean("awaitTaskType")
    public TaskType awaitTaskType() {
        return createTaskType("await");
    }

    @Bean("gatewayTaskType")
    public TaskType gatewayTaskType() {
        return createTaskType("gateway");
    }

    @Bean("startTaskType")
    public TaskType startTaskType() {
        return createTaskType("start");
    }

    @Bean("endTaskType")
    public TaskType endTaskType() {
        return createTaskType("end");
    }

    private TaskType createTaskType(String type) {
        return new TaskType(type, "1", true);
    }

}
