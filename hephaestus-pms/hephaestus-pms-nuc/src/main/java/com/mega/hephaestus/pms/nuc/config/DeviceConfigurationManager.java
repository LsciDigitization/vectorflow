package com.mega.hephaestus.pms.nuc.config;

import com.mega.component.nuc.device.DeviceType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeviceConfigurationManager {

    private final Map<String, DeviceConfiguration> device;

    public DeviceConfigurationManager(Map<String, DeviceConfiguration> device) {
        this.device = device;
    }

    public List<DeviceConfiguration> getDevices(DeviceType deviceType) {
        return device.values().stream()
                .filter(deviceConfiguration -> deviceConfiguration.getDeviceType().equals(deviceType))
                .collect(Collectors.toList());
    }

    public Optional<DeviceConfiguration> getDevice(DeviceType deviceType) {
        return getDevices(deviceType).stream().findFirst();
    }

    public Optional<DeviceConfiguration> getDevice(String deviceKey) {
        return device.values().stream()
                .filter(deviceConfiguration -> deviceKey.equals(deviceConfiguration.getDeviceKey()))
                .findFirst();
    }

    public Optional<DeviceConfiguration> getDevice(DeviceType deviceType, String deviceId) {
        return device.values().stream()
                .filter(deviceConfiguration ->
                        deviceConfiguration.getDeviceType().equals(deviceType) &&
                                deviceConfiguration.getDeviceId().equals(deviceId))
                .findFirst();
    }

}
