package com.mega.hephaestus.pms.workflow.work.workconsumer;

import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceLabwareManager;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlateConsumer;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlateRegister;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 通用板消费者
 */
//@Component
@Slf4j
public class WorkPlateGenericConsumer implements WorkPlateConsumer<InstanceLabwareModel>, WorkPlateRegister {

    private PlateType plateType;

//    private InstancePlateManager instancePlateManager;

    private InstanceLabwareManager instancePlateManager;
    public WorkPlateGenericConsumer() {
    }

    public WorkPlateGenericConsumer(PlateType plateType, InstanceLabwareManager instancePlateManager) {
        this.plateType = plateType;
        this.instancePlateManager = instancePlateManager;
    }

    public void setPlateType(PlateType plateType) {
        this.plateType = plateType;
    }

    public void setInstancePlateManager(InstanceLabwareManager instancePlateManager) {
        this.instancePlateManager = instancePlateManager;
    }

    @Override
    public void push(InstanceLabwareModel anEntry) {
        instancePlateManager.push(anEntry);
    }

    @Override
    public InstanceLabwareModel pop(long historyId) {
        Optional<InstanceLabwareModel> nonConsumed = instancePlateManager.getNonConsumed(historyId, plateType.getCode());
        return nonConsumed.orElse(null);
    }

    @Override
    public InstanceLabwareModel pop(long historyId, int plateNo) {
        Optional<InstanceLabwareModel> nonConsumed = instancePlateManager.getNonConsumed(historyId, plateType.getCode(), plateNo);
        return nonConsumed.orElse(null);
    }

    @Override
    public long size(long historyId) {
        return instancePlateManager.sizeByPoolType(historyId, plateType.getCode());
    }

    @Override
    public long fullSize(long historyId) {
        return instancePlateManager.fullSizeByPoolType(historyId, plateType.getCode());
    }

    @Override
    public PlateType plateType() {
        return plateType;
    }

}
