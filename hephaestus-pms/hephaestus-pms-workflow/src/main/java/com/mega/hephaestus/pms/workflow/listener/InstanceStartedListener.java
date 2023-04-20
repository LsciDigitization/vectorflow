package com.mega.hephaestus.pms.workflow.listener;

import com.mega.hephaestus.pms.workflow.event.InstanceStartedEvent;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceStepManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InstanceStartedListener {

    @Autowired
    private InstanceStepManager instanceStepManager;

    @EventListener
    public void saveFinishedStatus(InstanceStartedEvent event) {
        System.out.println("InstanceStartedEvent=========");
        System.out.println(event.toString());

        instanceStepManager.startInstance(event.getInstanceId());
    }

}
