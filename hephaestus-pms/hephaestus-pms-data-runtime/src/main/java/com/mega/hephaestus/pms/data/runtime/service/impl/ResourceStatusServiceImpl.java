package com.mega.hephaestus.pms.data.runtime.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceStatusEntity;
import com.mega.hephaestus.pms.data.runtime.mapper.ResourceStatusMapper;
import com.mega.hephaestus.pms.data.runtime.service.IResourceStatusService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@DS("runtime")
public class ResourceStatusServiceImpl extends
        ServiceImpl<ResourceStatusMapper, ResourceStatusEntity> implements IResourceStatusService {


    /**
     * 根据设备key 修改资源状态
     *
     * @param deviceKey      设备key
     * @param resourceStatus 资源状态
     * @return 是否成功
     */
    @Override
    public boolean updateOrInsertResourceStatus(String deviceKey, String resourceStatus,long processRecordId) {
        Optional<ResourceStatusEntity> resourceStatusOptional = getByDeviceKey(deviceKey,processRecordId);
        if (resourceStatusOptional.isPresent()) {
            ResourceStatusEntity resource = new ResourceStatusEntity();
            resource.setResourceStatus(resourceStatus);
            return lambdaUpdate()
                    .eq(ResourceStatusEntity::getDeviceKey, deviceKey)
                    .eq(ResourceStatusEntity::getProcessRecordId,processRecordId)
                    .update(resource);
        } else {
            ResourceStatusEntity resource = new ResourceStatusEntity();
            resource.setResourceStatus(resourceStatus);
            resource.setDeviceKey(deviceKey);
            resource.setProcessRecordId(processRecordId);
            return save(resource);
        }

    }

    /**
     * 根据设备key 获取资源状态
     *
     * @param deviceKey 设备key
     * @return 资源状态
     */
    @Override
    public Optional<ResourceStatusEntity> getByDeviceKey(String deviceKey, long processRecordId) {
        List<ResourceStatusEntity> list = lambdaQuery()
                .eq(ResourceStatusEntity::getDeviceKey, deviceKey)
                .list();

        if (CollectionUtils.isEmpty(list)) {
            return Optional.empty();
        }

        return Optional.ofNullable(list.get(0));
    }

    /**
     * 获取当前流程记录的 资源运行状态
     *
     * @param processRecordId 记录id
     * @return 资源状态
     */
    @Override
    public List<ResourceStatusEntity> listByProcessRecordId(long processRecordId) {
        List<ResourceStatusEntity> list = lambdaQuery()
                .eq(ResourceStatusEntity::getProcessRecordId, processRecordId)
                .list();

        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        }
        return List.of();
    }


}
