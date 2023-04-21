package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;

import java.util.List;


import com.mega.hephaestus.pms.agent.dashboard.domain.req.HephaestusInstanceFormDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.HephaestusInstanceDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.HephaestusInstanceSearchResultDTO;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 *
 *
 * @author xianming.hu
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface HephaestusInstanceStructMapper {

        HephaestusInstanceStructMapper INSTANCE = Mappers.getMapper(HephaestusInstanceStructMapper.class);


        HephaestusInstanceDTO entity2DTO(InstanceEntity entity);

        List<HephaestusInstanceDTO> entity2DTO(List<InstanceEntity> entityList);

        InstanceEntity dto2Entity(HephaestusInstanceDTO dto);

        List<InstanceEntity> dto2Entity(List<HephaestusInstanceDTO> dtoList);

        InstanceEntity formDTO2Entity(HephaestusInstanceFormDTO formDTO);

        void updateEntityFromDTO(HephaestusInstanceFormDTO formDTO, @MappingTarget InstanceEntity entity);

        @Mappings({
                @Mapping(source = "instanceStatus.name", target = "instanceStatus"),
                @Mapping(source = "activeStageStatus.name", target = "activeStageStatus"),
        })
        HephaestusInstanceSearchResultDTO entity2SearchResultDTO(InstanceEntity entity);

        List<HephaestusInstanceSearchResultDTO> entity2SearchResultDTO(List<InstanceEntity> entityList);
}
