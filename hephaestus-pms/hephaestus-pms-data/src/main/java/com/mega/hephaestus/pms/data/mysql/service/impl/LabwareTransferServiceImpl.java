package com.mega.hephaestus.pms.data.mysql.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.LabwareTransferMapper;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareTransferService;
import com.mega.hephaestus.pms.data.mysql.view.LabwareTransferView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * Transfer service implementation
 */
@Service
@DS("runtime")
public class LabwareTransferServiceImpl extends
        ServiceImpl<LabwareTransferMapper, LabwareTransferEntity> implements ILabwareTransferService {

    /**
     * 根据液体id 查询移液记录
     *
     * @param liquidId 液体id
     * @return 移液记录
     */
    @Override
    public List<LabwareTransferEntity> listByLiquidId(long liquidId) {

        List<LabwareTransferEntity> list = lambdaQuery()
                .eq(LabwareTransferEntity::getLiquidId, liquidId)
                .eq(LabwareTransferEntity::getIsDeleted, BooleanEnum.NO)
                .list();

        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }

    /**
     * 根据样本id 查询移液记录
     *
     * @param sampleId 样本id
     * @return 移液记录
     */
    @Override
    public List<LabwareTransferEntity> listBySampleId(long sampleId) {
        List<LabwareTransferEntity> list = lambdaQuery()
                .eq(LabwareTransferEntity::getSourceSampleId, sampleId)
                .eq(LabwareTransferEntity::getIsDeleted, BooleanEnum.NO)
                .list();

        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }

    @Override
    public IPage<LabwareTransferView> listBySampleIdAndLiquidId(Page<LabwareTransferEntity> page,long sampleId, long liquidId) {
        IPage<LabwareTransferView> labwareTransferViewIPage = baseMapper.listTransferJoinPlateJoinWellJoinLiquid(page, sampleId, liquidId);
        return labwareTransferViewIPage;
    }

    /**
     * @param sampleId
     * @param wellId
     * @return
     */
    @Override
    public List<LabwareTransferEntity> listBySampleIdAndWellId(long sampleId, long wellId,long processRecordId) {
        List<LabwareTransferEntity> list = baseMapper.listBySampleIdAndWellId(sampleId, wellId,processRecordId);
        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }
}

