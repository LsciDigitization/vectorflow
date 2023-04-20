package com.mega.hephaestus.pms.workflow.event;

import com.mega.component.nuc.device.DeviceType;
import org.springframework.context.ApplicationEvent;

/**
 * 实验结束事件
 */
public class CloseCapEvent extends ApplicationEvent {

    private final long instanceId;

    private final DeviceType deviceType;

    private final String deviceKey;

    public CloseCapEvent(long instanceId, DeviceType deviceType, String deviceKey) {
        super(instanceId);
        this.instanceId = instanceId;
        this.deviceType = deviceType;
        this.deviceKey = deviceKey;
    }

    public long getInstanceId() {
        return instanceId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    @Override
    public String toString() {
        return "CloseCapEvent{" +
                "instanceId=" + instanceId +
                ", deviceType=" + deviceType +
                ", deviceKey='" + deviceKey + '\'' +
                "} " + super.toString();
    }
}
