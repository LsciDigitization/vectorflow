package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.mega.hephaestus.pms.agent.dashboard.domain.manager.ProcessRecordDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct.InstanceCapStructMapper;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.InstanceCapDTO;
import com.mega.hephaestus.pms.data.model.enums.XcapStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceCapEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceCapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 胡贤明
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InstanceCapDTOService {


    private final IInstanceCapService instanceCapService;

    private final ProcessRecordDTOManager processRecordDTOManager;

    /**
     * 查询开
     * @return
     */
    public List<InstanceCapDTO> listInstanceCapByOpen(){
        // 获取正在运行的实验组
        Optional<ProcessRecordEntity> runningGroupOptional = processRecordDTOManager.getLast();

        if(runningGroupOptional.isPresent()){
            List<InstanceCapEntity> list =  instanceCapService.listByStatusAndExperimentGroupHistoryId(XcapStatusEnum.Open,runningGroupOptional.get().getId());
            if(CollectionUtils.isNotEmpty(list)){
                return InstanceCapStructMapper.INSTANCE.entity2DTO(list);
            }
            return List.of();
        }
         return List.of();

    }
}
