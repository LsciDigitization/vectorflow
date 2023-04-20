package com.mega.hephaestus.pms.workflow.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mega.hephaestus.pms.data.model.entity.ResourceEntity;
import com.mega.hephaestus.pms.data.model.enums.DeviceTaskStatusEnum;
import com.mega.hephaestus.pms.data.model.service.IResourceService;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.service.IDeviceTaskService;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceStatus;
import com.mega.hephaestus.pms.workflow.exception.DeviceTaskException;
import com.mega.hephaestus.pms.workflow.manager.dynamic.DeviceTaskManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupHistoryManager;
import com.mega.hephaestus.pms.workflow.manager.plan.DeviceResourceManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 11:24
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceTaskManagerImpl implements DeviceTaskManager {

    private final IDeviceTaskService deviceTaskService;

    private final IResourceService resourceService;

    private final ExperimentGroupHistoryManager experimentGroupHistoryManager;

    private final DeviceResourceManager deviceResourceManager;
    /**
     * 创建task
     *
     * @param instanceTask task任务
     * @param deviceKey 设备key
     * @return 创建后的deviceTask对象
     */
    public Optional<ResourceTaskEntity> createTask(InstanceTaskEntity instanceTask,
                                                   String deviceKey) {

        // 根据唯一索引查询 deviceTask
        // WHERE ((instance_id = ? AND task_id = ?) OR (device_key = ? AND task_status = ?))
        LambdaQueryWrapper<ResourceTaskEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .and(wrapper -> wrapper
                        .eq(ResourceTaskEntity::getInstanceId, instanceTask.getInstanceId())
                        .eq(ResourceTaskEntity::getTaskId, instanceTask.getTaskId()))
                .or(wrapper -> wrapper.eq(ResourceTaskEntity::getDeviceKey, deviceKey)
                        .eq(ResourceTaskEntity::getTaskStatus, DeviceTaskStatusEnum.Running.getValue()));

        List<ResourceTaskEntity> deviceTask = deviceTaskService.list(lambdaQueryWrapper);

        log.info("开始验证deviceTask,thread:{},instanceId:{},taskId:{},deviceKey:{},status:{},返回条数:{}",
                Thread.currentThread().getName(), instanceTask.getInstanceId(), instanceTask.getTaskId(),
                deviceKey, DeviceTaskStatusEnum.Running.getValue(), deviceTask.size());
        //  判断当前设备在任务表是否运行
        // 如果为空 则插入
        if (CollectionUtils.isEmpty(deviceTask)) {
            log.info("开始创建deviceTask,thread:{},instanceId:{},taskId:{},deviceKey:{},status:{},返回条数:{}",
                    Thread.currentThread().getName(), instanceTask.getInstanceId(), instanceTask.getTaskId(),
                    deviceKey, DeviceTaskStatusEnum.Running.getValue(), deviceTask.size());

            // 查询当前设备是否在运行
            ResourceEntity device = resourceService.lambdaQuery()
                    .eq(ResourceEntity::getDeviceKey, deviceKey)
                    .eq(ResourceEntity::getResourceStatus, DeviceTaskStatusEnum.Running.getValue()).one();

            Optional<DeviceResourceModel> deviceResource = deviceResourceManager.getDeviceResource(deviceKey);
            // 设备存在 且 正在运行
            boolean flag = deviceResource.isPresent() && DeviceResourceStatus.RUNNING.getCode().equals(deviceResource.get().getResourceStatus().getCode());
            if(!flag){
                // 创建deviceTask
                ResourceTaskEntity newDeviceTask = new ResourceTaskEntity();
                // 设备相关属性
                newDeviceTask.setDeviceType(instanceTask.getDeviceType());
                newDeviceTask.setDeviceKey(deviceKey);
                // 任务相关属性
                newDeviceTask.setTaskId(instanceTask.getTaskId());

                // 设置stepKey
                newDeviceTask.setStepKey(instanceTask.getStepKey());

                newDeviceTask.setTaskCommand(instanceTask.getTaskCommand());
                newDeviceTask.setTaskParameter(instanceTask.getTaskParameter());
                newDeviceTask.setTaskTimeoutSecond(instanceTask.getTimeoutSecond());
                newDeviceTask.setTaskName(instanceTask.getTaskName());
                // 设置requestId
                newDeviceTask.setTaskRequestId(UUID.randomUUID().toString());

                // 设置实验实例相关
                newDeviceTask.setInstanceId(instanceTask.getInstanceId());

                // 默认状态为running
                newDeviceTask.setTaskStatus(DeviceTaskStatusEnum.Running.getValue());
                newDeviceTask.setCreateTime(new Date());
                // 设置记录id
                Optional<ProcessRecordEntity> runningGroup = experimentGroupHistoryManager.getRunningGroup();
                runningGroup.ifPresent(hephaestusExperimentGroupHistory -> newDeviceTask.setProcessRecordId(hephaestusExperimentGroupHistory.getId()));
                // 持久化
                try {
                    deviceTaskService.save(newDeviceTask);
                    return Optional.of(newDeviceTask);
                } catch (Exception e) {
                    log.error("创建deviceTask异常,{}", e.getMessage());
                    return Optional.empty();
                }
            }else{
                log.info("设备运行中，deviceKey:[{}]",deviceKey);
                return Optional.empty();
            }

//            // 当前设备状态为运行 则不允许继续执行
//            if (Objects.isNull(device)) {
//
//
//            } else {
//
//            }
        }

        return Optional.empty();
    }


    /**
     * task 开始进行
     *
     * @param requestId task请求id
     * @return 是否成功
     */
    public ResourceTaskEntity runningTask(String requestId) {
        // 查询task
        Optional<ResourceTaskEntity> deviceTaskOptional = getDeviceTask(requestId);
        ResourceTaskEntity hephaestusDeviceTask;

        // 修改状态
        if (deviceTaskOptional.isPresent()) {
            hephaestusDeviceTask = deviceTaskOptional.get();
            hephaestusDeviceTask.setTaskStatus(DeviceTaskStatusEnum.Running.getValue());
            hephaestusDeviceTask.setStartTime(new Date());
            deviceTaskService.updateById(hephaestusDeviceTask);
            return hephaestusDeviceTask;
        }
        throw new DeviceTaskException(String.format("当前请求id:%s,未找到设备task", requestId));
    }


    /**
     * task 开始进行
     *
     * @param deviceKey 设备key
     * @param requestId task请求id
     * @return 是否成功
     */
    public ResourceTaskEntity runningTask(String requestId, String deviceKey) {
        // 查询task
        Optional<ResourceTaskEntity> deviceTaskOptional = getDeviceTask(requestId);
        ResourceTaskEntity hephaestusDeviceTask;

        // 修改状态
        if (deviceTaskOptional.isPresent()) {
            hephaestusDeviceTask = deviceTaskOptional.get();
            hephaestusDeviceTask.setTaskStatus(DeviceTaskStatusEnum.Running.getValue());
            hephaestusDeviceTask.setStartTime(new Date());
            hephaestusDeviceTask.setDeviceKey(deviceKey);
            deviceTaskService.updateById(hephaestusDeviceTask);
            return hephaestusDeviceTask;
        }
        throw new DeviceTaskException(String.format("当前请求id:%s,未找到设备task", requestId));
    }


    /**
     * 完成
     *
     * @param requestId 实例id
     */
    public ResourceTaskEntity finishedTask(String requestId) {
        // 查询task
        Optional<ResourceTaskEntity> deviceTaskOptional = getDeviceTask(requestId);
        ResourceTaskEntity hephaestusDeviceTask;

        // 修改状态
        if (deviceTaskOptional.isPresent()) {
            hephaestusDeviceTask = deviceTaskOptional.get();
            hephaestusDeviceTask.setTaskStatus(DeviceTaskStatusEnum.Finished.getValue());
            hephaestusDeviceTask.setEndTime(new Date());
            // 计算时间差
            Date startTime = hephaestusDeviceTask.getStartTime();
            long taskDuration = Duration.between(startTime.toInstant(), Instant.now()).toSeconds();
            log.info("任务请求id:{},耗时:{}", requestId, taskDuration);
            hephaestusDeviceTask.setTaskDuration((int) taskDuration);

            deviceTaskService.updateById(hephaestusDeviceTask);
            return hephaestusDeviceTask;
        }
        throw new DeviceTaskException(String.format("当前请求id:%s,未找到设备task", requestId));
    }

    /**
     * 失败
     *
     * @param requestId 实例id
     * @param errorMessage 错误信息
     */
    public ResourceTaskEntity failedTask(String requestId, String errorMessage) {
        // 查询task
        Optional<ResourceTaskEntity> deviceTaskOptional = getDeviceTask(requestId);
        ResourceTaskEntity hephaestusDeviceTask;

        // 修改状态
        if (deviceTaskOptional.isPresent()) {
            hephaestusDeviceTask = deviceTaskOptional.get();
            hephaestusDeviceTask.setTaskStatus(DeviceTaskStatusEnum.Failed.getValue());
            hephaestusDeviceTask.setEndTime(new Date());
            hephaestusDeviceTask.setTaskErrorMessage(errorMessage);
            deviceTaskService.updateById(hephaestusDeviceTask);
            return hephaestusDeviceTask;
        }
        throw new DeviceTaskException(String.format("当前请求id:%s,未找到设备task", requestId));
    }

    /**
     * 检查task 状态
     *
     * @param requestId 实例id
     * @return true 完成，false未完成，异常:失败或者找不到
     */
    public boolean checkTaskFinished(String requestId) {

        Optional<ResourceTaskEntity> deviceTaskOptional = getDeviceTask(requestId);

        if (deviceTaskOptional.isPresent()) {
            ResourceTaskEntity hephaestusDeviceTask = deviceTaskOptional.get();
            String taskStatus = hephaestusDeviceTask.getTaskStatus();
            // 设备task完成
            if (DeviceTaskStatusEnum.Finished.getValue().equals(taskStatus)) {
                return true;
            }
            // 设备task 失败 抛出异常
            else if (DeviceTaskStatusEnum.Failed.getValue().equals(taskStatus)) {
                throw new DeviceTaskException(String.format("当前请求id:%s,task失败", requestId));
            } else {
                return false;
            }
        }

        throw new DeviceTaskException(String.format("当前请求id:%s,未找到设备task", requestId));
    }

    /**
     * 根据请求id 获取设备task
     *
     * @param requestId 请求id
     * @return 设备task
     */
    public Optional<ResourceTaskEntity> getDeviceTask(String requestId) {

   /* List<ResourceTaskEntity> allResourceTasks = getAllResourceTasks();
    return allResourceTasks.stream().filter(v -> v.getTaskRequestId().equals(requestId))
        .findFirst();*/

        if(StringUtils.isNotBlank(requestId)){
            return deviceTaskService.getByRequestId(requestId);
        }
        return Optional.empty();
    }


    /**
     * 根据设备key范围获取可用设备key
     *
     * @param deviceKeyRange 设备范围
     * @return 可用设备key
     */
    public Set<String> getDeviceKey(String deviceKeyRange) {
        if (StringUtils.isBlank(deviceKeyRange)) {
            return Set.of();
        }
        List<String> deviceKeys = Arrays.asList(deviceKeyRange.split(","));

        // 根据key范围查询出来设备
        List<ResourceEntity> devices = resourceService.listByDeviceKeys(deviceKeys);
        if (CollectionUtils.isEmpty(devices)) {
            return Set.of();
        }

        //List<ResourceTaskEntity> allResourceTasks = getAllResourceTasks();

        // 查询正在任务中的key

//        allResourceTasks.stream()
//            .filter(v -> deviceKeys.contains(v.getDeviceKey()))
//            .filter(v -> DeviceTaskStatusEnum.Running.getValue().equals(v.getTaskStatus()))
//            .collect(Collectors.toList());
        List<ResourceTaskEntity> deviceTasks =  deviceTaskService.listByDeviceTypesAndTaskStatus(deviceKeys, DeviceTaskStatusEnum.Running.getValue());
        Set<String> inUseKey = deviceTasks.stream().map(ResourceTaskEntity::getDeviceKey)
                .collect(Collectors.toSet());

        return devices.stream().map(ResourceEntity::getDeviceKey)
                .filter(key -> !inUseKey.contains(key)).collect(Collectors.toSet());

    }


    /**
     * 根据requestIds 查询已经结束或者失败的task
     *
     * @param requestIds 请求ids
     * @return 任务集合
     */
    public List<ResourceTaskEntity> listFinishedAndFailedByRequestIds(Set<String> requestIds) {

        if (CollectionUtils.isNotEmpty(requestIds)) {
            List<String> status = Arrays
                    .asList(DeviceTaskStatusEnum.Failed.getValue(), DeviceTaskStatusEnum.Finished.getValue());

            //List<ResourceTaskEntity> allResourceTasks = getAllResourceTasks();
//      return allResourceTasks.stream()
//          .filter(v -> requestIds.contains(v.getTaskRequestId()))
//          .filter(deviceTask -> status.contains(deviceTask.getTaskStatus()))
//          .collect(Collectors.toList());
            return deviceTaskService.listByRequestIdIn(requestIds).stream()
                    .filter(deviceTask -> status.contains(deviceTask.getTaskStatus()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }


    /**
     * 根据设备获取正在运行的task 如果存在多个，则返回第一个
     *
     * @param deviceKey 设备key
     * @return HephaestusDeviceTask Optional
     */
    public Optional<ResourceTaskEntity> getRunningTask(String deviceKey) {

        if (StringUtils.isNotBlank(deviceKey)) {
            Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                    .getRunningGroup();
            if (runningGroupOptional.isPresent()) {

                //List<ResourceTaskEntity> allResourceTasks = getAllResourceTasks();
//        return allResourceTasks.stream()
//            .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//            .filter(v -> DeviceTaskStatusEnum.Running.getValue().equals(v.getTaskStatus()))
//            .findFirst();

                List<ResourceTaskEntity> list = deviceTaskService.lambdaQuery()
                        .eq(ResourceTaskEntity::getProcessRecordId,
                                runningGroupOptional.get().getId())
                        .eq(ResourceTaskEntity::getDeviceKey, deviceKey)
                        .eq(ResourceTaskEntity::getTaskStatus, DeviceTaskStatusEnum.Running.getValue())
                        .list();
                if (CollectionUtils.isNotEmpty(list)) {
                    return Optional.of(list.get(0));
                }
            } else {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    private List<ResourceTaskEntity> getAllResourceTasks() {
        List<ResourceTaskEntity> list = deviceTaskService.lambdaQuery().list();
        CopyOnWriteArrayList<ResourceTaskEntity> resourceTasks = new CopyOnWriteArrayList<>(list);
        return resourceTasks;
    }



}
