package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;

import com.mega.hephaestus.pms.agent.dashboard.domain.resp.HephaestusInstancePlateSearchResultDTO;
import com.mega.hephaestus.pms.data.runtime.entity.HephaestusInstancePlate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Builder;

import java.util.List;

/**
 * @author yinyse
 * @description TODO
 * @date 2022/11/25 3:05 PM
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface HephaestusInstancePlateStructMapper {
    HephaestusInstancePlateStructMapper INSTANCE = Mappers.getMapper(HephaestusInstancePlateStructMapper.class);

    List<HephaestusInstancePlateSearchResultDTO> entity2SearchResultDTO(List<HephaestusInstancePlate> entityList);
}
