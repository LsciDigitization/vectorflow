package com.mega.hephaestus.pms.workflow.listener;

import com.mega.hephaestus.pms.workflow.event.StepCreateEvent;
import com.mega.hephaestus.pms.workflow.manager.ExperimentInstanceStepBackManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceStepManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StepCreateListener {

    @Autowired
    ExperimentInstanceStepBackManager experimentInstanceStepBackManager;

    @Autowired
    InstanceStepManager experimentInstanceStepManager;

    @EventListener
    public void startEvent(StepCreateEvent  event) {
        System.out.println("StepCreateEvent=========");
        System.out.println(event.getStep().toString());
        // 插入数据
        // experimentInstanceStepBackManager.startStep(event.getInstanceId(), event.getStep());

        // 插入step 数据
        try {
            experimentInstanceStepManager.createStep(event.getInstanceId(), event.getStep());
        }catch (Exception e){
            log.error("插入step数据异常{}",e.getMessage());
        }

    }


}

