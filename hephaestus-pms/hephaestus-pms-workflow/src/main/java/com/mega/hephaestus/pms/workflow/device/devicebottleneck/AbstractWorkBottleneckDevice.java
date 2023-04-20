package com.mega.hephaestus.pms.workflow.device.devicebottleneck;

import com.mega.component.commons.date.DateUtil;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.component.nuc.plate.PlateType;
import com.mega.component.nuc.device.DeviceType;
import com.mega.hephaestus.pms.workflow.event.BottleneckRequiredTimeOfSumEvent;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
public abstract class AbstractWorkBottleneckDevice implements DeviceBottleneckRegister {

    protected WorkBusDaemonResource workBusDaemonResource;

    public AbstractWorkBottleneckDevice() {
    }

    public AbstractWorkBottleneckDevice(WorkBusDaemonResource workBusDaemonResource) {
        this.workBusDaemonResource = workBusDaemonResource;
    }

    public void setWorkBusDaemonResource(WorkBusDaemonResource workBusDaemonResource) {
        this.workBusDaemonResource = workBusDaemonResource;
    }

    protected abstract DeviceType getDeviceType();

    protected abstract PlateType getPlateType();

    protected long getAverageRemainTimeDuration(DeviceType deviceType1, PlateType poolType1, StepType startStep1, StepType endStep1, Function<InstanceStepEntity, Date> callStartTime) {
        // 瓶颈资源当前执行剩余时间（有多个则取平均值）
        List<InstanceStepEntity> bottleneckTasks = workBusDaemonResource.getInstanceStepManager().getUnfinishedInstanceStepsRange(poolType1, startStep1, endStep1);

        double averageTime = bottleneckTasks.stream()
                .mapToLong(v -> {
                    List<HephaestusStageTask> stepTasks = workBusDaemonResource.getExperimentStageTaskManager().getStageTasks(v.getInstanceId(), startStep1, endStep1);
                    long sumTimeoutSecond = stepTasks.stream()
                            .mapToLong(HephaestusStageTask::getTimeoutSecond)
                            .sum();
                    log.info("{} {} 瓶颈资源当前执行剩余时间 {} {}", deviceType1, poolType1, v.getInstanceId(), sumTimeoutSecond);

                    Date startTime = callStartTime.apply(v);

                    Date currentTime = new Date();
                    long middle = DateUtil.getIntervalTime(startTime, currentTime) / 1000;
                    middle = workBusDaemonResource.getTaskTimeRateService().getScaledDuration(middle);
                    // 计算剩余时间 sumTimeoutSecond - (currentTime - startTime)
                    return sumTimeoutSecond - middle;
                })
                .filter(v -> v > 0)
                .average()
                .orElse(0);
        // 取两台机器，谁剩余的短取谁的
        log.info("{} {} 瓶颈资源当前执行剩余时间（有多个则取平均值） {}", deviceType1, poolType1, averageTime);
        return (long) averageTime;
    }

    @Override
    public double requiredStartTime() {
        // 获取未完成的任务列表
        List<InstanceTaskEntity> instanceTasks = workBusDaemonResource.getInstanceTaskManager().getUnfinishedTasks();
        log.info("获取未完成的任务列表 {}", instanceTasks.size());

        /*
        计算需要的时间总和 = （
            Wait在瓶颈资源的所有任务数量 * 瓶颈资源执行时间 +
            到达瓶颈资源前的所有任务 * 瓶颈资源执行时间 +
            瓶颈资源当前执行剩余时间（有多个则取平均值）+
            正在等待瓶颈资源任务的等待时间之和
            ）
            / 瓶颈资源数量 * 瓶颈资源带板数 -
            启动新线程抵达瓶颈资源所需时间


            ============
            等待瓶颈资源任务的等待时间之和 =
            ● 正在running，没有等待时间
            ● 正在waiting，有等待时间
            ● 还未到达，有等待时间
            取上一组任务的等待瓶颈资源的平均时间 * 等待任务数
         */

        // 加入时间缩放比例
        int taskTimeRate = workBusDaemonResource.getTaskTimeRateService().getTaskTimeRate();
        long executionTime = workBusDaemonResource.getTaskTimeRateService().getScaledDuration(getBottleneckExecutionTimeDuration());
        long averageTime = workBusDaemonResource.getTaskTimeRateService().getScaledDuration(getBottleneckAverageRemainTimeDuration());
        long arrivalTime = workBusDaemonResource.getTaskTimeRateService().getScaledDuration(getBottleneckArrivalTimeDuration());
        long waitTime = workBusDaemonResource.getTaskTimeRateService().getScaledDuration(getBottleneckWaitTimeDuration());

        log.info("{} {} 执行时间缩放比例 {}", getDeviceType(), getPlateType(), taskTimeRate);
        log.info("{} {} 瓶颈资源执行时间缩放后时间 {}", getDeviceType(), getPlateType(), executionTime);
        log.info("{} {} 瓶颈资源当前执行剩余平均时间缩放后时间 {}", getDeviceType(), getPlateType(), averageTime);
        log.info("{} {} 到达瓶颈资源前所需的时间缩放后时间 {}", getDeviceType(), getPlateType(), arrivalTime);
        log.info("{} {} 等待瓶颈资源的所有任务等待时间 {}", getDeviceType(), getPlateType(), waitTime);

        // 当有当前执行任务时，取平均剩余时间
        if (averageTime > 0) {
            executionTime = Math.min(executionTime, averageTime);
        } else {
            executionTime = Math.abs(executionTime - 300);
        }

        int waitTaskCount = getBottleneckWaitTaskCount();
        int bottleneckDeviceCount = getBottleneckDeviceCount();
        int withPlatber = getBottleneckWithPlateNumber();
        int beforeTaskCount = getBottleneckBeforeTaskCount();

        /*
        计算需要的时间总和（
            Wait在瓶颈资源的所有任务数量 * 瓶颈资源执行时间 +
            到达瓶颈资源前的所有任务 * 瓶颈资源执行时间 +
            瓶颈资源当前执行剩余时间（有多个则取平均值）+
            上一批任务任务在瓶颈资源处的等待时间 * (到达瓶颈资源前的所有任务 + Wait在瓶颈资源的所有任务数量)
        ）/ 瓶颈资源数量 * 瓶颈资源带板数 - 启动新线程抵达瓶颈资源所需时间

        Wait在瓶颈资源的所有任务数量（动态的）
        到达瓶颈资源前的所有任务（动态的）
        瓶颈资源当前执行剩余时间（动态的）
        上一批任务任务在瓶颈资源处的等待时间（动态的）

        瓶颈资源执行时间 (固定的) 1220s
        启动新线程抵达瓶颈资源所需时间 (固定的) 1350s
        瓶颈资源数量 * 瓶颈资源带板数 (固定的)  4 x 1

        =====

        ( Wait在瓶颈资源的所有任务数量（动态的）+ 到达瓶颈资源前的所有任务（动态的）) * 瓶颈资源执行时间 (固定的)
        + Wait在瓶颈资源的所有任务数量（动态的）* 上一批任务任务在瓶颈资源处的平均等待时间（动态的）
        + 瓶颈资源当前执行平均剩余时间（动态的）

        到达瓶颈资源前所需的时间缩放后时间 1350
        1350 + 300 = 1650 大于1650 就不启动


        1650 * 4 = 6600

         (3 + 5) * 1220 = 9760
         + 3 * 43 = 129
         + 0


         9760 - 6600 = 3160

         3160 / 1220 = 2.5

         （计算需要的时间总和 / 瓶颈资源数量 * 瓶颈资源带板数） - 启动新线程抵达瓶颈资源所需时间
           （ C / A ） - B
           这个公式，（ C / A ） - B ,  A，B都是固定的，C是动态的
           （Wait在瓶颈资源的所有任务数量 * 瓶颈资源执行时间 + 到达瓶颈资源前的所有任务 * 瓶颈资源执行时间 ）
           （Wait在瓶颈资源的所有任务数量  + 到达瓶颈资源前的所有任务 ）* 瓶颈资源执行时间
           Wait在瓶颈资源的所有任务数量  + 到达瓶颈资源前的所有任务  =  任何时候都是8个
           过了工作站之后，结束一批，是4块板，又会启动2组，4块板上来，所以还是8块板任务
           C只需要  （Wait在瓶颈资源的所有任务数量 * 瓶颈资源执行时间 + 到达瓶颈资源前的所有任务 * 瓶颈资源执行时间 ） 这两个就可以满足条件

         */
        long requiredTimeOfSum1 = (
                getBottleneckWaitTaskCount() * executionTime +
                        getBottleneckBeforeTaskCount() * executionTime +
                        averageTime +
                        waitTime
        );

        log.info("{} {} requiredTimeOfSum1 ({}) = getBottleneckWaitTaskCount() ({}) * executionTime ({}) + getBottleneckBeforeTaskCount() ({}) * executionTime ({}) + averageTime ({}) + waitTime ({})", getDeviceType(), getPlateType(),
                requiredTimeOfSum1,
                getBottleneckWaitTaskCount(), executionTime, getBottleneckBeforeTaskCount(), executionTime, averageTime, waitTime);

        double requiredTimeOfSum;
        if (bottleneckDeviceCount == 0) {
            requiredTimeOfSum = - arrivalTime;
        } else {
            requiredTimeOfSum = 1.0 * requiredTimeOfSum1 / bottleneckDeviceCount * getBottleneckWithPlateNumber() - arrivalTime;
            log.info("{} {} requiredTimeOfSum ({}) = requiredTimeOfSum1 ({}) / bottleneckDeviceCount ({}) * getBottleneckWithPlatber() ({}) - arrivalTime ({}) ", getDeviceType(), getPlateType(),
                    requiredTimeOfSum, requiredTimeOfSum1,
                    bottleneckDeviceCount, getBottleneckWithPlateNumber(), arrivalTime);
        }
        long requiredTimeOfSumLong = Double.valueOf(requiredTimeOfSum).longValue();

        log.info("{} {} 计算需要的时间总和缩放比例下 {}", getDeviceType(), getPlateType(), requiredTimeOfSum);

        BottleneckRequiredTimeOfSumEvent.BottleneckValue bottleneckValue = BottleneckRequiredTimeOfSumEvent.BottleneckValue.builder()
                .waitTaskCount(waitTaskCount)
                .waitTimeDuration(waitTime)
                .executionTime(executionTime)
                .beforeTaskCount(beforeTaskCount)
                .averageTime(averageTime)
                .deviceCount(bottleneckDeviceCount)
                .deviceWithPlateNumber(withPlatber)
                .processRecordId(getExperimentHistoryId())
                .build();
        BottleneckRequiredTimeOfSumEvent bottleneckRequiredTimeOfSumEvent = new BottleneckRequiredTimeOfSumEvent(getDeviceType(), getPlateType().getCode(), new Date(), requiredTimeOfSumLong, bottleneckValue);
        workBusDaemonResource.getWorkEventPusher().sendBottleneckRequiredTimeOfSumEvent(bottleneckRequiredTimeOfSumEvent);

        // 还原时间缩放比例
        requiredTimeOfSum = workBusDaemonResource.getTaskTimeRateService().getRestoreDuration(requiredTimeOfSum);

        log.info("{} 计算需要的时间总和还原缩放比例后 {}", getDeviceType(), requiredTimeOfSum);

        return requiredTimeOfSum;
    }


    public long getExperimentHistoryId() {
        Optional<ProcessRecordEntity> runningGroupOptional = workBusDaemonResource.getExperimentGroupHistoryManager().getRunningGroup();
        if (runningGroupOptional.isEmpty()) {
            return 0;
        }

        ProcessRecordEntity experimentGroupHistory = runningGroupOptional.get();
        return experimentGroupHistory.getId();
    }

}
