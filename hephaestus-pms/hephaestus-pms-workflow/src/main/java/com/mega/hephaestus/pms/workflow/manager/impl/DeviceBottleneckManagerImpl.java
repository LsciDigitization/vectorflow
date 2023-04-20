package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.hephaestus.pms.data.runtime.entity.ResourceBottleneckEntity;
import com.mega.hephaestus.pms.data.runtime.service.IResourceBottleneckService;
import com.mega.hephaestus.pms.workflow.manager.dynamic.DeviceBottleneckManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DeviceBottleneckManagerImpl implements DeviceBottleneckManager {

    private final IResourceBottleneckService bottleneckService;

    public void save(ResourceBottleneckEntity deviceBottleneck ) {
        if (Objects.nonNull(deviceBottleneck)) {
            bottleneckService.save(deviceBottleneck);
        }
    }

}
