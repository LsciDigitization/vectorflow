package com.mega.hephaestus.pms.data.mysql.mapper;

import com.mega.component.mybatis.common.mapper.SuperMapper;
import com.mega.hephaestus.pms.data.mysql.entity.ProjectEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 项目表
 *
 * @author xianming.hu
 */
@Mapper
public interface VectorProjectMapper extends SuperMapper<ProjectEntity> {

}
