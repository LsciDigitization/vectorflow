package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.ProjectEntity;
import com.mega.hephaestus.pms.data.model.mapper.ProjectMapper;
import com.mega.hephaestus.pms.data.model.service.IProjectService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 实验资源组
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class ProjectServiceImpl extends
        ServiceImpl<ProjectMapper, ProjectEntity> implements IProjectService {


  /**
   * 根据type 获取资源组
   *
   * @param experimentType type
   * @return Optional<ProjectEntity>
   */
  @Override
  public Optional<ProjectEntity> getByExperimentType(String experimentType) {
    ProjectEntity one = lambdaQuery().eq(ProjectEntity::getExperimentType, experimentType)
        .eq(ProjectEntity::getIsDeleted, BooleanEnum.NO)
        .one();
    return Optional.ofNullable(one);
  }
}
