package com.mega.hephaestus.pms.workflow.general.devicebottleneck;

import com.mega.component.nuc.device.DeviceType;
import com.mega.component.nuc.plate.PlateType;
import com.mega.component.nuc.step.StepType;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
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
import java.util.stream.LongStream;

@Slf4j
public class WorkBottleneckIntelliXcapDevice extends AbstractWorkBottleneckDevice implements DeviceBottleneckRegister {

    private final DeviceType deviceType;

    private final GeneralPlateTypeEnum poolType;

    private final StepTypeEnum firstStep;

    private final StepTypeEnum startStep;

    private final StepTypeEnum endStep;

    public WorkBottleneckIntelliXcapDevice() {
        this.deviceType = GeneralDeviceTypeEnum.IntelliXcap;
        this.poolType = GeneralPlateTypeEnum.SAMPLE;
        this.startStep = StepTypeEnum.STEP5;
        this.endStep = StepTypeEnum.STEP7;
        this.firstStep = StepTypeEnum.STEP2;
    }

    public WorkBottleneckIntelliXcapDevice(WorkBusDaemonResource workBusDaemonResource) {
        super(workBusDaemonResource);

        this.deviceType = GeneralDeviceTypeEnum.IntelliXcap;
        this.poolType = GeneralPlateTypeEnum.SAMPLE;
        this.startStep = StepTypeEnum.STEP5;
        this.endStep = StepTypeEnum.STEP7;
        this.firstStep = StepTypeEnum.STEP2;
    }

    @Override
    protected DeviceType getDeviceType() {
        return this.deviceType;
    }

    @Override
    protected PlateType getPlateType() {
        return this.poolType;
    }

    /*
    step8 封穿刺膜 1min
    step7 加有机试剂 / 关盖 1min
    step6 样品转移与沉淀 18min
    step5 撕膜 / 开盖 1min
    step4 离心 2min
    step3 室温震荡 5min
    step2 室温解冻 15min

    到达离心 15 + 5 + 2 * 1 = 22min
    到达开盖 15 + 5 + 2 + 3 * 1 = 25min
    到达转移 15 + 5 + 2 + 1 + 4 * 1 = 27min
    到达封膜 15 + 5 + 2 + 1 + 18 + 1 + 1 + 7 * 1 = 46min

    查询请求资源列表里的当前节点属性为瓶颈对象的任务对象
    计算需要的时间总和 = Wait在瓶颈资源的所有任务数量 * 瓶颈资源执行时间 +
        到达瓶颈资源前的所有任务 * （到达瓶颈资源前所需的时间 + 瓶颈资源执行时间） +
        瓶颈资源当前执行剩余时间（有多个则取平均值）/ 瓶颈资源数量 * 瓶颈资源带板数 -
        启动新线程抵达瓶颈资源所需时间

    √ Wait在瓶颈资源的所有任务数量 = 开盖
    √ 瓶颈资源执行时间 = 开盖 + 转移 + 关盖 + 3 * 1 = 23min
    √ 到达瓶颈资源前的所有任务 = 到达开盖前的所有任务
    √ 到达瓶颈资源前所需的时间 = 到达开盖前所需要的时间 25min
    √ 瓶颈资源数量 = 开关盖机数量 2
    √ 瓶颈资源带板数 = 开并盖机带板数 1
    √ 启动新线程抵达瓶颈资源所需时间 = 到达开盖前所需要的时间 25min
    √ 瓶颈资源当前执行剩余时间（有多个则取平均值）

     */

    @Override
    public int getBottleneckWaitTaskCount() {
        // 等待开盖任务数量
        List<InstanceStepEntity> unfinishedInstanceStepsEquals = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceStepsEquals(poolType, startStep);
        long waitTaskCount = unfinishedInstanceStepsEquals.stream()
                .filter(v -> Objects.isNull(v.getStep6StartTime()))
                .count();
        log.info("{} {} 等待开盖任务数量 {}", deviceType, poolType, waitTaskCount);
        return (int) waitTaskCount + getStep6WaitTaskCount();
    }

    private int getStep6WaitTaskCount() {
        PlateType poolType1 = GeneralPlateTypeEnum.STANDARD;
        StepType startStep1 = StepTypeEnum.STEP6;
        // 等待转移任务数量
        List<InstanceStepEntity> unfinishedInstanceStepsEquals = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceStepsEquals(poolType1, startStep1);
        long waitCapCount = unfinishedInstanceStepsEquals.stream()
                .filter(v -> Objects.isNull(v.getStep6StartTime()))
                .count();
        log.info("{} {} 等待转移任务数量 {}", deviceType, poolType1, waitCapCount);
        return (int) waitCapCount;
    }

    @Override
    public long getBottleneckWaitTimeDuration() {
        // 等待瓶颈资源的所有任务等待时间
        List<InstanceStepEntity> unfinishedInstanceStepsEquals = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceStepsGreaterEquals(poolType, startStep);
        double waitAverage = unfinishedInstanceStepsEquals.stream()
                .filter(v -> Objects.nonNull(v.getStep5WaitingSecond()))
                // 等待开盖机的所有任务，step5的开始时间
                .sorted(Comparator.comparing(InstanceStepEntity::getStep5StartTime, Comparator.nullsLast(Date::compareTo)).reversed())
                // 取上一批4个任务
                .limit(4)
                // 取上一步的step4的间隔时间 + step5的等待时间
                .mapToLong(v -> v.getStep4IntervalSecond() + v.getStep5WaitingSecond())
                // 取平均数
                .average()
                // 为空时，默认值480
                .orElse(0);
        // 四舍五入
        long waitAverageLong = Math.round(waitAverage);
        log.info("{} {} 等待瓶颈资源的上一批任务平均等待时间 {}", deviceType, poolType, waitAverageLong);
        // 上一批任务任务在瓶颈资源处的等待时间 * (到达瓶颈资源前的所有任务 + Wait在瓶颈资源的所有任务数量)
//        long waitSumDuration = (getBottleneckWaitTaskCount() + getBottleneckBeforeTaskCount()) * waitAverageLong;
        long waitSumDuration = (getBottleneckWaitTaskCount()) * waitAverageLong;
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
        // 到达开盖任务前的任务数量
        List<InstanceStepEntity> unfinishedInstanceSteps = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceSteps(poolType, startStep, false);
        int arriveBeforeCount = unfinishedInstanceSteps.size();
        log.info("{} {} 到达开盖任务前的任务数量 {}", deviceType, poolType, arriveBeforeCount);
        return arriveBeforeCount + getStep6BeforeTaskCount();
    }

    private int getStep6BeforeTaskCount() {
        PlateType poolType1 = GeneralPlateTypeEnum.STANDARD;
        StepType startStep1 = StepTypeEnum.STEP6;
        StepType endStep1 = StepTypeEnum.STEP6;

        // 到达转前任务前的任务数量
        List<InstanceStepEntity> unfinishedInstanceSteps = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceSteps(poolType1, startStep1, false);
        int arriveCapBeforeCount = unfinishedInstanceSteps.size();
        log.info("{} {} 到达转移任务前的任务数量 {}", deviceType, poolType1, arriveCapBeforeCount);
        return arriveCapBeforeCount;
    }

    private long getStep5AverageRemainTimeDuration() {
        return getAverageRemainTimeDuration(deviceType, poolType, startStep, endStep, InstanceStepEntity::getStep5StartTime);
    }

    private long getStep12AverageRemainTimeDuration() {
        DeviceType deviceType1 = GeneralDeviceTypeEnum.Star;
        PlateType poolType1 = GeneralPlateTypeEnum.STANDARD;
        StepTypeEnum startStep1 = StepTypeEnum.STEP12;
        StepTypeEnum endStep1 = StepTypeEnum.STEP12;

        return getAverageRemainTimeDuration(deviceType1, poolType1, startStep1, endStep1, InstanceStepEntity::getStep12StartTime);
    }

    @Override
    public long getBottleneckAverageRemainTimeDuration() {
        // 瓶颈资源当前执行剩余时间（有多个则取平均值）
        long step5AverageRemainTimeDuration = getStep5AverageRemainTimeDuration();
        long step12AverageRemainTimeDuration = getStep12AverageRemainTimeDuration();
        // step5AverageRemainTimeDuration + step12AverageRemainTimeDuration
        // 最两个最大值
        return (long) LongStream.of(step5AverageRemainTimeDuration, step12AverageRemainTimeDuration)
                .average()
                .orElse(Math.min(step5AverageRemainTimeDuration, step12AverageRemainTimeDuration));
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
        int withPlateNumber = integers.stream().max(Comparator.comparing(Integer::valueOf)).orElse(1);
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
        // 启动新线程抵达瓶颈资源所需时间
        List<HephaestusStageTask> stageTasks2 = workBusDaemonResource.getExperimentStageTaskManager().getStageTasks(experimentId, firstStep, false, startStep, true);
        int arrivalTime = stageTasks2.stream().mapToInt(HephaestusStageTask::getTimeoutSecond).sum();
        // 每个步骤损失的时间定义，单位秒
        int size = stageTasks2.size();
        int lostTime = 5;
        arrivalTime = arrivalTime + size * lostTime;
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
    public StepTypeEnum stepType() {
        return this.startStep;
    }
}
