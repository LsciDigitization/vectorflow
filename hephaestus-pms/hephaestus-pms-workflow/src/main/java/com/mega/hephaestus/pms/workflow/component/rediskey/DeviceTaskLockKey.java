package com.mega.hephaestus.pms.workflow.component.rediskey;


import com.mega.component.nuc.device.DeviceType;

public class DeviceTaskLockKey implements RedisKey {

    private static final String redisKeyPrefix = "DeviceTaskLock:";

    private final DeviceType deviceType;
    private final String instanceId;

    public DeviceTaskLockKey(DeviceType deviceType, String instanceId) {
        this.deviceType = deviceType;
        this.instanceId = instanceId;
    }

    public DeviceTaskLockKey(DeviceType deviceType, long instanceId) {
        this.deviceType = deviceType;
        this.instanceId = String.valueOf(instanceId);
    }

    @Override
    public String key() {
        return String.format("%s:%s:%s", redisKeyPrefix, deviceType.getCode(), instanceId);
    }
}
