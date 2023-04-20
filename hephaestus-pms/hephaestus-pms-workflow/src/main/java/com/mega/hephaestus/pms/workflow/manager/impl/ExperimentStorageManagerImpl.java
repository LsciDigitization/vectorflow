package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.hephaestus.pms.data.model.entity.HephaestusResourceStorage;
import com.mega.hephaestus.pms.data.model.enums.StorageStatusEnum;
import com.mega.hephaestus.pms.data.model.service.IHephaestusStorageService;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 11:47
 */
@Component
public class ExperimentStorageManagerImpl implements ExperimentStorageManager {

    @Autowired
    private IHephaestusStorageService storageService;

    /**
     * 分配一个存储位资源
     * 指定设备device_key
     * 指定资源池ID
     *
     * @param deviceKey 设备唯一标识
     * @param poolId 资源池Id
     * @return HephaestusStorage Optional
     */
    public Optional<HephaestusResourceStorage> allocate(String deviceKey, Long poolId) {
        HephaestusResourceStorage hephaestusStorage = storageService.lambdaQuery()
                .eq(HephaestusResourceStorage::getDeviceKey, deviceKey)
                .eq(HephaestusResourceStorage::getPoolId, poolId)
                .eq(HephaestusResourceStorage::getStorageStatus, StorageStatusEnum.LOCK)
                .orderByDesc(HephaestusResourceStorage::getId)
                .last("limit 1")
                .one();

        // 更新占用状态
        if (hephaestusStorage != null) {
            hephaestusStorage.setStorageStatus(StorageStatusEnum.BUSY.getValue());
            storageService.updateById(hephaestusStorage);
        }

        return Optional.ofNullable(hephaestusStorage);
    }

}
