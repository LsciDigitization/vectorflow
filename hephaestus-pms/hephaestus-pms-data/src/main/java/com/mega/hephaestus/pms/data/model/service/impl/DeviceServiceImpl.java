package com.mega.hephaestus.pms.data.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.hephaestus.pms.data.model.entity.DeviceEntity;
import com.mega.hephaestus.pms.data.model.enums.DeviceModelTypeEnum;
import com.mega.hephaestus.pms.data.model.enums.StatusEnum;
import com.mega.hephaestus.pms.data.model.mapper.DeviceMapper;
import com.mega.hephaestus.pms.data.model.service.IDeviceService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 硬件配置表
 *
 * @author xianming.hu
 */
@Slf4j
@Service
public class DeviceServiceImpl extends
        ServiceImpl<DeviceMapper, DeviceEntity> implements IDeviceService {
    /**
     * 根据设备名称查询设备
     *
     * @param deviceName 设备名称
     * @return DeviceEntity 设备对象
     */
    @Override
    public Optional<DeviceEntity> getByDeviceName(String deviceName) {
        DeviceEntity one = lambdaQuery().eq(DeviceEntity::getDeviceName, deviceName).one();
        return Optional.ofNullable(one);
    }



    @Override
    public List<DeviceEntity> getDevices() {
        List<DeviceEntity> list = lambdaQuery()
                .eq(DeviceEntity::getIsDeleted, BooleanEnum.NO)
                .eq(DeviceEntity::getStatus, StatusEnum.NORMAL)
                .orderByAsc(DeviceEntity::getId)
                .list();

        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }

    @Override
    public DeviceEntity getByDeviceId(String deviceId) {
        return lambdaQuery().eq(DeviceEntity::getDeviceId, deviceId).one();
    }

    /**
     * 根据设备key 获取设备
     *
     * @param deviceKeys key集合
     * @return 设备集合
     */
    @Override
    public List<DeviceEntity> listByDeviceKeys(List<String> deviceKeys) {
         List<DeviceEntity> list = lambdaQuery().in(DeviceEntity::getDeviceKey, deviceKeys)
                .list();
        if(CollectionUtils.isEmpty(list)){
            return List.of();
        }
        return list;
    }

    @Override
    public List<DeviceEntity> getDevices(List<String> deviceKeys) {
        List<DeviceEntity> list = lambdaQuery()
                .in(!CollectionUtils.isEmpty(deviceKeys), DeviceEntity::getDeviceKey, deviceKeys)
                .eq(DeviceEntity::getIsDeleted, BooleanEnum.NO)
                .eq(DeviceEntity::getStatus, StatusEnum.NORMAL)
                .orderByAsc(DeviceEntity::getId)
                .list();

        if (Objects.isNull(list)) {
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
    public Optional<DeviceEntity> getByDeviceKey(String deviceKey) {
        List<DeviceEntity> list = lambdaQuery()
                .eq(DeviceEntity::getDeviceKey,deviceKey)
                .eq(DeviceEntity::getIsDeleted, BooleanEnum.NO)
                .list();

        if (CollectionUtils.isEmpty(list)) {
            return Optional.empty();
        }

        return Optional.ofNullable(list.get(0));
    }

    @Override
    public boolean updateResourceStatus(String deviceKey, String resourceStatus) {
        DeviceEntity device = new DeviceEntity();
        device.setResourceStatus(resourceStatus);

        return lambdaUpdate()
                .eq(DeviceEntity::getDeviceKey, deviceKey)
                .update(device);
    }

    /**
     * 根据多个key和状态获取设备
     *
     * @param deviceKeys 设备key
     * @param status     状态
     * @return 设备列表
     */
    @Override
    public List<DeviceEntity> listByDeviceKeysAndResourceStatus(List<String> deviceKeys, String status) {
        List<DeviceEntity> list = lambdaQuery()
                .in(!CollectionUtils.isEmpty(deviceKeys), DeviceEntity::getDeviceKey, deviceKeys)
                .eq(DeviceEntity::getIsDeleted, BooleanEnum.NO)
                .eq(DeviceEntity::getResourceStatus, status)
                .orderByAsc(DeviceEntity::getId)
                .list();
        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }

    @Override
    public List<DeviceEntity> listByProjectId(long projectId) {
        List<DeviceEntity> list = lambdaQuery()
                .eq(DeviceEntity::getIsDeleted, BooleanEnum.NO)
                .eq(DeviceEntity::getProjectId, projectId)
                .list();
        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }

    /**
     * 根据项目id 获取存储类设备
     *
     * @param projectId 项目id
     * @return 存储类设备
     */
    @Override
    public List<DeviceEntity> listStorageByProjectId(long projectId) {
        List<DeviceEntity> list = lambdaQuery()
                .eq(DeviceEntity::getProjectId, projectId)
                .eq(DeviceEntity::getDeviceModel, DeviceModelTypeEnum.STORAGE.getValue())
                .eq(DeviceEntity::getIsDeleted, BooleanEnum.NO)
                .list();
        if (Objects.isNull(list)) {
            return List.of();
        }

        return list;
    }

}
