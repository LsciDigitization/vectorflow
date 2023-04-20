package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.hephaestus.pms.data.model.entity.InventoryLocationEntity;
import com.mega.hephaestus.pms.data.model.service.IInventoryLocationService;
import com.mega.hephaestus.pms.workflow.manager.plan.InventoryLocationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryLocationManagerImpl implements InventoryLocationManager {

    private final IInventoryLocationService inventoryLocationService;

    @Override
    public List<InventoryLocationEntity> getAllInventoryLocations() {
        return inventoryLocationService.list();
    }

    @Override
    public List<InventoryLocationEntity> getInventoryLocationsByProjectId(Long projectId) {
        return inventoryLocationService.lambdaQuery()
                .eq(InventoryLocationEntity::getProjectId, projectId)
                .list();
    }

    @Override
    public List<InventoryLocationEntity> getInventoryLocationsByLocationType(String locationType) {
        return inventoryLocationService.lambdaQuery()
                .eq(InventoryLocationEntity::getLocationType, locationType)
                .list();
    }
}
