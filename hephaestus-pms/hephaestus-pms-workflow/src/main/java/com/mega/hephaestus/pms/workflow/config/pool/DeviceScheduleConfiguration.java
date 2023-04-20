package com.mega.hephaestus.pms.workflow.config.pool;

import com.mega.hephaestus.pms.workflow.config.WorkflowServiceFactory;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceBusDaemonResource;
import com.mega.hephaestus.pms.workflow.device.deviceschedule.DeviceSchedulePool;
import com.mega.hephaestus.pms.workflow.device.deviceschedule.DeviceScheduleRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration(proxyBeanMethods = true)
@Slf4j
public class DeviceScheduleConfiguration extends BaseConfiguration<DeviceScheduleRegister> {

    private final Map<String, DeviceScheduleRegister> deviceTypeCallableMap = new ConcurrentHashMap<>();

    @Resource
    private DeviceSchedulePool deviceSchedulePool;
    @Resource
    private DeviceBusDaemonResource deviceBusDaemonResource;
    @Resource
    private WorkflowServiceFactory workflowServiceFactory;

    private void registerDeviceSchedules(List<DeviceScheduleRegister> deviceScheduleRegisters) {
        for (DeviceScheduleRegister deviceScheduleRegister : deviceScheduleRegisters) {
            deviceScheduleRegister.setDeviceBusDaemonResource(deviceBusDaemonResource);
            deviceTypeCallableMap.put(deviceScheduleRegister.deviceType().getCode(), deviceScheduleRegister);
        }
    }


    @Override
    protected void registerServices() {
        List<DeviceScheduleRegister> deviceScheduleRegisters = workflowServiceFactory.loadDeviceScheduleRegisterServices();
        registerDeviceSchedules(deviceScheduleRegisters);
    }

    @Override
    protected void populatePool() {
        deviceTypeCallableMap.forEach(deviceSchedulePool::add);
    }
}
