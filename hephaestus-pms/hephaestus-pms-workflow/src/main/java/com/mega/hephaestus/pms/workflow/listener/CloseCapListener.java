package com.mega.hephaestus.pms.workflow.listener;

import com.mega.hephaestus.pms.workflow.event.CloseCapEvent;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceCapManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CloseCapListener {

    @Autowired
    private InstanceCapManager instanceCapManager;

    @EventListener
    public void closeCapEvent(CloseCapEvent event) {
        System.out.println("CloseCapEvent=========");
        System.out.println(event.getInstanceId());

        // 执行关盖
        try {
            instanceCapManager.closeCap(event.getInstanceId());
        } catch (Exception e) {
            log.info("实例id:{}，关盖产生异常", event.getInstanceId());
        }

    }


}

