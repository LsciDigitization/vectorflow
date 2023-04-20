package com.mega.hephaestus.pms.workflow.manager.plan;

import com.mega.hephaestus.pms.data.model.entity.HephaestusResourceStorage;

import java.util.Optional;

/**
 * 实验存储位操作
 */
public interface ExperimentStorageManager {

    /**
     * 分配一个存储位资源
     * 指定设备device_key
     * 指定资源池ID
     *
     * @param deviceKey 设备唯一标识
     * @param poolId 资源池Id
     * @return HephaestusStorage Optional
     */
    Optional<HephaestusResourceStorage> allocate(String deviceKey, Long poolId);


}
