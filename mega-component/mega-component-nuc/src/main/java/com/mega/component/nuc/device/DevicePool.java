package com.mega.component.nuc.device;


/**
 * 不带锁的设备池
 */
public class DevicePool extends AbstractDevicePool {

    private DevicePool() {
    }

    private static final class InstanceHolder {
        private static final DevicePool instance = new DevicePool();
    }

    public static DevicePool getInstance() {
        return InstanceHolder.instance;
    }

}
