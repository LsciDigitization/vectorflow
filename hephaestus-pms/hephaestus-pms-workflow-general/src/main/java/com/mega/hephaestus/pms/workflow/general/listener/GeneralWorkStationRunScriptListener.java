package com.mega.hephaestus.pms.workflow.general.listener;

import com.mega.component.bioflow.task.InstanceId;
import com.mega.hephaestus.pms.workflow.event.WorkStationRunScriptEvent;
import com.mega.hephaestus.pms.workflow.general.config.GeneralWorkStationEnum;
import com.mega.hephaestus.pms.workflow.general.manager.GeneralWorkStationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/11 19:34
 */
@Component
@Slf4j
public class GeneralWorkStationRunScriptListener {

    @Autowired
    private GeneralWorkStationManager workStationManager;

    @EventListener
    public void startEvent(WorkStationRunScriptEvent event) {
        log.info("WorkStationRunScriptListener=========");
        log.info("{}", event.toString());

        if (event.getWorkstationStep().equals(GeneralWorkStationEnum.SAMPLE_TRANSFER_AND_PRECIPITATION)) {
            log.info("SAMPLE_TRANSFER_AND_PRECIPITATION");
            workStationManager.transferWithPlan(new InstanceId(event.getInstanceId()), event.getStepType());
        } else if (event.getWorkstationStep().equals(GeneralWorkStationEnum.TRANSFER_SUPERNATANT)) {
            log.info("TRANSFER_SUPERNATANT");
            workStationManager.transferWithPlan(new InstanceId(event.getInstanceId()), event.getStepType());
        }
    }

}
