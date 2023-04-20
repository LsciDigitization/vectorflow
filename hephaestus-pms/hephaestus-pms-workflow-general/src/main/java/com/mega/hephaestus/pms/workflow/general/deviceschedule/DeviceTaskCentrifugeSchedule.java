package com.mega.hephaestus.pms.workflow.general.deviceschedule;

import com.mega.component.commons.date.DateUtil;
import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceBusDaemonResource;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceTaskSchedule;
import com.mega.hephaestus.pms.workflow.general.config.GeneralDeviceTypeEnum;
import com.mega.hephaestus.pms.workflow.general.config.GeneralPlateTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 离心机设备任务匹配
 */
@Slf4j
//@Component
public class DeviceTaskCentrifugeSchedule extends DeviceTaskSchedule {

    public DeviceTaskCentrifugeSchedule() {
    }

    public DeviceTaskCentrifugeSchedule(DeviceBusDaemonResource deviceBusDaemonResource) {
        super(deviceBusDaemonResource);
    }

    @Override
    public DeviceType deviceType() {
        return GeneralDeviceTypeEnum.Centrifuge;
    }

    @Override
    public void taskSchedule(DeviceResourceModel deviceResourceModel, List<InstanceTaskEntity> instanceTasks) {
        /*
        AABB设备算法：
            1）若资源状态为ready（表示为空设备），绑定A板子任务（表示进入一块A板），如果列表里有2块（2或者2B）则绑定2块，如果有4块板子（AABB或者4A/4B）那就绑定4块，并且改变deamon线程状态（唤起该设备线程）；
            2）若资源状态为wait（表示已经唤起，有一块或者三块板子），若为已经存在板子A/B，则绑定任务列表里的A/B；如果列表里还有2个A或2个B则一起绑定进来，改状态为running；

        实现思路：
        1. 判断任务列表是 < 2 时，跳过， = 2 时，取前2条任务执行
            AA
            BB
        2. >= 3 且 != 4 时，跳过，>= 4时，取前4条任务执行
            AABB
            AAAA
            BBBB
         */
        if (CollectionUtils.isNotEmpty(instanceTasks)) {
            int size = instanceTasks.size();
            if (size < 2) {
                return;
            }

            // STEP4 离心机2块、4块板一批
            step4Task(deviceResourceModel, instanceTasks);

            // STEP10 离心机使用4块板一批
            step10Task(deviceResourceModel, instanceTasks);
        }
    }


    void step4Task(DeviceResourceModel deviceResourceModel, List<InstanceTaskEntity> instanceTasks) {
        // 判断是AA或BB资源板
        long standardCount = instanceTasks.stream()
                .filter(v ->
                        v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode()) &&
                                StepTypeEnum.STEP4.getCode().equals(v.getStepKey())
                )
                .count();
        long sampleCount = instanceTasks.stream()
                .filter(v ->
                        v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode()) &&
                                StepTypeEnum.STEP4.getCode().equals(v.getStepKey())
                )
                .count();

        long standardStep3Count = instanceTasks.stream()
                .filter(v ->
                        v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode()) &&
                                StepTypeEnum.STEP3.getCode().equals(v.getStepKey())
                )
                .filter(v -> {
                    // 任务过期等待时间
                    long timeoutSecond = deviceBusDaemonResource.getTaskTimeRateService().getScaledDuration(v.getTimeoutSecond());
                    // 任务到达时间
                    Date startTime = v.getStartTime();
                    // 当前时间
                    Date currentTime = new Date();
                    // 计算任务剩余时间 timeoutSecond - (currentTime - startTime)
                    long remainingTime = timeoutSecond - (DateUtil.getIntervalTime(startTime, currentTime) / 1000);
                    log.info("离心机 {} {} 计算任务剩余时间 {}", GeneralPlateTypeEnum.STANDARD, StepTypeEnum.STEP3, remainingTime);
                    return remainingTime < 60;
                })
                .count();
        long sampleStep3Count = instanceTasks.stream()
                .filter(v ->
                        v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode()) &&
                                StepTypeEnum.STEP3.getCode().equals(v.getStepKey())
                )
                .filter(v -> {
                    // 任务过期等待时间
                    long timeoutSecond = deviceBusDaemonResource.getTaskTimeRateService().getScaledDuration(v.getTimeoutSecond());
                    // 任务到达时间
                    Date startTime = v.getStartTime();
                    // 当前时间
                    Date currentTime = new Date();
                    // 计算任务剩余时间 timeoutSecond - (currentTime - startTime)
                    long remainingTime = timeoutSecond - (DateUtil.getIntervalTime(startTime, currentTime) / 1000);
                    log.info("离心机 {} {} 计算任务剩余时间 {}", GeneralPlateTypeEnum.SAMPLE, StepTypeEnum.STEP3, remainingTime);
                    return remainingTime < 60;
                })
                .count();

        boolean step3IsWait = standardStep3Count > 2 || sampleStep3Count > 2;

        // 判断样品在离心机之前运行的数量 小于120s
//        long sampleStep4BeforeCount = instanceTasks.stream()
//                .filter(v ->
//                        v.getExperimentPoolType().equals(PoolTypeEnum.SAMPLE.getValue()) &&
//                                Objects.nonNull(v.getStepValue()) &&
//                                v.getStepValue() == StepType.STEP3.getValue() &&
//                                v.getTaskStatus().equals(InstanceTaskStatusEnum.Running.getValue())
//                )
//                .peek(instanceTask -> {
//                    // 任务到达时间
//                    Date startTime = instanceTask.getStartTime();
//                    // 任务过期等待时间
//                    long timeoutSecond = deviceBusDaemonResource.getTaskTimeRateService().getScaledDuration(instanceTask.getTimeoutSecond());
//                    // 当前时间
//                    Date currentTime = new Date();
//
//                    // 计算任务剩余时间 timeoutSecond - (currentTime - startTime)
//                    long remainingTime = timeoutSecond - (DateUtil.getIntervalTime(startTime, currentTime) / 1000);
//                    instanceTask.setWillRemainingTimeSecond(remainingTime);
//                })
//                .filter(v -> v.getWillRemainingTimeSecond() < deviceBusDaemonResource.getTaskTimeRateService().getScaledDuration(60))
////                .sorted(Comparator.comparing(InstanceTaskEntity::getWillRemainingTimeSecond, Comparator.nullsLast(Long::compareTo)))
//                .count();

        // 判断标品在离心机之前运行的数量 小于120s
//        long standardStep4BeforeCount = instanceTasks.stream()
//                .filter(v ->
//                        v.getExperimentPoolType().equals(PoolTypeEnum.STANDARD.getValue()) &&
//                                Objects.nonNull(v.getStepValue()) &&
//                                v.getStepValue() == StepType.STEP3.getValue() &&
//                                v.getTaskStatus().equals(InstanceTaskStatusEnum.Running.getValue())
//                )
//                .peek(instanceTask -> {
//                    // 任务到达时间
//                    Date startTime = instanceTask.getStartTime();
//                    // 任务过期等待时间
//                    long timeoutSecond = deviceBusDaemonResource.getTaskTimeRateService().getScaledDuration(instanceTask.getTimeoutSecond());
//                    // 当前时间
//                    Date currentTime = new Date();
//
//                    // 计算任务剩余时间 timeoutSecond - (currentTime - startTime)
//                    long remainingTime = timeoutSecond - (DateUtil.getIntervalTime(startTime, currentTime) / 1000);
//                    instanceTask.setWillRemainingTimeSecond(remainingTime);
//                })
//                .filter(v -> v.getWillRemainingTimeSecond() < deviceBusDaemonResource.getTaskTimeRateService().getScaledDuration(60))
//                .count();

        // 判断样品在离心机之后运行的数量
        long sampleStep4AfterCount = instanceTasks.stream()
                .filter(v ->
                        v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode()) &&
                                Objects.nonNull(v.getStepValue()) &&
                                v.getStepValue() > StepTypeEnum.STEP4.getValue() &&
                                v.getStepValue() <= StepTypeEnum.STEP6.getValue()
                )
                .count();
        // 判断标品在离心机之后运行的数量
        long standardStep4AfterCount = instanceTasks.stream()
                .filter(v ->
                        v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode()) &&
                                Objects.nonNull(v.getStepValue()) &&
                                v.getStepValue() > StepTypeEnum.STEP4.getValue() &&
                                v.getStepValue() <= StepTypeEnum.STEP6.getValue()
                )
                .count();

        // 符合AA
        if ((sampleStep4AfterCount == 0 || standardStep4AfterCount == 2) && standardCount < 2 && sampleCount >= 2 && !step3IsWait) {
            List<InstanceTaskEntity> newTasks = instanceTasks.stream()
                    .filter(v -> StepTypeEnum.STEP4.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode()))
                    .sorted(Comparator.comparing(InstanceTaskEntity::getWillExpireDurationSecond))
                    .limit(2)
                    .collect(Collectors.toList());
            crateDeviceTask(deviceResourceModel, newTasks);
        }
        // 符合BB
        else if ((standardStep4AfterCount == 0 || sampleStep4AfterCount == 2) && sampleCount < 2 && standardCount >= 2 && !step3IsWait) {
            List<InstanceTaskEntity> newTasks = instanceTasks.stream()
                    .filter(v -> StepTypeEnum.STEP4.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode()))
                    .sorted(Comparator.comparing(InstanceTaskEntity::getWillExpireDurationSecond))
                    .limit(2)
                    .collect(Collectors.toList());
            crateDeviceTask(deviceResourceModel, newTasks);
        }

        // Step4 判断是AABB资源板

        // 符合AABB
        if ( sampleCount >= 2 &&
                standardCount >= 2 ) {
            List<InstanceTaskEntity> newTasks = instanceTasks.stream()
                    .filter(v -> StepTypeEnum.STEP4.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode()))

                    .sorted(Comparator.comparing(InstanceTaskEntity::getWillExpireDurationSecond))
                    .limit(2)
                    .collect(Collectors.toList());

//            int[] plateNos = newTasks.stream().mapToInt(InstanceTaskEntity::getPlateNo).toArray();

            List<InstanceTaskEntity> newTasks2 = instanceTasks.stream()
                    .filter(v -> StepTypeEnum.STEP4.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode()))
//                    .filter(v -> Arrays.contains(plateNos, v.getPlateNo()))
                    .sorted(Comparator.comparing(InstanceTaskEntity::getWillExpireDurationSecond))
                    .limit(2)
                    .collect(Collectors.toList());

            if (newTasks2.size() == 2) {
                newTasks.addAll(newTasks2);
                newTasks.forEach(v -> log.info("离心机调度符合AABB条件的任务 {} 信息 {}", v.getPlateNo(), v));
                crateDeviceTask(deviceResourceModel, newTasks);
            }
        }
    }


    void step10Task(DeviceResourceModel deviceResourceModel, List<InstanceTaskEntity> instanceTasks) {
        // Step10 判断是AABB，AAAA, BBBB资源板
        long standardCountStep10 = instanceTasks.stream()
                .filter(v ->
                        v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode()) &&
                                v.getStepKey().equals(StepTypeEnum.STEP10.getCode())
                )
                .count();
        // 符合AAAA
        if (standardCountStep10 >= 4) {
            List<InstanceTaskEntity> newTasks = instanceTasks.stream()
                    .filter(v -> StepTypeEnum.STEP10.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode()))
                    .sorted(Comparator.comparing(InstanceTaskEntity::getWillExpireDurationSecond))
                    .limit(4)
                    .collect(Collectors.toList());
            crateDeviceTask(deviceResourceModel, newTasks);
        }
    }

}
