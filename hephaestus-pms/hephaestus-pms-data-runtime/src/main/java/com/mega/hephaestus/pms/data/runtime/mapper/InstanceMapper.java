package com.mega.hephaestus.pms.data.runtime.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.runtime.view.HephaestusInstanceExperimentView;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 *
 * @author xianming.hu
 */
@Mapper
@DS("runtime")
public interface InstanceMapper extends SuperMapper<InstanceEntity> {

    HephaestusInstanceExperimentView getInstanceExperiment(@Param("instanceId")Long instanceId);

}
