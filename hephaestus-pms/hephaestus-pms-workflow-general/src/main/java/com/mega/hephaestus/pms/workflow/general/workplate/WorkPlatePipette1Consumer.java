package com.mega.hephaestus.pms.workflow.general.workplate;

import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.general.config.GeneralPlateTypeEnum;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceLabwareManager;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlateConsumer;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlateRegister;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

//@Component
@Slf4j
public class WorkPlatePipette1Consumer implements WorkPlateConsumer<InstanceLabwareModel>, WorkPlateRegister {

    private static final PlateType plateType = GeneralPlateTypeEnum.PIPETTE1;

    private InstanceLabwareManager instanceLabwareManager;

    public WorkPlatePipette1Consumer() {
    }

    public WorkPlatePipette1Consumer(InstanceLabwareManager instanceLabwareManager) {
        this.instanceLabwareManager = instanceLabwareManager;
    }

    public void setInstancePlateManager(InstanceLabwareManager instanceLabwareManager) {
        this.instanceLabwareManager = instanceLabwareManager;
    }

    @Override
    public void push(InstanceLabwareModel anEntry) {
        instanceLabwareManager.push(anEntry);
    }

    @Override
    public InstanceLabwareModel pop(long historyId) {
        Optional<InstanceLabwareModel> nonConsumed = instanceLabwareManager.getNonConsumed(historyId, plateType.getCode());
        return nonConsumed.orElse(null);
    }

    @Override
    public InstanceLabwareModel pop(long historyId, int plateNo) {
        Optional<InstanceLabwareModel> nonConsumed = instanceLabwareManager.getNonConsumed(historyId, plateType.getCode(), plateNo);
        return nonConsumed.orElse(null);
    }

    @Override
    public long size(long historyId) {
        return instanceLabwareManager.sizeByPoolType(historyId, plateType.getCode());
    }

    @Override
    public long fullSize(long historyId) {
        return instanceLabwareManager.fullSizeByPoolType(historyId, plateType.getCode());
    }

    @Override
    public PlateType plateType() {
        return plateType;
    }
}
