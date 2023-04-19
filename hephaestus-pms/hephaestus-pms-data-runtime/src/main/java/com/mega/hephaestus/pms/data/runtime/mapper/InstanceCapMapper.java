package com.mega.hephaestus.pms.data.runtime.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceCapEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 开盖器
 *
 * @author xianming.hu
 */
@Mapper
@DS("runtime")
public interface InstanceCapMapper extends SuperMapper<InstanceCapEntity> {

}
