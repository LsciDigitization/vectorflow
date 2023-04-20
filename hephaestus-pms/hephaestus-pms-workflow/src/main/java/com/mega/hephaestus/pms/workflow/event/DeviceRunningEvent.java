package com.mega.hephaestus.pms.workflow.event;

import com.mega.component.nuc.command.Command;
import com.mega.component.nuc.device.AbstractDevice;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import org.springframework.context.ApplicationEvent;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/17 15:07
 */
public class DeviceRunningEvent extends ApplicationEvent {

    private final StepType stepType;

    private final AbstractDevice device;

    private final Command command;

    private final ResourceTaskEntity resourceTask;

    public DeviceRunningEvent(Object source, StepType stepType, AbstractDevice device, Command command, ResourceTaskEntity resourceTask) {
        super(source);
        this.stepType = stepType;
        this.device = device;
        this.command = command;
        this.resourceTask = resourceTask;
    }

    public StepType getStepType() {
        return stepType;
    }

    public AbstractDevice getDevice() {
        return device;
    }

    public Command getCommand() {
        return command;
    }

    public ResourceTaskEntity getResourceTask() {
        return resourceTask;
    }

    @Override
    public String toString() {
        return "DeviceRunningEvent{" +
                "stepType=" + stepType +
                ", device=" + device +
                ", command=" + command +
                ", resourceTask=" + resourceTask +
                "} " + super.toString();
    }
}
