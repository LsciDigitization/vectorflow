package com.mega.hephaestus.pms.workflow.event;

import com.mega.hephaestus.pms.workflow.work.workstep.Step;
import org.springframework.context.ApplicationEvent;

/**
 * 实验结束事件
 */
public class StepEndEvent extends ApplicationEvent {

    private final Step step;

    private final long instanceId;

    public StepEndEvent(Step step, long instanceId) {
        super(step);
        this.step = step;
        this.instanceId = instanceId;
    }
    public StepEndEvent(Step step, String instanceId) {
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
        return "StepEndEvent{" +
                "step=" + step +
                ", instanceId='" + instanceId + '\'' +
                "} " + super.toString();
    }
}
