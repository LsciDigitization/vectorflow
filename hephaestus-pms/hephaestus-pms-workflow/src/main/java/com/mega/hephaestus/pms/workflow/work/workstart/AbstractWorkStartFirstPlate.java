package com.mega.hephaestus.pms.workflow.work.workstart;

import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
public abstract class AbstractWorkStartFirstPlate extends AbstractWorkStartPlate {

    public AbstractWorkStartFirstPlate() {
    }

    public AbstractWorkStartFirstPlate(WorkBusDaemonResource workBusDaemonResource) {
        super(workBusDaemonResource);
    }

    /**
     * 启动移液枪头线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    public Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId) {
        long workPipetteSize = getWorkPlateConsumer(plateType()).size(experimentHistoryId);

        if (workPipetteSize > 0) {
            log.info("{} 队列大小 {}", plateType(), workPipetteSize);
            InstanceLabwareModel instanceLabwareEntity = getWorkPlateConsumer(plateType()).pop(experimentHistoryId);
            if (Objects.nonNull(instanceLabwareEntity)) {
                this.accept(instanceLabwareEntity);
                return Optional.of(instanceLabwareEntity);
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
        long workPipetteSize = getWorkPlateConsumer(plateType()).size(experimentHistoryId);

        if (workPipetteSize > 0) {
            log.info("{} 队列大小 {}", plateType(), workPipetteSize);
            InstanceLabwareModel instanceLabwareEntity = getWorkPlateConsumer(plateType()).pop(experimentHistoryId, plateNo);
            if (Objects.nonNull(instanceLabwareEntity)) {
                this.accept(instanceLabwareEntity);
                return Optional.of(instanceLabwareEntity);
            }
        }

        return Optional.empty();
    }


    /**
     * 批量运行启动板数，默认数量1
     * @param runnable 运行方法
     */
    protected void startBatch(Runnable runnable) {
        startBatch(runnable, 1);
    }

    /**
     * 批量运行启动板数
     * @param num 数量
     * @param runnable 运行方法
     */
    protected void startBatch(Runnable runnable, int num) {
        // 分别启动样品板和标品板工作线程
        // 需要同时各启动2块板子
        IntStream intStream = IntStream.rangeClosed(1, num);
        // 启动 标品 x 4 运行
        intStream.forEach(value -> {
            runnable.run();
        });
    }


    /**
     * 首块板子 启动
     * @param experimentHistoryId 实验ID
     * @param instanceTasks 实例任务 InstanceTaskEntity
     */
    public abstract void addStartWorkThread(long experimentHistoryId, List<InstanceTaskEntity> instanceTasks);

    protected abstract int getPrevNum();

}
