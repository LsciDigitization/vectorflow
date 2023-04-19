package com.mega.hephaestus.pms.data.model.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.entity.HephaestusExperimentGroupPool;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实验组关系
 *
 * @author xianming.hu
 */
@Mapper
public interface ExperimentGroupPoolMapper extends SuperMapper<HephaestusExperimentGroupPool> {

}
