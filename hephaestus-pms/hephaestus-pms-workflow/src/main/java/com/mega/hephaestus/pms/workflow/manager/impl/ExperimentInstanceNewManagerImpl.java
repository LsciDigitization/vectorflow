package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.model.entity.ExperimentEntity;
import com.mega.hephaestus.pms.data.model.entity.HephaestusExperimentGroupPool;
import com.mega.hephaestus.pms.data.model.entity.HephaestusResourceStorage;
import com.mega.hephaestus.pms.data.model.service.IHephaestusExperimentGroupPoolService;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.data.runtime.enums.ExperimentInstanceStatusEnum;
import com.mega.hephaestus.pms.data.runtime.enums.ExperimentStageStatusEnum;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceService;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentInstanceNewManager;
import com.mega.hephaestus.pms.workflow.instancecontext.ExperimentInstanceContext;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 11:35
 */
@Component
public class ExperimentInstanceNewManagerImpl implements ExperimentInstanceNewManager {

    @Autowired
    private IInstanceService instanceService;
    @Autowired
    private IHephaestusExperimentGroupPoolService experimentGroupPoolService;


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
    public InstanceEntity newExperimentInstance(ExperimentEntity experiment, HephaestusResourceStorage startStorage) {
        //创建实例上下文
//        ExperimentInstanceContext instanceContext = createInstanceContext(startStorage);
        String instanceTitle = makeInstanceTitle(experiment);

        InstanceEntity instanceEntity = new InstanceEntity();

        instanceEntity.setExperimentId(experiment.getId());
        instanceEntity.setInstanceTitle(instanceTitle);
//        instanceEntity.setInstanceContext(JSONUtil.toJsonStr(instanceContext));
        instanceEntity.setCreateTime(new Date());
        instanceEntity.setInstanceStatus(ExperimentInstanceStatusEnum.IDLE);
        instanceEntity.setActiveStageStatus(ExperimentStageStatusEnum.IDLE);
        instanceEntity.setStartStorageId(startStorage.getId());
        instanceEntity.setEndStorageId(startStorage.getId());
        instanceEntity.setCurrentStorageId(startStorage.getId());

        instanceService.save(instanceEntity); //持久化数据

        return instanceEntity;
    }

    /**
     * 开始一个实验
     *
     * @param instanceId 实例ID
     * @return InstanceEntity
     */
    public InstanceEntity startInstance(long instanceId, String runId) {
        Optional<InstanceEntity> instanceOptional = instanceService.getByIdForNoDeleted(instanceId);

        instanceOptional.ifPresent(instance -> {
            startInstance(instance, runId);
        });

        return instanceOptional.orElse(null);
    }

    /**
     * 开始一个实验
     *
     * @param instance InstanceEntity
     * @return InstanceEntity
     */
    public InstanceEntity startInstance(InstanceEntity instance, String runId) {
        InstanceEntity newInstanceEntity = new InstanceEntity();
        newInstanceEntity.setId(instance.getId());
        // 设为运行状态
        newInstanceEntity.setInstanceStatus(ExperimentInstanceStatusEnum.RUNNING);
        newInstanceEntity.setUpdateTime(new Date());
        newInstanceEntity.setInstanceStartTime(new Date());
        newInstanceEntity.setWorkflowRunId(runId);
        instanceService.updateById(newInstanceEntity);

        return newInstanceEntity;
    }

    /**
     * 更新实验实例状态
     *
     * @param instance InstanceEntity
     * @return InstanceEntity
     */
    public InstanceEntity updateInstance(InstanceEntity instance) {
        InstanceEntity newInstanceEntity = new InstanceEntity();
        newInstanceEntity.setId(instance.getId());
        newInstanceEntity.setProcessId(instance.getProcessId());
        newInstanceEntity.setProcessRecordId(instance.getProcessRecordId());
        instance.setUpdateTime(new Date());

        instanceService.updateById(newInstanceEntity);

        return newInstanceEntity;
    }

    /**
     * 结束一个实验
     *
     * @param instanceId 实例ID
     * @return InstanceEntity
     */
    public InstanceEntity endInstance(long instanceId) {
        Optional<InstanceEntity> instanceOptional = instanceService.getByIdForNoDeleted(instanceId);

        instanceOptional.ifPresent(this::endInstance);

        return instanceOptional.orElse(null);
    }

    /**
     * 结束一个实验
     *
     * @param instance InstanceEntity
     * @return InstanceEntity
     */
    public InstanceEntity endInstance(InstanceEntity instance) {
        InstanceEntity newInstanceEntity = new InstanceEntity();
        newInstanceEntity.setId(instance.getId());

        newInstanceEntity.setInstanceStatus(ExperimentInstanceStatusEnum.FINISHED);
        newInstanceEntity.setUpdateTime(new Date());
        newInstanceEntity.setInstanceEndTime(new Date());

        instanceService.updateById(newInstanceEntity);
        return newInstanceEntity;
    }


    /**
     * 生成实例名称
     *
     * @param experiment ExperimentEntity
     * @return 实例名称
     */
    private String makeInstanceTitle(ExperimentEntity experiment) {
        long time = (new Date()).getTime();
        String suffix = String.valueOf(time).substring(7);
        return experiment.getExperimentName() + "-" + suffix;
    }


    // 创建实例上下文
    @Deprecated(since = "20230330")
    private ExperimentInstanceContext createInstanceContext(HephaestusResourceStorage startStorage) {
        Optional<HephaestusExperimentGroupPool> experimentGroupPoolOptional = experimentGroupPoolService.getByIdForNoDeleted(startStorage.getPoolId());
        final AtomicReference<String> poolType = new AtomicReference<>();
        experimentGroupPoolOptional.ifPresentOrElse(hephaestusExperimentGroupPool -> {
            poolType.set(hephaestusExperimentGroupPool.getStoragePoolType());
        }, () -> {
            poolType.set(null);
        });

        ExperimentInstanceContext experimentInstanceContext = new ExperimentInstanceContext();
        experimentInstanceContext.setStartStorageId(startStorage.getId());
        experimentInstanceContext.setEndStorageId(startStorage.getId());
        experimentInstanceContext.setStartStorageKey(startStorage.getStorageKey());
        experimentInstanceContext.setStartPoolType(poolType.get());
        experimentInstanceContext.setStartPoolId(startStorage.getPoolId());

        return experimentInstanceContext;
    }

    /**
     * 获取实例列表
     *
     * @param ids 主键集合
     * @return List<InstanceEntity>
     */
    public List<InstanceEntity> getInstances(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return List.of();
        }

        List<InstanceEntity> instanceTaskList = instanceService.listByIds(ids);
        if (CollectionUtils.isNotEmpty(instanceTaskList)) {
            return instanceTaskList;
        }
        return List.of();
    }

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
    public List<InstanceEntity> getRunningInstances(Set<Long> instanceIds, StepTypeEnum stepType) {
        if (CollectionUtils.isNotEmpty(instanceIds)) {
            // 计算从step1 到当前的总数
            long sum = stepType.getStepTotal();
            List<InstanceEntity> instanceList = instanceService.lambdaQuery()
                    .in(InstanceEntity::getId, instanceIds)
                    .le(InstanceEntity::getCurrentStepTotal, sum)
                    .eq(InstanceEntity::getInstanceStatus, ExperimentInstanceStatusEnum.RUNNING).list();

            if (CollectionUtils.isNotEmpty(instanceList)) {

                return instanceList;
            }

            return List.of();
        }
        return List.of();
    }

    /**
     * 获取实例
     * @param instanceId 实例id
     * @return Optional<InstanceEntity>
     */
    public Optional<InstanceEntity> getInstance(long instanceId){
        InstanceEntity instance = instanceService.getById(instanceId);
        return Optional.of(instance);
    }

}
