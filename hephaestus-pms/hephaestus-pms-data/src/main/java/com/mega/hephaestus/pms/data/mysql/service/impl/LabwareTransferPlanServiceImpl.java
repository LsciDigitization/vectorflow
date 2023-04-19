package com.mega.hephaestus.pms.data.mysql.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferPlanEntity;
import com.mega.hephaestus.pms.data.mysql.mapper.LabwareTransferPlanMapper;
import com.mega.hephaestus.pms.data.mysql.service.ILabwareTransferPlanService;
import com.mega.hephaestus.pms.data.mysql.view.LabwareTransferPlanView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * TransferPlan service implementation
 */
@Service
public class LabwareTransferPlanServiceImpl extends ServiceImpl<LabwareTransferPlanMapper, LabwareTransferPlanEntity> implements ILabwareTransferPlanService {


    @Override
    public IPage<LabwareTransferPlanView> listTransferPlanJoinPlateJoinLiquid(Page<LabwareTransferPlanEntity> page,Long projectId) {
        return baseMapper.listTransferPlanJoinPlateJoinLiquid(page,projectId);
    }

    @Override
    public List<LabwareTransferPlanEntity> listByProjectId(long projectId) {
        List<LabwareTransferPlanEntity> list = lambdaQuery()
                .eq(LabwareTransferPlanEntity::getProjectId, projectId)
                .eq(LabwareTransferPlanEntity::getIsDeleted, BooleanEnum.NO)
                .list();

        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }

}
