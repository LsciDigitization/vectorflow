package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.hephaestus.pms.data.model.entity.HephaestusExperimentGroupPool;
import com.mega.hephaestus.pms.data.model.service.IHephaestusExperimentGroupPoolService;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentGroupPoolManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 11:33
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ExperimentGroupPoolManagerImpl implements ExperimentGroupPoolManager {

    private final IProcessRecordService historyService;
    private final IHephaestusExperimentGroupPoolService groupPoolService;

    /**
     * 根据实验组历史id 获取实验组池
     * <p>
     * SELECT * FROM hephaestus_experiment_group_history WHERE id=?
     * <p>Parameters: 1602284273584750594(Long)
     * <p>
     * <p>SELECT * FROM hephaestus_experiment_group_pool WHERE (experiment_group_id = ? AND is_deleted = ?)
     * <p>Parameters:1570357154636447747(Long), 0(Integer)
     *
     * @param historyId 实验组历史id
     * @return List<HephaestusExperimentGroupPool>
     */
    public List<HephaestusExperimentGroupPool> getExperimentGroupPools(long historyId) {
        ProcessRecordEntity groupHistory = historyService.getById(historyId);

        if (Objects.nonNull(groupHistory)) {
            Long groupId = groupHistory.getProcessId();

            List<HephaestusExperimentGroupPool> hephaestusExperimentGroupPools = groupPoolService.listByExperimentGroupId(groupId);
            if (CollectionUtils.isNotEmpty(hephaestusExperimentGroupPools)) {
                return hephaestusExperimentGroupPools;
            }
            return List.of();
        }

        return List.of();
    }

    public List<HephaestusExperimentGroupPool> getExperimentGroupPoolsByGroupId(long groupId) {
        List<HephaestusExperimentGroupPool> hephaestusExperimentGroupPools = groupPoolService.listByExperimentGroupId(groupId);
        if (CollectionUtils.isNotEmpty(hephaestusExperimentGroupPools)) {
            return hephaestusExperimentGroupPools;
        }

        return List.of();
    }

}
