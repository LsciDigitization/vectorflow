package com.mega.hephaestus.pms.workflow.work.workbus;

import com.mega.hephaestus.pms.data.mysql.entity.ProcessLabwareEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.component.nuc.plate.GenericPlateType;
import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.nuc.config.WorkProperties;
import com.mega.component.nuc.timing.Timing;
import com.mega.hephaestus.pms.workflow.work.workstart.WorkStartRegister;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 工作总线调度线程
 */
@Slf4j
public class WorkBusDaemon implements Runnable {

    private final WorkBusDaemonResource workBusDaemonResource;

    public WorkBusDaemon(WorkBusDaemonResource workBusDaemonResource) {
        this.workBusDaemonResource = workBusDaemonResource;
    }

    @Override
    public void run() {
        log.info("{} running...", this.getClass().getSimpleName());

        WorkProperties workProperties = workBusDaemonResource.getWorkProperties();
        if (!workProperties.isEnabled()) {
            return;
        }

        // 给创建的线程设置UncaughtExceptionHandler对象 里面实现异常的默认逻辑
        Thread.setDefaultUncaughtExceptionHandler((Thread thread1, Throwable e) -> {
            log.error("线程设置的exceptionHandler {} {}", thread1.getName(), e.getMessage());
            e.printStackTrace();
        });

        while (true) {
            try {
                log.debug("{} {}", Thread.currentThread().getName(), LocalTime.now());

                Optional<ProcessRecordEntity> runningGroupOptional = workBusDaemonResource.getExperimentGroupHistoryManager().getRunningGroup();
                runningGroupOptional.ifPresent(experimentGroupHistory -> {
                    long experimentHistoryId = experimentGroupHistory.getId();

//                    List<PoolTypeEnum> poolTypes = List.of(PoolTypeEnum.STANDARD, PoolTypeEnum.SAMPLE, PoolTypeEnum.EMPTY, PoolTypeEnum.PIPETTE1, PoolTypeEnum.PIPETTE2);

                    Long processId = experimentGroupHistory.getProcessId();
                    List<ProcessLabwareEntity> groupPools = workBusDaemonResource.getProcessLabwareManager().getProcessLabwaresByProcessId(processId);
                    List<PlateType> poolTypes = groupPools.stream()
                            .map(ProcessLabwareEntity::getLabwareType)
                            .map(GenericPlateType::new)
                            .collect(Collectors.toList());

                    // 获取未完成的任务列表
                    List<InstanceTaskEntity> instanceTasks = workBusDaemonResource.getInstanceTaskManager().getUnfinishedTasks();

                    // 循环所有的耗材消费任务
                    poolTypes.forEach(v -> {
                        Optional<WorkStartRegister> workStartRegister = workBusDaemonResource.getWorkStartPool().get(v.getCode());
                        workStartRegister.ifPresent(w -> {
                            w.addStartWorkThread(experimentHistoryId, instanceTasks);
                        });
                    });
                });

            } catch (Exception e) {
                log.error("{} Daemon run exception: {}", this.getClass().getSimpleName(), e.getMessage());
                e.printStackTrace();
            }


            // 休息一会
            try {
                int sleepSecond = 5;
                long sleepSecond2 = workBusDaemonResource.getTaskTimeRateService().getScaledDuration(sleepSecond);

                // 最小5秒
                Timing.of(sleepSecond2 * 1000, TimeUnit.MILLISECONDS).sleepMin(5000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
