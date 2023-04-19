package com.mega.hephaestus.pms.data.model.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.entity.HephaestusResourceStorage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 存储设备表
 *
 * @author xianming.hu
 */
@Mapper
public interface StorageMapper extends SuperMapper<HephaestusResourceStorage> {

}
