package com.mega.hephaestus.pms.workflow.manager.plan;

import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.workflow.work.worktask.WorkTaskDomain;

import java.util.function.Consumer;

/**
 * 实验管理操作
 */
@Deprecated(since = "20230323")
public interface ExperimentManager {

    /**
     * 从头开始一个实验
     *
     * @param experimentId 实验ID
     * @param startStorageId 存储板ID
     */
    void startWorkflow(Long experimentId, Long startStorageId);

    /**
     * 从头开始一个实验
     *
     * @param experimentId 实验ID
     * @param workTaskDomain WorkTaskDomain
     */
    void startWorkflow(Long experimentId, WorkTaskDomain workTaskDomain);

    /**
     * 从头开始一个实验
     *
     * @param experimentId 实验ID
     * @param workTaskDomain WorkTaskDomain
     */
    void startWorkflow(Long experimentId, WorkTaskDomain workTaskDomain, Consumer<InstanceEntity> consumer);


    /**
     * 从头开始一个实验
     *
     * @param experimentId 实验ID
     */
    void startWorkflow(Long experimentId, String title, Long startStorageId);

    /**
     * 指定一个阶段，开始一个实验
     *
     * @param experimentId 实验ID
     */
    void startWorkflow(Long experimentId, String stageName, String title, Long startStorageId);

    /**
     * 从头开始一个实验
     *
     * @param instanceId 实例ID
     */
    void startWorkflowStage(Long instanceId);

    /**
     * 从头开始一个实验
     *
     * @param instanceId 实例ID
     * @param stageName 阶段名称
     */
    void startWorkflowStage(Long instanceId, String stageName);

}
