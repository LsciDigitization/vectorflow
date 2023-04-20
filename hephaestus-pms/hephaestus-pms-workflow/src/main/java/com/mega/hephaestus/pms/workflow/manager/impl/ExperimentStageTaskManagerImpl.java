package com.mega.hephaestus.pms.workflow.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mega.component.mybatis.common.constant.BooleanEnum;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.model.service.IHephaestusStageTaskService;
import com.mega.component.nuc.plate.PlateType;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceLabwareManager;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentStageTaskManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 11:42
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ExperimentStageTaskManagerImpl implements ExperimentStageTaskManager {

    private final IHephaestusStageTaskService stageTaskService;

    //    private final ILabwarePlateService instanceLabwareService;
    private final InstanceLabwareManager instanceLabwareManager;

    /**
     * 获取一个实验的所有stages
     *
     * @param experimentId 实验ID
     * @return HephaestusStageTask list
     */
    public List<HephaestusStageTask> getStageTasks(long experimentId) {
        return stageTaskService.listByExperimentId(experimentId);
    }

    /**
     * 转换成MAP输出
     *
     * @param experimentId 实验ID
     * @return stageId为key的Map
     */
    public Map<Long, List<HephaestusStageTask>> getStageTasksAsGroup(long experimentId) {
        List<HephaestusStageTask> hephaestusStageTasks = stageTaskService.listByExperimentId(experimentId);
        return hephaestusStageTasks.stream().collect(
                Collectors.groupingBy(HephaestusStageTask::getStageId,
                        Collectors.mapping(Function.identity(), Collectors.toList())
                ));
    }

    /**
     * 获取一个实验的所有stages
     *
     * @param experimentId 实验ID
     * @param stageName    实验阶段名称
     * @return HephaestusStageTask list
     */
    public List<HephaestusStageTask> getStageTasks(long experimentId, String stageName) {
        return stageTaskService.listByExperimentStageName(experimentId, stageName);
    }

    /**
     * 获取一个实验的所有stages
     *
     * @param experimentId 实验ID
     * @param stageId      实验阶段ID
     * @return HephaestusStageTask list
     */
    public List<HephaestusStageTask> getStageTasks(long experimentId, Long stageId) {
        return stageTaskService.listByExperimentStageId(experimentId, stageId);
    }

    /**
     * 获取开始step 到结束step 中所有任务
     *
     * @param historyId    实验组历史id
     * @param poolTypeEnum 板类型
     * @param startStep    开始步骤
     * @param endStep      结束步骤
     * @return HephaestusStageTask list
     */
    public List<HephaestusStageTask> getStageTasks(long historyId, PlateType poolTypeEnum, StepType startStep, StepType endStep) {

        // 拿到板子 即拿到 实验id
        List<InstanceLabwareModel> instancePlateList = instanceLabwareManager.listByProcessRecordIdAndLabwareType(historyId, poolTypeEnum.getCode());

//                instanceLabwareService.listByProcessRecordIdAndLabwareType(historyId, poolTypeEnum.getValue());


        if (CollectionUtils.isNotEmpty(instancePlateList)) {
            //拿到实验id
            Long experimentId = instancePlateList.get(0).getExperimentId();

            // 根据stepKey 查询出stageTask
            List<HephaestusStageTask> stageTasks = listByExperimentId(experimentId, startStep, endStep);

            if (CollectionUtils.isNotEmpty(stageTasks)) {
                // 获取最大的和最小的序号
                OptionalInt maxOption = stageTasks.stream().mapToInt(HephaestusStageTask::getSortOrder).max();
                OptionalInt minOption = stageTasks.stream().mapToInt(HephaestusStageTask::getSortOrder).min();

                int maxStep = maxOption.orElse(9999);
                int minStep = minOption.orElse(0);
                log.info("开始:{},结束：{}", minStep, maxStep);
                // 区间查询任务清单
                List<HephaestusStageTask> result = stageTaskService
                        .lambdaQuery()
                        .eq(HephaestusStageTask::getIsClosed, BooleanEnum.NO.getCode()) // 只查询未关闭的
                        .eq(HephaestusStageTask::getIsDeleted, BooleanEnum.NO.getCode()) // 只查询未删除的
                        .eq(HephaestusStageTask::getExperimentId, experimentId)
                        .between(HephaestusStageTask::getSortOrder, minStep, maxStep).list();
                if (CollectionUtils.isNotEmpty(result)) {
                    return result;
                }
            }

            return List.of();
        }
        return List.of();
    }


    /**
     * 获取开始step 到结束step 中所有任务
     *
     * @param historyId         实验组历史id
     * @param poolType      板类型
     * @param startStep         开始步骤
     * @param startOpenInterval 是否开区间，默认false
     * @param endOpenInterval   是否开区间，默认false
     * @param endStep           结束步骤
     * @return HephaestusStageTask list
     */
    public List<HephaestusStageTask> getStageTasks(long historyId, PlateType poolType, StepType startStep, boolean startOpenInterval, StepType endStep, boolean endOpenInterval) {
        // 拿到板子 即拿到 实验id
        List<InstanceLabwareModel> instancePlateList = instanceLabwareManager.listByProcessRecordIdAndLabwareType(historyId, poolType.getCode());
//        .listByProcessRecordIdAndLabwareType(historyId, poolTypeEnum.getValue());

        if (CollectionUtils.isNotEmpty(instancePlateList)) {
            //拿到实验id
            Long experimentId = instancePlateList.get(0).getExperimentId();

            // 根据stepKey 查询出stageTask
            List<HephaestusStageTask> stageTasks = listByExperimentId(experimentId, startStep, endStep);

            if (CollectionUtils.isNotEmpty(stageTasks)) {
                return getStageTasks(stageTasks, experimentId, startOpenInterval, endOpenInterval);
            }

            return List.of();
        }
        return List.of();
    }


    /**
     * 获取开始step 到结束step 中所有任务
     *
     * @param experimentId 实验id
     * @param startStep    开始步骤
     * @param endStep      结束步骤
     * @return HephaestusStageTask list
     */
    public List<HephaestusStageTask> getStageTasks(long experimentId, StepType startStep, StepType endStep) {

        return getStageTasks(experimentId, startStep, false, endStep, false);
    }


    /**
     * 获取开始step 到结束step 中所有任务
     *
     * @param experimentId      实验id
     * @param startStep         开始步骤
     * @param startOpenInterval 是否开区间，默认false
     * @param endOpenInterval   是否开区间，默认false
     * @param endStep           结束步骤
     * @return HephaestusStageTask list
     */
    public List<HephaestusStageTask> getStageTasks(long experimentId, StepType startStep, boolean startOpenInterval, StepType endStep, boolean endOpenInterval) {
        // 根据stepKey 查询出stageTask
        List<HephaestusStageTask> stageTasks = listByExperimentId(experimentId, startStep, endStep);
        return getStageTasks(stageTasks, experimentId, startOpenInterval, endOpenInterval);
    }

    /**
     * 根据实验id step步骤 in获取任务
     * <p>
     * where experimentId = experimentId
     * and stepKey in (startStep,endStep)
     * </p>
     *
     * @param experimentId 实验id
     * @param startStep    开始步骤
     * @param endStep      结束步骤
     * @return List<HephaestusStageTask>
     */
    public List<HephaestusStageTask> listByExperimentId(long experimentId, StepType startStep, StepType endStep) {
        List<String> stepKeys = Arrays.asList(startStep.getCode(), endStep.getCode());
        List<HephaestusStageTask> stageTasks = stageTaskService.lambdaQuery().eq(HephaestusStageTask::getExperimentId, experimentId)
                .eq(HephaestusStageTask::getIsClosed, BooleanEnum.NO.getCode())
                .eq(HephaestusStageTask::getIsDeleted, BooleanEnum.NO.getCode()) // 只查询未删除的
                .in(HephaestusStageTask::getStepKey, stepKeys)
                .list();
        if (CollectionUtils.isNotEmpty(stageTasks)) {
            return stageTasks;
        }
        return List.of();
    }

    /**
     * 1、根据task 找到集合中SortOrder 最大(maxSortOrder)和最小(minSortOrder)
     *
     * <p>   2、根据是否开闭区间重新构造条件
     * <p>    开区间，不包含自己;SortOrder > minSortOrder and SortOrder < maxSortOrder
     * <p>    闭区间，包含自己;SortOrder >= minSortOrder and SortOrder <= maxSortOrder
     *
     * <p>3、新的查询条件
     * where experimentId = experimentId
     * and 步骤2的条件
     *
     * @param stageTasks        task任务
     * @param experimentId      实验id
     * @param startOpenInterval 是否开区间，默认false
     * @param endOpenInterval   是否开区间，默认false
     * @return List<HephaestusStageTask>
     */
    public List<HephaestusStageTask> getStageTasks(List<HephaestusStageTask> stageTasks, long experimentId, boolean startOpenInterval, boolean endOpenInterval) {
        // 获取最大的和最小的序号
        OptionalInt maxOption = stageTasks.stream().mapToInt(HephaestusStageTask::getSortOrder).max();
        OptionalInt minOption = stageTasks.stream().mapToInt(HephaestusStageTask::getSortOrder).min();

        int maxStep = maxOption.orElse(9999);
        int minStep = minOption.orElse(0);


        // 如果 查询出来的是 102 -1102
        // 开区间，不包含自己 则直接从 201-1099
        // 闭区间，包含自己，则 101-1199

        LambdaQueryWrapper<HephaestusStageTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HephaestusStageTask::getExperimentId, experimentId);
        queryWrapper.eq(HephaestusStageTask::getIsDeleted, BooleanEnum.NO.getCode());// 只查询未删除的
        queryWrapper.eq(HephaestusStageTask::getIsClosed, BooleanEnum.NO.getCode());// 只查询未关闭的
        if (startOpenInterval) {
            queryWrapper.gt(HephaestusStageTask::getSortOrder, minStep);
        } else {
            queryWrapper.ge(HephaestusStageTask::getSortOrder, minStep);
        }

        if (endOpenInterval) {
            queryWrapper.lt(HephaestusStageTask::getSortOrder, maxStep);
        } else {
            queryWrapper.le(HephaestusStageTask::getSortOrder, maxStep);
        }
        log.debug("开始step:{},startOpenInterval:{},结束step：{},endOpenInterval:{}", minStep, startOpenInterval, maxStep, endOpenInterval);

        // 区间查询任务清单
        List<HephaestusStageTask> result = stageTaskService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(result)) {
            return result;
        }
        return List.of();
    }

    /**
     *  获取 指定实验、指定步骤、未关闭的任务
     *
     * @param experimentId 实验id
     * @param stepType     指定步骤
     * @return HephaestusStageTask list
     */
    @Override
    public List<HephaestusStageTask> getStageTasks(long experimentId, StepType stepType) {
        List<HephaestusStageTask> stageTasks = stageTaskService.lambdaQuery().eq(HephaestusStageTask::getExperimentId, experimentId)
                .eq(HephaestusStageTask::getIsClosed, BooleanEnum.NO.getCode())
                .eq(HephaestusStageTask::getIsDeleted, BooleanEnum.NO.getCode()) // 只查询未删除的
                .eq(HephaestusStageTask::getStepKey, stepType.getCode())
                .list();
        if (CollectionUtils.isNotEmpty(stageTasks)) {
            return stageTasks;
        }
        return List.of();
    }

}
