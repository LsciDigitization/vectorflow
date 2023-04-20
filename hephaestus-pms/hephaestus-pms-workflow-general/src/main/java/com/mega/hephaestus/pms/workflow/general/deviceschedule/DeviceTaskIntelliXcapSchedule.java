package com.mega.hephaestus.pms.workflow.general.deviceschedule;

import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.model.enums.InstanceTaskStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceCapEntity;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceBusDaemonResource;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceTaskSchedule;
import com.mega.hephaestus.pms.workflow.event.CloseCapEvent;
import com.mega.hephaestus.pms.workflow.event.OpenCapEvent;
import com.mega.hephaestus.pms.workflow.general.config.GeneralDeviceTypeEnum;
import com.mega.hephaestus.pms.workflow.general.config.GeneralPlateTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 开关盖机设备任务分配
 */
@Slf4j
//@Component
public class DeviceTaskIntelliXcapSchedule extends DeviceTaskSchedule {
    public DeviceTaskIntelliXcapSchedule() {
    }

    public DeviceTaskIntelliXcapSchedule(DeviceBusDaemonResource deviceBusDaemonResource) {
        super(deviceBusDaemonResource);
    }

    @Override
    public DeviceType deviceType() {
        return GeneralDeviceTypeEnum.IntelliXcap;
    }

    @Override
    public void taskSchedule(DeviceResourceModel deviceResourceModel, List<InstanceTaskEntity> instanceTasks) {
        /*
        开盖后，等移液后，再关盖 是一个完整流程
         */
        // 获取未完成的任务列表
        List<InstanceTaskEntity> unfinishedTasks = deviceBusDaemonResource.getInstanceTaskManager().getUnfinishedTasks();
        Optional<InstanceCapEntity> uncloseCapsOptional = deviceBusDaemonResource.getInstanceCapManager().getUncloseCap(deviceResourceModel.getDeviceKey());
        uncloseCapsOptional.ifPresentOrElse(instanceCap -> {
            // 已开盖处理
            // 获取需要关盖的任务，发起关盖操作
            long awaitCloseCapTaskCount = instanceTasks.stream()
                    .filter(v ->
                            StepTypeEnum.STEP7.getCode().equals(v.getStepKey()) &&
                                    v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode()) &&
                                    Objects.equals(InstanceTaskStatusEnum.Await.getValue(), v.getTaskStatus())
                    )
                    .count();

            if (awaitCloseCapTaskCount > 0) {
                log.info("等待合盖的任务数量 {}", awaitCloseCapTaskCount);

                List<InstanceTaskEntity> newTasks = instanceTasks.stream()
                        .filter(v ->
                                StepTypeEnum.STEP7.getCode().equals(v.getStepKey()) &&
                                        v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode()) &&
                                        Objects.equals(InstanceTaskStatusEnum.Await.getValue(), v.getTaskStatus())
                        )
                        .peek(v -> {
                            log.info("等待合盖的任务 {}", v);
                        })
                        .filter(v -> {
                            return Objects.equals(instanceCap.getInstanceId(), v.getInstanceId());
                        })
                        .peek(v -> {
                            log.info("等待合盖的任务，判断当下分配的设备是否是step5的开盖设备 {}", v);
                        })
                        .sorted(Comparator.comparing(InstanceTaskEntity::getWillExpireDurationSecond))
                        .limit(1)
                        .collect(Collectors.toList());

                if (newTasks.size() > 0) {
                    InstanceTaskEntity instanceTaskEntity = newTasks.get(0);
                    if (instanceTaskEntity != null) {
                        crateDeviceTask(deviceResourceModel, newTasks);
                        // 发送合盖事件
                        deviceBusDaemonResource.getWorkEventPusher().sendCloseCapEvent(new CloseCapEvent(instanceTaskEntity.getInstanceId(), deviceResourceModel.getDeviceType(), deviceResourceModel.getDeviceKey()));
                    }
                }
            }
        }, () -> {
            // 新开盖处理
            // 获取需要开盖的任务，发起开盖操作
            long awaitOpenCapTaskCount = unfinishedTasks.stream()
                    .filter(v ->
                            StepTypeEnum.STEP5.getCode().equals(v.getStepKey()) &&
                                    v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode()) &&
                                    Objects.equals(InstanceTaskStatusEnum.Await.getValue(), v.getTaskStatus())
                    )
                    .count();

            if (awaitOpenCapTaskCount > 0) {
                List<InstanceTaskEntity> newTasks = instanceTasks.stream()
                        .filter(v ->
                                StepTypeEnum.STEP5.getCode().equals(v.getStepKey()) &&
                                        v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode()) &&
                                        Objects.equals(InstanceTaskStatusEnum.Await.getValue(), v.getTaskStatus())
                        )
                        .sorted(Comparator.comparing(InstanceTaskEntity::getWillExpireDurationSecond))
                        .limit(1)
                        .collect(Collectors.toList());

                if (newTasks.size() > 0) {
                    InstanceTaskEntity instanceTaskEntity = newTasks.get(0);
                    if (instanceTaskEntity != null) {
                        crateDeviceTask(deviceResourceModel, newTasks);
                        // 发送开盖事件
                        deviceBusDaemonResource.getWorkEventPusher().sendOpenCapEvent(new OpenCapEvent(instanceTaskEntity.getInstanceId(), deviceResourceModel.getDeviceType(), deviceResourceModel.getDeviceKey()));
                    }
                }
            }

        });

    }
}
