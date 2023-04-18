package com.mega.component.nuc.device;

/**
 * 带设备锁的设备池
 */
public class DeviceLockPool extends AbstractDevicePool {

    private DeviceLockPool() {
    }

    private static final class InstanceHolder {
        private static final DeviceLockPool instance = new DeviceLockPool();
    }

    public static DeviceLockPool getInstance() {
        return InstanceHolder.instance;
    }

}
