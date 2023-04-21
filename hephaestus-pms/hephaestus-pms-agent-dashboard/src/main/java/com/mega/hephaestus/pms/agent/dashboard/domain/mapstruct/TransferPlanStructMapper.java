package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;

import com.mega.hephaestus.pms.agent.dashboard.domain.resp.TransferPlanSearchResultDTO;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferPlanEntity;
import com.mega.hephaestus.pms.data.mysql.view.LabwareTransferPlanView;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 *
 *
 * @author xianming.hu
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface TransferPlanStructMapper {

        TransferPlanStructMapper INSTANCE = Mappers.getMapper(TransferPlanStructMapper.class);

        List<TransferPlanSearchResultDTO> entity2SearchResultDto(List<LabwareTransferPlanEntity> entityList);

        List<TransferPlanSearchResultDTO> view2SearchResultDto(List<LabwareTransferPlanView> entityList);
}
