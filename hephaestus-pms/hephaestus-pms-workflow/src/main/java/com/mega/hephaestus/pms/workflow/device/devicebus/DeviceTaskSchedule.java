package com.mega.hephaestus.pms.workflow.device.devicebus;

import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.hephaestus.pms.workflow.device.deviceschedule.DeviceScheduleRegister;
import com.mega.hephaestus.pms.workflow.event.StepStartEvent;
import com.mega.hephaestus.pms.workflow.work.workstep.Step;
import com.mega.hephaestus.pms.workflow.work.workstep.StepStatus;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public abstract class DeviceTaskSchedule implements DeviceScheduleRegister {

    protected DeviceBusDaemonResource deviceBusDaemonResource;

    public DeviceTaskSchedule() {
    }

    public DeviceTaskSchedule(DeviceBusDaemonResource deviceBusDaemonResource) {
        this.deviceBusDaemonResource = deviceBusDaemonResource;
    }

    public void setDeviceBusDaemonResource(DeviceBusDaemonResource deviceBusDaemonResource) {
        this.deviceBusDaemonResource = deviceBusDaemonResource;
    }

    /**
     * 创建任务
     * @param deviceResourceModel DeviceResourceModel
     * @param instanceTasks InstanceTaskEntity
     */
    protected void crateDeviceTask(DeviceResourceModel deviceResourceModel, List<InstanceTaskEntity> instanceTasks) {
        // 创建deviceTask 截取前面四条
        Optional<ResourceTaskEntity> deviceTaskOptional = deviceBusDaemonResource.getDeviceTaskManager().createTask(instanceTasks.get(0), deviceResourceModel.getDeviceKey());
        if (deviceTaskOptional.isPresent()) {
            // 修改设备状态
            deviceBusDaemonResource.getDeviceResourceManager().runningDeviceResource(deviceResourceModel.getDeviceKey());
            // 修改绑定的 instanceTask
            instanceTasks.forEach(task -> {
                String requestId = deviceTaskOptional.get().getTaskRequestId();
                // 修改task状态
                deviceBusDaemonResource.getInstanceTaskManager().runningTask(task.getId(), requestId, deviceResourceModel.getDeviceKey());
                // 投递Step Start事件
                if (StringUtils.isNotBlank(task.getStepKey())) {
                    Step step = new Step();
                    step.setName(task.getTaskName());
                    step.setType(StepTypeEnum.toEnum(task.getStepKey()));
                    step.setStatus(StepStatus.Started);
                    step.setNextStepDurationSecond(task.getNextTaskExpireDurationSecond());
                    step.setStartTime(new Date());
                    deviceBusDaemonResource.getWorkEventPusher().sendStepStartEvent(new StepStartEvent(step, task.getInstanceId()));
                }
            });
        }
    }

    protected void crateDeviceTask(DeviceResourceModel deviceResourceModel, InstanceTaskEntity instanceTask) {
        // 创建deviceTask 截取前面四条
        Optional<ResourceTaskEntity> deviceTaskOptional = deviceBusDaemonResource.getDeviceTaskManager().createTask(instanceTask, deviceResourceModel.getDeviceKey());
        if (deviceTaskOptional.isPresent()) {
            // 修改设备状态
            deviceBusDaemonResource.getDeviceResourceManager().runningDeviceResource(deviceResourceModel.getDeviceKey());
            // 修改绑定的 instanceTask
            String requestId = deviceTaskOptional.get().getTaskRequestId();
            // 修改task状态
            deviceBusDaemonResource.getInstanceTaskManager().runningTask(instanceTask.getId(), requestId, deviceResourceModel.getDeviceKey());
        }
    }


}
