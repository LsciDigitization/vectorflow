package com.mega.hephaestus.pms.workflow.device.deviceclient;

import com.mega.hephaestus.pms.nuc.client.GenericDeviceClient;
import com.mega.hephaestus.pms.nuc.config.DeviceConfiguration;
import com.mega.hephaestus.pms.nuc.config.DeviceConfigurationManager;
import com.mega.component.nuc.device.DeviceType;
import com.mega.hephaestus.pms.nuc.manager.GenericDeviceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

public class GenericDeviceManagerBuilder implements Callable<GenericDeviceManager> {

    private final GenericDeviceManagerBuilderResource resource;

    private final DeviceType deviceType;
    private final String deviceKey;

    public GenericDeviceManagerBuilder(GenericDeviceManagerBuilderResource resource, DeviceType deviceType, String deviceKey) {
        this.resource = resource;
        this.deviceType = deviceType;
        this.deviceKey = deviceKey;
    }

    @Override
    public GenericDeviceManager call() throws Exception {
        DeviceConfiguration deviceConfiguration = resource.deviceConfigurationManager.getDevice(deviceKey).orElseGet(() -> {
            DeviceConfiguration emptyDevice = new DeviceConfiguration();
            emptyDevice.setDeviceId("0");
            emptyDevice.setDeviceType(deviceType);
            emptyDevice.setDeviceKey(deviceKey);
            return emptyDevice;
        });

        FeignClientBuilder feignClientBuilder = new FeignClientBuilder(resource.applicationContext);
        FeignClientBuilder.Builder<GenericDeviceClient> clientBuilder = feignClientBuilder.forType(GenericDeviceClient.class, deviceType.getCode());
        clientBuilder.url(deviceConfiguration.getUrl());

        return GenericDeviceManager.of(clientBuilder.build(), deviceConfiguration);
    }

    @Component
    @RequiredArgsConstructor
    public static class GenericDeviceManagerBuilderResource {
        @Resource
        private final DeviceConfigurationManager deviceConfigurationManager;
        private final ApplicationContext applicationContext;
    }


}
