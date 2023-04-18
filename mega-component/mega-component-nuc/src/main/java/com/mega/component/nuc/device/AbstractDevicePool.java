package com.mega.component.nuc.device;

import com.mega.component.nuc.exceptions.DeviceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractDevicePool {

    private final Map<String, AbstractDevice> pool = new ConcurrentHashMap<>();

    private String genKey(AbstractDevice device) {
        return device.getDeviceType() + "-" + device.getDeviceId();
    }

    private String genKey(String deviceId, DeviceType deviceType) {
        return deviceType + "-" + deviceId;
    }

    public void addDevice(AbstractDevice device) {
        pool.put(genKey(device), device);
    }

    public boolean hasDevice(AbstractDevice device) {
        return pool.containsKey(genKey(device));
    }

    @Deprecated(since = "20221102")
    public boolean hasDevice(String deviceId) {
        return pool.containsKey(deviceId);
    }

    public boolean hasDevice(String deviceId, DeviceType deviceType) {
        String key = genKey(deviceId, deviceType);
        return pool.containsKey(key);
    }

    @Deprecated(since = "20221102")
    public AbstractDevice getDevice(String deviceId) {
        if (pool.containsKey(deviceId)) {
            return pool.get(deviceId);
        }
        throw new DeviceException("设备未加入设备池中，请先添加设备");
    }

    public AbstractDevice getDevice(String deviceId, DeviceType deviceType) {
        String key = genKey(deviceId, deviceType);
        if (pool.containsKey(key)) {
            AbstractDevice device = pool.get(key);
            if (device.getDeviceType() == deviceType) {
                return device;
            } else {
                throw new DeviceException(String.format("设备类型不匹配，当前设备类型为%s", device.getDeviceType()));
            }
        }
        throw new DeviceException("设备未加入设备池中，请先添加设备");
    }

    public List<AbstractDevice> getDevices() {
        return new ArrayList<>(pool.values());
    }

}
