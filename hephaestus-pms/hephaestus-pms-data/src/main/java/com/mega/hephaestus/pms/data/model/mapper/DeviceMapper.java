package com.mega.hephaestus.pms.data.model.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.entity.DeviceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 硬件配置表
 *
 * @author xianming.hu
 */
@Mapper
public interface DeviceMapper extends SuperMapper<DeviceEntity> {

}
