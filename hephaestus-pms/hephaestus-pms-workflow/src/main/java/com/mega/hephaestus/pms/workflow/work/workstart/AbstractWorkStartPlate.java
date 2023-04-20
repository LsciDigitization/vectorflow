package com.mega.hephaestus.pms.workflow.work.workstart;

import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.work.workbus.WorkBusDaemonResource;
import com.mega.hephaestus.pms.workflow.work.workplate.WorkPlateRegister;
import com.mega.hephaestus.pms.workflow.work.worktask.MessageDomain;
import com.mega.hephaestus.pms.workflow.work.worktask.WorkTaskDomain;

public abstract class AbstractWorkStartPlate implements WorkStartRegister {

    protected WorkBusDaemonResource workBusDaemonResource;

    public AbstractWorkStartPlate() {
    }

    public AbstractWorkStartPlate(WorkBusDaemonResource workBusDaemonResource) {
        this.workBusDaemonResource = workBusDaemonResource;
    }

    public void setWorkBusDaemonResource(WorkBusDaemonResource workBusDaemonResource) {
        this.workBusDaemonResource = workBusDaemonResource;
    }

    public WorkPlateRegister getWorkPlateConsumer(PlateType poolType) {
        return workBusDaemonResource.getWorkPlatePool().get(poolType.getCode()).orElse(null);
    }

    public WorkPlateRegister getWorkPlateConsumer(String poolType) {
        return workBusDaemonResource.getWorkPlatePool().get(poolType).orElse(null);
    }

    protected void accept(MessageDomain<WorkTaskDomain> workTaskDomainMessageDomain) {
        System.out.println(workTaskDomainMessageDomain);

        WorkTaskDomain content = workTaskDomainMessageDomain.getContent();
        Long experimentId = content.getExperimentId();

        workBusDaemonResource.getExperimentFlowStart().startWorkflow(experimentId, content);
    }

    protected void accept(InstanceLabwareModel instanceLabwareEntity) {
        System.out.println(instanceLabwareEntity);

        Long experimentId = instanceLabwareEntity.getExperimentId();
        WorkTaskDomain workTaskDomain = instancePlateToWorkTaskDomain(instanceLabwareEntity);
        workBusDaemonResource.getExperimentFlowStart().startWorkflow(experimentId, workTaskDomain, hephaestusInstance -> {
            // update instance plate
            workBusDaemonResource.getInstanceLabwareManager().consumePlate(workTaskDomain.getExperimentPlateId(), hephaestusInstance.getId(),hephaestusInstance.getProcessRecordId());
            // create instance step
            workBusDaemonResource.getInstanceStepManager().createInstance(hephaestusInstance.getId(), workTaskDomain.getExperimentPoolType());
        });

    }

    protected WorkTaskDomain instancePlateToWorkTaskDomain(InstanceLabwareModel instanceLabwareEntity) {
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

}
