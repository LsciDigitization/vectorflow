package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.view.HephaestusInstanceTaskView;
import com.mega.hephaestus.pms.data.runtime.mapper.InstanceTaskMapper;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
public class InstanceTaskServiceImpl extends
        ServiceImpl<InstanceTaskMapper, InstanceTaskEntity> implements IInstanceTaskService {


    /**
     * 根据实例id 获取task
     *
     * @param instanceId 实例id
     * @return 设备task
     */
    @Override
    public Optional<InstanceTaskEntity> getByInstanceId(long instanceId) {
        InstanceTaskEntity one = lambdaQuery().eq(InstanceTaskEntity::getInstanceId, instanceId).one();
        return Optional.ofNullable(one);
    }

    /**
     * 根据请求id 获取task
     *
     * @param requestId 请求id
     * @return 设备task
     */
    @Override
    public Optional<InstanceTaskEntity> getByRequestId(String requestId) {
        List<InstanceTaskEntity> instanceTaskList = this.listByRequestId(requestId);
        if (CollectionUtils.isNotEmpty(instanceTaskList)) {
            return Optional.of(instanceTaskList.get(0));
        }
        return Optional.empty();
    }

    /**
     * 根据状态获取集合
     *
     * @param taskStatus 任务状态
     * @return 集合
     */
    @Override
    public List<InstanceTaskEntity> listByTaskStatus(String taskStatus, long processRecordId) {
        List<InstanceTaskEntity> list = lambdaQuery()
                .eq(InstanceTaskEntity::getTaskStatus, taskStatus)
                .eq(InstanceTaskEntity::getProcessRecordId,processRecordId)
                .orderByAsc(InstanceTaskEntity::getPriority)
                .orderByDesc(InstanceTaskEntity::getUpdatePriorityTime)
                .list();
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
    public Optional<InstanceTaskEntity> getByDeviceKeyAndStatus(String deviceKey, String taskStatus) {
        InstanceTaskEntity one = lambdaQuery()
                .eq(InstanceTaskEntity::getDeviceKey, deviceKey)
                .eq(InstanceTaskEntity::getTaskStatus, taskStatus)
                .one();
        return Optional.ofNullable(one);
    }

    /**
     * 根据设备类型和状态获取任务列表
     *
     * @param deviceType 设备类型
     * @param taskStatus 任务状态
     * @return 集合
     */
    @Override
    public List<InstanceTaskEntity> listByDeviceTypeAndTaskStatus(String deviceType, String taskStatus) {
        List<InstanceTaskEntity> list = lambdaQuery()
                .eq(InstanceTaskEntity::getDeviceType, deviceType)
                .eq(InstanceTaskEntity::getTaskStatus, taskStatus)
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
    public List<InstanceTaskEntity> listByDeviceTypesAndTaskStatus(List<String> deviceKeys, String taskStatus) {
        List<InstanceTaskEntity> list = lambdaQuery()
                .in(InstanceTaskEntity::getDeviceKey, deviceKeys)
                .eq(InstanceTaskEntity::getTaskStatus, taskStatus)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    /**
     * 根据实例id 修改 requestId和状态
     *
     * @param instanceId 实例id
     * @param requestId  请求id
     * @param taskStatus 状态
     * @return 是否成功
     */
    @Override
    public boolean updateRequestIdAndTaskStatusByInstanceId(long instanceId, String requestId, String taskStatus) {
        InstanceTaskEntity instanceTask = new InstanceTaskEntity();
        instanceTask.setTaskRequestId(requestId);
        instanceTask.setTaskStatus(taskStatus);
        return lambdaUpdate().eq(InstanceTaskEntity::getInstanceId, instanceId).update(instanceTask);
    }

    @Override
    public List<InstanceTaskEntity> listByTaskStatuses(List<String> taskStatuses) {
        List<InstanceTaskEntity> list = lambdaQuery().in(InstanceTaskEntity::getTaskStatus, taskStatuses).list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    @Override
    public List<InstanceTaskEntity> listByInstanceId(Long instanceId) {
        return lambdaQuery().eq(InstanceTaskEntity::getInstanceId, instanceId).list();
    }

    /**
     * 根据请求id 批量修改状态
     *
     * @param requestIds requestIds
     * @param status     需要修改的状态
     * @return 是否成功
     */
    @Override
    public boolean batchUpdateStatusByRequestIdIn(List<String> requestIds, String status) {
        InstanceTaskEntity instanceTask = new InstanceTaskEntity();
        instanceTask.setTaskStatus(status);
        return lambdaUpdate().in(InstanceTaskEntity::getTaskRequestId, requestIds).update(instanceTask);
    }

    /**
     * 根据requestId查询集合
     *
     * @param requestId requestId
     * @return 集合
     */
    @Override
    public List<InstanceTaskEntity> listByRequestId(String requestId) {
        List<InstanceTaskEntity> list = lambdaQuery()
                .eq(InstanceTaskEntity::getTaskRequestId, requestId)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    @Override
    public List<InstanceTaskEntity> listByStatusAndPriority(String status, Integer priority) {
        List<InstanceTaskEntity> list = lambdaQuery()
                .eq(StringUtils.isNotEmpty(status), InstanceTaskEntity::getTaskStatus, status)
                .eq(Objects.nonNull(priority), InstanceTaskEntity::getInstanceId, priority)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    @Override
    public List<InstanceTaskEntity> listByInstanceIds(List<Long> instanceIds) {
        return lambdaQuery().in(InstanceTaskEntity::getInstanceId, instanceIds).list();
    }

    @Override
    public List<HephaestusInstanceTaskView> listInstanceTaskViewByInstanceIds(Set<Long> instanceIds) {
        if (CollectionUtils.isNotEmpty(instanceIds)) {
            return baseMapper.listInstanceTaskViewInInstanceIds(instanceIds);
        }

        return List.of();
    }

    @Override
    public List<InstanceTaskEntity> listByTaskStatusesAndHistoryId(List<String> taskStatuses, long processRecordId) {
        if (CollectionUtils.isNotEmpty(taskStatuses)) {
            List<InstanceTaskEntity> list = lambdaQuery()
                    .eq(InstanceTaskEntity::getProcessRecordId, processRecordId)
                    .in(InstanceTaskEntity::getTaskStatus, taskStatuses).list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

    /***
     * 根据实验组历史id 查询任务
     * @param processRecordId 实验组历史id
     * @return 任务集合
     */
    @Override
    public List<InstanceTaskEntity> listByHistoryId(long processRecordId) {
        List<InstanceTaskEntity> list = lambdaQuery()
                .eq(InstanceTaskEntity::getProcessRecordId, processRecordId)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }


}
