package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.mega.hephaestus.pms.agent.dashboard.domain.manager.ProcessRecordDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct.HephaestusDeviceTaskStructMapper;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.ResourceTaskDTO;
import com.mega.hephaestus.pms.data.model.enums.DeviceTaskStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ResourceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.service.IDeviceTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author yinyse
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceTaskDTOService {


    private final IDeviceTaskService hephaestusDeviceTaskService;


    private final ProcessRecordDTOManager processRecordDTOManager;


    public List<ResourceTaskDTO> deviceTaskDynamic(){
        // 获取正在运行的实验组
        Optional<ProcessRecordEntity> runningGroupOptional = processRecordDTOManager.getLast();

        if(runningGroupOptional.isPresent()){
            List<ResourceTaskEntity> resourceTaskEntities = hephaestusDeviceTaskService.listByStatus(DeviceTaskStatusEnum.Running.getValue());
            return HephaestusDeviceTaskStructMapper.INSTANCE
                    .entity2ResourceTaskDto(resourceTaskEntities);
        }
        return List.of();
    }

}
