package com.mega.hephaestus.pms.data.runtime.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实例step mapper
 *
 * @author xianming.hu
 */
@Mapper
@DS("runtime")
public interface InstanceStepMapper extends SuperMapper<InstanceStepEntity> {

}
