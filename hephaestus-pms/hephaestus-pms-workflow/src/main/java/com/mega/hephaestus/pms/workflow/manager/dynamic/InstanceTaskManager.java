package com.mega.hephaestus.pms.workflow.manager.dynamic;

import com.mega.component.bioflow.task.StageTaskEntity;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.model.entity.*;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.component.nuc.device.DeviceType;

import java.util.*;

/**
 * 设备task
 */
public interface InstanceTaskManager {

    /**
     * 创建task
     *
     * @param deviceType 设备DeviceType枚举类
     * @param stageTask  task任务
     * @return 创建后的deviceTask对象
     */
    InstanceTaskEntity createTask(DeviceType deviceType, HephaestusStageTask stageTask, long instanceId);

    /**
     * 创建task
     *
     * @param deviceType 设备DeviceType枚举类
     * @param stageTask  task任务
     * @return 创建后的deviceTask对象
     */
    InstanceTaskEntity createTask(DeviceType deviceType, StageTaskEntity stageTask, long instanceId);


    /**
     * 根据主键 修改当前task 为运行
     *
     * @param id        主键id
     * @param requestId 请求id
     * @return 修改后的对象
     */
    InstanceTaskEntity runningTask(long id, String requestId, String deviceKey);


    /**
     * 检查task 状态
     *
     * @param id 实例id
     * @return true 完成，false未完成，异常:失败或者找不到
     */
    boolean checkTaskFinished(long id);

    /**
     * 修改task 完成
     *
     * @param requestId hephaestusInstanceTask
     */
    boolean updateTaskFinished(String requestId);

    /**
     * 修改task 失败
     *
     * @param requestId    hephaestusInstanceTask
     * @param errorMessage 错误信息
     */
    boolean updateTaskFailed(String requestId, String errorMessage);


    /**
     * 获取等待中的任务
     *
     * @return 返回等待中的任务
     */
    List<InstanceTaskEntity> awaitTasks();

    /**
     * 获取运行中的任务
     *
     * @return 返回等待中的任务
     */
    List<InstanceTaskEntity> runningTasks();

    /**
     * 优先级升级
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean priorityUpgrade(long id);

    /**
     * 优先级升级到最高
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean priorityHighest(long id);

    /**
     * 查询未完成的任务
     * <p>
     * 等待和正在运行的任务
     * </p>
     *
     * @return InstanceTaskEntity List
     */
    List<InstanceTaskEntity> getUnfinishedTasks();

    /**
     * 获取开始step 到结束step 中所有任务
     *
     * @param instanceId 实例id
     * @param startStep  开始
     * @param endStep    结束
     * @return 任务
     */
    List<InstanceTaskEntity> getInstanceTasks(long instanceId, StepType startStep, StepType endStep);

    /**
     * 获取开始step 到结束step 中所有任务
     *
     * @param instanceId        实例id
     * @param startStep         开始步骤
     * @param startOpenInterval 是否开区间，默认false
     * @param endOpenInterval   是否开区间，默认false
     * @param endStep           结束步骤
     * @return InstanceTaskEntity list
     */
    List<InstanceTaskEntity> getInstanceTasks(long instanceId, StepType startStep, boolean startOpenInterval, StepType endStep, boolean endOpenInterval);

    /**
     * 根据实例id stepType 等值查询instanceTask
     * <p>
     * select *  FROM hephaestus_instance_task WHERE (instance_id = ? AND step_key = ?)
     * <p>
     * eg:1598225528316321793(Long), step12(String)
     * </p>
     *
     * @param instanceId 实例id
     * @param step       步骤
     * @return List<InstanceTaskEntity>
     */
    List<InstanceTaskEntity> getInstanceTasksEquals(long instanceId, StepType step);

    /**
     * 根据实例id和taskId 查找实例任务
     * <p>
     * SELECT * FROM hephaestus_instance_task WHERE (instance_id = ? AND task_id = ?)
     * </p>
     *
     * @param instanceId 实例id
     * @param taskId     任务id
     * @return Optional
     */
    Optional<InstanceTaskEntity> getInstanceTask(long instanceId, long taskId);


    /**
     * 根据实验组历史id 刷新批次号
     *
     * @param experimentGroupHistoryId 历史组id
     */
    void updateBatchNo(long experimentGroupHistoryId);
}
