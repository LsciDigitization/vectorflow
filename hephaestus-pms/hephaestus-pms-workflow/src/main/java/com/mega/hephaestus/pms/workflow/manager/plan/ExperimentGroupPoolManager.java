package com.mega.hephaestus.pms.workflow.manager.plan;

import com.mega.hephaestus.pms.data.model.entity.HephaestusExperimentGroupPool;

import java.util.List;

/**
 * 实验资源池操作
 */
public interface ExperimentGroupPoolManager {

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
    List<HephaestusExperimentGroupPool> getExperimentGroupPools(long historyId);

    List<HephaestusExperimentGroupPool> getExperimentGroupPoolsByGroupId(long groupId);

}
