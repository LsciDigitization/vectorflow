package com.mega.hephaestus.pms.workflow.config.pool;

import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.hephaestus.pms.workflow.config.properties.ExperimentProperties;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.device.deviceclient.DeviceClientPool;
import com.mega.hephaestus.pms.workflow.device.deviceclient.GenericDeviceManagerBuilder;
import com.mega.hephaestus.pms.workflow.manager.plan.DeviceResourceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

@Configuration(proxyBeanMethods = true)
@Slf4j
public class DeviceClientConfiguration extends BaseConfiguration<Object> {

    @Resource
    private GenericDeviceManagerBuilder.GenericDeviceManagerBuilderResource genericDeviceManagerBuilderResource;

    @Resource
    private DeviceClientPool deviceClientPool;

    @Resource
    private DeviceResourceManager deviceResourceManager;

    @Resource
    private ExperimentProperties experimentProperties;

    private void addDeviceToClientPool(DeviceResourceModel model) {
        DeviceType deviceType = getDeviceType(model);

        GenericDeviceManagerBuilder genericDeviceManagerBuilder = new GenericDeviceManagerBuilder(genericDeviceManagerBuilderResource, deviceType, model.getDeviceKey());

        try {
            deviceClientPool.add(model.getDeviceKey(), genericDeviceManagerBuilder.call());
        } catch (Exception e) {
            log.error("GenericDeviceManagerBuilder error {}", e.getMessage());
        }
    }

    private DeviceType getDeviceType(DeviceResourceModel model) {
        try {
            return model.getDeviceType();
        } catch (IllegalArgumentException exception) {
            log.info("Device Type undefined {}", model.getDeviceType());
            return DeviceTypeEnum.Unknown;
        }
    }

    @Override
    protected void registerServices() {

    }

    @Override
    protected void populatePool() {
        List<DeviceResourceModel> devices = deviceResourceManager.getAllDevices(experimentProperties.getCode());
        devices.forEach(this::addDeviceToClientPool);
    }
}
