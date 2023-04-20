package com.mega.hephaestus.pms.workflow.device.devicebottleneck;

import com.mega.component.nuc.plate.PlateType;
import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;

public interface DeviceBottleneckRegister extends WorkBottleneckDevice {

    DeviceType deviceType();

    PlateType plateType();

    StepType stepType();

    void setWorkBusDaemonResource(WorkBusDaemonResource workBusDaemonResource);

}
