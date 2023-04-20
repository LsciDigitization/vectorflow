package com.mega.hephaestus.pms.workflow.general.workstart;

import com.mega.component.nuc.plate.PlateType;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.model.enums.InstanceTaskStatusEnum;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessLabwareEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.workflow.general.config.GeneralPlateTypeEnum;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import com.mega.hephaestus.pms.workflow.work.workstart.AbstractWorkStartPlate;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
public class WorkStartPipette1Plate extends AbstractWorkStartPlate {

    public WorkStartPipette1Plate() {
    }

    public WorkStartPipette1Plate(WorkBusDaemonResource workBusDaemonResource) {
        super(workBusDaemonResource);
    }

    /**
     * 启动移液枪头线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    public Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId) {
        long workPipetteSize = getWorkPlateConsumer(GeneralPlateTypeEnum.PIPETTE1).size(experimentHistoryId);

        if (workPipetteSize > 0) {
            log.info("移液枪头1队列大小 {}", workPipetteSize);
            InstanceLabwareModel instancePlate = getWorkPlateConsumer(GeneralPlateTypeEnum.PIPETTE1).pop(experimentHistoryId);
            if (Objects.nonNull(instancePlate)) {
                this.accept(instancePlate);
                return Optional.of(instancePlate);
            }
        }

        return Optional.empty();
    }

    /**
     * 启动移液枪头线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    public Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId, int plateNo) {
        long workPipetteSize = getWorkPlateConsumer(GeneralPlateTypeEnum.PIPETTE1).size(experimentHistoryId);

        if (workPipetteSize > 0) {
            log.info("移液枪头1队列大小 {}", workPipetteSize);
            InstanceLabwareModel instancePlate = getWorkPlateConsumer(GeneralPlateTypeEnum.PIPETTE1).pop(experimentHistoryId, plateNo);
            if (Objects.nonNull(instancePlate)) {
                this.accept(instancePlate);
                return Optional.of(instancePlate);
            }
        }

        return Optional.empty();
    }

    public boolean justPipetteEnabled() {
        Optional<ProcessRecordEntity> runningGroupOptional = workBusDaemonResource.getExperimentGroupHistoryManager().getRunningGroup();
        AtomicBoolean enabled = new AtomicBoolean(false);

        runningGroupOptional.ifPresent(v -> {
            List<ProcessLabwareEntity> processLabwares = workBusDaemonResource.getProcessLabwareManager().getProcessLabwares(v.getId());
            boolean b = processLabwares.stream()
                    .anyMatch(a -> a.getLabwareType().equals(GeneralPlateTypeEnum.PIPETTE1.getCode()));
            enabled.set(b);
        });

        return enabled.get();
    }

    /**
     * 移液枪头1 启动
     * @param experimentHistoryId 实验ID
     * @param instanceTasks 实例任务 InstanceTaskEntity
     */
    public void addStartWorkThread(long experimentHistoryId, List<InstanceTaskEntity> instanceTasks) {
        if (! justPipetteEnabled()) {
            return;
        }

        // 启动移液枪头条件设定
        List<InstanceStepEntity> unfinishedInstanceSteps = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceStepsGreaterEquals(GeneralPlateTypeEnum.STANDARD, StepTypeEnum.STEP5);
        unfinishedInstanceSteps = unfinishedInstanceSteps.stream()
                .filter(a -> {
                    long c = instanceTasks.stream()
                            .filter(b -> b.getInstanceId().equals(a.getInstanceId()) &&
                                    StepTypeEnum.STEP6.getCode().equals(b.getStepKey()) &&
                                    InstanceTaskStatusEnum.Await.getValue().equals(b.getTaskStatus()))
                            .count();
                    log.info("启动移液枪头条件 instanceId {} step {} taskStatus {} {}", a.getInstanceId(), StepTypeEnum.STEP6.getCode(), InstanceTaskStatusEnum.Await.getValue(), c);
                    return c > 0;
                })
                .collect(Collectors.toList());
        long standardAwaitCount = unfinishedInstanceSteps.size();
        log.info("启动移液枪头条件 移液枪头转移与沉淀等待数量 {}", standardAwaitCount);

        if (standardAwaitCount >= 2) {
            unfinishedInstanceSteps.stream()
                    .sorted(Comparator.comparing(InstanceStepEntity::getPlateNo))
                    .mapToInt(InstanceStepEntity::getPlateNo)
                    .limit(2)
                    .forEach(v -> {
                        startInstancePlate(experimentHistoryId, v);
                    });
        }
    }

    @Override
    public PlateType plateType() {
        return GeneralPlateTypeEnum.PIPETTE1;
    }
}
