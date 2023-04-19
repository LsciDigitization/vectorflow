package com.mega.hephaestus.pms.data.mysql.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mega.component.mybatis.common.service.IBaseService;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferEntity;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferPlanEntity;
import com.mega.hephaestus.pms.data.mysql.view.LabwareTransferPlanView;
import com.mega.hephaestus.pms.data.mysql.view.LabwareTransferView;

import java.util.List;
import java.util.Optional;

/**
 * TransferPlan service interface
 */
public interface ILabwareTransferPlanService extends IBaseService<LabwareTransferPlanEntity> {




    IPage<LabwareTransferPlanView> listTransferPlanJoinPlateJoinLiquid(Page<LabwareTransferPlanEntity> page,Long projectId);

    List<LabwareTransferPlanEntity> listByProjectId(long projectId);
}

