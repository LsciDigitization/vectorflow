package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.mega.hephaestus.pms.agent.dashboard.domain.manager.ProcessRecordDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.ProjectDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.ProcessModel;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.ResourceDTO;
import com.mega.hephaestus.pms.data.model.entity.DeviceEntity;
import com.mega.hephaestus.pms.data.model.entity.ProcessEntity;
import com.mega.hephaestus.pms.data.model.entity.ResourceEntity;
import com.mega.hephaestus.pms.data.model.service.IDeviceService;
import com.mega.hephaestus.pms.data.model.service.IProcessService;
import com.mega.hephaestus.pms.data.model.service.IResourceService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 资源组管理表
 *
 * @author xianming.hu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceDTOService {

    private final IResourceService resourceService;

    private final IProcessRecordService hephaestusExperimentGroupHistoryService;

    private final IProcessService hephaestusExperimentGroupService;

    private final IDeviceService deviceService;
    // 获取流程
    private final ProcessRecordDTOManager processRecordDTOManager;

    // 获取项目
    private final ProjectDTOManager projectDTOManager;

    public List<ResourceDTO> resourceColorByRunning() {


        Optional<ProcessRecordEntity> lastOptional = processRecordDTOManager.getLast();

        if (lastOptional.isPresent()) {
            // 查询正在运行的实验组的板池情况
            return resourceColor(lastOptional.get().getId());
        }

        return List.of();
    }

    public List<ResourceDTO> resourceColor(Long historyId) {
        ProcessRecordEntity processRecordEntity = hephaestusExperimentGroupHistoryService.getById(historyId);
        ProcessEntity processEntity = hephaestusExperimentGroupService.getById(processRecordEntity.getProcessId());
        List<ResourceEntity> resources = resourceService.listByProjectId(processEntity.getProjectId());

        List<String> deviceKeyList = resources.stream().map(ResourceEntity::getDeviceKey).collect(Collectors.toList());
        List<DeviceEntity> deviceEntities = deviceService.listByDeviceKeys(deviceKeyList);

        Map<String, List<DeviceEntity>> devicesMap = deviceEntities.stream().collect(Collectors.groupingBy(DeviceEntity::getDeviceKey));

        List<ResourceDTO> resourceDTOS = new ArrayList<>();
        for (ResourceEntity resource : resources) {
            ResourceDTO dto = new ResourceDTO();
            dto.setColor(resource.getResourceColor());
            dto.setDeviceKey(resource.getDeviceKey());
            dto.setDeviceType(resource.getDeviceType());
            dto.setResourceBottleneck(resource.getResourceBottleneck());
            dto.setResourcePlateNumber(resource.getResourcePlateNumber());

            if (devicesMap.containsKey(resource.getDeviceKey())) {
                List<DeviceEntity> devices = devicesMap.get(resource.getDeviceKey());
                if (CollectionUtils.isNotEmpty(devices)) {
                    dto.setDeviceTypeName(devices.get(0).getDeviceName());
                }
            }
            resourceDTOS.add(dto);
        }

        return resourceDTOS;
    }

    // 获取存储类设备
    public List<ResourceDTO> listStorageDevice(){
        Optional<ProcessModel> currentProcess = projectDTOManager.getCurrentProcess();
        if(currentProcess.isPresent()){
            List<DeviceEntity> deviceEntities = deviceService.listStorageByProjectId(currentProcess.get().getProjectId());
            return deviceEntities.stream().map(v ->{
                ResourceDTO dto = new ResourceDTO();
                dto.setDeviceKey(v.getDeviceKey());
                dto.setDeviceType(v.getDeviceType());
                dto.setDeviceName(v.getDeviceName());
                return dto;
            }).collect(Collectors.toList());
        }
        return List.of();
    }
}
