package com.mega.hephaestus.pms.workflow.general.deviceschedule;

import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessLabwareEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceBusDaemonResource;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceTaskSchedule;
import com.mega.hephaestus.pms.workflow.general.config.GeneralDeviceTypeEnum;
import com.mega.hephaestus.pms.workflow.general.config.GeneralPlateTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bouncycastle.util.Arrays;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 液体工作站设备任务匹配
 */
@Slf4j
//@Component
public class DeviceTaskStarSchedule extends DeviceTaskSchedule {

    public DeviceTaskStarSchedule() {
    }

    public DeviceTaskStarSchedule(DeviceBusDaemonResource deviceBusDaemonResource) {
        super(deviceBusDaemonResource);
    }

    @Override
    public DeviceType deviceType() {
        return GeneralDeviceTypeEnum.Star;
    }

    @Override
    public void taskSchedule(DeviceResourceModel deviceResourceModel, List<InstanceTaskEntity> instanceTasks) {
        /*
        1. 样品与转移：判断任务列表是 < 4 时，跳过，>= 4 时，取前4条任务执行
        必须 AABB 组合

        2. 转上清：判断任务列表是 < 8 时，跳过，>= 8 时，取前8条任务执行
        必须 AAAACCCC 组合
         */
        if (CollectionUtils.isNotEmpty(instanceTasks)) {
            int size = instanceTasks.size();
            if (size < 4) {
                return;
            }

            // 判断是AABB资源板
            long standardCountAA = instanceTasks.stream().filter(v -> StepTypeEnum.STEP6.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode())).count();
            log.info("移液工作站 standardCountAA count {}", standardCountAA);
            long sampleCountBB = instanceTasks.stream().filter(v -> StepTypeEnum.STEP6.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode())).count();
            log.info("移液工作站 sampleCountBB count {}", sampleCountBB);
            long pipette1CountDDDD = instanceTasks.stream().filter(v -> StepTypeEnum.STEP6.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.PIPETTE1.getCode())).count();
            log.info("移液工作站 pipette1CountDDDD count {}", pipette1CountDDDD);

            if (justPipette1Enabled()) {
                // 符合AABBDDDD
                if ( sampleCountBB >= 2 && standardCountAA >= 2 && pipette1CountDDDD >= 2 ) {
                    // 标品板
                    List<InstanceTaskEntity> newTasks = instanceTasks.stream()
                            .filter(v -> StepTypeEnum.STEP6.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode()))
                            .sorted(Comparator.comparing(InstanceTaskEntity::getPlateNo))
                            .limit(2)
                            .collect(Collectors.toList());

                    int[] plateNos = newTasks.stream().mapToInt(InstanceTaskEntity::getPlateNo).toArray();
                    log.info("移液工作站 standardAA plateNos {}", plateNos);
                    // 样品板
                    List<InstanceTaskEntity> newTasks2 = instanceTasks.stream()
                            .filter(v -> StepTypeEnum.STEP6.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode()))
                            .filter(v -> Arrays.contains(plateNos, v.getPlateNo()))
                            .sorted(Comparator.comparing(InstanceTaskEntity::getPlateNo))
                            .limit(2)
                            .collect(Collectors.toList());
                    log.info("移液工作站 样品板 {}", newTasks2);
                    // 移液枪头
                    List<InstanceTaskEntity> newTasks3 = instanceTasks.stream()
                            .filter(v -> StepTypeEnum.STEP6.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.PIPETTE1.getCode()))
                            .sorted(Comparator.comparing(InstanceTaskEntity::getPlateNo))
                            .limit(2)
                            .collect(Collectors.toList());
                    log.info("移液工作站 移液枪头 {}", newTasks3);
                    if (newTasks2.size() == 2 && newTasks3.size() == 2) {
                        newTasks.addAll(newTasks2);
                        newTasks.addAll(newTasks3);
                        newTasks.forEach(v -> log.info("移液工作站Star调度符合AABBDDDD条件的任务 {} 信息 {}", v.getPlateNo(), v));
                        crateDeviceTask(deviceResourceModel, newTasks);
                    }
                }
            } else {
                // 符合AABB
                if ( sampleCountBB >= 2 && standardCountAA >= 2 ) {
                    // 标品板
                    List<InstanceTaskEntity> newTasks = instanceTasks.stream()
                            .filter(v -> StepTypeEnum.STEP6.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode()))
                            .sorted(Comparator.comparing(InstanceTaskEntity::getPlateNo))
                            .limit(2)
                            .collect(Collectors.toList());

                    int[] plateNos = newTasks.stream().mapToInt(InstanceTaskEntity::getPlateNo).toArray();
                    log.info("移液工作站 standardAA plateNos {}", plateNos);
                    // 样品板
                    List<InstanceTaskEntity> newTasks2 = instanceTasks.stream()
                            .filter(v -> StepTypeEnum.STEP6.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.SAMPLE.getCode()))
                            .filter(v -> Arrays.contains(plateNos, v.getPlateNo()))
                            .sorted(Comparator.comparing(InstanceTaskEntity::getPlateNo))
                            .limit(2)
                            .collect(Collectors.toList());
                    log.info("移液工作站 样品板 {}", newTasks2);
                    if (newTasks2.size() == 2) {
                        newTasks.addAll(newTasks2);
                        newTasks.forEach(v -> log.info("移液工作站Star调度符合AABB条件的任务 {} 信息 {}", v.getPlateNo(), v));
                        crateDeviceTask(deviceResourceModel, newTasks);
                    }
                }
            }

            // 判断step4的离心机任务超过4个
            List<InstanceStepEntity> unfinishedInstanceStepsEqualsStep4 = deviceBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceStepsRangeClosed(StepTypeEnum.STEP4, StepTypeEnum.STEP6);
            long waitTaskCountStep4 = unfinishedInstanceStepsEqualsStep4.stream()
                    .filter(v -> Objects.nonNull(v.getStep4StartTime()) && Objects.isNull(v.getStep6StartTime())) // step6_start_time == null
                    .count();
            log.info("判断step5的开盖机任务超过2个 {}", waitTaskCountStep4);
            // 判断step6的任务是否超过4个，超过4个优先第一阶段任务先进行
            List<InstanceStepEntity> unfinishedInstanceStepsEquals = deviceBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceStepsEquals(StepTypeEnum.STEP6);
            long waitTaskCount = unfinishedInstanceStepsEquals.stream()
                    .filter(v -> Objects.isNull(v.getStep6StartTime()))
                    .count();
            log.info("判断step6的任务是否超过4个 {} ", waitTaskCount);
            // 检测到开盖机任务已经开始，或者工作站等待任务已经有一个在等待，则优先第一阶段的工作站任务
            if (waitTaskCount >= 1 || waitTaskCountStep4 >= 1) {
                return;
            }

            // 判断是AAAACCCCEEEE资源板
            long standardCountAAAA = instanceTasks.stream().filter(v -> StepTypeEnum.STEP12.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode())).count();
            log.info("移液工作站 standardCountAAAA count {}", standardCountAAAA);
            long emptyCountCCCC = instanceTasks.stream().filter(v -> StepTypeEnum.STEP12.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.EMPTY.getCode())).count();
            log.info("移液工作站 emptyCountCCCC count {}", emptyCountCCCC);
            long pipette2CountEEEE = instanceTasks.stream().filter(v -> StepTypeEnum.STEP12.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.PIPETTE2.getCode())).count();
            log.info("移液工作站 pipette2CountEEEE count {}", pipette2CountEEEE);

            if (justPipette2Enabled()) {
                // 符合AAAACCCCEEEE
                if ( standardCountAAAA >= 4 && emptyCountCCCC >= 4 && pipette2CountEEEE >= 4 ) {
                    // 标品板
                    List<InstanceTaskEntity> newTasks = instanceTasks.stream()
                            .filter(v -> StepTypeEnum.STEP12.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode()))
                            .sorted(Comparator.comparing(InstanceTaskEntity::getPlateNo))
                            .limit(4)
                            .collect(Collectors.toList());
                    log.info("移液工作站 标品板 {}", newTasks);
                    // 空板
                    List<InstanceTaskEntity> newTasks2 = instanceTasks.stream()
                            .filter(v -> StepTypeEnum.STEP12.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.EMPTY.getCode()))
                            .sorted(Comparator.comparing(InstanceTaskEntity::getPlateNo))
                            .limit(4)
                            .collect(Collectors.toList());
                    log.info("移液工作站 样品板 {}", newTasks2);
                    // 移液枪头
                    List<InstanceTaskEntity> newTasks3 = instanceTasks.stream()
                            .filter(v -> StepTypeEnum.STEP12.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.PIPETTE2.getCode()))
                            .sorted(Comparator.comparing(InstanceTaskEntity::getPlateNo))
                            .limit(4)
                            .collect(Collectors.toList());
                    log.info("移液工作站 移液枪头 {}", newTasks3);
                    newTasks.addAll(newTasks2);
                    newTasks.addAll(newTasks3);
                    crateDeviceTask(deviceResourceModel, newTasks);
                }
            } else {
                // 符合AAAACCCC
                if ( standardCountAAAA >= 4 && emptyCountCCCC >= 4) {
                    // 标品板
                    List<InstanceTaskEntity> newTasks = instanceTasks.stream()
                            .filter(v -> StepTypeEnum.STEP12.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.STANDARD.getCode()))
                            .sorted(Comparator.comparing(InstanceTaskEntity::getPlateNo))
                            .limit(4)
                            .collect(Collectors.toList());
                    log.info("移液工作站 标品板 {}", newTasks);
                    // 空板
                    List<InstanceTaskEntity> newTasks2 = instanceTasks.stream()
                            .filter(v -> StepTypeEnum.STEP12.getCode().equals(v.getStepKey()) && v.getExperimentPoolType().equals(GeneralPlateTypeEnum.EMPTY.getCode()))
                            .sorted(Comparator.comparing(InstanceTaskEntity::getPlateNo))
                            .limit(4)
                            .collect(Collectors.toList());
                    log.info("移液工作站 样品板 {}", newTasks2);
                    newTasks.addAll(newTasks2);
                    crateDeviceTask(deviceResourceModel, newTasks);
                }
            }

        }
    }

    /**
     * 判断移液枪头1是否启用
     */
    private boolean justPipette1Enabled() {
        Optional<ProcessRecordEntity> runningGroupOptional = deviceBusDaemonResource.getExperimentGroupHistoryManager().getRunningGroup();
        AtomicBoolean enabled = new AtomicBoolean(false);

        runningGroupOptional.ifPresent(v -> {
            List<ProcessLabwareEntity> processLabwares = deviceBusDaemonResource.getProcessLabwareManager().getProcessLabwares(v.getId());
            boolean b = processLabwares.stream()
                    .anyMatch(a -> a.getLabwareType().equals(GeneralPlateTypeEnum.PIPETTE1.getCode()));
            enabled.set(b);
        });

        return enabled.get();
    }

    /**
     * 判断移液枪头2是否启用
     */
    private boolean justPipette2Enabled() {
        Optional<ProcessRecordEntity> runningGroupOptional = deviceBusDaemonResource.getExperimentGroupHistoryManager().getRunningGroup();
        AtomicBoolean enabled = new AtomicBoolean(false);

        runningGroupOptional.ifPresent(v -> {
            List<ProcessLabwareEntity> processLabwares = deviceBusDaemonResource.getProcessLabwareManager().getProcessLabwares(v.getId());
            boolean b = processLabwares.stream()
                    .anyMatch(a -> a.getLabwareType().equals(GeneralPlateTypeEnum.PIPETTE2.getCode()));
            enabled.set(b);
        });

        return enabled.get();
    }

}
