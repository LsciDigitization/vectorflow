package com.mega.hephaestus.pms.workflow.listener;

import com.mega.hephaestus.pms.workflow.event.StepEndEvent;
import com.mega.hephaestus.pms.workflow.manager.ExperimentInstanceStepBackManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceStepManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StepEndListener {

    @Autowired
    ExperimentInstanceStepBackManager experimentInstanceStepBackManager;

    @Autowired
    InstanceStepManager experimentInstanceStepManager;
    @EventListener
    public void startEvent(StepEndEvent event) {
        System.out.println("StepEndEvent=========");
        System.out.println(event.getStep().toString());

        // 插入数据
        // experimentInstanceStepBackManager.endStep(event.getInstanceId(), event.getStep());

        // 插入数据
        try {
            experimentInstanceStepManager.endStep(event.getInstanceId(), event.getStep());
        }catch (Exception e){
            log.error("插入step数据异常{}",e.getMessage());

        }
    }


}

