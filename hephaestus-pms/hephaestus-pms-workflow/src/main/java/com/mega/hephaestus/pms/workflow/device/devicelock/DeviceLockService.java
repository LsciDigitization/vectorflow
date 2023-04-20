package com.mega.hephaestus.pms.workflow.device.devicelock;

import com.mega.hephaestus.pms.data.model.enums.DeviceTaskStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.service.IDeviceTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 设备锁仪表盘数据管理
 */
@Component
@RequiredArgsConstructor
public class DeviceLockService {

    private final IDeviceTaskService deviceTaskService;

    /**
     * 获取正在运行的任务
     *
     * @param deviceKey 设备key
     * @return HephaestusDeviceTask Optional
     */
    public Optional<ResourceTaskEntity> getRunningTask(String deviceKey) {
        return deviceTaskService.getByDeviceKeyAndStatus(deviceKey, DeviceTaskStatusEnum.Running.getValue());
    }

//    /**
//     * 根据设备类型查询正在等待中的任务
//     *
//     * @param deviceType 设备类型
//     * @return List<HephaestusDeviceTask> 集合
//     */
//    public List<HephaestusDeviceTask> listByDeviceType(String deviceType) {
//        return deviceTaskService.listByDeviceTypeAndTaskStatus(deviceType, DeviceTaskStatusEnum.Await.getValue());
//    }
//
//    public List<HephaestusDeviceTask> listByDeviceType(DeviceType deviceType) {
//        return deviceTaskService.listByDeviceTypeAndTaskStatus(deviceType.getCode(), DeviceTaskStatusEnum.Await.getValue());
//    }


}
