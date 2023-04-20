package com.mega.hephaestus.pms.workflow.manager.dynamic;

import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.work.workstep.Step;


import java.util.*;


/**
 * 实验实例关键步骤操作
 */
public interface InstanceStepManager {

    /**
     * 创建一个步骤
     *
     * @param instanceId 实例id
     * @return 是否成功
     */
    boolean createInstance(long instanceId, PlateType poolType);

    boolean createInstance(long instanceId, String poolType);

    /**
     * 开始一个步骤
     *
     * @param instanceId 实例id
     * @return 是否成功
     */
    boolean startInstance(long instanceId);

    /**
     * Step 创建
     *
     * @param instanceId 实例id
     * @param step       step
     * @return 是否成功
     */

    boolean createStep(long instanceId, Step step);

    /**
     * Step 开始
     *
     * @param instanceId 实例id
     * @param step       step
     * @return 是否成功
     */

    boolean startStep(long instanceId, Step step);

    /**
     * Step 结束
     *
     * @param instanceId 实例id
     * @param step       step
     * @return 是否成功
     */
    boolean endStep(long instanceId, Step step);

    /**
     * step完成
     *
     * @param instanceId 实例iD
     * @return 是否成功
     */
    boolean finishedInstance(long instanceId);

    /**
     * 获取未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (instance_status IN (?,?)) Parameters: 0(Integer),
     * 5(Integer)
     * </p>
     *
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getUnfinishedInstanceSteps();

    /**
     * 根据板类型 获取未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (instance_status IN (?,?) AND pool_type = ?)
     * </p>
     *
     * @param poolType 板类型
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getUnfinishedInstanceSteps(PlateType poolType);

    /**
     * 根据板类型 查询<=  未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (pool_type = ? AND step_total <= ? AND
     * instance_status IN (?,?))
     * </p>
     *
     * @param poolType 板类型
     * @param stepType     stepType
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getUnfinishedInstanceSteps(PlateType poolType, StepType stepType);

    /**
     * 根据板类型 查询  未完成的实例步骤
     *
     * @param poolType 板类型
     * @param stepType     stepType
     * @param isInclusive  是否包含等于
     * @return List<InstanceStepEntity>
     */
    // todo 调整计算总和方法
    List<InstanceStepEntity> getUnfinishedInstanceSteps(PlateType poolType, StepType stepType, boolean isInclusive);

    /**
     * 根据板类型 查询 = step 未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (step_total = ? AND instance_status IN (?,?))
     * <p/>
     *
     * @param stepType stepType
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getUnfinishedInstanceStepsEquals(StepType stepType);

    /**
     * 根据板类型 查询 = step 未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (pool_type = ? AND step_total = ? AND
     * instance_status IN (?,?))
     * <p/>
     *
     * @param stepType stepType
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getUnfinishedInstanceStepsEquals(PlateType poolType, StepType stepType);

    /**
     * 根据板类型,StepTotal在(startType,endType)区间（不包含首尾），未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (step_total > ? AND step_total < ? AND
     * instance_status IN (?,?))
     * </p>
     *
     * @param startType 开始stepType
     * @param endType   结束endType
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getUnfinishedInstanceStepsRange(StepType startType, StepType endType);

    /**
     * 根据板类型,StepTotal在(startType,endType)区间（不包含首尾），未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (pool_type = ? AND step_total > ? AND step_total <
     * ? AND instance_status IN (?,?))
     * </p>
     *
     * @param poolType 板类型
     * @param startType    开始stepType
     * @param endType      结束endType
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getUnfinishedInstanceStepsRange(PlateType poolType, StepType startType, StepType endType);

    /**
     * 根据板类型,StepTotal在(startType,endType)区间（包含首尾），未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step  WHERE (step_total >= ? AND step_total <= ? AND
     * instance_status IN (?,?))
     * <p/>
     *
     * @param startType 开始stepType
     * @param endType   结束endType
     * @return List<InstanceStepEntity>
     */
    // todo 调整计算总和方法
    List<InstanceStepEntity> getUnfinishedInstanceStepsRangeClosed(StepType startType, StepType endType);

    /**
     * 根据板类型,StepTotal在(startType,endType)区间（包含首尾），未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step  WHERE (pool_type = ? AND step_total >= ? AND step_total
     * <= ? AND instance_status IN (?,?))
     * <p/>
     *
     * @param poolType 板类型
     * @param startType    开始stepType
     * @param endType      结束endType
     * @return List<InstanceStepEntity>
     */
    // todo 调整计算总和方法
    List<InstanceStepEntity> getUnfinishedInstanceStepsRangeClosed(PlateType poolType, StepType startType, StepType endType);

    /**
     * 根据板类型 查询 >= step 未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step  WHERE (step_total >= ? AND instance_status IN (?,?))
     * <p/>
     *
     * @param stepType stepType
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getUnfinishedInstanceStepsGreaterEquals(StepType stepType);

    /**
     * 根据板类型 查询 >= step 未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step  WHERE (pool_type = ? AND step_total >= ? AND
     * instance_status IN (?,?))
     * <p/>
     *
     * @param poolType 板类型
     * @param stepType     stepType
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getUnfinishedInstanceStepsGreaterEquals(PlateType poolType, StepType stepType);


    /**
     * 获取已完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (instance_status IN (?,?)) Parameters: 0(Integer),
     * 5(Integer)
     * </p>
     *
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getFinishedInstanceSteps();


    /**
     * 根据板类型 查询 = step 所有的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (step_total = ?)
     * <p/>
     *
     * @param stepType stepType
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getAllInstanceStepsEquals(StepType stepType);

    /**
     * 根据板类型,StepTotal在(startType,endType)区间（不包含首尾），所有的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (step_total > ? AND step_total < ?)
     * </p>
     *
     * @param startType 开始stepType
     * @param endType   结束endType
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getAllInstanceStepsRange(StepType startType, StepType endType);

    /**
     * 根据板类型,StepTotal在(startType,endType)区间（包含首尾），未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step  WHERE (step_total >= ? AND step_total <= ?)
     * <p/>
     *
     * @param startType 开始stepType
     * @param endType   结束endType
     * @return List<InstanceStepEntity>
     */
    List<InstanceStepEntity> getAllInstanceStepsRangeClosed(StepType startType, StepType endType);


    /**
     * 创建step 数据填充
     *
     * @param instanceStep instanceStep
     * @param step         step
     */
    void makeCreateInstanceStep(InstanceStepEntity instanceStep, Step step);

    /**
     * 开始step 数据填充
     *
     * @param instanceStep instanceStep
     * @param step         step
     */
    void makeStartInstanceStep(InstanceStepEntity instanceStep, Step step);


    /**
     * endstep 数据填充
     *
     * @param instanceStep instanceStep
     * @param step         step
     */
    void makeEndInstanceStep(InstanceStepEntity instanceStep, Step step);
}
