package com.mega.hephaestus.pms.workflow.work.workplate;

import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceLabwareManager;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;

public interface WorkPlateRegister extends WorkPlateConsumer<InstanceLabwareModel> {

    PlateType plateType();

    void setInstancePlateManager(InstanceLabwareManager instancePlateManager);

}
