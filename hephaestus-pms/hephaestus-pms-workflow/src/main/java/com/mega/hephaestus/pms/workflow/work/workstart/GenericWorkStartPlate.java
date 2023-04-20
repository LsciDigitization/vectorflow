package com.mega.hephaestus.pms.workflow.work.workstart;

import com.mega.component.bioflow.task.ProcessRecordId;
import com.mega.component.bioflow.task.ProjectId;
import com.mega.hephaestus.pms.data.model.entity.ProjectEntity;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessLabwareEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.event.ProcessIterationConsumeEvent;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.manager.model.ProcessIterationModel;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/30 13:35
 */
@Slf4j
public class GenericWorkStartPlate extends AbstractWorkStartPlate {

    private final PlateType plateType;

    public GenericWorkStartPlate(WorkBusDaemonResource workBusDaemonResource, PlateType plateType) {
        super(workBusDaemonResource);
        this.plateType = plateType;
    }

    @Override
    public PlateType plateType() {
        return plateType;
    }

    /**
     * 启动标品板线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    @Override
    public Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId) {
        long workPlateSize = getWorkPlateConsumer(plateType()).size(experimentHistoryId);

        if (workPlateSize > 0) {
            log.info("工作队列大小 {}", workPlateSize);

            InstanceLabwareModel instancePlate = getWorkPlateConsumer(plateType()).pop(experimentHistoryId);
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
        long workPlateSize = getWorkPlateConsumer(plateType()).size(experimentHistoryId);

        if (workPlateSize > 0) {
            log.info("工作队列大小 {}", workPlateSize);

            InstanceLabwareModel instancePlate = getWorkPlateConsumer(plateType()).pop(experimentHistoryId, plateNo);
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
        // 只有标记为主入口的板子才会启动新的工作线程
        List<ProcessLabwareEntity> processLabwares = workBusDaemonResource.getProcessLabwareManager().getProcessLabwares(experimentHistoryId);
        ProcessLabwareEntity mainEntity = processLabwares.stream()
                .filter(v -> v.getLabwareType().equals(plateType.getCode()) && v.getIsMain().toBoolean())
                .findFirst()
                .orElse(null);

        if (Objects.isNull(mainEntity)) {
            log.info("实验组 {} 未找到主板", experimentHistoryId);
            return;
        }

        List<ProcessIterationModel> processIterationModels = workBusDaemonResource.getProcessIterationManager().listByUnConsumed(experimentHistoryId);
        if (processIterationModels.isEmpty()) {
            log.info("实验组 {} 未找到未消费的通量", experimentHistoryId);
            return;
        }

        // 瓶颈资源设备耗时计算
        if (! workBottleneckChecking(experimentHistoryId)) {
            return;
        }

        String code = workBusDaemonResource.getExperimentProperties().getCode();
        Optional<ProjectEntity> resourceGroupOptional = workBusDaemonResource.getResourceGroupManager().getByExperimentType(code);
        if (resourceGroupOptional.isEmpty()) {
            log.info("实验组 {} 未找到资源组", experimentHistoryId);
            return;
        }

        ProjectEntity projectEntity = resourceGroupOptional.get();
        ProjectId projectId = new ProjectId(projectEntity.getId());
        ProcessRecordId processRecordId = new ProcessRecordId(experimentHistoryId);

        Optional<ProcessIterationModel> processIterationModelOptional = workBusDaemonResource.getProcessIterationManager().getUnConsumed(experimentHistoryId);
        processIterationModelOptional.ifPresent(v -> {
            // 通量消费
            // 这时应该做通量消费,发送事件ProcessIterationConsumeEvent
            ProcessIterationConsumeEvent event = new ProcessIterationConsumeEvent(this, projectId, processRecordId, v.getIterationNo());
            workBusDaemonResource.getWorkEventPusher().sendProcessIterationConsumeEvent(event);
        });
    }

    private boolean workBottleneckChecking(long experimentHistoryId) {
        return true;
    }

}
