package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import java.util.*;
import java.util.stream.Collectors;



import com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct.HephaestusInstanceStructMapper;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.*;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceService;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceTaskService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xianming.hu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InstanceDTOService {

    private final IInstanceService hephaestusInstanceService;
    private final IInstanceTaskService instanceTaskService;




    public List<HephaestusInstanceDynamicDTO> instanceDynamicList() {

        List<InstanceTaskEntity> list = instanceTaskService.list();
        if (CollectionUtils.isNotEmpty(list)) {
            List<Long> instanceIds = list.stream().map(InstanceTaskEntity::getInstanceId).collect(Collectors.toList());
            List<HephaestusInstanceDynamicDTO> result = new ArrayList<>(list.size());

            final Map<Long, List<InstanceTaskEntity>> collect = list.stream().collect(Collectors.groupingBy(InstanceTaskEntity::getInstanceId));

            List<InstanceEntity> instanceEntities = hephaestusInstanceService.listByIds(instanceIds);

            collect.forEach((k, v) -> {
                HephaestusInstanceDynamicDTO dto = new HephaestusInstanceDynamicDTO();

                instanceEntities.forEach(instance -> {
                    if (String.valueOf(k).equals(instance.getId().toString())) {
                        dto.setInstanceTitle(instance.getInstanceTitle());
                    }

                });


                List<HephaestusInstanceDynamicDTO.StageTaskEntityDTO> stageTaskEntityDTOList = new ArrayList<>();
                v.forEach(stageTaskEntity -> {

                    HephaestusInstanceDynamicDTO.StageTaskEntityDTO stageTaskEntityDTO = new HephaestusInstanceDynamicDTO.StageTaskEntityDTO();
                    stageTaskEntityDTO.setTaskStatus(stageTaskEntity.getTaskStatus());
                    stageTaskEntityDTO.setTaskStartTime(stageTaskEntity.getStartTime());
                    stageTaskEntityDTOList.add(stageTaskEntityDTO);

                });
                dto.setStageTaskList(stageTaskEntityDTOList);
                result.add(dto);
            });
            return result;
        }


        return List.of();
    }

    /**
     * 根据实验组历史id查询 instance列表
     *
     * @param experimentGroupHistoryId 实验组历史表
     * @return 实验组集合
     */
    public List<HephaestusInstanceSearchResultDTO> listByExperimentGroupHistoryId(Long experimentGroupHistoryId, Long experimentId) {
        List<InstanceEntity> instanceEntities = hephaestusInstanceService.listByExperimentGroupHistoryId(experimentGroupHistoryId, experimentId);
        return HephaestusInstanceStructMapper.INSTANCE
                .entity2SearchResultDTO(instanceEntities);
    }


}
