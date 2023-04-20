package com.mega.hephaestus.pms.workflow.work.workstart;

import com.mega.component.bioflow.task.ProcessRecordId;
import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceLabwareManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceStepManager;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.task.stageflow.ExperimentFlowStart;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlatePool;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlateRegister;
import com.mega.hephaestus.pms.workflow.work.worktask.WorkTaskDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/30 17:58
 */
@Component
@Slf4j
public class InstancePlateStarter {

    @Resource
    private WorkPlatePool workPlatePool;
    @Resource
    private ExperimentFlowStart experimentFlowStart;
    @Resource
    private InstanceLabwareManager instanceLabwareManager;
    @Resource
    private InstanceStepManager instanceStepManager;

    /**
     * 启动标品板线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    public Optional<InstanceLabwareModel> startInstancePlate(ProcessRecordId experimentHistoryId, PlateType plateType) {
        long workPlateSize = getWorkPlateConsumer(plateType).size(experimentHistoryId.getLongId());

        if (workPlateSize > 0) {
            log.info("工作队列大小 {}", workPlateSize);

            InstanceLabwareModel instancePlate = getWorkPlateConsumer(plateType).pop(experimentHistoryId.getLongId());
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
    public Optional<InstanceLabwareModel> startInstancePlate(ProcessRecordId experimentHistoryId, PlateType plateType, int plateNo) {
        long workPlateSize = getWorkPlateConsumer(plateType).size(experimentHistoryId.getLongId());

        if (workPlateSize > 0) {
            log.info("工作队列大小 {}", workPlateSize);

            InstanceLabwareModel instancePlate = getWorkPlateConsumer(plateType).pop(experimentHistoryId.getLongId(), plateNo);
            if (Objects.nonNull(instancePlate)) {
                this.accept(instancePlate);
                return Optional.of(instancePlate);
            }
        }

        return Optional.empty();
    }

    public WorkPlateRegister getWorkPlateConsumer(PlateType plateType) {
        return workPlatePool.get(plateType.getCode()).orElse(null);
    }

    private void accept(InstanceLabwareModel instanceLabwareEntity) {
        System.out.println(instanceLabwareEntity);

        Long experimentId = instanceLabwareEntity.getExperimentId();
        WorkTaskDomain workTaskDomain = instancePlateToWorkTaskDomain(instanceLabwareEntity);
        experimentFlowStart.startWorkflow(experimentId, workTaskDomain, hephaestusInstance -> {
            // update instance plate
            instanceLabwareManager.consumePlate(workTaskDomain.getExperimentPlateId(), hephaestusInstance.getId(),hephaestusInstance.getProcessRecordId());
            // create instance step
            instanceStepManager.createInstance(hephaestusInstance.getId(), workTaskDomain.getExperimentPoolType());
        });
    }

    public WorkTaskDomain instancePlateToWorkTaskDomain(InstanceLabwareModel instanceLabwareEntity) {
        WorkTaskDomain workTaskDomain = new WorkTaskDomain();
        workTaskDomain.setExperimentPlateId(instanceLabwareEntity.getId());
        workTaskDomain.setName(instanceLabwareEntity.getExperimentName());
        workTaskDomain.setExperimentId(instanceLabwareEntity.getExperimentId());
        workTaskDomain.setExperimentGroupId(instanceLabwareEntity.getProcessId());
//        workTaskDomain.setExperimentPoolId(instanceLabwareEntity.get());
        workTaskDomain.setExperimentPoolType(instanceLabwareEntity.getLabwareType());
        workTaskDomain.setExperimentPlateStorageId(instanceLabwareEntity.getLabwareNestId());
//        workTaskDomain.setDeviceKey(instanceLabwareEntity.get());
        workTaskDomain.setExperimentGroupHistoryId(instanceLabwareEntity.getProcessRecordId());
        return workTaskDomain;
    }

    /**
     * 批量运行启动板数，默认数量1
     * @param runnable 运行方法
     */
    public void startBatch(Runnable runnable) {
        startBatch(runnable, 1);
    }

    /**
     * 批量运行启动板数
     * @param num 数量
     * @param runnable 运行方法
     */
    public void startBatch(Runnable runnable, int num) {
        // 分别启动样品板和标品板工作线程
        // 需要同时各启动2块板子
        IntStream intStream = IntStream.rangeClosed(1, num);
        // 启动 标品 x 4 运行
        intStream.forEach(value -> {
            runnable.run();
        });
    }

}
