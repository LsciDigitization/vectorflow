package com.mega.hephaestus.pms.workflow.listener;

import com.mega.hephaestus.pms.workflow.event.OpenCapEvent;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceCapManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OpenCapListener {

    @Autowired
    private InstanceCapManager instanceCapManager;

    @EventListener
    public void openCapEvent(OpenCapEvent event) {
        System.out.println("OpenCapEvent=========");
        System.out.println(event.getInstanceId());

        // 执行开盖
        try {
            instanceCapManager.openCap(event.getInstanceId(),event.getDeviceType(),event.getDeviceKey());
        } catch (Exception e) {
            log.info("实例id:{}，设备key:{},开盖产生异常", event.getInstanceId(),event.getDeviceKey());
        }
    }


}

