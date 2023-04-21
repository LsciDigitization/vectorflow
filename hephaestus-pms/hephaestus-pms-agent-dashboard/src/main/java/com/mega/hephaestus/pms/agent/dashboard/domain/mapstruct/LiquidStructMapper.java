package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;

import com.mega.hephaestus.pms.agent.dashboard.domain.resp.LiquidSearchResultDTO;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareLiquidEntity;
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
public interface LiquidStructMapper {

        LiquidStructMapper INSTANCE = Mappers.getMapper(LiquidStructMapper.class);


        LiquidSearchResultDTO entity2ResourceDto(LabwareLiquidEntity entity);

        List<LiquidSearchResultDTO> entity2SearchResultDto(List<LabwareLiquidEntity> entityList);

}
