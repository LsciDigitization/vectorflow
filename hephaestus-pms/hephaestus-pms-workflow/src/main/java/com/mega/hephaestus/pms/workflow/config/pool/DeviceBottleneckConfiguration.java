package com.mega.hephaestus.pms.workflow.config.pool;

import com.mega.hephaestus.pms.workflow.config.WorkflowServiceFactory;
import com.mega.hephaestus.pms.workflow.device.devicebottleneck.DeviceBottleneckPool;
import com.mega.hephaestus.pms.workflow.device.devicebottleneck.DeviceBottleneckRegister;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration(proxyBeanMethods = true)
@Slf4j
public class DeviceBottleneckConfiguration extends BaseConfiguration<DeviceBottleneckRegister> {

    private final Map<String, DeviceBottleneckRegister> deviceTypeCallableMap = new ConcurrentHashMap<>();

    @Resource
    private DeviceBottleneckPool deviceBottleneckPool;

    @Resource
    private WorkBusDaemonResource workBusDaemonResource;

    @Resource
    private WorkflowServiceFactory workflowServiceFactory;

    private void loadDeviceBottleneckRegisters() {
        List<DeviceBottleneckRegister> deviceBottleneckRegisters = workflowServiceFactory.loadDeviceBottleneckRegisterServices();

        for (DeviceBottleneckRegister deviceBottleneckRegister : deviceBottleneckRegisters) {
            String key = deviceBottleneckPool.key(deviceBottleneckRegister.deviceType(), deviceBottleneckRegister.stepType());
            deviceTypeCallableMap.put(key, deviceBottleneckRegister);
        }
    }

    private void initializeDeviceBottleneckPool() {
        deviceTypeCallableMap.forEach((key, register) -> {
            register.setWorkBusDaemonResource(workBusDaemonResource);
            deviceBottleneckPool.add(key, register);
        });
    }

    @Override
    protected void registerServices() {
        loadDeviceBottleneckRegisters();
    }

    @Override
    protected void populatePool() {
        initializeDeviceBottleneckPool();
    }
}
