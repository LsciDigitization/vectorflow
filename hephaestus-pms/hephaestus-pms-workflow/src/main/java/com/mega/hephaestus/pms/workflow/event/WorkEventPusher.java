package com.mega.hephaestus.pms.workflow.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class WorkEventPusher {

    @Autowired
    private ApplicationContext applicationContext;

    public void sendStepCreateEvent(StepCreateEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendStepStartEvent(StepStartEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendStepEndEvent(StepEndEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendInstanceStartedEvent(InstanceStartedEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendInstanceFinishedEvent(InstanceFinishedEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendBottleneckRequiredTimeOfSumEvent(BottleneckRequiredTimeOfSumEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendOpenCapEvent(OpenCapEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendCloseCapEvent(CloseCapEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendStartWorkflowBeforeEvent(StartWorkflowBeforeEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendProcessIterationConsumeEvent(ProcessIterationConsumeEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendStartInstancePlateEvent(StartInstancePlateEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendWorkStationRunScriptEvent(WorkStationRunScriptEvent event) {
        applicationContext.publishEvent(event);
    }

    public void sendDeviceRunningEvent(DeviceRunningEvent event) {
        applicationContext.publishEvent(event);
    }

}
