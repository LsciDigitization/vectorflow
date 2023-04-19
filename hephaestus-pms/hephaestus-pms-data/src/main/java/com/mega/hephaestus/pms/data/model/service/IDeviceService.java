package com.mega.hephaestus.pms.data.model.service;


import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.model.entity.DeviceEntity;

import java.util.List;
import java.util.Optional;


/**
 * 硬件配置表
 * @author xianming.hu
 */
public interface IDeviceService extends IBaseService<DeviceEntity> {

    /**
     * 根据设备名称查询设备
     * @param deviceName 设备名称
     * @return  DeviceEntity 设备对象
     */
    Optional<DeviceEntity> getByDeviceName(String deviceName);



    List<DeviceEntity> getDevices();


    DeviceEntity getByDeviceId(String deviceId);

    /**
     * 根据设备key 获取设备
     * @param deviceKeys key集合
     * @return 设备集合
     */
    List<DeviceEntity> listByDeviceKeys(List<String> deviceKeys);


    List<DeviceEntity> getDevices(List<String> deviceKeys);

    /**
     * 根据key 获取对象
     * @param deviceKey key
     * @return 获取对象
     */
    Optional<DeviceEntity> getByDeviceKey(String deviceKey);


    boolean updateResourceStatus(String deviceKey, String  resourceStatus);


    /**
     * 根据多个key和状态获取设备
     * @param deviceKeys 设备key
     * @param status 状态
     * @return  设备列表
     */
    List<DeviceEntity> listByDeviceKeysAndResourceStatus(List<String> deviceKeys, String status);


    List<DeviceEntity> listByProjectId(long projectId);


    /**
     * 根据项目id 获取存储类设备
     * @param projectId 项目id
     * @return 存储类设备
     */
    List<DeviceEntity> listStorageByProjectId(long projectId);

}

