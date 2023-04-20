package com.mega.hephaestus.pms.workflow.manager.plan;

import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.component.nuc.plate.PlateType;

import java.util.*;

/**
 * 实验任务操作
 */
public interface ExperimentStageTaskManager {

    /**
     * 获取一个实验的所有stages
     *
     * @param experimentId 实验ID
     * @return HephaestusStageTask list
     */
    List<HephaestusStageTask> getStageTasks(long experimentId);

    /**
     * 转换成MAP输出
     *
     * @param experimentId 实验ID
     * @return stageId为key的Map
     */
    Map<Long, List<HephaestusStageTask>> getStageTasksAsGroup(long experimentId);

    /**
     * 获取一个实验的所有stages
     *
     * @param experimentId 实验ID
     * @param stageName    实验阶段名称
     * @return HephaestusStageTask list
     */
    List<HephaestusStageTask> getStageTasks(long experimentId, String stageName);

    /**
     * 获取一个实验的所有stages
     *
     * @param experimentId 实验ID
     * @param stageId      实验阶段ID
     * @return HephaestusStageTask list
     */
    List<HephaestusStageTask> getStageTasks(long experimentId, Long stageId);

    /**
     * 获取开始step 到结束step 中所有任务
     *
     * @param historyId    实验组历史id
     * @param poolType 板类型
     * @param startStep    开始步骤
     * @param endStep      结束步骤
     * @return HephaestusStageTask list
     */
    List<HephaestusStageTask> getStageTasks(long historyId, PlateType poolType, StepType startStep, StepType endStep);


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
    List<HephaestusStageTask> getStageTasks(long historyId, PlateType poolType, StepType startStep, boolean startOpenInterval, StepType endStep, boolean endOpenInterval);


    /**
     * 获取开始step 到结束step 中所有任务
     *
     * @param experimentId    实验id
     * @param startStep    开始步骤
     * @param endStep      结束步骤
     * @return HephaestusStageTask list
     */
    List<HephaestusStageTask> getStageTasks(long experimentId, StepType startStep, StepType endStep);




    /**
     * 获取开始step 到结束step 中所有任务
     *
     * @param experimentId    实验id
     * @param startStep    开始步骤
     * @param startOpenInterval 是否开区间，默认false
     * @param endOpenInterval   是否开区间，默认false
     * @param endStep      结束步骤
     * @return HephaestusStageTask list
     */
    List<HephaestusStageTask> getStageTasks(long experimentId, StepType startStep, boolean startOpenInterval, StepType endStep, boolean endOpenInterval);

    /**
     *  根据实验id step步骤 in获取任务
     *  <p>
     *      where experimentId = experimentId
     *      and stepKey in (startStep,endStep)
     *  </p>
     * @param experimentId 实验id
     * @param startStep 开始步骤
     * @param endStep 结束步骤
     * @return List<HephaestusStageTask>
     */
    List<HephaestusStageTask> listByExperimentId(long experimentId, StepType startStep, StepType endStep);

    /**
     *
     *  1、根据task 找到集合中SortOrder 最大(maxSortOrder)和最小(minSortOrder)
     *
     *<p>   2、根据是否开闭区间重新构造条件
     *<p>    开区间，不包含自己;SortOrder > minSortOrder and SortOrder < maxSortOrder
     *<p>    闭区间，包含自己;SortOrder >= minSortOrder and SortOrder <= maxSortOrder
     *
     * <p>3、新的查询条件
     *     where experimentId = experimentId
     *     and 步骤2的条件
     *
     *
     * @param stageTasks task任务
     * @param experimentId 实验id
     * @param startOpenInterval 是否开区间，默认false
     * @param endOpenInterval   是否开区间，默认false
     * @return List<HephaestusStageTask>
     */
    List<HephaestusStageTask> getStageTasks(List<HephaestusStageTask> stageTasks,long experimentId,boolean startOpenInterval,boolean endOpenInterval);


    /**
     * 获取 指定实验、指定步骤、未关闭的任务
     *
     * @param experimentId    实验id
     * @param stepType    指定步骤
     * @return HephaestusStageTask list
     */
    List<HephaestusStageTask> getStageTasks(long experimentId, StepType stepType);

}
