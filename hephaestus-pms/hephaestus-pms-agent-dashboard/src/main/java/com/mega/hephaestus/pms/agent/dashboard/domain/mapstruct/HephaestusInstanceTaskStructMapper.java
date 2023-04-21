package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;


import com.mega.hephaestus.pms.agent.dashboard.domain.resp.HephaestusInstanceTaskSearchResultDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.InstanceTaskDTO;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author yinyse
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface HephaestusInstanceTaskStructMapper {
    HephaestusInstanceTaskStructMapper INSTANCE = Mappers.getMapper(HephaestusInstanceTaskStructMapper.class);

    List<HephaestusInstanceTaskSearchResultDTO> entity2SearchResultDTO(List<InstanceTaskEntity> entityList);


    List<InstanceTaskDTO> entity2InstanceTaskDTO(List<InstanceTaskEntity> entityList);

}
