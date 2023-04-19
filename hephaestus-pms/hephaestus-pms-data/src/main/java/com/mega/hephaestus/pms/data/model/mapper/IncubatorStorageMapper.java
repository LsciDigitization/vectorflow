package com.mega.hephaestus.pms.data.model.mapper;


import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.entity.HephaestusIncubatorStorage;
import org.apache.ibatis.annotations.Mapper;

/**
 * Co2培育箱存放模型表
 *
 * @author xianming.hu
 */
@Mapper
@Deprecated(since = "20221115")
public interface IncubatorStorageMapper extends SuperMapper<HephaestusIncubatorStorage> {

}
