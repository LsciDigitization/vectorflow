package com.mega.hephaestus.pms.workflow.general.listener;

import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.workflow.event.DeviceRunningEvent;
import com.mega.hephaestus.pms.workflow.event.WorkEventPusher;
import com.mega.hephaestus.pms.workflow.event.WorkStationRunScriptEvent;
import com.mega.hephaestus.pms.workflow.general.config.GeneralWorkStationEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 设备运行监听器
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/17 15:11
 */
@Component
@AllArgsConstructor
@Slf4j
public class GeneralDeviceRunningListener {

    private final WorkEventPusher workEventPusher;

    @EventListener
    public void saveDeviceRunningEvent(DeviceRunningEvent event) {
        log.info("DeviceRunningEvent=========\n{}", event.toString());

        // 分发工作站事件
        if (event.getStepType().getCode().equals(StepTypeEnum.STEP6.getCode())) {
            WorkStationRunScriptEvent workStationRunScriptEvent = new WorkStationRunScriptEvent(this, event.getStepType(), GeneralWorkStationEnum.SAMPLE_TRANSFER_AND_PRECIPITATION, event.getResourceTask().getInstanceId());
            workEventPusher.sendWorkStationRunScriptEvent(workStationRunScriptEvent);
        } else if (event.getStepType().getCode().equals(StepTypeEnum.STEP12.getCode())) {
            WorkStationRunScriptEvent workStationRunScriptEvent = new WorkStationRunScriptEvent(this, event.getStepType(), GeneralWorkStationEnum.TRANSFER_SUPERNATANT, event.getResourceTask().getInstanceId());
            workEventPusher.sendWorkStationRunScriptEvent(workStationRunScriptEvent);
        }
    }
}
