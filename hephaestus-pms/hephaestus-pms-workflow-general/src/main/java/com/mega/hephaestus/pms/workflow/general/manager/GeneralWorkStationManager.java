package com.mega.hephaestus.pms.workflow.general.manager;

import com.mega.component.bioflow.task.InstanceId;
import com.mega.component.bioflow.task.ProcessRecordId;
import com.mega.component.bioflow.task.ProjectId;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferPlanEntity;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareTransferPlanService;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceService;
import com.mega.hephaestus.pms.workflow.general.config.GeneralPlateTypeEnum;
import com.mega.hephaestus.pms.workflow.manager.TransferManager;
import com.mega.hephaestus.pms.workflow.manager.TransferType;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceLabwareManager;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.work.workstep.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/12 18:11
 */
@Component
@Slf4j
public class GeneralWorkStationManager {

    @Autowired
    private InstanceLabwareManager instanceLabwareManager;
    @Autowired
    private TransferManager transferManager;
    @Autowired
    private ILabwareTransferPlanService labwareTransferPlanService;
    @Autowired
    private IInstanceService instanceService;


    // 根据ProjectId, StepType获取对应的LabwareTransferPlan列表，并排好序
    public List<LabwareTransferPlanEntity> getLabwareTransferPlanList(ProjectId projectId, StepType stepType) {
        List<LabwareTransferPlanEntity> labwareTransferPlanList = labwareTransferPlanService.lambdaQuery()
                .eq(LabwareTransferPlanEntity::getProjectId, projectId.getLongId())
                .eq(LabwareTransferPlanEntity::getStepKey, stepType.getCode())
                .list();
        labwareTransferPlanList
                .sort(Comparator.comparing(LabwareTransferPlanEntity::getSortOrder));
        return labwareTransferPlanList;
    }


    // 根据转移计划生成转移记录
    public void transferWithPlan(InstanceId instanceId, Step step) {
        transferWithPlan(instanceId, step.getType());
    }

    public void transferWithPlan(InstanceId instanceId, StepType stepType) {
        Optional<InstanceLabwareModel> instanceLabwareModelOptional = instanceLabwareManager.getByInstanceId(instanceId.getLongId());
        instanceLabwareModelOptional.ifPresent(model -> {
            // 获取转移计划列表
            List<LabwareTransferPlanEntity> labwareTransferPlanList = getLabwareTransferPlanList(new ProjectId(model.getProjectId()), stepType);
            labwareTransferPlanList.forEach(plan -> {
                log.info(plan.toString());
                long sourcePlateId = getPlateIdByType(new ProcessRecordId(model.getProcessRecordId()), model.getIterationId(), plan.getSourcePlateType());
                long targetPlateId = getPlateIdByType(new ProcessRecordId(model.getProcessRecordId()), model.getIterationId(), plan.getDestinationPlateType());

                if (plan.getTransferType().equals(TransferType.SAMPLE.getCode())) {
                    // 样品转移
                    transferManager.transferPlate(plan, sourcePlateId, targetPlateId);
                } else if (plan.getTransferType().equals(TransferType.LIQUID.getCode())) {
                    // 加液沉淀
                    transferManager.addLiquid(plan, targetPlateId);
                }

            });
        });
    }

    // 转移与沉淀
    public void transferWithPrecipitation(InstanceId instanceId) {
        // 样品转移
        // 加液沉淀
        AtomicLong sourcePlateId = new AtomicLong();
        AtomicLong targetPlateId = new AtomicLong();
        Optional<InstanceLabwareModel> instanceLabwareModelOptional = instanceLabwareManager.getByInstanceId(instanceId.getLongId());
        instanceLabwareModelOptional.ifPresent(instanceLabwareModel -> {
            List<InstanceLabwareModel> instanceLabwareModels = instanceLabwareManager.listByIterationId(instanceLabwareModel.getIterationId(), instanceLabwareModel.getProcessRecordId());
            instanceLabwareModels.forEach(model -> {
                log.info(model.toString());
                if (model.getLabwareType().equals(GeneralPlateTypeEnum.SAMPLE.getCode())) {
                    sourcePlateId.set(model.getPlateId());
                } else if (model.getLabwareType().equals(GeneralPlateTypeEnum.STANDARD.getCode())) {
                    targetPlateId.set(model.getPlateId());
                }
            });
        });

        // 从样品板转移到标品板
        transferManager.transferPlate(sourcePlateId.get(), targetPlateId.get(), 1, 20.0f);
        transferManager.addLiquid(targetPlateId.get(), 1, 1, 10.0f);
    }


    // 获取指定板类型的PlateId
    public long getPlateIdByType(ProcessRecordId processRecordId, long iterationId, String plateType) {
        List<InstanceLabwareModel> instanceLabwareModels = instanceLabwareManager.listByIterationId(iterationId, processRecordId.getLongId());
        return instanceLabwareModels.stream()
                .filter(model -> model.getLabwareType().equals(plateType))
                .findFirst()
                .map(InstanceLabwareModel::getPlateId)
                .orElse(-1L);
    }


    // 转上清
    public void transferSupernatant(InstanceId instanceId) {
        // 转上清
        AtomicLong sourcePlateId = new AtomicLong();
        AtomicLong targetPlateId = new AtomicLong();
        Optional<InstanceLabwareModel> instanceLabwareModelOptional = instanceLabwareManager.getByInstanceId(instanceId.getLongId());
        instanceLabwareModelOptional.ifPresent(instanceLabwareModel -> {

            List<InstanceLabwareModel> instanceLabwareModels = instanceLabwareManager.listByIterationId(instanceLabwareModel.getIterationId(), instanceLabwareModel.getProcessRecordId());
            instanceLabwareModels.forEach(model -> {
                log.info(model.toString());
                if (model.getLabwareType().equals(GeneralPlateTypeEnum.STANDARD.getCode())) {
                    sourcePlateId.set(model.getPlateId());
                } else if (model.getLabwareType().equals(GeneralPlateTypeEnum.EMPTY.getCode())) {
                    targetPlateId.set(model.getPlateId());
                }
            });
        });

        // 从标品板转移至空板
        transferManager.transferPlate(sourcePlateId.get(), targetPlateId.get(), 2, 40.0f);
    }


}
