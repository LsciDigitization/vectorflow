package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.ProjectEntity;

import java.util.Optional;


/**
 * 实验资源组
 *
 * @author xianming.hu
 */
public interface IProjectService extends IService<ProjectEntity> {


  /**
   * 根据type 获取资源组
   * @param experimentType type
   * @return  Optional<ProjectEntity>
   */
  Optional<ProjectEntity> getByExperimentType(String experimentType);


}

