package com.mega.hephaestus.pms.workflow.manager.plan;

import com.mega.hephaestus.pms.data.model.entity.ResourceEntity;

import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import java.util.List;
import java.util.Optional;

/**
 * @author 胡贤明
 */
public interface DeviceResourceManager {

    /**
     * 根据设备key 获取资源
     * <p>
     * <p>
     * WHERE (device_key = ? AND is_deleted = 0)
     * </p>
     *
     * @param deviceKey 设备key
     * @return Optional 资源
     */
    Optional<DeviceResourceModel> getDeviceResource(String deviceKey);




    /**
     * 获取所有空闲设备
     *
     * @return 空闲设备
     */
    List<DeviceResourceModel> getIdleDevices();

    /**
     * 获取所有运行中的设备
     *
     * @return 空闲设备
     */
    List<DeviceResourceModel> getRunningDevices();

    /**
     * 根据key 修改资源为idle
     *
     * @param deviceKey 资源key
     * @return 是否成功
     */
    boolean idleDeviceResource(String deviceKey);

    /**
     * 根据key 修改资源为running
     *
     * @param deviceKey 资源key
     * @return 是否成功
     */
    boolean runningDeviceResource(String deviceKey);


    /**
     * 获取所有设备资源
     *
     * @return 空闲设备
     */
    List<DeviceResourceModel> getAllDevices();

    /**
     * 获取所有瓶颈资源
     * <p>
     * SELECT * from  hephaestus_device  WHERE (resource_bottleneck = true AND is_deleted = 0)
     * </p>
     *
     * @return 空闲设备
     */
    List<DeviceResourceModel> getBottleneckDevices();

    DeviceResourceModel deviceToResourceModel(ResourceEntity device);


    /**
     * 根据资源组id 获取资源
     *
     * @param experimentType 实验类型
     * @return List<DeviceResourceModel> 资源
     */
    List<DeviceResourceModel> getAllDevices(String experimentType);

    /**
     * 根据资源组id 获取资源(无资源状态)
     *
     * @param experimentType 实验类型
     * @return List<DeviceResourceModel> 资源
     */
    List<DeviceResourceModel> getAllDevicesNoStatus(String experimentType);

}
