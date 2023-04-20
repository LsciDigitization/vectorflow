package com.mega.hephaestus.pms.workflow.manager.plan;


import com.mega.hephaestus.pms.data.model.entity.ProjectEntity;

import java.util.Optional;


/**
 * 设备task
 */
public interface ResourceGroupManager {

    /**
     * 根据type 获取资源组
     *
     * @param experimentType type
     * @return Optional<ProjectEntity>
     */
    Optional<ProjectEntity> getByExperimentType(String experimentType);

}
