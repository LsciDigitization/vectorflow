package com.mega.hephaestus.pms.data.runtime.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskResultEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实例结果 mapper
 *
 * @author xianming.hu
 */
@Mapper
@DS("runtime")
public interface InstanceTaskResultMapper extends SuperMapper<InstanceTaskResultEntity> {


}
