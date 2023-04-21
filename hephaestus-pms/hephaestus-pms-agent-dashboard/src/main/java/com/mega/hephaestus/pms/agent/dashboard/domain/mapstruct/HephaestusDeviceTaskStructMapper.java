package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;

import java.util.List;

import com.mega.hephaestus.pms.agent.dashboard.domain.resp.HephaestusDeviceTaskSearchResultDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.ResourceTaskDTO;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Builder;

/**
 * 设备任务表
 *
 * @author xianming.hu
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface HephaestusDeviceTaskStructMapper {

        HephaestusDeviceTaskStructMapper INSTANCE = Mappers.getMapper(HephaestusDeviceTaskStructMapper.class);


        HephaestusDeviceTaskSearchResultDTO entity2SearchResultDto(ResourceTaskEntity entity);

        List<HephaestusDeviceTaskSearchResultDTO> entity2SearchResultDto(List<ResourceTaskEntity> entityList);

        List<ResourceTaskDTO> entity2ResourceTaskDto(List<ResourceTaskEntity> entityList);

}
