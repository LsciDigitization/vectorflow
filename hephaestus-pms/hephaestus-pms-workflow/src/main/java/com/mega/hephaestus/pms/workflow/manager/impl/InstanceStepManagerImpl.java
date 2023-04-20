package com.mega.hephaestus.pms.workflow.manager.impl;

import com.mega.component.commons.date.DateUtil;
import com.mega.component.json.JsonFacade;
import com.mega.component.nuc.step.StepType;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.enums.ExperimentInstanceStatusEnum;
import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceService;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceStepService;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupHistoryManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceLabwareManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceStepManager;
import com.mega.hephaestus.pms.workflow.work.workstep.Step;
//import com.mega.hephaestus.pms.nuc.step.StepTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 13:27
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InstanceStepManagerImpl implements InstanceStepManager {

    private final IInstanceStepService stepService;

    private final IInstanceService instanceService;

    private final InstanceLabwareManager instanceLabwareManager;
    private final JsonFacade jsonFacade;

    private final ExperimentGroupHistoryManager experimentGroupHistoryManager;

    /**
     * 创建一个步骤
     *
     * @param instanceId 实例id
     * @return 是否成功
     */
    public boolean createInstance(long instanceId, PlateType poolType) {
        InstanceStepEntity instanceStep = new InstanceStepEntity();
        instanceStep.setInstanceId(instanceId);
        instanceStep.setCreateTime(new Date());
        instanceStep.setPoolType(poolType.getCode());
        instanceStep.setInstanceStatus(ExperimentInstanceStatusEnum.IDLE.getValue());
        instanceStep.setStepTotal(0L);
        // 插入板序号
        Optional<InstanceLabwareModel> instancePlate;
        instancePlate = instanceLabwareManager.getByInstanceId(instanceId);
        if (instancePlate.isPresent()) {
            log.info("实例id{},instanceStep插入板序号", instanceId);
            instanceStep.setPlateNo(instancePlate.get().getIterationNo());
            // 设置历史组id
            instanceStep.setProcessRecordId(instancePlate.get().getProcessRecordId());
        }

        return stepService.saveOrUpdate(instanceStep);
    }

    public boolean createInstance(long instanceId, String poolType) {
        InstanceStepEntity instanceStep = new InstanceStepEntity();
        instanceStep.setInstanceId(instanceId);
        instanceStep.setCreateTime(new Date());
        instanceStep.setPoolType(poolType);
        instanceStep.setInstanceStatus(ExperimentInstanceStatusEnum.IDLE.getValue());
        instanceStep.setStepTotal(0L);

        // 插入板序号
        Optional<InstanceLabwareModel> instancePlate = instanceLabwareManager.getByInstanceId(instanceId);
        if (instancePlate.isPresent()) {
            log.info("实例id{},instanceStep插入板序号", instanceId);
            instanceStep.setPlateNo(instancePlate.get().getIterationNo());
            // 设置历史组id
            instanceStep.setProcessRecordId(instancePlate.get().getProcessRecordId());
        }

        return stepService.saveOrUpdate(instanceStep);
    }

    /**
     * 开始一个步骤
     *
     * @param instanceId 实例id
     * @return 是否成功
     */
    public boolean startInstance(long instanceId) {
        InstanceStepEntity instanceStep = stepService.lambdaQuery()
                .eq(InstanceStepEntity::getInstanceId, instanceId).one();

        // 实例步骤不存在 则创建一个
        if (Objects.isNull(instanceStep)) {
            instanceStep = new InstanceStepEntity();
            instanceStep.setInstanceId(instanceId);
            instanceStep.setCreateTime(new Date());
        }

        instanceStep.setStartTime(new Date());

        if (Objects.isNull(instanceStep.getPoolType())) {
            Optional<InstanceLabwareModel> plateOptional = instanceLabwareManager.getByInstanceId(instanceId);
            if (plateOptional.isPresent()) {
                instanceStep.setPoolType(plateOptional.get().getLabwareType());
            }
        }

        // 板池存在 则插入 step
        instanceStep.setInstanceStatus(ExperimentInstanceStatusEnum.IDLE.getValue());
        instanceStep.setStepTotal(0L);

        // 插入板序号
        Optional<InstanceLabwareModel> instancePlate = instanceLabwareManager.getByInstanceId(instanceId);
        if (instancePlate.isPresent()) {
            log.info("实例id{},instanceStep插入板序号", instanceId);
            instanceStep.setPlateNo(instancePlate.get().getIterationNo());

            // 设置历史组id
            instanceStep.setProcessRecordId(instancePlate.get().getProcessRecordId());
        }

        return stepService.saveOrUpdate(instanceStep);
    }

    /**
     * Step 创建
     *
     * @param instanceId 实例id
     * @param step       step
     * @return 是否成功
     */

    public boolean createStep(long instanceId, Step step) {

        InstanceStepEntity instanceStep = stepService.lambdaQuery()
                .eq(InstanceStepEntity::getInstanceId, instanceId).one();

        // 实力步骤不存在 则创建失败
        if (Objects.nonNull(instanceStep)) {

            long sum = step.getType().getStepTotal();
            instanceStep.setStepTotal(sum);

            // 获取instance实例数据
            InstanceEntity instance = instanceService.getById(instanceId);
            if (Objects.nonNull(instance)) {
                if (Objects.nonNull(step.getType())) {
                    if (StringUtils.isNotBlank(step.getType().getCode())) {

                        // 填充数据toJsonString
                        InstanceEntity newInstanceEntity = new InstanceEntity();
                        newInstanceEntity.setId(instance.getId());
                        Optional<String> stepOptional = jsonFacade.toJsonString(step);
                        stepOptional.ifPresent(newInstanceEntity::setCurrentStep);
                        newInstanceEntity.setCurrentStepTotal(sum);

                        instanceService.updateById(newInstanceEntity);
                    }
                }


            }

            //
            makeCreateInstanceStep(instanceStep, step);

            return stepService.updateById(instanceStep);
        }
        return false;
    }

    /**
     * Step 开始
     *
     * @param instanceId 实例id
     * @param step       step
     * @return 是否成功
     */

    public boolean startStep(long instanceId, Step step) {

        InstanceStepEntity instanceStep = stepService.lambdaQuery()
                .eq(InstanceStepEntity::getInstanceId, instanceId).one();

        // 实力步骤不存在 则创建失败
        if (Objects.nonNull(instanceStep)) {

            instanceStep.setInstanceStatus(ExperimentInstanceStatusEnum.RUNNING.getValue());
            long sum = step.getType().getStepTotal();
            instanceStep.setStepTotal(sum);

            // 获取instance实例数据
            InstanceEntity instance = instanceService.getById(instanceId);


            if (Objects.nonNull(instance)) {
                if (Objects.nonNull(step.getType())) {
                    if (StringUtils.isNotBlank(step.getType().getCode())) {

                        // 填充数据toJsonString
                        InstanceEntity newInstanceEntity = new InstanceEntity();
                        newInstanceEntity.setId(instance.getId());
                        Optional<String> stepOptional = jsonFacade.toJsonString(step);
                        stepOptional.ifPresent(newInstanceEntity::setCurrentStep);
                        newInstanceEntity.setCurrentStepTotal(sum);

                        instanceService.updateById(newInstanceEntity);
                    }
                }


            }

            //
            makeStartInstanceStep(instanceStep, step);

            return stepService.updateById(instanceStep);
        }
        return false;
    }

    /**
     * Step 结束
     *
     * @param instanceId 实例id
     * @param step       step
     * @return 是否成功
     */
    public boolean endStep(long instanceId, Step step) {
        InstanceStepEntity instanceStep = stepService.lambdaQuery()
                .eq(InstanceStepEntity::getInstanceId, instanceId).one();
        if (Objects.nonNull(instanceStep)) {
            makeEndInstanceStep(instanceStep, step);
            // 计算从step1 到当前的总数
            long sum = step.getType().getStepTotal();

            instanceStep.setStepTotal(sum);
            return stepService.updateById(instanceStep);
        }

        return false;
    }

    /**
     * step完成
     *
     * @param instanceId 实例iD
     * @return 是否成功
     */
    public boolean finishedInstance(long instanceId) {
        InstanceStepEntity instanceStep = stepService.lambdaQuery()
                .eq(InstanceStepEntity::getInstanceId, instanceId).one();
        if (Objects.nonNull(instanceStep)) {
            instanceStep.setFinishTime(new Date());
            instanceStep.setInstanceStatus(ExperimentInstanceStatusEnum.FINISHED.getValue());

            return stepService.updateById(instanceStep);
        }

        return false;
    }

    /**
     * 获取未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (instance_status IN (?,?)) Parameters: 0(Integer),
     * 5(Integer)
     * </p>
     *
     * @return List<InstanceStepEntity>
     */
    public List<InstanceStepEntity> getUnfinishedInstanceSteps() {
        // 状态
        List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                ExperimentInstanceStatusEnum.RUNNING.getValue());
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
//      List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//      List<InstanceStepEntity> list = allInstanceSteps.stream()
//          .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//          .filter(v -> status.contains(v.getInstanceStatus()))
//          .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .in(InstanceStepEntity::getInstanceStatus, status).list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

    /**
     * 根据板类型 获取未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (instance_status IN (?,?) AND pool_type = ?)
     * </p>
     *
     * @param poolType 板类型
     * @return List<InstanceStepEntity>
     */
    public List<InstanceStepEntity> getUnfinishedInstanceSteps(PlateType poolType) {
        // 状态
        List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                ExperimentInstanceStatusEnum.RUNNING.getValue());
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {

//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .filter(v -> poolTypeEnum.getValue().equals(v.getPoolType()))
//                    .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .eq(InstanceStepEntity::getPoolType, poolType.getCode())
                    .in(InstanceStepEntity::getInstanceStatus, status)
                    .list();

            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

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
    public List<InstanceStepEntity> getUnfinishedInstanceSteps(PlateType poolType,
                                                               StepType stepType) {

        List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                ExperimentInstanceStatusEnum.RUNNING.getValue());

        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .filter(v -> poolTypeEnum.getValue().equals(v.getPoolType()))
//                    .filter(v -> v.getStepTotal() <= stepType.getStepTotal())
//                    .collect(Collectors.toList());

            // 查询 总合小于等于step1-stepType
            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .eq(InstanceStepEntity::getPoolType, poolType.getCode())
                    .le(InstanceStepEntity::getStepTotal, stepType.getStepTotal())
                    .in(InstanceStepEntity::getInstanceStatus, status)
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

    /**
     * 根据板类型 查询  未完成的实例步骤
     *
     * @param poolType 板类型
     * @param stepType     stepType
     * @param isInclusive  是否包含等于
     * @return List<InstanceStepEntity>
     */
    // todo 调整计算总和方法
    public List<InstanceStepEntity> getUnfinishedInstanceSteps(PlateType poolType,
                                                               StepType stepType, boolean isInclusive) {
        if (isInclusive) {
            return getUnfinishedInstanceSteps(poolType, stepType);
        }

        List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                ExperimentInstanceStatusEnum.RUNNING.getValue());
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            // 查询 总合小于等于step1-stepType
            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .eq(InstanceStepEntity::getPoolType, poolType.getCode())
                    .lt(InstanceStepEntity::getStepTotal, stepType.getStepTotal())
                    .in(InstanceStepEntity::getInstanceStatus, status)
                    .list();
//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .filter(v -> poolTypeEnum.getValue().equals(v.getPoolType()))
//                    .filter(v -> v.getStepTotal() < stepType.getStepTotal())
//                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

    /**
     * 根据板类型 查询 = step 未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (step_total = ? AND instance_status IN (?,?))
     * <p/>
     *
     * @param stepType stepType
     * @return List<InstanceStepEntity>
     */
    public List<InstanceStepEntity> getUnfinishedInstanceStepsEquals(StepType stepType) {

        List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                ExperimentInstanceStatusEnum.RUNNING.getValue());
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            // 查询 总合小于等于step1-stepType

//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .filter(v -> v.getStepTotal().equals(stepType.getStepTotal()))
//                    .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .eq(InstanceStepEntity::getStepTotal, stepType.getStepTotal())
                    .in(InstanceStepEntity::getInstanceStatus, status)
                    .list();

            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

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
    public List<InstanceStepEntity> getUnfinishedInstanceStepsEquals(PlateType poolType,
                                                                     StepType stepType) {

        List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                ExperimentInstanceStatusEnum.RUNNING.getValue());
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            // 查询 总合小于等于step1-stepType
//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> poolTypeEnum.getValue().equals(v.getPoolType()))
//                    .filter(v -> v.getStepTotal().equals(stepType.getStepTotal()))
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .eq(InstanceStepEntity::getPoolType, poolType.getCode())
                    .eq(InstanceStepEntity::getStepTotal, stepType.getStepTotal())
                    .in(InstanceStepEntity::getInstanceStatus, status)
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

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
    public List<InstanceStepEntity> getUnfinishedInstanceStepsRange(StepType startType,
                                                                    StepType endType) {
        List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                ExperimentInstanceStatusEnum.RUNNING.getValue());
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            // 查询 总合 >开始 并且 小于 endType

//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> v.getStepTotal() > startType.getStepTotal())
//                    .filter(v -> v.getStepTotal() < endType.getStepTotal())
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .gt(InstanceStepEntity::getStepTotal, startType.getStepTotal())
                    .lt(InstanceStepEntity::getStepTotal, endType.getStepTotal())
                    .in(InstanceStepEntity::getInstanceStatus, status)
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

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
    public List<InstanceStepEntity> getUnfinishedInstanceStepsRange(PlateType poolType,
                                                                    StepType startType, StepType endType) {
        List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                ExperimentInstanceStatusEnum.RUNNING.getValue());
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            // 查询 总合 >开始 并且 小于 endType
//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> poolTypeEnum.getValue().equals(v.getPoolType()))
//                    .filter(v -> v.getStepTotal() > startType.getStepTotal())
//                    .filter(v -> v.getStepTotal() < endType.getStepTotal())
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .eq(InstanceStepEntity::getPoolType, poolType.getCode())
                    .gt(InstanceStepEntity::getStepTotal, startType.getStepTotal())
                    .lt(InstanceStepEntity::getStepTotal, endType.getStepTotal())
                    .in(InstanceStepEntity::getInstanceStatus, status)
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

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
    public List<InstanceStepEntity> getUnfinishedInstanceStepsRangeClosed(StepType startType,
                                                                          StepType endType) {
        List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                ExperimentInstanceStatusEnum.RUNNING.getValue());

        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            // 查询 总合 >=开始 并且 <= endType
//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> v.getStepTotal() >= startType.getStepTotal())
//                    .filter(v -> v.getStepTotal() <= endType.getStepTotal())
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .ge(InstanceStepEntity::getStepTotal, startType.getStepTotal())
                    .le(InstanceStepEntity::getStepTotal, endType.getStepTotal())
                    .in(InstanceStepEntity::getInstanceStatus, status)
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

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
    public List<InstanceStepEntity> getUnfinishedInstanceStepsRangeClosed(
            PlateType poolType, StepType startType, StepType endType) {
        List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                ExperimentInstanceStatusEnum.RUNNING.getValue());

        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            // 查询 总合 >=开始 并且 <= endType
//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> poolTypeEnum.getValue().equals(v.getPoolType()))
//                    .filter(v -> v.getStepTotal() >= startType.getStepTotal())
//                    .filter(v -> v.getStepTotal() <= endType.getStepTotal())
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .eq(InstanceStepEntity::getPoolType, poolType.getCode())
                    .ge(InstanceStepEntity::getStepTotal, startType.getStepTotal())
                    .le(InstanceStepEntity::getStepTotal, endType.getStepTotal())
                    .in(InstanceStepEntity::getInstanceStatus, status)
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

    /**
     * 根据板类型 查询 >= step 未完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step  WHERE (step_total >= ? AND instance_status IN (?,?))
     * <p/>
     *
     * @param stepType stepType
     * @return List<InstanceStepEntity>
     */
    public List<InstanceStepEntity> getUnfinishedInstanceStepsGreaterEquals(StepType stepType) {
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                    ExperimentInstanceStatusEnum.RUNNING.getValue());

//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> v.getStepTotal() >= stepType.getStepTotal())
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .collect(Collectors.toList());

            // 查询 大于等于stepType
            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .ge(InstanceStepEntity::getStepTotal, stepType.getStepTotal())
                    .in(InstanceStepEntity::getInstanceStatus, status)
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

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
    public List<InstanceStepEntity> getUnfinishedInstanceStepsGreaterEquals(
            PlateType poolType, StepType stepType) {
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            List<Integer> status = Arrays.asList(ExperimentInstanceStatusEnum.IDLE.getValue(),
                    ExperimentInstanceStatusEnum.RUNNING.getValue());

            // 查询 大于等于stepType
//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> poolTypeEnum.getValue().equals(v.getPoolType()))
//                    .filter(v -> v.getStepTotal() >= stepType.getStepTotal())
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .eq(InstanceStepEntity::getPoolType, poolType.getCode())
                    .ge(InstanceStepEntity::getStepTotal, stepType.getStepTotal())
                    .in(InstanceStepEntity::getInstanceStatus, status)
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }


    /**
     * 获取已完成的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (instance_status IN (?,?)) Parameters: 0(Integer),
     * 5(Integer)
     * </p>
     *
     * @return List<InstanceStepEntity>
     */
    public List<InstanceStepEntity> getFinishedInstanceSteps() {

        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            // 状态
            List<Integer> status = List.of(ExperimentInstanceStatusEnum.FINISHED.getValue());
//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> status.contains(v.getInstanceStatus()))
//                    .collect(Collectors.toList());
//
            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .in(InstanceStepEntity::getInstanceStatus, status).list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }



    /**
     * 根据板类型 查询 = step 所有的实例步骤
     * <p>
     * SELECT * FROM hephaestus_instance_step WHERE (step_total = ?)
     * <p/>
     *
     * @param stepType stepType
     * @return List<InstanceStepEntity>
     */
    public List<InstanceStepEntity> getAllInstanceStepsEquals(StepType stepType) {

        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            // 查询 总合小于等于step1-stepType

//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> v.getStepTotal().equals(stepType.getStepTotal()))
//                    .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .eq(InstanceStepEntity::getStepTotal, stepType.getStepTotal())
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

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
    public List<InstanceStepEntity> getAllInstanceStepsRange(StepType startType,
                                                             StepType endType) {

        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            // 查询 总合 >开始 并且 小于 endType

//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> v.getStepTotal() > startType.getStepTotal())
//                    .filter(v -> v.getStepTotal() < endType.getStepTotal())
//                    .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .gt(InstanceStepEntity::getStepTotal, startType.getStepTotal())
                    .lt(InstanceStepEntity::getStepTotal, endType.getStepTotal())
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }

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
    public List<InstanceStepEntity> getAllInstanceStepsRangeClosed(StepType startType,
                                                                   StepType endType) {
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager
                .getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            // 查询 总合 >=开始 并且 <= endType
//            List<InstanceStepEntity> allInstanceSteps = getAllInstanceSteps();
//            List<InstanceStepEntity> list = allInstanceSteps.stream()
//                    .filter(v -> runningGroupOptional.get().getId().equals(v.getExperimentGroupHistoryId()))
//                    .filter(v -> v.getStepTotal() >= startType.getStepTotal())
//                    .filter(v -> v.getStepTotal() <= endType.getStepTotal())
//                    .collect(Collectors.toList());

            List<InstanceStepEntity> list = stepService.lambdaQuery()
                    .eq(InstanceStepEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .ge(InstanceStepEntity::getStepTotal, startType.getStepTotal())
                    .le(InstanceStepEntity::getStepTotal, endType.getStepTotal())
                    .list();
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            return List.of();
        }

        return List.of();
    }


    /**
     * 创建step 数据填充
     *
     * @param instanceStep instanceStep
     * @param step         step
     */
    public void makeCreateInstanceStep(InstanceStepEntity instanceStep, Step step) {
        Date createTime = step.getStartTime();
        if (StepTypeEnum.STEP1.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep1Key(StepTypeEnum.STEP1.getCode());
            instanceStep.setStep1Name(step.getName());
            instanceStep.setStep1CreateTime(createTime);
        }

        // 步骤2
        if (StepTypeEnum.STEP2.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep2Key(StepTypeEnum.STEP2.getCode());
            instanceStep.setStep2Name(step.getName());
            instanceStep.setStep2CreateTime(createTime);

        }

        // 步骤3
        if (StepTypeEnum.STEP3.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep3Key(StepTypeEnum.STEP3.getCode());
            instanceStep.setStep3Name(step.getName());
            instanceStep.setStep3CreateTime(createTime);

        }

        // 步骤4
        if (StepTypeEnum.STEP4.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep4Key(StepTypeEnum.STEP4.getCode());
            instanceStep.setStep4Name(step.getName());
            instanceStep.setStep4CreateTime(createTime);
        }

        // 步骤5
        if (StepTypeEnum.STEP5.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep5Key(StepTypeEnum.STEP5.getCode());
            instanceStep.setStep5Name(step.getName());
            instanceStep.setStep5CreateTime(createTime);

        }

        // 步骤6
        if (StepTypeEnum.STEP6.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep6Key(StepTypeEnum.STEP6.getCode());
            instanceStep.setStep6Name(step.getName());
            instanceStep.setStep6CreateTime(createTime);
        }

        // 步骤2
        if (StepTypeEnum.STEP7.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep7Key(StepTypeEnum.STEP7.getCode());
            instanceStep.setStep7Name(step.getName());
            instanceStep.setStep7CreateTime(createTime);
        }

        // 步骤8
        if (StepTypeEnum.STEP8.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep8Key(StepTypeEnum.STEP8.getCode());
            instanceStep.setStep8Name(step.getName());
            instanceStep.setStep8CreateTime(createTime);
        }

        // 步骤9
        if (StepTypeEnum.STEP9.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep9Key(StepTypeEnum.STEP9.getCode());
            instanceStep.setStep9Name(step.getName());
            instanceStep.setStep9CreateTime(createTime);

        }

        // 步骤10
        if (StepTypeEnum.STEP10.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep10Key(StepTypeEnum.STEP10.getCode());
            instanceStep.setStep10Name(step.getName());
            instanceStep.setStep10CreateTime(createTime);

        }

        // 步骤11
        if (StepTypeEnum.STEP11.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep11Key(StepTypeEnum.STEP11.getCode());
            instanceStep.setStep11Name(step.getName());
            instanceStep.setStep11CreateTime(createTime);

        }

        // 步骤12
        if (StepTypeEnum.STEP12.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep12Key(StepTypeEnum.STEP12.getCode());
            instanceStep.setStep12Name(step.getName());
            instanceStep.setStep12CreateTime(createTime);

        }

        // 步骤13
        if (StepTypeEnum.STEP13.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep13Key(StepTypeEnum.STEP13.getCode());
            instanceStep.setStep13Name(step.getName());
            instanceStep.setStep13CreateTime(createTime);

        }

        // 步骤 14
        if (StepTypeEnum.STEP14.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep14Key(StepTypeEnum.STEP14.getCode());
            instanceStep.setStep14Name(step.getName());
            instanceStep.setStep14CreateTime(createTime);

        }

        // 步骤15
        if (StepTypeEnum.STEP15.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep15Key(StepTypeEnum.STEP15.getCode());
            instanceStep.setStep15Name(step.getName());
            instanceStep.setStep15CreateTime(createTime);

        }

        // 步骤16
        if (StepTypeEnum.STEP16.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep16Key(StepTypeEnum.STEP16.getCode());
            instanceStep.setStep16Name(step.getName());
            instanceStep.setStep16CreateTime(createTime);

        }

        // 步骤17
        if (StepTypeEnum.STEP17.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep17Key(StepTypeEnum.STEP17.getCode());
            instanceStep.setStep17Name(step.getName());
            instanceStep.setStep17CreateTime(createTime);

        }

        // 步骤18
        if (StepTypeEnum.STEP18.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep18Key(StepTypeEnum.STEP18.getCode());
            instanceStep.setStep18Name(step.getName());
            instanceStep.setStep18CreateTime(createTime);

        }

        // 步骤19
        if (StepTypeEnum.STEP19.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep19Key(StepTypeEnum.STEP19.getCode());
            instanceStep.setStep19Name(step.getName());
            instanceStep.setStep19CreateTime(createTime);

        }

        // 步骤20
        if (StepTypeEnum.STEP20.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep20Key(StepTypeEnum.STEP20.getCode());
            instanceStep.setStep20Name(step.getName());
            instanceStep.setStep20CreateTime(createTime);

        }

        // 步骤21
        if (StepTypeEnum.STEP21.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep21Key(StepTypeEnum.STEP21.getCode());
            instanceStep.setStep21Name(step.getName());
            instanceStep.setStep21CreateTime(createTime);

        }

        // 步骤22
        if (StepTypeEnum.STEP22.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep22Key(StepTypeEnum.STEP22.getCode());
            instanceStep.setStep22Name(step.getName());
            instanceStep.setStep22CreateTime(createTime);

        }

        // 步骤23
        if (StepTypeEnum.STEP23.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep23Key(StepTypeEnum.STEP23.getCode());
            instanceStep.setStep23Name(step.getName());
            instanceStep.setStep23CreateTime(createTime);

        }

        // 步骤24
        if (StepTypeEnum.STEP24.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep24Key(StepTypeEnum.STEP24.getCode());
            instanceStep.setStep24Name(step.getName());
            instanceStep.setStep24CreateTime(createTime);

        }

        // 步骤25
        if (StepTypeEnum.STEP25.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep25Key(StepTypeEnum.STEP25.getCode());
            instanceStep.setStep25Name(step.getName());
            instanceStep.setStep25CreateTime(createTime);

        }

        // 步骤26
        if (StepTypeEnum.STEP26.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep26Key(StepTypeEnum.STEP26.getCode());
            instanceStep.setStep26Name(step.getName());
            instanceStep.setStep26CreateTime(createTime);

        }

        // 步骤27
        if (StepTypeEnum.STEP27.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep27Key(StepTypeEnum.STEP27.getCode());
            instanceStep.setStep27Name(step.getName());
            instanceStep.setStep27CreateTime(createTime);

        }

        // 步骤28
        if (StepTypeEnum.STEP28.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep28Key(StepTypeEnum.STEP28.getCode());
            instanceStep.setStep28Name(step.getName());
            instanceStep.setStep28CreateTime(createTime);

        }

        // 步骤29
        if (StepTypeEnum.STEP29.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep29Key(StepTypeEnum.STEP29.getCode());
            instanceStep.setStep29Name(step.getName());
            instanceStep.setStep29CreateTime(createTime);

        }

        // 步骤30
        if (StepTypeEnum.STEP30.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep30Key(StepTypeEnum.STEP30.getCode());
            instanceStep.setStep30Name(step.getName());
            instanceStep.setStep30CreateTime(createTime);

        }

        // 步骤31
        if (StepTypeEnum.STEP31.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep31Key(StepTypeEnum.STEP31.getCode());
            instanceStep.setStep31Name(step.getName());
            instanceStep.setStep31CreateTime(createTime);
        }

    }

    /**
     * 开始step 数据填充
     *
     * @param instanceStep instanceStep
     * @param step         step
     */
    public void makeStartInstanceStep(InstanceStepEntity instanceStep, Step step) {
        // 步骤真正的开始时间
        Date currentDate = step.getStartTime();
        if (StepTypeEnum.STEP1.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep1Key(StepTypeEnum.STEP1.getCode());
            instanceStep.setStep1Name(step.getName());
            instanceStep.setStep1StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep1CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep1WaitingSecond(waitingSecond);
            }
        }

        // 步骤2
        if (StepTypeEnum.STEP2.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep2Key(StepTypeEnum.STEP2.getCode());
            instanceStep.setStep2Name(step.getName());
            instanceStep.setStep2StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep2CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep2WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep1EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep1IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤3
        if (StepTypeEnum.STEP3.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep3Key(StepTypeEnum.STEP3.getCode());
            instanceStep.setStep3Name(step.getName());
            instanceStep.setStep3StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep3CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep3WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep2EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep2IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤4
        if (StepTypeEnum.STEP4.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep4Key(StepTypeEnum.STEP4.getCode());
            instanceStep.setStep4Name(step.getName());
            instanceStep.setStep4StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep4CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep4WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep3EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep3IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤5
        if (StepTypeEnum.STEP5.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep5Key(StepTypeEnum.STEP5.getCode());
            instanceStep.setStep5Name(step.getName());
            instanceStep.setStep5StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep5CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep5WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep4EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep4IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤6
        if (StepTypeEnum.STEP6.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep6Key(StepTypeEnum.STEP6.getCode());
            instanceStep.setStep6Name(step.getName());
            instanceStep.setStep6StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep6CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep6WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep5EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep5IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤2
        if (StepTypeEnum.STEP7.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep7Key(StepTypeEnum.STEP7.getCode());
            instanceStep.setStep7Name(step.getName());
            instanceStep.setStep7StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep7CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep7WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep6EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep6IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤8
        if (StepTypeEnum.STEP8.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep8Key(StepTypeEnum.STEP8.getCode());
            instanceStep.setStep8Name(step.getName());
            instanceStep.setStep8StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep8CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep8WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep7EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep7IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤9
        if (StepTypeEnum.STEP9.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep9Key(StepTypeEnum.STEP9.getCode());
            instanceStep.setStep9Name(step.getName());
            instanceStep.setStep9StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep9CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep9WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep8EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep8IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤10
        if (StepTypeEnum.STEP10.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep10Key(StepTypeEnum.STEP10.getCode());
            instanceStep.setStep10Name(step.getName());
            instanceStep.setStep10StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep10CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep10WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep9EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep9IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤11
        if (StepTypeEnum.STEP11.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep11Key(StepTypeEnum.STEP11.getCode());
            instanceStep.setStep11Name(step.getName());
            instanceStep.setStep11StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep11CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep11WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep10EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep10IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤12
        if (StepTypeEnum.STEP12.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep12Key(StepTypeEnum.STEP12.getCode());
            instanceStep.setStep12Name(step.getName());
            instanceStep.setStep12StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep12CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep12WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep11EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep11IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤13
        if (StepTypeEnum.STEP13.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep13Key(StepTypeEnum.STEP13.getCode());
            instanceStep.setStep13Name(step.getName());
            instanceStep.setStep13StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep13CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep13WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep12EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep12IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤 14
        if (StepTypeEnum.STEP14.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep14Key(StepTypeEnum.STEP14.getCode());
            instanceStep.setStep14Name(step.getName());
            instanceStep.setStep14StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep14CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep14WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep13EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep13IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤15
        if (StepTypeEnum.STEP15.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep15Key(StepTypeEnum.STEP15.getCode());
            instanceStep.setStep15Name(step.getName());
            instanceStep.setStep15StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep15CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep15WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep14EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep14IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤16
        if (StepTypeEnum.STEP16.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep16Key(StepTypeEnum.STEP16.getCode());
            instanceStep.setStep16Name(step.getName());
            instanceStep.setStep16StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep16CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep16WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep15EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep15IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤17
        if (StepTypeEnum.STEP17.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep17Key(StepTypeEnum.STEP17.getCode());
            instanceStep.setStep17Name(step.getName());
            instanceStep.setStep17StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep17CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep17WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep16EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep16IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤18
        if (StepTypeEnum.STEP18.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep18Key(StepTypeEnum.STEP18.getCode());
            instanceStep.setStep18Name(step.getName());
            instanceStep.setStep18StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep18CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep18WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep17EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep17IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤19
        if (StepTypeEnum.STEP19.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep19Key(StepTypeEnum.STEP19.getCode());
            instanceStep.setStep19Name(step.getName());
            instanceStep.setStep19StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep19CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep19WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep18EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep18IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤20
        if (StepTypeEnum.STEP20.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep20Key(StepTypeEnum.STEP20.getCode());
            instanceStep.setStep20Name(step.getName());
            instanceStep.setStep20StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep20CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep20WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep19EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep19IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤21
        if (StepTypeEnum.STEP21.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep21Key(StepTypeEnum.STEP21.getCode());
            instanceStep.setStep21Name(step.getName());
            instanceStep.setStep21StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep21CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep21WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep20EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep20IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤22
        if (StepTypeEnum.STEP22.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep22Key(StepTypeEnum.STEP22.getCode());
            instanceStep.setStep22Name(step.getName());
            instanceStep.setStep22StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep22CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep22WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep21EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep21IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤23
        if (StepTypeEnum.STEP23.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep23Key(StepTypeEnum.STEP23.getCode());
            instanceStep.setStep23Name(step.getName());
            instanceStep.setStep23StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep23CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep23WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep22EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep22IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤24
        if (StepTypeEnum.STEP24.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep24Key(StepTypeEnum.STEP24.getCode());
            instanceStep.setStep24Name(step.getName());
            instanceStep.setStep24StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep24CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep24WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep23EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep23IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤25
        if (StepTypeEnum.STEP25.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep25Key(StepTypeEnum.STEP25.getCode());
            instanceStep.setStep25Name(step.getName());
            instanceStep.setStep25StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep25CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep25WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep24EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep24IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤26
        if (StepTypeEnum.STEP26.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep26Key(StepTypeEnum.STEP26.getCode());
            instanceStep.setStep26Name(step.getName());
            instanceStep.setStep26StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep26CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep26WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep25EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep25IntervalSecond(intervalTime);
                }

            }
        }

        // 步骤27
        if (StepTypeEnum.STEP27.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep27Key(StepTypeEnum.STEP27.getCode());
            instanceStep.setStep27Name(step.getName());
            instanceStep.setStep27StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep27CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep27WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep26EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep26IntervalSecond(intervalTime);
                }

            }
        }

        // 步骤28
        if (StepTypeEnum.STEP28.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep28Key(StepTypeEnum.STEP28.getCode());
            instanceStep.setStep28Name(step.getName());
            instanceStep.setStep28StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep28CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep28WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep27EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep27IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤29
        if (StepTypeEnum.STEP29.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep29Key(StepTypeEnum.STEP29.getCode());
            instanceStep.setStep29Name(step.getName());
            instanceStep.setStep29StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep29CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep29WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep28EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep28IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤30
        if (StepTypeEnum.STEP30.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep30Key(StepTypeEnum.STEP30.getCode());
            instanceStep.setStep30Name(step.getName());
            instanceStep.setStep30StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep30CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep30WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep29EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep29IntervalSecond(intervalTime);
                }
            }
        }

        // 步骤31
        if (StepTypeEnum.STEP31.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep31Key(StepTypeEnum.STEP31.getCode());
            instanceStep.setStep31Name(step.getName());
            instanceStep.setStep31StartTime(currentDate);

            // 当前步骤 开始时间 - 创建时间 = 等待时间
            Date createTime = instanceStep.getStep31CreateTime();
            if (Objects.nonNull(createTime)) {
                long waitingSecond = DateUtil.getIntervalTime(createTime, currentDate) / 1000;
                instanceStep.setStep31WaitingSecond(waitingSecond);

                // 上一个任务结束时间存在 则设置间隔时间为 当前步骤开始时间减去上一个步骤结束时间
                Date lastEndTime = instanceStep.getStep30EndTime();
                if (Objects.nonNull(lastEndTime)) {
                    long intervalTime = DateUtil.getIntervalTime(lastEndTime, createTime) / 1000;
                    instanceStep.setStep30IntervalSecond(intervalTime);
                }
            }
        }

    }


    /**
     * endstep 数据填充
     *
     * @param instanceStep instanceStep
     * @param step         step
     */
    public void makeEndInstanceStep(InstanceStepEntity instanceStep, Step step) {
        Date currentDate = step.getEndTime();
        if (StepTypeEnum.STEP1.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep1EndTime(currentDate);
            Date startTime = instanceStep.getStep1CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep1DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP2.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep2EndTime(currentDate);
            Date startTime = instanceStep.getStep2CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep2DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP3.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep3EndTime(currentDate);
            Date startTime = instanceStep.getStep3CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep3DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP4.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep4EndTime(currentDate);
            Date startTime = instanceStep.getStep4CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep4DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP5.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep5EndTime(currentDate);
            Date startTime = instanceStep.getStep5CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep5DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP6.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep6EndTime(currentDate);
            Date startTime = instanceStep.getStep6CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep6DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP7.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep7EndTime(currentDate);
            Date startTime = instanceStep.getStep7CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep7DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP8.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep8EndTime(currentDate);
            Date startTime = instanceStep.getStep8CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep8DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP9.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep9EndTime(currentDate);
            Date startTime = instanceStep.getStep9CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep9DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP10.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep10EndTime(currentDate);
            Date startTime = instanceStep.getStep10CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep10DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP11.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep11EndTime(currentDate);
            Date startTime = instanceStep.getStep11CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep11DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP12.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep12EndTime(currentDate);
            Date startTime = instanceStep.getStep12CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep12DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP13.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep13EndTime(currentDate);
            Date startTime = instanceStep.getStep13CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep13DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP14.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep14EndTime(currentDate);
            Date startTime = instanceStep.getStep14CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep14DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP15.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep15EndTime(currentDate);
            Date startTime = instanceStep.getStep15CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep15DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP16.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep16EndTime(currentDate);
            Date startTime = instanceStep.getStep16CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep16DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP17.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep17EndTime(currentDate);
            Date startTime = instanceStep.getStep17CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep17DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP18.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep18EndTime(currentDate);
            Date startTime = instanceStep.getStep18CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep18DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP19.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep19EndTime(currentDate);
            Date startTime = instanceStep.getStep19CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep19DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP20.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep20EndTime(currentDate);
            Date startTime = instanceStep.getStep20CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep20DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP21.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep21EndTime(currentDate);
            Date startTime = instanceStep.getStep21CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep21DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP22.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep22EndTime(currentDate);
            Date startTime = instanceStep.getStep22CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep22DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP23.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep23EndTime(currentDate);
            Date startTime = instanceStep.getStep23CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep23DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP24.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep24EndTime(currentDate);
            Date startTime = instanceStep.getStep24CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep24DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP25.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep25EndTime(currentDate);
            Date startTime = instanceStep.getStep25CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep25DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP26.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep26EndTime(currentDate);
            Date startTime = instanceStep.getStep26CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep26DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP27.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep27EndTime(currentDate);
            Date startTime = instanceStep.getStep27CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep27DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP28.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep28EndTime(currentDate);
            Date startTime = instanceStep.getStep28CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep28DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP29.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep29EndTime(currentDate);
            Date startTime = instanceStep.getStep29CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep29DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP30.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep30EndTime(currentDate);
            Date startTime = instanceStep.getStep30CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep30DurationSecond(duration);
            }
        }

        if (StepTypeEnum.STEP31.getCode().equals(step.getType().getCode())) {
            instanceStep.setStep31EndTime(currentDate);
            Date startTime = instanceStep.getStep31CreateTime();
            if (Objects.nonNull(startTime)) {
                long duration = DateUtil.getIntervalTime(startTime, currentDate) / 1000;
                instanceStep.setStep31DurationSecond(duration);
            }
        }
    }

}
