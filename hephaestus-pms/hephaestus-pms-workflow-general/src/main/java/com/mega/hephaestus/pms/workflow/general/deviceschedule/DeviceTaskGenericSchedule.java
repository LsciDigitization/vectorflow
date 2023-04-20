package com.mega.hephaestus.pms.workflow.general.deviceschedule;

import com.mega.component.nuc.device.DeviceType;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceBusDaemonResource;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceTaskSchedule;
import com.mega.hephaestus.pms.workflow.general.config.GeneralDeviceTypeEnum;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用设备任务匹配
 */
//@Component
public class DeviceTaskGenericSchedule extends DeviceTaskSchedule {

    public DeviceTaskGenericSchedule() {
    }

    public DeviceTaskGenericSchedule(DeviceBusDaemonResource deviceBusDaemonResource) {
        super(deviceBusDaemonResource);
    }

    @Override
    public DeviceType deviceType() {
        return GeneralDeviceTypeEnum.Unknown;
    }

    @Override
    public void taskSchedule(DeviceResourceModel deviceResourceModel, List<InstanceTaskEntity> instanceTasks) {
        // 更新instanceTask 绑定 deviceKey
        // 创建deviceTask，绑定requestId
        if (CollectionUtils.isNotEmpty(instanceTasks)) {
            int size = instanceTasks.size();
            if (size >= 1) {
                List<InstanceTaskEntity> newTasks = instanceTasks.stream()
                        .sorted(Comparator.comparing(InstanceTaskEntity::getWillExpireDurationSecond))
                        .limit(1)
                        .collect(Collectors.toList());
                crateDeviceTask(deviceResourceModel, newTasks);
            }
        }
    }

}
