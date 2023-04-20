package com.mega.hephaestus.pms.workflow.listener;

import com.mega.hephaestus.pms.workflow.event.StartWorkflowBeforeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartWorkflowBeforeListener {

    @EventListener
    public void saveFinishedStatus(StartWorkflowBeforeEvent event) {
        System.out.println("StartWorkflowBeforeEvent=========");
        System.out.println(event.toString());


    }
}
