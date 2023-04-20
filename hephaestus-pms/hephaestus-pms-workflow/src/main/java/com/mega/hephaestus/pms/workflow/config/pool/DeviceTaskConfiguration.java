package com.mega.hephaestus.pms.workflow.config.pool;

import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.hephaestus.pms.workflow.config.properties.ExperimentProperties;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskEntity;
import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskPool;
import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskSortedSet;
import com.mega.hephaestus.pms.workflow.manager.plan.DeviceResourceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@Slf4j
public class DeviceTaskConfiguration extends BaseConfiguration<DeviceTaskEntity> {

    @Resource
    private RedisTemplate<String, DeviceTaskEntity> redisTemplate;
    @Resource
    private DeviceTaskPool deviceTaskPool;
    @Resource
    private DeviceResourceManager deviceResourceManager;
    @Resource
    private ExperimentProperties experimentProperties;

    private DeviceType getDeviceType(DeviceResourceModel hephaestusDevice) {
        try {
            return hephaestusDevice.getDeviceType();
        } catch (IllegalArgumentException exception) {
            log.info("Device Type undefined {}", hephaestusDevice.getDeviceType());
            return DeviceTypeEnum.Unknown;
        }
    }

    private void registerDeviceTasks(List<DeviceResourceModel> devices) {
        devices.forEach(hephaestusDevice -> {
            DeviceType deviceType = getDeviceType(hephaestusDevice);
            DeviceTaskSortedSet taskSortedSet = new DeviceTaskSortedSet(deviceType, hephaestusDevice.getDeviceKey(), redisTemplate);
            deviceTaskPool.add(hephaestusDevice.getDeviceKey(), taskSortedSet);
        });
    }

    @Override
    protected void registerServices() {
        List<DeviceResourceModel> devices = deviceResourceManager.getAllDevices(experimentProperties.getCode());
        registerDeviceTasks(devices);
    }

    @Override
    protected void populatePool() {

    }
}
