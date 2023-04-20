package com.mega.hephaestus.pms.workflow.manager.plan;

import com.mega.hephaestus.pms.data.model.entity.InventoryLocationEntity;

import java.util.List;

public interface InventoryLocationManager {

    /**
     * 获取所有的库存位置
     *
     * @return List<InventoryLocation>
     */
    List<InventoryLocationEntity> getAllInventoryLocations();

    /**
     * 根据项目ID获取库存位置
     *
     * @param projectId 项目ID
     * @return List<InventoryLocation>
     */
    List<InventoryLocationEntity> getInventoryLocationsByProjectId(Long projectId);

    /**
     * 根据库存位置类型获取库存位置
     *
     * @param locationType 库存位置类型
     * @return List<InventoryLocation>
     */
    List<InventoryLocationEntity> getInventoryLocationsByLocationType(String locationType);
}
