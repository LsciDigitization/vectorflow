package com.mega.hephaestus.pms.workflow.manager;

import com.mega.hephaestus.pms.data.mysql.entity.*;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareLiquidService;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareSampleService;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareTransferService;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareWellService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupHistoryManager;
import com.mega.hephaestus.pms.workflow.utils.WellUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/11 17:13
 */
@Component
public class TransferManager {

    @Autowired
    private ILabwareWellService labwareWellService;
    @Autowired
    private ILabwareSampleService labwareSampleService;
    @Autowired
    private ILabwareLiquidService labwareLiquidService;
    @Autowired
    private ILabwareTransferService labwareTransferService;
    @Autowired
    private ExperimentGroupHistoryManager experimentGroupHistoryManager;

    private final LabwareTransferEntity sampleEntity;

    private final LabwareTransferEntity liquidEntity;

    {
        sampleEntity = new LabwareTransferEntity();
        sampleEntity.setPlanId(-1L);
        sampleEntity.setLiquidId(-1L);
        sampleEntity.setTransferType("sample");

        liquidEntity = new LabwareTransferEntity();
        liquidEntity.setPlanId(-1L);
        liquidEntity.setTransferType("liquid");
        liquidEntity.setSourcePlate(-1L);
        liquidEntity.setSourceWell(-1L);
        liquidEntity.setSourceSampleId(-1L);

    }

    // 指定转移计划进行转移
    public void transferPlate(LabwareTransferPlanEntity plan, long sourcePlateId, long targetPlateId) {
        List<LabwareWellEntity> entities;
        if (plan.getWellRange().equals("ALL")) {
            // 根据板ID查找所有孔位
            entities = labwareWellService.lambdaQuery()
                    .eq(LabwareWellEntity::getPlateId, sourcePlateId)
                    .list();
        } else {
            List<String> wells = WellUtils.splitWellRanges(plan.getWellRange());
            entities = labwareWellService.lambdaQuery()
                    .eq(LabwareWellEntity::getPlateId, sourcePlateId)
                    .in(LabwareWellEntity::getWellKey, wells)
                    .list();
        }

        // 根据板ID查找所有孔位
        entities.forEach(sourceWell -> {
                    LabwareWellEntity targetWell = labwareWellService.lambdaQuery()
                            .eq(LabwareWellEntity::getPlateId, targetPlateId)
                            .eq(LabwareWellEntity::getWellKey, sourceWell.getWellKey())
                            .one();
                    if (targetWell == null) {
                        throw new RuntimeException("target well not found");
                    }

                    transferSample(plan, sourceWell, targetWell);
                });
    }

    // 双板所有空位全部转移
    public void transferPlate(long sourcePlateId, long targetPlateId, long pipetteId, float volume) {
        // 根据板ID查找所有孔位
        labwareWellService.lambdaQuery()
                .eq(LabwareWellEntity::getPlateId, sourcePlateId)
                .list()
                .forEach(sourceWell -> {
                    LabwareWellEntity targetWell = labwareWellService.lambdaQuery()
                            .eq(LabwareWellEntity::getPlateId, targetPlateId)
                            .eq(LabwareWellEntity::getWellKey, sourceWell.getWellKey())
                            .one();
                    if (targetWell == null) {
                        throw new RuntimeException("target well not found");
                    }

                    transferSample(sourceWell, targetWell, pipetteId, volume);
                });
    }

    // 双板所有空位全部转移
    public void transferPlate(LabwarePlateEntity source, LabwarePlateEntity target, long pipetteId, float volume) {
        transferPlate(source.getId(), target.getId(), pipetteId, volume);
    }


    // 双板对空位转移
    public void transferPlate(LabwarePlateEntity source, LabwarePlateEntity target, String wellKey, long pipetteId, float volume) {
        // 根据wellKey查找对应的孔位
        LabwareWellEntity sourceWell = labwareWellService.lambdaQuery()
                .eq(LabwareWellEntity::getPlateId, source.getId())
                .eq(LabwareWellEntity::getWellKey, wellKey)
                .one();
        if (sourceWell == null) {
            throw new RuntimeException("source well not found");
        }

        LabwareWellEntity targetWell = labwareWellService.lambdaQuery()
                .eq(LabwareWellEntity::getPlateId, target.getId())
                .eq(LabwareWellEntity::getWellKey, wellKey)
                .one();
        if (targetWell == null) {
            throw new RuntimeException("target well not found");
        }

        transferSample(sourceWell, targetWell, pipetteId, volume);
    }

    // 目标空位无数据，使用whole转移，且源孔的sample_id清空，目标孔的sample_id由源孔的转移过来
    private void transferWholeSample(LabwareWellEntity source, LabwareWellEntity target, long pipetteId, float volume, Long planId) {
        // 增加转移记录
        createTransferRecord(source, target, pipetteId, volume, SampleTransferMethod.WHOLE.getCode(), planId);

        // 更新源孔位的样品ID为-1
        LabwareWellEntity sourceWell = labwareWellService.getById(source.getId());
        LabwareWellEntity newSourceWell = new LabwareWellEntity();
        newSourceWell.setId(sourceWell.getId());
        newSourceWell.setSampleId(-1L);
        labwareWellService.updateById(newSourceWell);

        // 更新目标孔位的样品ID为源孔位的样品ID
        LabwareWellEntity targetWell = labwareWellService.getById(target.getId());
        LabwareWellEntity newTargetWell = new LabwareWellEntity();
        newTargetWell.setId(targetWell.getId());
        newTargetWell.setSampleId(target.getSampleId());
        labwareWellService.updateById(newTargetWell);
    }

    // 目标空位无数据，使用partial转移，且保留两个孔位一样的sample_id
    private void transferPartialSample(LabwareWellEntity source, LabwareWellEntity target, long pipetteId, float volume, Long planId) {
        // 增加转移记录
        createTransferRecord(source, target, pipetteId, volume, SampleTransferMethod.PARTIAL.getCode(), planId);

        // 源孔位的样品ID保持不变
        // 更新目标孔位的样品ID为源孔位的样品ID
        LabwareWellEntity targetWell = labwareWellService.getById(target.getId());
        LabwareWellEntity newTargetWell = new LabwareWellEntity();
        newTargetWell.setId(targetWell.getId());
        newTargetWell.setSampleId(target.getSampleId());
        labwareWellService.updateById(newTargetWell);

        // 转移液体部分体积
        transferSampleVolume(source, target, pipetteId, volume);
    }

    // 目标空位有数据，使用mix 混合转移
    private void transferMixSample(LabwareWellEntity source, LabwareWellEntity target, long pipetteId, float volume, Long planId) {
        // 增加转移记录
        createTransferRecord(source, target, pipetteId, volume, SampleTransferMethod.MIX.getCode(), planId);

        // 转移液体部分体积
        transferSampleVolume(source, target, pipetteId, volume);
    }


    // 转移液体部分体积
    private void transferSampleVolume(LabwareWellEntity source, LabwareWellEntity target, long pipetteId, float volume) {
        // 从源孔位中扣除样品体积
        LabwareSampleEntity sourceSample = labwareSampleService.getById(source.getSampleId());
        LabwareSampleEntity newSourceSample = new LabwareSampleEntity();
        newSourceSample.setId(sourceSample.getId());
        newSourceSample.setWellVolume(sourceSample.getWellVolume() - volume);
        labwareSampleService.updateById(newSourceSample);

        // 更新目标孔位的样品ID，和样品体积
        if (target.getSampleId() != -1) {
            LabwareSampleEntity targetSample = labwareSampleService.getById(target.getSampleId());
            LabwareSampleEntity newTargetSample = new LabwareSampleEntity();
            newTargetSample.setId(targetSample.getId());
            newTargetSample.setWellVolume(targetSample.getWellVolume() + volume);
            labwareSampleService.updateById(newTargetSample);
        }
    }

    private void createTransferRecord(LabwareWellEntity source, LabwareWellEntity target, long pipetteId, float volume, String sampleTransferMethod, Long planId) {
        // 增加转移记录
        LabwareTransferEntity entity = new LabwareTransferEntity();
        entity.setTransferType(sampleEntity.getTransferType());
        entity.setLiquidId(sampleEntity.getLiquidId());
        entity.setPlanId(Objects.nonNull(planId) ? planId : sampleEntity.getPlanId());
        entity.setProjectId(source.getProjectId());
        entity.setSourcePlate(source.getPlateId());
        entity.setSourceWell(source.getId());
        entity.setSourceSampleId(source.getSampleId());
        entity.setDestinationPlate(target.getPlateId());
        entity.setDestinationWell(target.getId());
        entity.setDestinationSampleId(target.getSampleId());
        entity.setSampleTransferMethod(sampleTransferMethod);
        entity.setPipetteId(pipetteId);
        entity.setLiquidId(sampleEntity.getLiquidId());
        entity.setVolume(volume);
        entity.setTransferTime(new Date());

        // set process record id
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager.getRunningGroup();
        runningGroupOptional.ifPresent(runningGroup -> {
            entity.setProcessRecordId(runningGroup.getId());
        });

        labwareTransferService.save(entity);
    }

    // 移样, 通过plan.sample_transfer_method判断移样方式
    public void transferSample(LabwareTransferPlanEntity plan, LabwareWellEntity source, LabwareWellEntity target) {
        long pipetteId = plan.getPipetteId();
        float volume = plan.getVolume();

        // plan.sample_transfer_method = whole，直接使用whole转移
        if (plan.getSampleTransferMethod().equals(SampleTransferMethod.WHOLE.getCode())) {
            transferWholeSample(source, target, pipetteId, volume, plan.getId());
        }

        // plan.sample_transfer_method = partial，直接使用partial转移
        if (plan.getSampleTransferMethod().equals(SampleTransferMethod.PARTIAL.getCode())) {
            transferPartialSample(source, target, pipetteId, volume, plan.getId());
        }

        // plan.sample_transfer_method = mix，直接使用mix转移
        if (plan.getSampleTransferMethod().equals(SampleTransferMethod.MIX.getCode())) {
            transferMixSample(source, target, pipetteId, volume, plan.getId());
        }
    }

    // 移样
    public void transferSample(LabwareWellEntity source, LabwareWellEntity target, long pipetteId, Float volume) {
        // 1. hephaestus_labware_transfer 表：记录转移操作的细节，包括起始板、目标板、移液头、液体等信息。
        // 1. hephaestus_labware_well 表：更新转移前后起始孔位和目标孔位的样品ID。
        // 1. hephaestus_labware_sample 表：如果有样品被移液，需要更新样品的 well_volume 等信息。

        // 源孔位的样品ID为-1，说明是空孔位，直接使用whole转移
        if (source.getSampleId() == -1) {
            return;
        }

        // 目标孔位的样品ID为-1，说明是空孔位，直接使用whole转移
        if (target.getSampleId() == -1) {
            transferWholeSample(source, target, pipetteId, volume, null);
            return;
        }

        // 源孔位和目标孔位的样品ID一样，说明是同一个样品，直接使用partial转移
        if (source.getSampleId().equals(target.getSampleId())) {
            transferPartialSample(source, target, pipetteId, volume, null);
            return;
        }

        // 源孔位和目标孔位的样品ID不一样，说明是不同的样品，直接使用mix转移
        transferMixSample(source, target, pipetteId, volume, null);
    }


    // 指定板加液
    public void addLiquid(long targetPlateId, long liquidId, long pipetteId, float volume) {
        // 根据板ID查找所有孔位
        labwareWellService.lambdaQuery()
                .eq(LabwareWellEntity::getPlateId, targetPlateId)
                .list()
                .forEach(well -> {
                    addLiquid(well, liquidId, pipetteId, volume, null);
                });
    }

    public void addLiquid(LabwarePlateEntity plate, long liquidId, long pipetteId, float volume) {
        addLiquid(plate.getId(), liquidId, pipetteId, volume);
    }

    /**
     * 指定板加液
     * @param plan    转移计划
     * @param targetPlateId    目标板ID
     */
    public void addLiquid(LabwareTransferPlanEntity plan, long targetPlateId) {
        List<LabwareWellEntity> entities;
        if (plan.getWellRange().equals("ALL")) {
            // 根据板ID查找所有孔位
            entities = labwareWellService.lambdaQuery()
                    .eq(LabwareWellEntity::getPlateId, targetPlateId)
                    .list();
        } else {
            List<String> wells = WellUtils.splitWellRanges(plan.getWellRange());
            entities = labwareWellService.lambdaQuery()
                    .eq(LabwareWellEntity::getPlateId, targetPlateId)
                    .in(LabwareWellEntity::getWellKey, wells)
                    .list();
        }

        entities.forEach(well -> {
                    addLiquid(well, plan.getLiquidId(), plan.getPipetteId(), plan.getVolume(), plan.getId());
                });
    }

    /**
     * 指定孔位加液
     * @param plan    转移计划
     * @param target    目标孔位
     */
    public void addLiquid(LabwareTransferPlanEntity plan, LabwareWellEntity target) {
        addLiquid(target, plan.getLiquidId(), plan.getPipetteId(), plan.getVolume(), plan.getId());
    }

    /**
     * 指定孔位加液
     * @param target    目标孔位
     * @param liquidId    液体ID
     * @param pipetteId    移液头ID
     * @param volume    移液量
     */
    public void addLiquid(LabwareWellEntity target, long liquidId, long pipetteId, float volume, Long planId) {
        // 1. hephaestus_labware_transfer 表：记录转移操作的细节，包括起始板、目标板、移液头、液体等信息。
        // 1. hephaestus_labware_well 表：更新目标孔位的液体ID。
        // 1. hephaestus_labware_liquid：更新液体信息。

        // 增加转移记录
        LabwareTransferEntity entity = new LabwareTransferEntity();
        entity.setSourcePlate(liquidEntity.getSourcePlate());
        entity.setSourceWell(liquidEntity.getSourceWell());
        entity.setSourceSampleId(liquidEntity.getSourceSampleId());
        entity.setTransferType(liquidEntity.getTransferType());
        entity.setPlanId(Objects.nonNull(planId) ? planId : liquidEntity.getPlanId());
        entity.setProjectId(target.getProjectId());
        entity.setDestinationPlate(target.getPlateId());
        entity.setDestinationWell(target.getId());
        entity.setDestinationSampleId(target.getSampleId());
        entity.setPipetteId(pipetteId);
        entity.setLiquidId(liquidId);
        entity.setVolume(volume);
        entity.setTransferTime(new Date());

        // set process record id
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager.getRunningGroup();
        runningGroupOptional.ifPresent(runningGroup -> {
            entity.setProcessRecordId(runningGroup.getId());
        });

        labwareTransferService.save(entity);

        // 更新目标孔位的液体ID
        LabwareWellEntity targetWell = labwareWellService.getById(target.getId());
        LabwareWellEntity newTargetWell = new LabwareWellEntity();
        newTargetWell.setId(targetWell.getId());
        newTargetWell.setLiquidId(liquidId);
        labwareWellService.updateById(newTargetWell);

        // 更新液体信息
        LabwareSampleEntity sample = labwareSampleService.getById(target.getSampleId());
        LabwareSampleEntity newSample = new LabwareSampleEntity();
        newSample.setId(sample.getId());
        newSample.setWellVolume(sample.getWellVolume() + volume);
        labwareSampleService.updateById(newSample);

        // 减少液体的总体积
        LabwareLiquidEntity liquid = labwareLiquidService.getById(liquidId);
        LabwareLiquidEntity newLiquid = new LabwareLiquidEntity();
        newLiquid.setId(liquid.getId());
        newLiquid.setVolume(liquid.getVolume() - volume);
        labwareLiquidService.updateById(newLiquid);
    }


}
