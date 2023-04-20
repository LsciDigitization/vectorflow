package com.mega.hephaestus.pms.workflow.config;

import com.mega.hephaestus.pms.data.model.service.IDeviceService;
import com.mega.hephaestus.pms.nuc.config.DeviceConfiguration;
import com.mega.hephaestus.pms.nuc.config.DeviceConfigurationManager;
import com.mega.hephaestus.pms.nuc.config.DeviceProperties;
import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.hephaestus.pms.workflow.config.properties.ExperimentProperties;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.manager.plan.DeviceResourceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration(proxyBeanMethods = true)
@ConditionalOnProperty(name = "hephaestus.nuc.registry-type", havingValue = "database")
@Slf4j
public class DevicePropertiesByDatabaseConfiguration {

    @Resource
    private IDeviceService hephaestusDeviceService;

    @Resource
    private DeviceProperties deviceProperties;

    @Resource
    private DeviceResourceManager deviceResourceManager;

    @Resource
    private ExperimentProperties experimentProperties;
    /**
     * 加载数据库中的设备表进行设备注册
     */
    @Bean
    public DeviceConfigurationManager deviceConfigurationManager() {
        List<DeviceResourceModel> devices = deviceResourceManager.getAllDevices(experimentProperties.getCode());
        final Map<String, DeviceConfiguration> deviceConfigurationMap = new HashMap<>();
        devices.forEach(hephaestusDevice -> {
            DeviceType deviceType;
            try {
                deviceType = hephaestusDevice.getDeviceType();
            } catch (IllegalArgumentException exception) {
                deviceType = DeviceTypeEnum.Unknown;
                log.info("Device Type undefined {}", hephaestusDevice.getDeviceType());
            }

            DeviceConfiguration device = new DeviceConfiguration();
            device.setDeviceType(deviceType);
            device.setDeviceId(hephaestusDevice.getDeviceId());
            device.setDeviceAlias(hephaestusDevice.getDeviceName());
            device.setDeviceKey(hephaestusDevice.getDeviceKey());

            // TODO url 需要重新处理
            if (deviceProperties.isUseProxy()) {
                device.setUrl(hephaestusDevice.getUrl());
            } else {
                device.setUrl(hephaestusDevice.getUrl());
            }
            device.setCallbackUrl(hephaestusDevice.getCallbackUrl());

            deviceConfigurationMap.put(hephaestusDevice.getDeviceKey(), device);
        });

        return new DeviceConfigurationManager(deviceConfigurationMap);
    }

}
