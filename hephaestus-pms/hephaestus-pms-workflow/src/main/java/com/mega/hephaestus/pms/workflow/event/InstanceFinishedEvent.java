package com.mega.hephaestus.pms.workflow.event;

import org.springframework.context.ApplicationEvent;

public class InstanceFinishedEvent extends ApplicationEvent {

    private final long instanceId;

    public InstanceFinishedEvent(long instanceId) {
        super(instanceId);
        this.instanceId = instanceId;
    }

    public long getInstanceId() {
        return instanceId;
    }

    @Override
    public String toString() {
        return "InstanceFinishedEvent{" +
                "instanceId=" + instanceId +
                '}';
    }
}
