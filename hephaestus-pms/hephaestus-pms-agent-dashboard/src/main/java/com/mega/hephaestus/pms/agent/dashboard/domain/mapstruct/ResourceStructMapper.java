package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;

import com.mega.hephaestus.pms.agent.dashboard.domain.resp.ResourceDTO;
import com.mega.hephaestus.pms.data.model.entity.ResourceEntity;
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
public interface ResourceStructMapper {

        ResourceStructMapper INSTANCE = Mappers.getMapper(ResourceStructMapper.class);


        ResourceDTO entity2ResourceDto(ResourceEntity entity);

        List<ResourceDTO> entity2SearchResultDto(List<ResourceEntity> entityList);

}
