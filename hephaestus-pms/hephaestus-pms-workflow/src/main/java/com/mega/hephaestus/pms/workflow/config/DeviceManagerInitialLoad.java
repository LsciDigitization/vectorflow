package com.mega.hephaestus.pms.workflow.config;

import com.mega.hephaestus.pms.data.model.entity.DeviceEntity;
import com.mega.hephaestus.pms.data.model.service.IDeviceService;
import com.mega.hephaestus.pms.nuc.client.GenericDeviceClient;
import com.mega.hephaestus.pms.nuc.config.DeviceConfiguration;
import com.mega.hephaestus.pms.nuc.config.DeviceConfigurationManager;
import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.hephaestus.pms.nuc.manager.DeviceManagerFactory;
import com.mega.hephaestus.pms.nuc.manager.GenericDeviceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Deprecated(since = "20221115")
//@Component
@Slf4j
public class DeviceManagerInitialLoad {

    @Resource
    private DeviceConfigurationManager deviceConfigurationManager;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private IDeviceService hephaestusDeviceService;
    @Resource
    private DeviceManagerFactory deviceManagerFactory;

    @PostConstruct
    void init() {
        List<DeviceEntity> devices = hephaestusDeviceService.getDevices();
        devices.forEach(hephaestusDevice -> {
            DeviceType deviceType;
            try {
                deviceType = DeviceTypeEnum.toEnum(hephaestusDevice.getDeviceType());
            } catch (IllegalArgumentException exception) {
                deviceType = DeviceTypeEnum.Unknown;
                log.info("Device Type undefined {}", hephaestusDevice.getDeviceType());
            }

            GenericDeviceManager of = genericDeviceManager(deviceType);
            deviceManagerFactory.putDeviceManager(DeviceTypeEnum.Centrifuge, of);
        });
    }

    public GenericDeviceManager genericDeviceManager(DeviceType deviceType) {
        DeviceConfiguration deviceConfiguration = deviceConfigurationManager.getDevice(deviceType).orElseGet(() -> {
            DeviceConfiguration emptyDevice = new DeviceConfiguration();
            emptyDevice.setDeviceId("0");
            emptyDevice.setDeviceType(deviceType);
            emptyDevice.setDeviceKey(deviceType + "-" + 0);
            return emptyDevice;
        });

        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(applicationContext);
        FeignClientBuilder.Builder<GenericDeviceClient> clientBuilder = feignClientBuilder.forType(GenericDeviceClient.class, deviceType.getCode());
        clientBuilder.url(deviceConfiguration.getUrl());

        GenericDeviceManager of = GenericDeviceManager.of(clientBuilder.build(), deviceConfiguration);
        deviceManagerFactory.putDeviceManager(deviceType, of);
        return of;
    }

}
