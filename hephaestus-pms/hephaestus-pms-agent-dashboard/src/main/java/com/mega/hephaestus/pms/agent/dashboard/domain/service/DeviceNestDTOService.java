package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.mega.hephaestus.pms.agent.dashboard.domain.manager.ProjectDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.ProcessModel;
import com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct.DeviceNestStructMapper;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.DeviceNestGroupDTO;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.DeviceNestResultDTO;
import com.mega.hephaestus.pms.data.mysql.entity.DeviceNestGroupEntity;
import com.mega.hephaestus.pms.data.mysql.service.IDeviceNestGroupService;
import com.mega.hephaestus.pms.data.mysql.service.IDeviceNestService;
import com.mega.hephaestus.pms.data.mysql.view.NestPlateView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

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
public class DeviceNestDTOService {


    // 获取项目
    private final ProjectDTOManager projectDTOManager;

    private final IDeviceNestGroupService nestGroupService;

    private final IDeviceNestService nestService;


    /***
     * 一个设备多个塔
     * 一个塔多个存储位
     * 一个存储位对应一个耗材
     */
    public List<DeviceNestGroupDTO> listByDeviceKey(String deviceKey) {

        Optional<ProcessModel> currentProcess = projectDTOManager.getCurrentProcess();
        if (currentProcess.isPresent()) {
            Long projectId = currentProcess.get().getProjectId();
            // 多少组
            List<DeviceNestGroupEntity> deviceNestGroupEntities = nestGroupService.listByProjectIdAndDeviceKey(projectId, deviceKey);
            if (CollectionUtils.isNotEmpty(deviceNestGroupEntities)) {

                List<Long> collect = deviceNestGroupEntities.stream().map(DeviceNestGroupEntity::getId).collect(Collectors.toList());
                // 查询托架和板
                List<NestPlateView> nestPlateViews = listByGroupId(collect);

                List<DeviceNestResultDTO> deviceNestResultDList = DeviceNestStructMapper.INSTANCE.view2SearchResultDto(nestPlateViews);

                return transformNestGroup(deviceNestGroupEntities,deviceNestResultDList);
            }
//            nestService
            return List.of();
        }
        return List.of();
    }


    private List<DeviceNestGroupDTO> transformNestGroup(List<DeviceNestGroupEntity> groupNestList, List<DeviceNestResultDTO> nestList) {
        if (CollectionUtils.isNotEmpty(groupNestList)) {
            Map<Long, List<DeviceNestResultDTO>> nestMap   = nestList.stream().collect(Collectors.groupingBy(DeviceNestResultDTO::getDeviceNestGroupId));

           return groupNestList.stream().map(v ->{
                Long id = v.getId();

                DeviceNestGroupDTO dto = new DeviceNestGroupDTO();
                dto.setId(id);
                dto.setProjectId(v.getProjectId());
                dto.setName(v.getName());
                dto.setTowerNo(v.getTowerNo());

                if(nestMap.containsKey(id)){
                    List<DeviceNestResultDTO> nestResultList = nestMap.get(id);
                    if(CollectionUtils.isNotEmpty(nestResultList)){
                        dto.setNestList(nestResultList);
                    }
                }

                return dto;
            }).collect(Collectors.toList());
        }
        return List.of();
    }

    private List<NestPlateView> listByGroupId(List<Long> ids) {
        return nestService.listByNestGroupId(ids);
    }

}
