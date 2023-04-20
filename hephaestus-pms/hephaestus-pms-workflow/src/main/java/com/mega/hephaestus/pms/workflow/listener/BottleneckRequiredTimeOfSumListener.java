package com.mega.hephaestus.pms.workflow.listener;

import com.mega.hephaestus.pms.data.runtime.entity.ResourceBottleneckEntity;
import com.mega.hephaestus.pms.workflow.event.BottleneckRequiredTimeOfSumEvent;
import com.mega.hephaestus.pms.workflow.manager.dynamic.DeviceBottleneckManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BottleneckRequiredTimeOfSumListener {

    @Autowired
    private DeviceBottleneckManager deviceBottleneckManager;

    @EventListener
    public void listener(BottleneckRequiredTimeOfSumEvent event) {
        System.out.println("BottleneckRequiredTimeOfSumEvent=========");
        System.out.println(event.toString());

        // 构造数据库对象
        ResourceBottleneckEntity deviceBottleneck = new ResourceBottleneckEntity();
        deviceBottleneck.setDeviceType(event.getDeviceType().getCode());
        deviceBottleneck.setPoolType(event.getPoolType());
        deviceBottleneck.setCalculatingTime(event.getCalculatingTime());
        deviceBottleneck.setRequiredTimeOfSum(event.getRequiredTimeOfSum());
        deviceBottleneck.setWaitTaskCount(event.getBottleneckValue().getWaitTaskCount());
        deviceBottleneck.setExecutionTime(event.getBottleneckValue().getExecutionTime());
        deviceBottleneck.setBeforeTaskCount(event.getBottleneckValue().getBeforeTaskCount());
        deviceBottleneck.setAverageTime(event.getBottleneckValue().getAverageTime());
        deviceBottleneck.setDeviceCount(event.getBottleneckValue().getDeviceCount());
        deviceBottleneck.setDeviceWithPlateNumber(event.getBottleneckValue().getDeviceWithPlateNumber());
        deviceBottleneck.setWaitTimeDuration(event.getBottleneckValue().getWaitTimeDuration());
        deviceBottleneck.setProcessRecordId(event.getBottleneckValue().getProcessRecordId());
        // 插入数据
        deviceBottleneckManager.save(deviceBottleneck);
    }
}
