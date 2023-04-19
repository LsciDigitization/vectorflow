package com.mega.hephaestus.pms.data.runtime.service;


import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * 设备task 接口
 *
 * @author xianming.hu
 */
public interface IDeviceTaskService extends IBaseService<ResourceTaskEntity> {


    /**
     * 根据请求id 获取task
     *
     * @param requestId 请求id
     * @return 设备task
     */
    Optional<ResourceTaskEntity> getByRequestId(String requestId);

    /**
     * 根据状态获取集合
     *
     * @param taskStatus 任务状态
     * @return 集合
     */
    List<ResourceTaskEntity> listByTaskStatus(String taskStatus);

    /**
     * 根据设备key和状态获取任务
     *
     * @param deviceKey  设备ket
     * @param taskStatus 状态
     * @return 任务
     */
    Optional<ResourceTaskEntity> getByDeviceKeyAndStatus(String deviceKey, String taskStatus);


    /**
     * 根据设备类型和状态获取任务列表
     *
     * @param deviceType 设备类型
     * @param taskStatus 任务状态
     * @return 集合
     */
    List<ResourceTaskEntity> listByDeviceTypeAndTaskStatus(String deviceType, String taskStatus);

    /**
     * 根据设备key 获取任务
     *
     * @param deviceKeys key集合
     * @return 设备任务集合
     */
    List<ResourceTaskEntity> listByDeviceTypesAndTaskStatus(List<String> deviceKeys, String taskStatus);


    /**
     * 根据设备key和状态查询
     *
     * @param deviceKey  设备key
     * @param taskStatus 状态集合
     * @return 任务集合
     */
    List<ResourceTaskEntity> listByDeviceKeyAndTaskStatus(String deviceKey, List<String> taskStatus);

    List<ResourceTaskEntity> listByTaskIds(List<Long> taskIds);

    /**
     *  根据requestId in查询
     * @param requestIds 集合
     * @return 任务集合
     */
    List<ResourceTaskEntity> listByRequestIdIn(Set<String> requestIds);


    /**
     *  根据资源状态查询
     * @param status 状态
     * @return 任务集合
     */
    List<ResourceTaskEntity> listByStatus(String status);

}

