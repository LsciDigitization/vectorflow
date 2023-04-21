package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.mega.hephaestus.pms.agent.dashboard.domain.manager.InstanceIterationDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.InstanceLabwareDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.ProcessRecordDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.InstanceIterationModel;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.InstanceIterationDTO;
import com.mega.hephaestus.pms.data.model.entity.LabwarePlateTypeEntity;
import com.mega.hephaestus.pms.data.model.service.ILabwarePlateTypeService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 胡贤明
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InstanceIterationDTOService {

    private final IProcessRecordService groupHistoryService;

    private final InstanceIterationDTOManager manager;

    private final InstanceLabwareDTOManager labwareDTOManager;

    private final ILabwarePlateTypeService plateTypeService;

    private final ProcessRecordDTOManager processRecordDTOManager;

    /**
     * 查询开
     */
    public List<InstanceIterationDTO> list(){
        // 获取正在运行的实验组
        Optional<ProcessRecordEntity> groupHistoryOptional = processRecordDTOManager.getLast();
         if(groupHistoryOptional.isPresent()){
            // 拿到所有的通量
            List<InstanceIterationModel> allInstanceIterationModel = manager.getAllInstanceIterationModel(groupHistoryOptional.get().getId());

            // 所有通量的id
            List<Long> iterationIds = allInstanceIterationModel.stream().map(InstanceIterationModel::getId).collect(Collectors.toList());

            // 拿到所有的耗材类型
            List<InstanceLabwareModel> labwareEntityList = labwareDTOManager.listByIterationIds(iterationIds);

            // key 通量id value 为通量对应的耗材
            Map<Long, List<InstanceLabwareModel>> collect = labwareEntityList.stream().collect(Collectors.groupingBy(InstanceLabwareModel::getIterationId));

            // 所有耗材 类型
            Set<String> labwareTypes = labwareEntityList.stream().map(InstanceLabwareModel::getLabwareType).distinct().collect(Collectors.toSet());
            List<LabwarePlateTypeEntity> labwarePlateTypeEntities = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(labwareTypes)){
                labwarePlateTypeEntities = plateTypeService.listByKeys(labwareTypes);
            }

            Map<String, List<LabwarePlateTypeEntity>> plateTypeMap = labwarePlateTypeEntities.stream().collect(Collectors.groupingBy(LabwarePlateTypeEntity::getPlateKey));

            return allInstanceIterationModel.stream().map(iterationModel ->{
                InstanceIterationDTO instanceIterationDTO = new InstanceIterationDTO();
                instanceIterationDTO.setId(iterationModel.getId());
                instanceIterationDTO.setProjectId(iterationModel.getProjectId());
                instanceIterationDTO.setIterationNo(iterationModel.getIterationNo());
                instanceIterationDTO.setIsConsumed(iterationModel.getIsConsumed());
                instanceIterationDTO.setConsumeTime(iterationModel.getConsumeTime());
                instanceIterationDTO.setIsFinished(iterationModel.getIsFinished());
                instanceIterationDTO.setFinishTime(iterationModel.getFinishTime());

                List<InstanceLabwareModel> labwareList = collect.get(iterationModel.getId());
                if(CollectionUtils.isNotEmpty(labwareList)){
                    List<InstanceIterationDTO.LabwareDTO> labwareDTOList = labwareList.stream().map(labware -> {

                        InstanceIterationDTO.LabwareDTO labwareDTO = new InstanceIterationDTO.LabwareDTO();
                        labwareDTO.setId(labware.getId());
                        labwareDTO.setLabwareType(labware.getLabwareType());
                        if(plateTypeMap.containsKey(labware.getLabwareType())){
                            List<LabwarePlateTypeEntity> plateTypes = plateTypeMap.get(labware.getLabwareType());
                            if(CollectionUtils.isNotEmpty(plateTypes)){
                                labwareDTO.setLabwareName(plateTypes.get(0).getPlateName());
                                labwareDTO.setLabwareColor(plateTypes.get(0).getPlateColor());
                            }
                        }
                        return labwareDTO;
                    }).collect(Collectors.toList());
                    instanceIterationDTO.setLabwares(labwareDTOList);
                }
                return instanceIterationDTO;
            }).collect(Collectors.toList());

        }
         return List.of();

    }



}
