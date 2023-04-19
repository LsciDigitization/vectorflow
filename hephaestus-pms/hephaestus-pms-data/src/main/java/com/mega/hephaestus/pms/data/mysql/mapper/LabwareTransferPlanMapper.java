package com.mega.hephaestus.pms.data.mysql.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferPlanEntity;
import com.mega.hephaestus.pms.data.mysql.view.LabwareTransferPlanView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * TransferPlan表 Mapper 接口
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/3 21:40
 */
@Mapper
public interface LabwareTransferPlanMapper extends SuperMapper<LabwareTransferPlanEntity> {

    IPage<LabwareTransferPlanView> listTransferPlanJoinPlateJoinLiquid(Page<LabwareTransferPlanEntity> page, @Param("projectId") Long projectId);

}
