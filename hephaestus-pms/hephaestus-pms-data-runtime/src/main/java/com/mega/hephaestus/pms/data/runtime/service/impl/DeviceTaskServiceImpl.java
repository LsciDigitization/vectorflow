package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.service.IDeviceTaskService;
import com.mega.hephaestus.pms.data.runtime.mapper.ResourceTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 设备task 实现类
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@DS("runtime")
public class DeviceTaskServiceImpl extends
        ServiceImpl<ResourceTaskMapper, ResourceTaskEntity> implements IDeviceTaskService {


    /**
     * 根据请求id 获取task
     *
     * @param requestId 请求id
     * @return 设备task
     */
    @Override
    public Optional<ResourceTaskEntity> getByRequestId(String requestId) {
        ResourceTaskEntity one = lambdaQuery().eq(ResourceTaskEntity::getTaskRequestId, requestId).one();
        return Optional.ofNullable(one);
    }

    /**
     * 根据状态获取集合
     *
     * @param taskStatus 任务状态
     * @return 集合
     */
    @Override
    public List<ResourceTaskEntity> listByTaskStatus(String taskStatus) {
        List<ResourceTaskEntity> list = lambdaQuery().eq(ResourceTaskEntity::getTaskStatus, taskStatus).list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    /**
     * 根据设备key和状态获取任务
     *
     * @param deviceKey  设备ket
     * @param taskStatus 状态
     * @return 任务
     */

    @Override
    public Optional<ResourceTaskEntity> getByDeviceKeyAndStatus(String deviceKey, String taskStatus) {
        List<ResourceTaskEntity> list = lambdaQuery()
                .eq(ResourceTaskEntity::getDeviceKey, deviceKey)
                .eq(ResourceTaskEntity::getTaskStatus, taskStatus)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return Optional.empty();
        }
        return Optional.ofNullable(list.get(0));
    }

    /**
     * 根据设备类型和状态获取任务列表
     *
     * @param deviceType 设备类型
     * @param taskStatus 任务状态
     * @return 集合
     */
    @Override
    public List<ResourceTaskEntity> listByDeviceTypeAndTaskStatus(String deviceType, String taskStatus) {
        List<ResourceTaskEntity> list = lambdaQuery()
                .eq(ResourceTaskEntity::getDeviceType, deviceType)
                .eq(ResourceTaskEntity::getTaskStatus, taskStatus)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    /**
     * 根据设备key 获取任务
     *
     * @param deviceKeys key集合
     * @return 设备任务集合
     */
    @Override
    public List<ResourceTaskEntity> listByDeviceTypesAndTaskStatus(List<String> deviceKeys, String taskStatus) {
        List<ResourceTaskEntity> list = lambdaQuery()
                .in(CollectionUtils.isNotEmpty(deviceKeys), ResourceTaskEntity::getDeviceKey, deviceKeys)
                .eq(ResourceTaskEntity::getTaskStatus, taskStatus)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    /**
     * 根据设备key和状态查询
     *
     * @param deviceKey  设备key
     * @param taskStatus 状态集合
     * @return 任务集合
     */
    @Override
    public List<ResourceTaskEntity> listByDeviceKeyAndTaskStatus(String deviceKey, List<String> taskStatus) {
        List<ResourceTaskEntity> list = lambdaQuery()
                .eq(ResourceTaskEntity::getDeviceKey, deviceKey)
                .in(CollectionUtils.isNotEmpty(taskStatus), ResourceTaskEntity::getTaskStatus, taskStatus)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    @Override
    public List<ResourceTaskEntity> listByTaskIds(List<Long> taskIds) {
        List<ResourceTaskEntity> list = lambdaQuery()
                .in(CollectionUtils.isNotEmpty(taskIds), ResourceTaskEntity::getTaskId, taskIds)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    /**
     * 根据requestId in查询
     *
     * @param requestIds 集合
     * @return 任务集合
     */
    @Override
    public List<ResourceTaskEntity> listByRequestIdIn(Set<String> requestIds) {
        List<ResourceTaskEntity> list = lambdaQuery()
                .in(ResourceTaskEntity::getTaskRequestId, requestIds)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    /**
     * 根据资源状态查询
     *
     * @param status 状态
     * @return 任务集合
     */
    @Override
    public List<ResourceTaskEntity> listByStatus(String status) {
        List<ResourceTaskEntity> list = lambdaQuery()
                .in(ResourceTaskEntity::getTaskStatus, status)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }


}
