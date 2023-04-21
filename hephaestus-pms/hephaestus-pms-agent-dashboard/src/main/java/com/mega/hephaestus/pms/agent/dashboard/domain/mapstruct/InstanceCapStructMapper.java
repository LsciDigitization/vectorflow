package com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct;


import com.mega.hephaestus.pms.agent.dashboard.domain.resp.InstanceCapDTO;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceCapEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 *
 *
 * @author xianming.hu
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface InstanceCapStructMapper {

        InstanceCapStructMapper INSTANCE = Mappers.getMapper(InstanceCapStructMapper.class);


        InstanceCapDTO entity2DTO(InstanceCapEntity entity);

        List<InstanceCapDTO> entity2DTO(List<InstanceCapEntity> entityList);

}
