package com.mega.hephaestus.pms.data.runtime.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.view.HephaestusInstanceTaskView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 *  InstanceTaskMapper
 *
 * @author xianming.hu
 */
@Mapper
@DS("runtime")
public interface InstanceTaskMapper extends SuperMapper<InstanceTaskEntity> {

    List<HephaestusInstanceTaskView> listInstanceTaskViewInInstanceIds(@Param("instanceIds") Set<Long> instanceIds);

}
