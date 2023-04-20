package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.hephaestus.pms.data.mysql.entity.ProcessLabwareEntity;
import com.mega.hephaestus.pms.data.mysql.service.IProcessLabwareService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ProcessLabwareManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * @author wangzhengdong
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessLabwareManagerImpl implements ProcessLabwareManager {
    private final IProcessRecordService historyService;
    private final IProcessLabwareService processLabwareService;


    /**
     * 根据实验组历史id 获取实验组池
     *
     * @param processRecordId 流程记录id(原实验组历史id)
     * @return List<ProcessLabwareEntity>
     */
    @Override
    public List<ProcessLabwareEntity> getProcessLabwares(long processRecordId) {
        ProcessRecordEntity groupHistory = historyService.getById(processRecordId);


        if (Objects.nonNull(groupHistory)) {
            Long processId = groupHistory.getProcessId();

            List<ProcessLabwareEntity> processLabwareList = processLabwareService.listByProcessId(processId);
            if (CollectionUtils.isNotEmpty(processLabwareList)) {
                return processLabwareList;
            }
            return List.of();
        }

        return List.of();
    }

    /**
     * 根据流程ID 获取流程耗材池
     *
     * @param processId 流程id(原实验组id)
     * @return List<ProcessLabwareEntity>
     */
    @Override
    public List<ProcessLabwareEntity> getProcessLabwaresByProcessId(long processId) {
        List<ProcessLabwareEntity> processLabwareList = processLabwareService.listByProcessId(processId);
        if (CollectionUtils.isNotEmpty(processLabwareList)) {
            return processLabwareList;
        }
        return List.of();
    }

    /**
     * 根据流程id 拿到通量主任务
     *
     * @param processId 流程id
     * @return 通量主任务
     */
    @Override
    public Optional<ProcessLabwareEntity> getMainByProcessId(long processId) {
        List<ProcessLabwareEntity> processLabwareList = processLabwareService.listByProcessId(processId);
        if (CollectionUtils.isNotEmpty(processLabwareList)) {
            return processLabwareList.stream().filter(v ->v.getIsMain().toBoolean()).findFirst();

        }
        return Optional.empty();
    }

    /**
     * 根据实验id 拿到对应的耗材流程
     *
     * @param experimentId 实验id
     * @param processId    流程id
     * @return 流程耗材
     */
    @Override
    public Optional<ProcessLabwareEntity> getByExperimentIdAndProcessId(long experimentId, long processId) {
        return processLabwareService.getByExperimentIdAndProcessId(experimentId,processId);
    }
}
