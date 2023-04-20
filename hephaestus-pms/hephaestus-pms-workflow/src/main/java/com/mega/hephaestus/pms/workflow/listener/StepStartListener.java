package com.mega.hephaestus.pms.workflow.listener;

import com.mega.hephaestus.pms.workflow.event.StepStartEvent;
import com.mega.hephaestus.pms.workflow.event.WorkStationRunScriptEvent;
import com.mega.hephaestus.pms.workflow.manager.ExperimentInstanceStepBackManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceStepManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StepStartListener {

    @Autowired
    ExperimentInstanceStepBackManager experimentInstanceStepBackManager;

    @Autowired
    InstanceStepManager experimentInstanceStepManager;

    @EventListener
    public void startEvent(StepStartEvent event) {
        System.out.println("StartEventListener=========");
        System.out.println(event.getStep().toString());

        // 插入step 数据
        try {
            experimentInstanceStepManager.startStep(event.getInstanceId(), event.getStep());
        } catch (Exception e) {
            log.error("插入step数据异常{}", e.getMessage());
        }
    }


}

