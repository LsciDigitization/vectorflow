package com.mega.hephaestus.pms.data.mysql.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.mysql.entity.DeviceCommandEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 设备命令
 *
 * @author xianming.hu
 */
@Mapper
public interface DeviceCommandMapper extends SuperMapper<DeviceCommandEntity> {

}
