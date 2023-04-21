package com.mega.hephaestus.pms.agent.dashboard.domain.service;


import com.mega.hephaestus.pms.agent.dashboard.domain.resp.PlateTypeColorDTO;
import com.mega.hephaestus.pms.data.model.entity.ProcessEntity;
import com.mega.hephaestus.pms.data.model.entity.LabwarePlateTypeEntity;

import com.mega.hephaestus.pms.data.model.service.IProcessService;
import com.mega.hephaestus.pms.data.model.service.ILabwarePlateTypeService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author yinyse
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlateTypeDTOService {


    private final ILabwarePlateTypeService resourcePlateTypeService;

    private final IProcessRecordService hephaestusExperimentGroupHistoryService;

    private final IProcessService hephaestusExperimentGroupService;


    private final IProcessRecordService groupHistoryService;

    public List<PlateTypeColorDTO> plateTypeColorByRunning() {

        Optional<ProcessRecordEntity> lastOptional = groupHistoryService.getLast();
        if (lastOptional.isPresent()) {
            // 查询正在运行的实验组的板池情况
            return plateTypeColor(lastOptional.get().getId());
        }

        return List.of();
    }

    public List<PlateTypeColorDTO> plateTypeColor(Long historyId) {
        ProcessRecordEntity processRecordEntity = hephaestusExperimentGroupHistoryService.getById(historyId);
        ProcessEntity processEntity = hephaestusExperimentGroupService.getById(processRecordEntity.getProcessId());
        List<LabwarePlateTypeEntity> labwarePlateTypeEntities = resourcePlateTypeService.listByResourceGroupId(processEntity.getProjectId());
        List<PlateTypeColorDTO> plateTypeColorDTOS = new ArrayList<>();
        for (LabwarePlateTypeEntity labwarePlateTypeEntity : labwarePlateTypeEntities) {
            PlateTypeColorDTO dto = new PlateTypeColorDTO();
            dto.setColor(labwarePlateTypeEntity.getPlateColor());
            dto.setPoolTypeName(labwarePlateTypeEntity.getPlateType());
            dto.setPoolTypeName(dto.getPoolTypeName());
            dto.setPoolKey(labwarePlateTypeEntity.getPlateKey());
            plateTypeColorDTOS.add(dto);
        }
        return plateTypeColorDTOS;
    }


}
