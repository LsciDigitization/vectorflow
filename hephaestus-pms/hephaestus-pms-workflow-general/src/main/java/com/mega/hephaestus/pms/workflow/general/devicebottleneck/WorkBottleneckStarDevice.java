package com.mega.hephaestus.pms.workflow.general.devicebottleneck;

import com.mega.component.commons.date.DateUtil;
import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.plate.PlateType;
import com.mega.component.nuc.step.StepType;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.model.enums.InstanceTaskStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.workflow.device.devicebottleneck.AbstractWorkBottleneckDevice;
import com.mega.hephaestus.pms.workflow.device.devicebottleneck.DeviceBottleneckRegister;
import com.mega.hephaestus.pms.workflow.device.devicebus.DeviceResourceModel;
import com.mega.hephaestus.pms.workflow.general.config.GeneralDeviceTypeEnum;
import com.mega.hephaestus.pms.workflow.general.config.GeneralPlateTypeEnum;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class WorkBottleneckStarDevice extends AbstractWorkBottleneckDevice implements DeviceBottleneckRegister {

    private final DeviceType deviceType;

    private final PlateType poolType;

    private final StepType firstStep;

    private final StepType startStep;

    private final StepType endStep;

    public WorkBottleneckStarDevice() {
        this.deviceType = GeneralDeviceTypeEnum.Star;
        this.poolType = GeneralPlateTypeEnum.STANDARD;
        this.startStep = StepTypeEnum.STEP12;  // STEP6 STEP12
        this.endStep = StepTypeEnum.STEP12; // STEP6 STEP12
        this.firstStep = StepTypeEnum.STEP1;
    }

    public WorkBottleneckStarDevice(WorkBusDaemonResource workBusDaemonResource) {
        super(workBusDaemonResource);

        this.deviceType = GeneralDeviceTypeEnum.Star;
        this.poolType = GeneralPlateTypeEnum.STANDARD;
        this.startStep = StepTypeEnum.STEP12;  // STEP6 STEP12
        this.endStep = StepTypeEnum.STEP12; // STEP6 STEP12
        this.firstStep = StepTypeEnum.STEP1;
    }

    @Override
    protected DeviceType getDeviceType() {
        return this.deviceType;
    }

    @Override
    protected PlateType getPlateType() {
        return this.poolType;
    }

    @Override
    public int getBottleneckWaitTaskCount() {
        // 等待 瓶颈资源 任务数量
        List<InstanceStepEntity> unfinishedInstanceStepsEquals = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceStepsEquals(poolType, startStep);
        long waitTaskCount = unfinishedInstanceStepsEquals.stream()
                .filter(v -> Objects.isNull(v.getStep11StartTime()))
                .count();
        log.info("{} {} 等待开盖任务数量 {}", deviceType, poolType, waitTaskCount);
        return (int) waitTaskCount;
    }

    @Override
    public long getBottleneckWaitTimeDuration() {
        // 等待瓶颈资源的所有任务等待时间
        List<InstanceStepEntity> unfinishedInstanceStepsEquals = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceStepsGreaterEquals(poolType, startStep);
        double waitAverage = unfinishedInstanceStepsEquals.stream()
                .filter(v -> Objects.nonNull(v.getStep12WaitingSecond()))
                .sorted(Comparator.comparing(InstanceStepEntity::getStep4StartTime, Comparator.nullsLast(Date::compareTo)).reversed())
                .limit(4)
                .mapToLong(v -> v.getStep11IntervalSecond() + v.getStep12WaitingSecond())
                .average()
                .orElse(0);
        // 四舍五入
        long waitAverageLong = Math.round(waitAverage);
        log.info("{} {} 等待瓶颈资源的上一批任务平均等待时间 {}", deviceType, poolType, waitAverageLong);
        long waitSumDuration = (getBottleneckWaitTaskCount() + getBottleneckBeforeTaskCount()) * waitAverageLong;
        log.info("{} {} 等待瓶颈资源的所有任务等待时间 {}", deviceType, poolType, waitSumDuration);
        return waitSumDuration;
    }

    @Override
    public long getBottleneckExecutionTimeDuration() {
        Optional<ProcessRecordEntity> runningGroupOptional = workBusDaemonResource.getExperimentGroupHistoryManager().getRunningGroup();
        if (runningGroupOptional.isEmpty()) {
            return 0;
        }

        ProcessRecordEntity experimentGroupHistory = runningGroupOptional.get();
        long experimentHistoryId = experimentGroupHistory.getId();

        // 瓶颈资源执行时间
        long experimentId = workBusDaemonResource.getInstanceLabwareManager().getExperimentId(poolType.getCode(), experimentHistoryId);
        List<HephaestusStageTask> stageTasks = workBusDaemonResource.getExperimentStageTaskManager().getStageTasks(experimentId, startStep, endStep);
        int executionTime = stageTasks.stream().mapToInt(HephaestusStageTask::getTimeoutSecond).sum();
        log.info("{} {} 瓶颈资源执行时间 {}", deviceType, poolType, executionTime);
        return executionTime;
    }

    @Override
    public int getBottleneckBeforeTaskCount() {
        // 到达 瓶颈资源 任务前的任务数量
        List<InstanceStepEntity> unfinishedInstanceSteps = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceSteps(poolType, startStep, false);
        int arriveCapBeforeCount = unfinishedInstanceSteps.size();
        log.info("{} {} 到达开盖任务前的任务数量 {}", deviceType, poolType, arriveCapBeforeCount);
        return arriveCapBeforeCount;
    }

    @Override
    public long getBottleneckAverageRemainTimeDuration() {
        // 瓶颈资源 当前执行剩余时间（有多个则取平均值）
        List<InstanceStepEntity> bottleneckTasks = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceStepsEquals(poolType, startStep);
        double averageTime = bottleneckTasks.stream()
                .mapToLong(v -> {
                    List<InstanceTaskEntity> step5Tasks = workBusDaemonResource.getInstanceTaskManager().getInstanceTasks(v.getInstanceId(), startStep, startStep);
                    if (step5Tasks.size() == 1) {
                        Optional<InstanceTaskEntity> first = step5Tasks.stream().findFirst();
                        InstanceTaskEntity instanceTaskEntity = first.get();
                        if (instanceTaskEntity.getTaskStatus().equals(InstanceTaskStatusEnum.Running.getValue())) {
                            Date startTime = instanceTaskEntity.getStartTime();
                            Date currentTime = new Date();
                            long middle = DateUtil.getIntervalTime(startTime, currentTime) / 1000;
                            middle = workBusDaemonResource.getTaskTimeRateService().getScaledDuration(middle);
                            long totalSecond = instanceTaskEntity.getTimeoutSecond();
                            // 计算剩余时间 totalSecond - (currentTime - startTime)
                            return totalSecond - middle;
                        }
                    }
                    return -1;
                })
                .filter(v -> v > 0)
                .average()
                .orElse(0);
        log.info("{} {} 瓶颈资源当前执行剩余时间（有多个则取平均值） {}", deviceType, poolType, averageTime);

        return Double.valueOf(Math.ceil(averageTime)).longValue();
    }

    @Override
    public int getBottleneckDeviceCount() {
        List<DeviceResourceModel> bottleneckDevices = workBusDaemonResource.getDeviceResourceManager().getBottleneckDevices();
        List<DeviceResourceModel> capDevices = bottleneckDevices.stream()
                .filter(v -> v.getDeviceType().getCode().equals(deviceType.getCode()))
                .collect(Collectors.toList());

        // 瓶颈资源数量
        int bottleneckDeviceCount = capDevices.size();
        log.info("{} {} 瓶颈资源数量 {}", deviceType, poolType, bottleneckDeviceCount);
        return bottleneckDeviceCount;
    }

    @Override
    public int getBottleneckWithPlateNumber() {
        List<DeviceResourceModel> bottleneckDevices = workBusDaemonResource.getDeviceResourceManager().getBottleneckDevices();
        List<DeviceResourceModel> capDevices = bottleneckDevices.stream()
                .filter(v -> v.getDeviceType().getCode().equals(deviceType.getCode()))
                .collect(Collectors.toList());

        // 瓶颈资源带板数
        List<Integer> integers = capDevices.stream().findFirst().map(DeviceResourceModel::getResourcePlateNumber).orElse(List.of());
        int withPlateNumber = integers.stream().min(Comparator.comparing(Integer::valueOf)).orElse(1);
        log.info("{} {} 瓶颈资源带板数 {}", deviceType, poolType, withPlateNumber);
        return withPlateNumber;
    }

    @Override
    public long getBottleneckArrivalTimeDuration() {
        Optional<ProcessRecordEntity> runningGroupOptional = workBusDaemonResource.getExperimentGroupHistoryManager().getRunningGroup();
        if (runningGroupOptional.isEmpty()) {
            return 0;
        }

        ProcessRecordEntity experimentGroupHistory = runningGroupOptional.get();
        long experimentHistoryId = experimentGroupHistory.getId();

        long experimentId = workBusDaemonResource.getInstanceLabwareManager().getExperimentId(poolType.getCode(), experimentHistoryId);
        // 到达瓶颈资源前所需的时间
        List<HephaestusStageTask> stageTasks2 = workBusDaemonResource.getExperimentStageTaskManager().getStageTasks(experimentId, firstStep, false, startStep, true);
        int arrivalTime = stageTasks2.stream().mapToInt(HephaestusStageTask::getTimeoutSecond).sum();
        log.info("{} {} 到达瓶颈资源前所需的时间 {}", deviceType, poolType, arrivalTime);
        return arrivalTime;
    }

    @Override
    public DeviceType deviceType() {
        return this.deviceType;
    }

    @Override
    public PlateType plateType() {
        return this.poolType;
    }

    @Override
    public StepType stepType() {
        return this.startStep;
    }
}
