package com.mega.hephaestus.pms.data.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mega.hephaestus.pms.data.model.entity.LabwarePlateTypeEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * 板类型
 *
 * @author xianming.hu
 */
public interface ILabwarePlateTypeService extends IService<LabwarePlateTypeEntity> {


    /**
     * 根据资源id查询板类型
     * @param resourceGroupId 资源组id
     * @return List<LabwarePlateTypeEntity>
     */
    List<LabwarePlateTypeEntity> listByResourceGroupId(long resourceGroupId);

    /**
     * 根据板key 查询板类型
     * @param plateKey 板类型key
     * @return 板类型
     */
    Optional<LabwarePlateTypeEntity> getByPlateKey(String plateKey);

    /**
     * 根据 key 批量查询
     * @param keys keys
     * @return   List<LabwarePlateTypeEntity>
     */
    List<LabwarePlateTypeEntity> listByKeys(Set<String> keys);


    Optional<LabwarePlateTypeEntity> getByPlateKey(String plateKey,long projectId);
}

