package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.ResourceEntity;

import java.util.List;
import java.util.Optional;


/**
 * 实验资源
 *
 * @author xianming.hu
 */
public interface IResourceService extends IService<ResourceEntity> {


    List<ResourceEntity> getDevices();


    ResourceEntity getByDeviceId(String deviceId);


    /**
     * 根据设备key 获取设备
     * @param deviceKeys key集合
     * @return 设备集合
     */
    List<ResourceEntity> listByDeviceKeys(List<String> deviceKeys);

    /**
     * 根据key 获取对象
     * @param deviceKey key
     * @return 获取对象
     */
    Optional<ResourceEntity> getByDeviceKey(String deviceKey);



    Optional<ResourceEntity> getByDeviceKey(String deviceKey, String resourceId);

    boolean updateResourceStatus(String deviceKey, String  resourceStatus);


    /**
     * 根据资源组id查询资源列表
     * @param projectId 资源组id
     * @return  List<ResourceEntity>
     */
    List<ResourceEntity> listByProjectId(long projectId);
}

