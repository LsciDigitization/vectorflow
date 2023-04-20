package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceService;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentExecutorManager;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentStageManager;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentStageTaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 11:27
 */
@Component
public class ExperimentExecutorManagerImpl implements ExperimentExecutorManager {

    @Autowired
    private ExperimentStageManager experimentStageManager;

    @Autowired
    private ExperimentStageTaskManager experimentStageTaskManager;

    @Autowired
    private IInstanceService instanceService;

    /**
     * 获取一个实验的实验实例
     * @param instanceId 实验实例ID
     * @return InstanceEntity Optional
     */
    public Optional<InstanceEntity> getInstance(Long instanceId) {
        InstanceEntity instanceEntity = instanceService.getById(instanceId);
        return Optional.ofNullable(instanceEntity);
    }

    /**
     * 获取一个实验的所有stages
     * @param instanceId 实验实例ID
     * @return HephaestusStage list
     */
    public List<HephaestusStage> getStages(Long instanceId) {
        InstanceEntity instanceEntity = instanceService.getById(instanceId);
        if (instanceEntity != null) {
            return experimentStageManager.getStages(instanceEntity.getExperimentId());
        }
        return List.of();
    }


    /**
     * 获取一个实验的所有stage tasks
     * @param instanceId 实验实例ID
     * @return HephaestusStage list
     */
    public List<HephaestusStageTask> getStageTasks(Long instanceId) {
        List<HephaestusStage> stages = getStages(instanceId);

        return stages.stream()
                .map(stage -> experimentStageTaskManager.getStageTasks(stage.getExperimentId(), stage.getId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    /**
     * 获取实验的一个Stage
     * @param instanceId 实验实例ID
     * @param stageName 阶段名称
     * @return HephaestusStage Optional
     */
    public Optional<HephaestusStage> getStage(Long instanceId, String stageName) {
        List<HephaestusStage> stages = getStages(instanceId);
        return stages.stream()
                .filter(v -> v.getStageName().equals(stageName))
                .findFirst();
    }

    /**
     * 获取实验的一个Stage
     * @param instanceEntity 实验实例HephaestusInstance
     * @param stageName 阶段名称
     * @return HephaestusStage Optional
     */
    public Optional<HephaestusStage> getStage(InstanceEntity instanceEntity, String stageName) {
        List<HephaestusStage> stages = experimentStageManager.getStages(instanceEntity.getExperimentId());
        return stages.stream()
                .filter(v -> v.getStageName().equals(stageName))
                .findFirst();
    }

    /**
     * 获取实验的一个Stage
     * @param stages HephaestusStage list
     * @param stageName 阶段名称
     * @return HephaestusStage Optional
     */
    public Optional<HephaestusStage> getStage(List<HephaestusStage> stages, String stageName) {
        return stages.stream()
                .filter(v -> v.getStageName().equals(stageName))
                .findFirst();
    }

}
