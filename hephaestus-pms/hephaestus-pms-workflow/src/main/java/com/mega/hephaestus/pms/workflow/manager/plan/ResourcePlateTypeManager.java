package com.mega.hephaestus.pms.workflow.manager.plan;

import com.mega.hephaestus.pms.data.model.entity.LabwarePlateTypeEntity;

import java.util.List;

/**
 * @author 胡贤明
 */
public interface ResourcePlateTypeManager {

    /**
     * 根据资源id查询板类型
     *
     * @param resourceGroupId 资源组id
     * @return List<LabwarePlateTypeEntity>
     */
    List<LabwarePlateTypeEntity> getResourcePlateTypes(long resourceGroupId);


    /**
     * 获取所有的资源
     *
     * @return LabwarePlateTypeEntity list
     */
    List<LabwarePlateTypeEntity> getAllPlates();

    /**
     * 根据资源组ID获取所有的资源
     *
     * @param resourceGroupId 资源组ID
     * @return ResourceEntity list
     */
    List<LabwarePlateTypeEntity> getAllPlates(long resourceGroupId);

    /**
     * 根据实验类型获取所有的资源
     *
     * @param experimentType 实验类型
     * @return ResourceEntity list
     */
    List<LabwarePlateTypeEntity> getAllPlates(String experimentType);


}
