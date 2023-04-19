package com.mega.hephaestus.pms.data.mysql.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.mysql.entity.DeviceEntity;

import org.apache.ibatis.annotations.Mapper;


/**
 * 设备
 *
 * @author xianming.hu
 */
@Mapper
public interface VectorDeviceMapper extends SuperMapper<DeviceEntity> {

}
