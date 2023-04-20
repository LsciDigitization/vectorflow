package com.mega.hephaestus.pms.workflow.general.workstart;

import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.workflow.general.config.GeneralPlateTypeEnum;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import com.mega.hephaestus.pms.workflow.work.workstart.AbstractWorkStartPlate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class WorkStartSamplePlate extends AbstractWorkStartPlate {

    public WorkStartSamplePlate() {
    }

    public WorkStartSamplePlate(WorkBusDaemonResource workBusDaemonResource) {
        super(workBusDaemonResource);
    }

    public boolean justPipetteEnabled() {
        return false;
    }

    /**
     * 启动样品板线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    public Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId) {
        long workSampleSize = getWorkPlateConsumer(GeneralPlateTypeEnum.SAMPLE).size(experimentHistoryId);

        if (workSampleSize > 0) {
            log.info("样品队列大小 {}", workSampleSize);

            InstanceLabwareModel instancePlate = getWorkPlateConsumer(GeneralPlateTypeEnum.SAMPLE).pop(experimentHistoryId);
            if (Objects.nonNull(instancePlate)) {
                this.accept(instancePlate);
                return Optional.of(instancePlate);
            }
        }

        return Optional.empty();
    }

    /**
     * 启动样品板线程任务
     *
     * @param experimentHistoryId 实验组任务执行ID
     */
    public Optional<InstanceLabwareModel> startInstancePlate(long experimentHistoryId, int plateNo) {
        long workSampleSize = getWorkPlateConsumer(GeneralPlateTypeEnum.SAMPLE).size(experimentHistoryId);

        if (workSampleSize > 0) {
            log.info("样品队列大小 {}", workSampleSize);

            InstanceLabwareModel instancePlate = getWorkPlateConsumer(GeneralPlateTypeEnum.SAMPLE).pop(experimentHistoryId, plateNo);
            if (Objects.nonNull(instancePlate)) {
                this.accept(instancePlate);
                return Optional.of(instancePlate);
            }
        }

        return Optional.empty();
    }

    @Override
    public void addStartWorkThread(long experimentHistoryId, List<InstanceTaskEntity> instanceTasks) {

    }

    @Override
    public PlateType plateType() {
        return GeneralPlateTypeEnum.SAMPLE;
    }
}
