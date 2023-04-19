package com.mega.hephaestus.pms.data.runtime.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceStatusEntity;

import java.util.List;
import java.util.Optional;


public interface IResourceStatusService extends IService<ResourceStatusEntity> {

    /**
     * 根据设备key 修改资源状态
     * 存在则修改
     * 不存在则新增
     * @param deviceKey 设备key
     * @param resourceStatus 资源状态
     * @param processRecordId 记录id
     * @return 是否成功
     */
    boolean updateOrInsertResourceStatus(String deviceKey, String resourceStatus,long processRecordId);

    /**
     * 根据 资源key 获取资源状态
     * @param deviceKey 设备key
     * @param processRecordId 记录id
     * @return 资源状态
     */
    Optional<ResourceStatusEntity> getByDeviceKey(String deviceKey, long processRecordId);


    /**
     * 获取当前流程记录的 资源运行状态
     * @param processRecordId  记录id
     * @return 资源状态
     */
    List<ResourceStatusEntity> listByProcessRecordId(long processRecordId);
}

