package com.mega.hephaestus.pms.workflow.device.devicelock;

import com.mega.hephaestus.pms.workflow.device.devicetask.DeviceTaskEntity;


public class GenericDeviceLock implements DeviceLock {

    private final String deviceKey;

    public GenericDeviceLock(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    @Override
    public boolean lock(DeviceTaskEntity anEntry, Long priority) {
        return false;
    }

    @Override
    public boolean unlock(DeviceTaskEntity anEntry) {
        return false;
    }

    @Override
    public boolean acquireLock(DeviceTaskEntity anEntry) {
        return false;
    }


}
