package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.ResourceEntity;
import com.mega.hephaestus.pms.data.model.mapper.ResourceMapper;
import com.mega.hephaestus.pms.data.model.service.IResourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * 实验资源
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class ResourceServiceImpl extends
        ServiceImpl<ResourceMapper, ResourceEntity> implements IResourceService {



    @Override
    public List<ResourceEntity> getDevices() {
        List<ResourceEntity> list = lambdaQuery()
                .eq(ResourceEntity::getIsDeleted, BooleanEnum.NO)
                .orderByAsc(ResourceEntity::getId)
                .list();

        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }

    @Override
    public ResourceEntity getByDeviceId(String deviceId) {
        return null;
    }


    /**
     * 根据设备key 获取设备
     *
     * @param deviceKeys key集合
     * @return 设备集合
     */
    @Override
    public List<ResourceEntity> listByDeviceKeys(List<String> deviceKeys) {
        List<ResourceEntity> list = lambdaQuery().in(ResourceEntity::getDeviceKey, deviceKeys)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

    /**
     * 根据key 获取对象
     *
     * @param deviceKey key
     * @return 获取对象
     */
    @Override
    public Optional<ResourceEntity> getByDeviceKey(String deviceKey) {
        List<ResourceEntity> list = lambdaQuery()
                .eq(ResourceEntity::getDeviceKey, deviceKey)
                .eq(ResourceEntity::getIsDeleted, BooleanEnum.NO)
                .list();

        if (CollectionUtils.isEmpty(list)) {
            return Optional.empty();
        }

        return Optional.ofNullable(list.get(0));
    }

    @Override
    public Optional<ResourceEntity> getByDeviceKey(String deviceKey, String resourceId) {
        List<ResourceEntity> list = lambdaQuery()
                .eq(ResourceEntity::getDeviceKey, deviceKey)
                .eq(ResourceEntity::getIsDeleted, BooleanEnum.NO)
                .eq(ResourceEntity::getProjectId, resourceId)
                .list();

        if (CollectionUtils.isEmpty(list)) {
            return Optional.empty();
        }

        return Optional.ofNullable(list.get(0));
    }

    @Override
    public boolean updateResourceStatus(String deviceKey, String resourceStatus) {
        ResourceEntity resource = new ResourceEntity();
        resource.setResourceStatus(resourceStatus);

        return lambdaUpdate()
                .eq(ResourceEntity::getDeviceKey, deviceKey)
                .update(resource);
    }

    /**
     * 根据资源组id查询资源列表
     *
     * @param projectId 资源组id
     * @return List<ResourceEntity>
     */
    @Override
    public List<ResourceEntity> listByProjectId(long projectId) {
        List<ResourceEntity> list = lambdaQuery()
                .in(ResourceEntity::getProjectId, projectId)
                .eq(ResourceEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        return list;
    }

}
