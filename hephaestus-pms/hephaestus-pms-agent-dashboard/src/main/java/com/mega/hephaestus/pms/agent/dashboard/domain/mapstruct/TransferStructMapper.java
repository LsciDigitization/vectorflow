package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;

import com.mega.hephaestus.pms.agent.dashboard.domain.resp.TransferSearchResultDTO;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareTransferEntity;
import com.mega.hephaestus.pms.data.mysql.view.LabwareTransferView;
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
public interface TransferStructMapper {

        TransferStructMapper INSTANCE = Mappers.getMapper(TransferStructMapper.class);


        TransferSearchResultDTO entity2ResourceDto(LabwareTransferEntity entity);

        List<TransferSearchResultDTO> entity2SearchResultDto(List<LabwareTransferEntity> entityList);


        List<TransferSearchResultDTO> view2SearchResultDto(List<LabwareTransferView> entityList);
}
