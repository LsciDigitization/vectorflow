package com.mega.hephaestus.pms.workflow.general.workstart;

import com.mega.component.nuc.plate.PlateType;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.model.enums.InstanceTaskStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.workflow.general.config.GeneralPlateTypeEnum;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import com.mega.hephaestus.pms.workflow.work.workstart.AbstractWorkStartPlate;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class WorkStartEmptyPlate extends AbstractWorkStartPlate {

    public WorkStartEmptyPlate() {
    }

    public WorkStartEmptyPlate(WorkBusDaemonResource workBusDaemonResource) {
        super(workBusDaemonResource);
    }


    public Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId, int plateNo) {
        long workEmptySize = getWorkPlateConsumer(GeneralPlateTypeEnum.EMPTY).size(experimentHistoryId);

        if (workEmptySize > 0) {
            log.info("空板队列大小 {}", workEmptySize);
            InstanceLabwareModel instancePlate = getWorkPlateConsumer(GeneralPlateTypeEnum.EMPTY).pop(experimentHistoryId, plateNo);
            if (Objects.nonNull(instancePlate)) {
                this.accept(instancePlate);
                return Optional.of(instancePlate);
            }
        }

        return Optional.empty();
    }

    /**
     * 启动空板线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    public Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId) {
        long workEmptySize = getWorkPlateConsumer(GeneralPlateTypeEnum.EMPTY).size(experimentHistoryId);

        if (workEmptySize > 0) {
            log.info("空板队列大小 {}", workEmptySize);
            InstanceLabwareModel instancePlate = getWorkPlateConsumer(GeneralPlateTypeEnum.EMPTY).pop(experimentHistoryId);
            if (Objects.nonNull(instancePlate)) {
                this.accept(instancePlate);
                return Optional.of(instancePlate);
            }
        }

        return Optional.empty();
    }

    /**
     * 实验过程中，自动启动新板工作线程（New）
     *
     * @param experimentHistoryId 实验组执行ID
     * @param instanceTasks       未完成的任务HephaestusInstanceTask
     */
    public void addStartWorkThread(long experimentHistoryId, List<InstanceTaskEntity> instanceTasks) {
        // 启动空板条件设定
        List<InstanceStepEntity> unfinishedInstanceSteps = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceStepsGreaterEquals(GeneralPlateTypeEnum.STANDARD, StepTypeEnum.STEP11);
        unfinishedInstanceSteps = unfinishedInstanceSteps.stream()
                .filter(a -> {
                    long c = instanceTasks.stream()
                            .filter(b -> b.getInstanceId().equals(a.getInstanceId()) &&
                                    StepTypeEnum.STEP12.getCode().equals(b.getStepKey()) &&
                                    InstanceTaskStatusEnum.Await.getValue().equals(b.getTaskStatus()))
                            .count();
                    log.info("启动空板条件 instanceId {} step {} taskStatus {} {}", a.getInstanceId(), StepTypeEnum.STEP12.getCode(), InstanceTaskStatusEnum.Await.getValue(), c);
                    return c > 0;
                })
                .collect(Collectors.toList());
        long standardAwaitCount = unfinishedInstanceSteps.size();
        log.info("启动空板条件 空板转上清等待数量 {}", standardAwaitCount);

        if (standardAwaitCount >= 4) {
            unfinishedInstanceSteps.stream()
                    .sorted(Comparator.comparing(InstanceStepEntity::getPlateNo))
                    .mapToInt(InstanceStepEntity::getPlateNo)
                    .limit(4)
                    .forEach(v -> {
                        startInstancePlate(experimentHistoryId, v);
                    });
        }
    }


    /**
     * 实验过程中，自动启动新板工作线程
     *
     * @param experimentHistoryId 实验组执行ID
     * @param instanceTasks       未完成的任务HephaestusInstanceTask
     */
    public void addStarteWorkThread2(long experimentHistoryId, List<InstanceTaskEntity> instanceTasks) {
        // 启动空板条件设定
        List<InstanceStepEntity> unfinishedEmptyInstanceStepsAll = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceSteps(GeneralPlateTypeEnum.EMPTY);
        int unfinishedEmptyInstanceStepsSize = unfinishedEmptyInstanceStepsAll.size();

        List<InstanceStepEntity> unfinishedEmptyInstanceSteps = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceSteps(GeneralPlateTypeEnum.EMPTY, StepTypeEnum.STEP12);
        long emptyAwaitCount = unfinishedEmptyInstanceSteps.size();
        log.info("启动空板条件 空板转上清等待数量 {}", emptyAwaitCount);

        if (emptyAwaitCount < 4 || unfinishedEmptyInstanceStepsSize <= 4) {
            long div = 4 - emptyAwaitCount;
            log.info("启动空板条件 空板转上清补充数量 {}", div);
            IntStream intStream = IntStream.rangeClosed(1, (int) div);
            // 取4块空板启动运行
            intStream.forEach(value -> {
                startInstancePlate(experimentHistoryId);
            });
        }
    }


    @Override
    public PlateType plateType() {
        return GeneralPlateTypeEnum.EMPTY;
    }
}
