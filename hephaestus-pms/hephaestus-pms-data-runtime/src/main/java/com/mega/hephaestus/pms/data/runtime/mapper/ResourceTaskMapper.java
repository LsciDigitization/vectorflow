package com.mega.hephaestus.pms.data.runtime.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *  设备taskMapper
 *
 * @author xianming.hu
 */
@Mapper
@DS("runtime")
public interface ResourceTaskMapper extends SuperMapper<ResourceTaskEntity> {

}
