package com.mega.hephaestus.pms.workflow.device.deviceschedule;

import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.component.nuc.device.DeviceType;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceBusDaemonResource;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;

import java.util.List;

public interface DeviceScheduleRegister {

    DeviceType deviceType();

    void taskSchedule(DeviceResourceModel deviceResourceModel, List<InstanceTaskEntity> instanceTasks);

    void setDeviceBusDaemonResource(DeviceBusDaemonResource deviceBusDaemonResource);

}
