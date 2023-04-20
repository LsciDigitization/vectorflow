package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.LabwarePlateTypeEntity;
import com.mega.hephaestus.pms.data.model.entity.ProjectEntity;
import com.mega.hephaestus.pms.data.model.service.IProjectService;
import com.mega.hephaestus.pms.data.model.service.ILabwarePlateTypeService;
import com.mega.hephaestus.pms.workflow.manager.plan.ResourcePlateTypeManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 13:41
 */
@Component
@RequiredArgsConstructor
public class ResourcePlateTypeManagerImpl implements ResourcePlateTypeManager {

    private final ILabwarePlateTypeService resourcePlateTypeService;

    private final IProjectService resourceGroupService;

    /**
     * 根据资源id查询板类型
     *
     * @param resourceGroupId 资源组id
     * @return List<LabwarePlateTypeEntity>
     */
    public List<LabwarePlateTypeEntity> getResourcePlateTypes(long resourceGroupId) {
        return resourcePlateTypeService.listByResourceGroupId(resourceGroupId);
    }


    /**
     * 获取所有的资源
     *
     * @return LabwarePlateTypeEntity list
     */
    public List<LabwarePlateTypeEntity> getAllPlates() {
        List<LabwarePlateTypeEntity> list = resourcePlateTypeService.lambdaQuery()
                .eq(LabwarePlateTypeEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        return new CopyOnWriteArrayList<>(list);
    }

    /**
     * 根据资源组ID获取所有的资源
     *
     * @param resourceGroupId 资源组ID
     * @return ResourceEntity list
     */
    public List<LabwarePlateTypeEntity> getAllPlates(long resourceGroupId) {
        List<LabwarePlateTypeEntity> list = resourcePlateTypeService.lambdaQuery()
                .eq(LabwarePlateTypeEntity::getIsDeleted, BooleanEnum.NO)
                .eq(LabwarePlateTypeEntity::getProjectId, resourceGroupId)
                .list();
        return new CopyOnWriteArrayList<>(list);
    }

    /**
     * 根据实验类型获取所有的资源
     *
     * @param experimentType 实验类型
     * @return ResourceEntity list
     */
    public List<LabwarePlateTypeEntity> getAllPlates(String experimentType) {
        Optional<ProjectEntity> experimentTypeOptional = resourceGroupService.getByExperimentType(experimentType);

        if (experimentTypeOptional.isPresent()) {
            ProjectEntity projectEntity = experimentTypeOptional.get();
            List<LabwarePlateTypeEntity> list = resourcePlateTypeService.lambdaQuery()
                    .eq(LabwarePlateTypeEntity::getIsDeleted, BooleanEnum.NO)
                    .eq(LabwarePlateTypeEntity::getProjectId, projectEntity.getId())
                    .list();
            return new CopyOnWriteArrayList<>(list);
        }

        return List.of();
    }

}
