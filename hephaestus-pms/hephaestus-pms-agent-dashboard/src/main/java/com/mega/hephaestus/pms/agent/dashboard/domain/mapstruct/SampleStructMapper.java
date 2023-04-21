package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;

import com.mega.hephaestus.pms.agent.dashboard.domain.resp.SampleDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.SampleSearchResultDTO;
import com.mega.hephaestus.pms.data.mysql.entity.LabwareSampleEntity;
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
public interface SampleStructMapper {

        SampleStructMapper INSTANCE = Mappers.getMapper(SampleStructMapper.class);


        SampleSearchResultDTO entity2SearchResultDto(LabwareSampleEntity entity);

        List<SampleSearchResultDTO> entity2SearchResultDto(List<LabwareSampleEntity> entityList);


        SampleDTO entity2SampleDTO(LabwareSampleEntity entity);
}
