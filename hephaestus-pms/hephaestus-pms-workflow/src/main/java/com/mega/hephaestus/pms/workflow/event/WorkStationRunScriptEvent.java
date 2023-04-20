package com.mega.hephaestus.pms.workflow.event;

import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.workflow.task.stagetask.WorkstationStep;
import org.springframework.context.ApplicationEvent;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/11 18:28
 */
public class WorkStationRunScriptEvent extends ApplicationEvent {

    private final StepType stepType;

    private final WorkstationStep workstationStep;

    // 实验实例ID
    private final long instanceId;

    public WorkStationRunScriptEvent(Object source, StepType stepType, WorkstationStep workstationStep, long instanceId) {
        super(source);
        this.stepType = stepType;
        this.workstationStep = workstationStep;
        this.instanceId = instanceId;
    }

    public StepType getStepType() {
        return stepType;
    }

    public WorkstationStep getWorkstationStep() {
        return workstationStep;
    }

    public long getInstanceId() {
        return instanceId;
    }

    @Override
    public String toString() {
        return "WorkStationRunScriptEvent{" +
                "stepType=" + stepType +
                ", workstationStep=" + workstationStep +
                ", instanceId=" + instanceId +
                "} " + super.toString();
    }
}
