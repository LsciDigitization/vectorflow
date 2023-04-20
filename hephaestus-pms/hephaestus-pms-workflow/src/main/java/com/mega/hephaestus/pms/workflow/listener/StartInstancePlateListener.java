package com.mega.hephaestus.pms.workflow.listener;

import com.mega.hephaestus.pms.workflow.event.StartInstancePlateEvent;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.work.workstart.InstancePlateStarter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/30 17:47
 */
@Component
@Slf4j
public class StartInstancePlateListener {

    @Resource
    private InstancePlateStarter instancePlateStarter;

    @EventListener
    public void startInstancePlateEvent(StartInstancePlateEvent event) {
        System.out.println("StartInstancePlateEvent=========\n" + event);

        Optional<InstanceLabwareModel> instanceLabwareModelOptional = instancePlateStarter.startInstancePlate(event.getProcessRecordId(), event.getPlateType(), event.getIterationNo());
        if (instanceLabwareModelOptional.isEmpty()) {
            log.info("实验组 {} 未找到实例化的板子", event.getProcessRecordId());
            return;
        }

        InstanceLabwareModel instanceLabwareModel = instanceLabwareModelOptional.get();
        log.info("实验组 {} 找到实例化的板子 {}, 启动成功", event.getProcessRecordId(), instanceLabwareModel.getId());
    }

}
