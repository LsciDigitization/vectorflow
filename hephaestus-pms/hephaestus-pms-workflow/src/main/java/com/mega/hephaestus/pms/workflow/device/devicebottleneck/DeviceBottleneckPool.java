package com.mega.hephaestus.pms.workflow.device.devicebottleneck;


import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.workflow.device.AbstractPool;

public class DeviceBottleneckPool extends AbstractPool<DeviceBottleneckRegister> {

    public DeviceBottleneckPool() {
    }

    public String key(DeviceType deviceType, StepType stepType) {
        return deviceType.getCode() + "-" + stepType.getCode();
    }

}
