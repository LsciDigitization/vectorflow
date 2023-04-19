package com.mega.hephaestus.pms.data.model.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.model.entity.ExperimentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实验管理表
 *
 * @author xianming.hu
 */
@Mapper
public interface ExperimentMapper extends SuperMapper<ExperimentEntity> {

}
