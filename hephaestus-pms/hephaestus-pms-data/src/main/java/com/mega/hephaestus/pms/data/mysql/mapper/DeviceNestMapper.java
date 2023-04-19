package com.mega.hephaestus.pms.data.mysql.mapper;

import com.mega.hephaestus.pms.data.mysql.view.NestPlateView;
import org.apache.ibatis.annotations.Mapper;
import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.mysql.entity.DeviceNestEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备托架
 *
 * @author xianming.hu
 */
@Mapper
public interface DeviceNestMapper extends SuperMapper<DeviceNestEntity> {

    List<NestPlateView> getNestWithPlate(@Param("ids") List<Long> ids);
}
