package com.mega.hephaestus.pms.workflow.manager.plan;

import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;

import java.util.List;
import java.util.Optional;

/**
 * 实验执行
 */
public interface ExperimentExecutorManager {

    /**
     * 获取一个实验的实验实例
     * @param instanceId 实验实例ID
     * @return InstanceEntity Optional
     */
    Optional<InstanceEntity> getInstance(Long instanceId);

    /**
     * 获取一个实验的所有stages
     * @param instanceId 实验实例ID
     * @return HephaestusStage list
     */
    List<HephaestusStage> getStages(Long instanceId);


    /**
     * 获取一个实验的所有stage tasks
     * @param instanceId 实验实例ID
     * @return HephaestusStage list
     */
    List<HephaestusStageTask> getStageTasks(Long instanceId);


    /**
     * 获取实验的一个Stage
     * @param instanceId 实验实例ID
     * @param stageName 阶段名称
     * @return HephaestusStage Optional
     */
    Optional<HephaestusStage> getStage(Long instanceId, String stageName);

    /**
     * 获取实验的一个Stage
     * @param instanceEntity 实验实例HephaestusInstance
     * @param stageName 阶段名称
     * @return HephaestusStage Optional
     */
    Optional<HephaestusStage> getStage(InstanceEntity instanceEntity, String stageName);

    /**
     * 获取实验的一个Stage
     * @param stages HephaestusStage list
     * @param stageName 阶段名称
     * @return HephaestusStage Optional
     */
    Optional<HephaestusStage> getStage(List<HephaestusStage> stages, String stageName);


}
