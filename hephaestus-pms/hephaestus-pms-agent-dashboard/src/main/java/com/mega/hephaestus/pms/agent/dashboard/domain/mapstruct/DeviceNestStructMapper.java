package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;

import com.mega.hephaestus.pms.agent.dashboard.domain.resp.DeviceNestResultDTO;
import com.mega.hephaestus.pms.data.mysql.view.NestPlateView;
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
public interface DeviceNestStructMapper {

        DeviceNestStructMapper INSTANCE = Mappers.getMapper(DeviceNestStructMapper.class);


        DeviceNestResultDTO view2Dto(NestPlateView entity);

        List<DeviceNestResultDTO> view2SearchResultDto(List<NestPlateView> entityList);

}
