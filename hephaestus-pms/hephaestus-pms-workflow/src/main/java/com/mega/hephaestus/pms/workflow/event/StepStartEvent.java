package com.mega.hephaestus.pms.workflow.event;

import com.mega.hephaestus.pms.workflow.work.workstep.Step;
import org.springframework.context.ApplicationEvent;

/**
 * 实验进入事件
 */
public class StepStartEvent extends ApplicationEvent {

    private final Step step;

    // 实验实例ID
    private final long instanceId;

    public StepStartEvent(Step step, long instanceId) {
        super(step);
        this.step = step;
        this.instanceId =instanceId;
    }

    public StepStartEvent(Step step, String instanceId) {
        super(step);
        this.step = step;
        this.instanceId = Long.parseLong(instanceId);
    }

    public Step getStep() {
        return step;
    }

    public long getInstanceId() {
        return instanceId;
    }

    @Override
    public String toString() {
        return "StepStartEvent{" +
                "step=" + step +
                ", instanceId='" + instanceId + '\'' +
                "} " + super.toString();
    }
}
