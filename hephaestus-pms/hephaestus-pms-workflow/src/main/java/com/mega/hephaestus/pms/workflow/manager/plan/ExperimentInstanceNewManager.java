package com.mega.hephaestus.pms.workflow.manager.plan;

import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.model.entity.*;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 实验实例操作
 */
@Component
public interface ExperimentInstanceNewManager {

    /*
     * 根据实验ID，开始一个新的实验
     * instanceStatus 枚举类
     * status 枚举类
     * activeStageStatus 枚举类
     * instanceTitle 实验实例名称生成方法
     */

    /**
     * 创建一个实验，并从第一个Stage开始执行
     *
     * @param experiment   实验对象
     * @param startStorage 开始位置ID，hephaestus_storage存储模型主键，所有实验必须从板栈开始。
     *                     结束位置ID，hephaestus_storage存储模型主键
     * @return InstanceEntity
     */
    InstanceEntity newExperimentInstance(ExperimentEntity experiment, HephaestusResourceStorage startStorage);

    /**
     * 开始一个实验
     *
     * @param instanceId 实例ID
     * @return InstanceEntity
     */
    InstanceEntity startInstance(long instanceId, String runId);

    /**
     * 开始一个实验
     *
     * @param instance InstanceEntity
     * @return InstanceEntity
     */
    InstanceEntity startInstance(InstanceEntity instance, String runId);

    /**
     * 更新实验实例状态
     *
     * @param instance InstanceEntity
     * @return InstanceEntity
     */
    InstanceEntity updateInstance(InstanceEntity instance);
    /**
     * 结束一个实验
     *
     * @param instanceId 实例ID
     * @return InstanceEntity
     */
    InstanceEntity endInstance(long instanceId);

    /**
     * 结束一个实验
     *
     * @param instance InstanceEntity
     * @return InstanceEntity
     */
    InstanceEntity endInstance(InstanceEntity instance);

    /**
     * 获取实例列表
     *
     * @param ids 主键集合
     * @return List<InstanceEntity>
     */
    List<InstanceEntity> getInstances(Set<Long> ids);

    /**
     * 获取运行中的 instance
     *<p>
     *    select * from  hephaestus_instance WHERE (id IN (?,?) AND current_step_total <= ? AND instance_status = ?)
     *
     *</p>
     * @param instanceIds 实例id
     * @param stepType    步骤
     * @return List<InstanceEntity>
     */
    List<InstanceEntity> getRunningInstances(Set<Long> instanceIds, StepTypeEnum stepType);

    /**
     * 获取实例
     * @param instanceId 实例id
     * @return Optional<InstanceEntity>
     */
    Optional<InstanceEntity> getInstance(long instanceId);

}
