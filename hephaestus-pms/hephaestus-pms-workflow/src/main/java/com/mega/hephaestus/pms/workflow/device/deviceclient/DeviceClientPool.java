package com.mega.hephaestus.pms.workflow.device.deviceclient;


import com.mega.hephaestus.pms.nuc.client.GenericDeviceClient;
import com.mega.component.nuc.device.AbstractDevice;
import com.mega.component.nuc.device.GenericDevice;
import com.mega.hephaestus.pms.nuc.manager.GenericDeviceManager;
import com.mega.hephaestus.pms.workflow.device.AbstractPool;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class DeviceClientPool extends AbstractPool<GenericDeviceManager> {

    public DeviceClientPool() {
    }

    public AbstractDevice getDevice(String deviceKey) {
        // 获取对应的device对象
        AtomicReference<GenericDevice> deviceAtomicReference = new AtomicReference<>();
        Optional<GenericDeviceManager> deviceManagerOptional = get(deviceKey);
        deviceManagerOptional.ifPresent(deviceManager -> {
            deviceAtomicReference.set(deviceManager.getDevice());
        });
        return deviceAtomicReference.get();
    }

    public GenericDeviceClient getClient(String deviceKey) {
        // 获取对应的device对象
        AtomicReference<GenericDeviceClient> clientAtomicReference = new AtomicReference<>();
        Optional<GenericDeviceManager> deviceManagerOptional = get(deviceKey);
        deviceManagerOptional.ifPresent(deviceManager -> {
            clientAtomicReference.set(deviceManager.getClient());
        });
        return clientAtomicReference.get();
    }


}
