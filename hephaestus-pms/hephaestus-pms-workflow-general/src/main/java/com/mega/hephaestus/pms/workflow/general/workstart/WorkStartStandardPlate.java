package com.mega.hephaestus.pms.workflow.general.workstart;

import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.plate.PlateType;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.workflow.general.config.GeneralDeviceTypeEnum;
import com.mega.hephaestus.pms.workflow.general.config.GeneralPlateTypeEnum;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import com.mega.hephaestus.pms.workflow.work.workstart.AbstractWorkStartPlate;
import com.mega.hephaestus.pms.workflow.work.workstart.WorkStartRegister;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.IntStream;

@Slf4j
public class WorkStartStandardPlate extends AbstractWorkStartPlate {
    public WorkStartStandardPlate() {
    }

    public WorkStartStandardPlate(WorkBusDaemonResource workBusDaemonResource) {
        super(workBusDaemonResource);
    }

    /**
     * 启动标品板线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    @Override
    public Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId) {
        long workStandardSize = getWorkPlateConsumer(GeneralPlateTypeEnum.STANDARD).size(experimentHistoryId);

        if (workStandardSize > 0) {
            log.info("标品队列大小 {}", workStandardSize);

            InstanceLabwareModel instancePlate = getWorkPlateConsumer(GeneralPlateTypeEnum.STANDARD).pop(experimentHistoryId);
            if (Objects.nonNull(instancePlate)) {
                this.accept(instancePlate);
                return Optional.of(instancePlate);
            }
        }

        return Optional.empty();
    }

    /**
     * 启动标品板线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    @Override
    public Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId, int plateNo) {
        long workStandardSize = getWorkPlateConsumer(GeneralPlateTypeEnum.STANDARD).size(experimentHistoryId);

        if (workStandardSize > 0) {
            log.info("标品队列大小 {}", workStandardSize);

            InstanceLabwareModel instancePlate = getWorkPlateConsumer(GeneralPlateTypeEnum.STANDARD).pop(experimentHistoryId, plateNo);
            if (Objects.nonNull(instancePlate)) {
                this.accept(instancePlate);
                return Optional.of(instancePlate);
            }
        }

        return Optional.empty();
    }

    /**
     * 实验过程中，自动启动新板工作线程
     *
     * @param experimentHistoryId 实验组执行ID
     * @param instanceTasks       未完成的任务HephaestusInstanceTask
     */
    public void addStartWorkThread(long experimentHistoryId, List<InstanceTaskEntity> instanceTasks) {
        long workSampleSize = getWorkPlateConsumer(GeneralPlateTypeEnum.SAMPLE).size(experimentHistoryId);
        long workStandardSize = getWorkPlateConsumer(GeneralPlateTypeEnum.STANDARD).size(experimentHistoryId);
        log.info("板池剩余资源计算 workSampleSize {}, workStandardSize {}", workSampleSize, workStandardSize);

        if (workSampleSize == 0 && workStandardSize == 0) {
            log.info("板池都为空 workSampleSize {}, workStandardSize {}，忽略启动瓶颈资源调度计算", workSampleSize, workStandardSize);
            return;
        }

        // 瓶颈资源设备耗时计算
        if (! workBottleneckChecking(experimentHistoryId)) {
            return;
        }

        Optional<WorkStartRegister> workStartSample = workBusDaemonResource.getWorkStartPool().get(GeneralPlateTypeEnum.SAMPLE.getCode());

        // 分别启动样品板和标品板工作线程
        // 需要同时各启动2块板子
        IntStream intStream = IntStream.rangeClosed(1, 2);
        // 启动 标品 x 4 运行
        intStream.forEach(value -> {
            startInstancePlate(experimentHistoryId).ifPresent(v -> {
                // 启动标品序号 与 样品序号 一致板条件
                workStartSample.ifPresent(a -> {
                    a.startInstancePlate(experimentHistoryId, v.getIterationNo());
                });
            });
        });

    }


    private boolean workBottleneckChecking(long experimentHistoryId) {
        Map<DeviceType, List<StepTypeEnum>> stepMap = new HashMap<>();
        stepMap.put(GeneralDeviceTypeEnum.Centrifuge,
                List.of(StepTypeEnum.STEP4, StepTypeEnum.STEP10)
        );
        stepMap.put(GeneralDeviceTypeEnum.IntelliXcap,
                List.of(StepTypeEnum.STEP5)
        );

        // 瓶颈资源设备耗时计算
        double requiredTimeOfSum = stepMap.entrySet().stream()
                .map(m -> {
                    List<StepTypeEnum> steps = m.getValue();
                    double sum1 = steps.stream()
                            .map(v -> {
                                String key = workBusDaemonResource.getDeviceBottleneckPool().key(m.getKey(), v);
                                return workBusDaemonResource.getDeviceBottleneckPool().get(key).orElse(null);
                            })
                            .filter(Objects::nonNull)
                            .mapToDouble(v -> {
                                double sum2 = v.requiredStartTime();
                                log.info("瓶颈资源Step计算 {} {} {}", m.getKey(), v.stepType(), sum2);
                                return sum2;
                            })
                            .filter(v -> v != 0)
                            .max()
                            .orElse(0);
                    log.info("瓶颈资源设备计算 {} {}", m.getKey(), sum1);
                    return sum1;
                })
                .mapToDouble(Double::longValue)
                .filter(v -> v != 0)
                .max()
                .orElse(0)
                ;

        // 如果所需时间小于节点过期限制时间，则启动新任务
        List<HephaestusStageTask> stageTasks = workBusDaemonResource.getExperimentStageTaskManager().getStageTasks(experimentHistoryId, GeneralPlateTypeEnum.STANDARD, StepTypeEnum.STEP6, StepTypeEnum.STEP6);
        Optional<HephaestusStageTask> first = stageTasks.stream().findFirst();
        if (first.isPresent()) {
            HephaestusStageTask hephaestusStageTask = first.get();
            return requiredTimeOfSum != 0 && !(requiredTimeOfSum > hephaestusStageTask.getNextTaskExpireDurationSecond());
        }

        return true;
    }

    @Override
    public PlateType plateType() {
        return GeneralPlateTypeEnum.STANDARD;
    }
}
