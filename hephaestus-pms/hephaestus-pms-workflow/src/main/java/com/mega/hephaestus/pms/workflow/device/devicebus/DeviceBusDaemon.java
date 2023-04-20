package com.mega.hephaestus.pms.workflow.device.devicebus;

import com.mega.component.commons.date.DateUtil;
import com.mega.hephaestus.pms.data.model.enums.DeviceTaskStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.hephaestus.pms.nuc.config.WorkProperties;
import com.mega.component.nuc.device.DeviceTypeEnum;
import com.mega.component.nuc.timing.Timing;
import com.mega.hephaestus.pms.workflow.device.deviceschedule.DeviceScheduleRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 设备总线调度线程
 */
@Slf4j
public class DeviceBusDaemon implements Runnable {

    private final DeviceBusDaemonResource deviceBusDaemonResource;

    public DeviceBusDaemon(DeviceBusDaemonResource deviceBusDaemonResource) {
        this.deviceBusDaemonResource = deviceBusDaemonResource;
    }

    @Override
    public void run() {
        log.info("{} running...", this.getClass().getSimpleName());

        WorkProperties workProperties = deviceBusDaemonResource.getWorkProperties();
        if (!workProperties.isEnabled()) {
            return;
        }

        while (true) {
            try {
                log.debug("{} {}", Thread.currentThread().getName(), LocalTime.now());

                // step1 处理任务的优先级
                // 获取所有等待任务，按优化级排列
                Map<String, List<InstanceTaskEntity>> deviceTypeMap = getDeviceTypeMap();

                // step2 调度device空闲设备的任务
                List<DeviceResourceModel> idleDevices = deviceBusDaemonResource.getDeviceResourceManager().getIdleDevices();
                idleDevices.forEach(deviceResourceModel -> {
                    List<InstanceTaskEntity> instanceTasks = deviceTypeMap.get(deviceResourceModel.getDeviceType().getCode());
                    if (CollectionUtils.isNotEmpty(instanceTasks)) {
                        /*
                        获取到空闲设备
                        通过deviceType 匹配任务
                        每种deviceType类型的计算方式不同
                         */
                        String deviceTypeCode = deviceResourceModel.getDeviceType().getCode();
                        if (deviceBusDaemonResource.getDeviceSchedulePool().has(deviceTypeCode)) {
                            Optional<DeviceScheduleRegister> deviceScheduleRegister = deviceBusDaemonResource.getDeviceSchedulePool().get(deviceTypeCode);
                            deviceScheduleRegister.ifPresent(v -> {
                                v.taskSchedule(deviceResourceModel, instanceTasks);
                            });
                        } else {
                            Optional<DeviceScheduleRegister> deviceScheduleRegister = deviceBusDaemonResource.getDeviceSchedulePool().get(DeviceTypeEnum.Unknown.getCode());
                            deviceScheduleRegister.ifPresent(v -> {
                                v.taskSchedule(deviceResourceModel, instanceTasks);
                            });
                        }
                    }
                });


                // step3 调度请求回表任务
                List<InstanceTaskEntity> runningInstanceTasks = deviceBusDaemonResource.getInstanceTaskManager().runningTasks();
                scheduleDeviceResourceBackTask(runningInstanceTasks);

            } catch (Exception e) {
                log.error("{} Daemon run exception: {}", this.getClass().getSimpleName(), e.getMessage());
                e.printStackTrace();
            }

            // 休息一会
            try {
                int sleepSecond = 5;
                long sleepSecond2 = deviceBusDaemonResource.getTaskTimeRateService().getScaledDuration(sleepSecond);

                // 最小0.5秒
                Timing.of(sleepSecond2 * 1000, TimeUnit.MILLISECONDS).sleepMin(500);

            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * 计算任务的优先级
     *
     * @return InstanceTaskEntity List
     */
    List<InstanceTaskEntity> instanceTasksPriorityCalculation() {
        List<InstanceTaskEntity> instanceTasks = deviceBusDaemonResource.getInstanceTaskManager().awaitTasks();

        return instanceTasks.stream()
                .peek(instanceTask -> {
                    // 任务到达时间
                    Date createTime = instanceTask.getCreateTime();
                    // 任务过期等待时间
                    long nextTaskExpireDurationSecond = deviceBusDaemonResource.getTaskTimeRateService().getScaledDuration(instanceTask.getNextTaskExpireDurationSecond());
                    // 当前时间
                    Date currentTime = new Date();

                    // 计算过期时间 nextTaskExpireDurationSecond - (currentTime - createTime)
                    long intervalTime = nextTaskExpireDurationSecond - (DateUtil.getIntervalTime(createTime, currentTime) / 1000);

                    instanceTask.setWillExpireDurationSecond(intervalTime);
                })
                .collect(Collectors.toList());
    }


    /**
     * 按设备类型分组列表
     *
     * @return InstanceTaskEntity list
     */
    Map<String, List<InstanceTaskEntity>> getDeviceTypeMap() {
        List<InstanceTaskEntity> instanceTasks = instanceTasksPriorityCalculation();
        Map<String, List<InstanceTaskEntity>> deviceTypeMap = instanceTasks.stream()
                .collect(Collectors.groupingBy(InstanceTaskEntity::getDeviceType, Collectors.mapping(Function.identity(), Collectors.toList())));

        // 将要到期时间排序，负数优先
        // sorted(Comparator.comparing(InstanceTaskEntity::getWillExpireDurationSecond))
        // 优先级，更新优先级时间排序
        // Comparator.comparing(InstanceTaskEntity::getPriority)
        //                            .thenComparing(InstanceTaskEntity::getUpdatePriorityTime, Comparator.nullsLast(Date::compareTo)).reversed()
        deviceTypeMap.forEach((s, hephaestusInstanceTasks) -> {
            List<InstanceTaskEntity> newInstanceTaskEntities = new ArrayList<>(hephaestusInstanceTasks);
            deviceTypeMap.put(s, newInstanceTaskEntities);
        });

        return deviceTypeMap;
    }


    /**
     * 调度device资源回表的任务
     */
    void scheduleDeviceResourceBackTask(List<InstanceTaskEntity> instanceTasks) {
        // 获取正在运行的任务
        Set<String> requestIdSetRunning = instanceTasks.stream().map(InstanceTaskEntity::getTaskRequestId).collect(Collectors.toSet());
        // 获取已经执行完成的任务
        List<ResourceTaskEntity> hephaestusDeviceTasks = deviceBusDaemonResource.getDeviceTaskManager().listFinishedAndFailedByRequestIds(requestIdSetRunning);
        // 更新任务完成状态
        hephaestusDeviceTasks.forEach(deviceTask -> {
            if (deviceTask.getTaskStatus().equals(DeviceTaskStatusEnum.Finished.getValue())) {
                deviceBusDaemonResource.getInstanceTaskManager().updateTaskFinished(deviceTask.getTaskRequestId());
            } else if (deviceTask.getTaskStatus().equals(DeviceTaskStatusEnum.Failed.getValue())) {
                deviceBusDaemonResource.getInstanceTaskManager().updateTaskFailed(deviceTask.getTaskRequestId(), deviceTask.getTaskErrorMessage());
            }
        });
    }


}
