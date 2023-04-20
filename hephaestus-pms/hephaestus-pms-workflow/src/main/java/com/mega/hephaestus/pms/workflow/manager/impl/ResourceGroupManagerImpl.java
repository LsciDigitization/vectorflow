package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.hephaestus.pms.data.model.entity.ProjectEntity;
import com.mega.hephaestus.pms.data.model.service.IProjectService;
import com.mega.hephaestus.pms.workflow.manager.plan.ResourceGroupManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 13:40
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceGroupManagerImpl implements ResourceGroupManager {

    private final IProjectService resourceGroupService;

    /**
     * 根据type 获取资源组
     *
     * @param experimentType type
     * @return Optional<ProjectEntity>
     */
    public Optional<ProjectEntity> getByExperimentType(String experimentType) {
        return resourceGroupService.getByExperimentType(experimentType);
    }

}
