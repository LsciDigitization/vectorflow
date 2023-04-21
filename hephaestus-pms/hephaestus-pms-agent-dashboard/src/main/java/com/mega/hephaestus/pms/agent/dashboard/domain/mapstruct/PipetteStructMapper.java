package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;

import com.mega.hephaestus.pms.agent.dashboard.domain.resp.PipetteSearchResultDTO;
import com.mega.hephaestus.pms.data.mysql.entity.LabwarePipetteEntity;
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
public interface PipetteStructMapper {

        PipetteStructMapper INSTANCE = Mappers.getMapper(PipetteStructMapper.class);


        PipetteSearchResultDTO entity2ResourceDto(LabwarePipetteEntity entity);

        List<PipetteSearchResultDTO> entity2SearchResultDto(List<LabwarePipetteEntity> entityList);

}
