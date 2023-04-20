package com.mega.hephaestus.pms.workflow.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mega.component.bioflow.task.StageTaskEntity;
import com.mega.component.bioflow.task.StepKey;
import com.mega.component.nuc.step.StepType;
import com.mega.component.nuc.step.StepTypeEnum;
import com.mega.hephaestus.pms.data.model.entity.*;
import com.mega.hephaestus.pms.data.model.enums.DeviceTaskPriorityEnum;
import com.mega.hephaestus.pms.data.model.enums.InstanceTaskStatusEnum;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceStepEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceStepService;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceTaskService;
import com.mega.component.nuc.device.DeviceType;
import com.mega.hephaestus.pms.workflow.exception.InstanceTaskException;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ExperimentGroupHistoryManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceLabwareManager;
import com.mega.hephaestus.pms.workflow.manager.dynamic.InstanceTaskManager;
import com.mega.hephaestus.pms.workflow.manager.model.InstanceLabwareModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author wangzhengdong
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InstanceTaskManagerImpl implements InstanceTaskManager {

    private final IInstanceTaskService instanceTaskService;

    private final InstanceLabwareManager labwareManager;

    private final IInstanceStepService stepService;

    private final ExperimentGroupHistoryManager experimentGroupHistoryManager;

    /**
     * 创建task
     *
     * @param deviceType 设备DeviceType枚举类
     * @param stageTask  task任务
     * @return 创建后的deviceTask对象
     */
    public InstanceTaskEntity createTask(DeviceType deviceType, HephaestusStageTask stageTask, long instanceId) {

        Optional<InstanceTaskEntity> instanceTaskOptional = getInstanceTask(instanceId, stageTask.getId());

        // 存在则不创建 直接返回
        if (instanceTaskOptional.isPresent()) {
            log.info("实例id:{},任务id:{}，已存在实例任务，直接返回", instanceId, stageTask.getId());
            return instanceTaskOptional.get();
        } else {
            InstanceTaskEntity instanceTask = new InstanceTaskEntity();
            // 设备相关属性
            instanceTask.setDeviceType(deviceType.getCode());
            instanceTask.setDeviceKeyRange(stageTask.getDeviceKey());

            // 任务相关属性
            instanceTask.setTaskCommand(stageTask.getTaskCommand());
            instanceTask.setTaskParameter(stageTask.getTaskParameter());
            instanceTask.setTaskId(stageTask.getId());
            instanceTask.setTaskRequestId(UUID.randomUUID().toString());
            instanceTask.setTimeoutSecond(stageTask.getTimeoutSecond());
            instanceTask.setTaskName(stageTask.getTaskName());
            instanceTask.setInstanceId(instanceId);
            instanceTask.setTaskNo(stageTask.getSortOrder());

            // 默认状态为创建
            instanceTask.setTaskStatus(InstanceTaskStatusEnum.Await.getValue());
            instanceTask.setCreateTime(new Date());

            // 优先级
            instanceTask.setPriority(DeviceTaskPriorityEnum.Low.getValue());
            instanceTask.setUpdatePriorityTime(new Date());

            // 到达下一个任务最大间距时间 单位秒 -1 没有限制
            instanceTask.setNextTaskExpireDurationSecond(stageTask.getNextTaskExpireDurationSecond());

            if (StringUtils.isNotBlank(stageTask.getStepKey())) {
                instanceTask.setStepKey(stageTask.getStepKey());
                instanceTask.setStepValue(StepTypeEnum.toEnum(stageTask.getStepKey()).getValue());
            }


            Optional<InstanceLabwareModel> instancePlate = labwareManager.getByInstanceId(instanceId);
//            instancePlate.ifPresent(plate -> instanceTask.setExperimentPoolType(plate.getExperimentPoolType()));

            if (instancePlate.isPresent()) {
                log.info("实例id{},instanceTask插入板序号", instanceId);
                instanceTask.setExperimentPoolType(instancePlate.get().getLabwareType());

                instanceTask.setPlateNo(instancePlate.get().getIterationNo());
                // 设置记录id
                instanceTask.setProcessRecordId(instancePlate.get().getProcessRecordId());
            }

            // 持久化
            instanceTaskService.save(instanceTask);
            return instanceTask;
        }

    }


    /**
     * 创建task
     *
     * @param deviceType 设备DeviceType枚举类
     * @param stageTask  task任务
     * @return 创建后的deviceTask对象
     */
    public InstanceTaskEntity createTask(DeviceType deviceType, StageTaskEntity stageTask, long instanceId) {

        long stageTaskId = stageTask.getId().getLongId();

        Optional<InstanceTaskEntity> instanceTaskOptional = getInstanceTask(instanceId, stageTaskId);

        // 存在则不创建 直接返回
        if (instanceTaskOptional.isPresent()) {
            log.info("实例id:{},任务id:{}，已存在实例任务，直接返回", instanceId, stageTaskId);
            return instanceTaskOptional.get();
        } else {
            InstanceTaskEntity instanceTask = new InstanceTaskEntity();
            // 设备相关属性
            instanceTask.setDeviceType(deviceType.getCode());
//            instanceTask.setDeviceKeyRange(stageTask.getDeviceKey());

            // 任务相关属性
            instanceTask.setTaskCommand(stageTask.getTaskCommand());
            instanceTask.setTaskParameter(stageTask.getTaskParameter());
            instanceTask.setTaskId(stageTaskId);
            instanceTask.setTaskRequestId(UUID.randomUUID().toString());
            instanceTask.setTimeoutSecond(stageTask.getTimeoutSecond());
            instanceTask.setTaskName(stageTask.getTaskName());
            instanceTask.setInstanceId(instanceId);
            instanceTask.setTaskNo(stageTask.getSortOrder());

            // 默认状态为创建
            instanceTask.setTaskStatus(InstanceTaskStatusEnum.Await.getValue());
            instanceTask.setCreateTime(new Date());

            // 优先级
            instanceTask.setPriority(DeviceTaskPriorityEnum.Low.getValue());
            instanceTask.setUpdatePriorityTime(new Date());

            // 到达下一个任务最大间距时间 单位秒 -1 没有限制
            instanceTask.setNextTaskExpireDurationSecond(stageTask.getNextTaskExpireDurationSecond());

            StepKey stepKey = stageTask.getStepKey();
            if (Objects.nonNull(stepKey)) {
                instanceTask.setStepKey(stepKey.toString());
                instanceTask.setStepValue(StepTypeEnum.toEnum(stepKey.toString()).getValue());
            }


            Optional<InstanceLabwareModel> instancePlate = labwareManager.getByInstanceId(instanceId);
//            instancePlate.ifPresent(plate -> instanceTask.setExperimentPoolType(plate.getExperimentPoolType()));
            log.info("开始创建实例{}", instanceTask);
            if (instancePlate.isPresent()) {
                log.info("实例id{},instanceTask插入板序号", instanceId);
                instanceTask.setExperimentPoolType(instancePlate.get().getLabwareType());

                instanceTask.setPlateNo(instancePlate.get().getIterationNo());
                // 设置记录id
                instanceTask.setProcessRecordId(instancePlate.get().getProcessRecordId());
            }

            // 持久化
            instanceTaskService.save(instanceTask);
            return instanceTask;
        }
    }

    /**
     * 根据主键 修改当前task 为运行
     *
     * @param id        主键id
     * @param requestId 请求id
     * @return 修改后的对象
     */
    public InstanceTaskEntity runningTask(long id, String requestId, String deviceKey) {
        InstanceTaskEntity instanceTaskEntity = new InstanceTaskEntity();
        instanceTaskEntity.setId(id);
        instanceTaskEntity.setTaskRequestId(requestId);
        instanceTaskEntity.setTaskStatus(InstanceTaskStatusEnum.Running.getValue());
        instanceTaskEntity.setDeviceKey(deviceKey);
        instanceTaskEntity.setStartTime(new Date());

        instanceTaskService.updateById(instanceTaskEntity);

//        List<InstanceTaskEntity> allInstanceTasks = getAllInstanceTasks();
//        allInstanceTasks.stream()
//                .filter(v -> v.getId().equals(id))
//                .peek(v -> {
//                    v.setTaskRequestId(requestId);
//                    v.setTaskStatus(InstanceTaskStatusEnum.Running.getValue());
//                    v.setDeviceKey(deviceKey);
//                    v.setStartTime(new Date());
//                }).collect(Collectors.toList());

        return instanceTaskEntity;
    }


    /**
     * 检查task 状态
     *
     * @param id 实例id
     * @return true 完成，false未完成，异常:失败或者找不到
     */
    public boolean checkTaskFinished(long id) {

        InstanceTaskEntity instanceTask = instanceTaskService.getById(id);

        if (Objects.nonNull(instanceTask)) {

            String taskStatus = instanceTask.getTaskStatus();
            // 设备task完成
            if (InstanceTaskStatusEnum.Finished.getValue().equals(taskStatus)) {
                return true;
            }
            // 设备task 失败 抛出异常
            else if (InstanceTaskStatusEnum.Failed.getValue().equals(taskStatus)) {
                throw new InstanceTaskException(String.format("当前instanceTaskId:%s,失败", id));
            } else {
                return false;
            }
        }

        throw new InstanceTaskException(String.format("当前instanceTaskId:%s,未找到", id));
    }

    /**
     * 修改task 完成
     *
     * @param requestId hephaestusInstanceTask
     */
    public boolean updateTaskFinished(String requestId) {
        // 修改状态
        InstanceTaskEntity instanceTaskEntity = new InstanceTaskEntity();
        instanceTaskEntity.setTaskStatus(InstanceTaskStatusEnum.Finished.getValue());
        instanceTaskEntity.setEndTime(new Date());
        return instanceTaskService.lambdaUpdate()
                .eq(InstanceTaskEntity::getTaskRequestId, requestId)
                .update(instanceTaskEntity);
    }

    /**
     * 修改task 失败
     *
     * @param requestId    hephaestusInstanceTask
     * @param errorMessage 错误信息
     */
    public boolean updateTaskFailed(String requestId, String errorMessage) {

        InstanceTaskEntity instanceTaskEntity = new InstanceTaskEntity();
        instanceTaskEntity.setTaskErrorMessage(errorMessage);
        instanceTaskEntity.setTaskStatus(InstanceTaskStatusEnum.Failed.getValue());
        instanceTaskEntity.setEndTime(new Date());
        return instanceTaskService.lambdaUpdate()
                .eq(InstanceTaskEntity::getTaskRequestId, requestId)
                .update(instanceTaskEntity);
    }


    /**
     * 获取等待中的任务
     *
     * @return 返回等待中的任务
     */
    public List<InstanceTaskEntity> awaitTasks() {

        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager.getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            return instanceTaskService.listByTaskStatus(InstanceTaskStatusEnum.Await.getValue(), runningGroupOptional.get().getId());
        } else {
            return List.of();
        }
    }

    /**
     * 获取运行中的任务
     *
     * @return 返回等待中的任务
     */
    public List<InstanceTaskEntity> runningTasks() {

        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager.getRunningGroup();
        if (runningGroupOptional.isPresent()) {
            return instanceTaskService.listByTaskStatus(InstanceTaskStatusEnum.Running.getValue(), runningGroupOptional.get().getId());
        } else {
            return List.of();
        }
    }

    /**
     * 优先级升级
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean priorityUpgrade(long id) {

        InstanceTaskEntity task = instanceTaskService.getById(id);

        if (Objects.nonNull(task)) {

            Integer priority = task.getPriority();
            // 如果优先级不存在 则设置为最低
            if (Objects.isNull(priority)) {
                task.setPriority(DeviceTaskPriorityEnum.Low.getValue());
                task.setUpdatePriorityTime(new Date());
            } else {
                // 低升级到中 中升级到高
                if (priority == DeviceTaskPriorityEnum.Low.getValue()) {
                    task.setPriority(DeviceTaskPriorityEnum.Medium.getValue());
                } else if (priority == DeviceTaskPriorityEnum.Medium.getValue()) {
                    task.setPriority(DeviceTaskPriorityEnum.High.getValue());
                } else {
                    task.setPriority(DeviceTaskPriorityEnum.High.getValue());
                }

                task.setUpdatePriorityTime(new Date());
                return instanceTaskService.updateById(task);
            }


        }
        return false;
    }

    /**
     * 优先级升级到最高
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean priorityHighest(long id) {
        InstanceTaskEntity task = instanceTaskService.getById(id);

        if (Objects.nonNull(task)) {
            task.setPriority(DeviceTaskPriorityEnum.High.getValue());
            task.setUpdatePriorityTime(new Date());
            return instanceTaskService.updateById(task);
        }
        return false;
    }

    /**
     * 查询未完成的任务
     * <p>
     * 等待和正在运行的任务
     * </p>
     *
     * @return 未完成的任务
     */
    public List<InstanceTaskEntity> getUnfinishedTasks() {
        Optional<ProcessRecordEntity> runningGroupOptional = experimentGroupHistoryManager.getRunningGroup();

        if (runningGroupOptional.isPresent()) {

            List<String> statusList = Arrays.asList(InstanceTaskStatusEnum.Running.getValue(), InstanceTaskStatusEnum.Await.getValue());


            List<InstanceTaskEntity> list = instanceTaskService.lambdaQuery()
                    .in(InstanceTaskEntity::getTaskStatus, statusList)
                    .eq(InstanceTaskEntity::getProcessRecordId, runningGroupOptional.get().getId())
                    .list();
            if (CollectionUtils.isEmpty(list)) {
                return List.of();
            }
            return list;
        }
        return List.of();
    }

    /**
     * 获取开始step 到结束step 中所有任务
     *
     * @param instanceId 实例id
     * @param startStep  开始
     * @param endStep    结束
     * @return 任务
     */
    public List<InstanceTaskEntity> getInstanceTasks(long instanceId, StepType startStep, StepType endStep) {
        return getInstanceTasks(instanceId, startStep, false, endStep, false);
    }

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
    public List<InstanceTaskEntity> getInstanceTasks(long instanceId, StepType startStep, boolean startOpenInterval, StepType endStep, boolean endOpenInterval) {
        // start end 相等时  直接等值查询
        if (startStep.getCode().equals(endStep.getCode())) {
            log.info("开始step:{},结束step:{},相等,执行等值查询", startStep.getCode(), endStep.getCode());
            return getInstanceTasksEquals(instanceId, startStep);
        } else {
            List<String> stepKeys = Arrays.asList(startStep.getCode(), endStep.getCode());

//            // 获取实例任务列表
//            List<InstanceTaskEntity> hephaestusInstanceTasks = getAllInstanceTasks();
//
//            List<InstanceTaskEntity> list = hephaestusInstanceTasks.stream()
//                    .filter(v -> {
//                        return stepKeys.contains(v.getStepKey()) // in
//                                && v.getInstanceId().equals(instanceId);
//                    })
//                    .collect(Collectors.toList());

            List<InstanceTaskEntity> list = instanceTaskService.lambdaQuery()
                    .eq(InstanceTaskEntity::getInstanceId, instanceId)
                    .in(InstanceTaskEntity::getStepKey, stepKeys)
                    .list();

            if (CollectionUtils.isNotEmpty(list)) {
                // 获取开始日期 最大和最小
                List<Integer> collect = list.stream().map(InstanceTaskEntity::getTaskNo)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                Integer max = Collections.max(collect);
                Integer min = Collections.min(collect);

//                List<InstanceTaskEntity> result = hephaestusInstanceTasks.stream()
//                        .filter(v -> {
//                            return v.getInstanceId().equals(instanceId)
//                                    ;
//                        })
//                        .filter(v -> {
//                            if (startOpenInterval) {
//                                // 开区间 大于
//                                return v.getTaskNo() >= min;
//                            } else {
//                                // 闭区间 大于等于
//                                return Objects.equals(v.getTaskNo(), min);
//                            }
//                        })
//                        .filter(v -> {
//                            if (endOpenInterval) {
//                                return v.getTaskNo() <= max;
//                            } else {
//                                return Objects.equals(v.getTaskNo(), max);
//                            }
//                        })
//                        .collect(Collectors.toList());

                LambdaQueryWrapper<InstanceTaskEntity> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(InstanceTaskEntity::getInstanceId, instanceId);
                if (startOpenInterval) {
                    // 开区间 大于
                    queryWrapper.gt(InstanceTaskEntity::getTaskNo, min);
                } else {
                    // 闭区间 大于等于
                    queryWrapper.ge(InstanceTaskEntity::getTaskNo, min);
                }

                if (endOpenInterval) {
                    queryWrapper.lt(InstanceTaskEntity::getTaskNo, max);
                } else {
                    queryWrapper.le(InstanceTaskEntity::getTaskNo, max);
                }
                log.debug("开始step:{},startOpenInterval:{},结束step：{},endOpenInterval:{}", min, startOpenInterval, max, endOpenInterval);

                List<InstanceTaskEntity> result = instanceTaskService.list(queryWrapper);
                if (CollectionUtils.isNotEmpty(result)) {
                    return result;
                } else {
                    return List.of();
                }
            }

            return List.of();
        }


    }

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
    public List<InstanceTaskEntity> getInstanceTasksEquals(long instanceId, StepType step) {
//        List<InstanceTaskEntity> list = getAllInstanceTasks();
////
//        List<InstanceTaskEntity> result = list.stream()
//            .filter(v ->  v.getInstanceId().equals(instanceId))
//            .filter(v -> v.getStepKey().equals(step.getCode()))
//            .collect(Collectors.toList());

        List<InstanceTaskEntity> list = instanceTaskService.lambdaQuery()
                .eq(InstanceTaskEntity::getInstanceId, instanceId)
                .eq(InstanceTaskEntity::getStepKey, step.getCode())
                .list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        }
        return List.of();
    }

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
    public Optional<InstanceTaskEntity> getInstanceTask(long instanceId, long taskId) {
//        List<InstanceTaskEntity> list = getAllInstanceTasks();
//        list.stream()
//                .filter(v -> v.getInstanceId().equals(instanceId))
//                .filter(v -> v.getTaskId().equals(taskId))
//                .findFirst();

        InstanceTaskEntity one = instanceTaskService.lambdaQuery().eq(InstanceTaskEntity::getInstanceId, instanceId)
                .eq(InstanceTaskEntity::getTaskId, taskId)
                .one();

        return Optional.ofNullable(one);
    }


    /**
     * 根据实验组历史id 刷新批次号
     *
     * @param experimentGroupHistoryId 历史组id
     */
    public void updateBatchNo(long experimentGroupHistoryId) {

//        List<InstanceTaskEntity> allInstanceTasks = getAllInstanceTasks();
//
//        //  获取等于STEP12步骤
//        List<InstanceTaskEntity> list = allInstanceTasks.stream()
//                .filter(v -> v.getStepKey().equals(StepType.STEP12.getCode()))
//                .filter(v -> v.getExperimentGroupHistoryId().equals(experimentGroupHistoryId))
//                .collect(Collectors.toList());

        List<InstanceTaskEntity> list = instanceTaskService.lambdaQuery()
                .eq(InstanceTaskEntity::getStepKey, StepTypeEnum.STEP12.getCode())
                .eq(InstanceTaskEntity::getProcessRecordId, experimentGroupHistoryId)
                .list();

        Map<String, List<InstanceTaskEntity>> collect1 = list.stream().collect(Collectors.groupingBy(InstanceTaskEntity::getTaskRequestId));
        // TaskRequestId 去重
        AtomicInteger i = new AtomicInteger(1);


        Map<String, List<InstanceStepEntity>> collect2 = new TreeMap<>();

        collect1.forEach((k, v) -> {
            List<Long> longs = v.stream().map(InstanceTaskEntity::getInstanceId).collect(Collectors.toList());
            List<InstanceStepEntity> list1 = stepService.lambdaQuery().in(InstanceStepEntity::getInstanceId, longs).orderByAsc(InstanceStepEntity::getCreateTime).list();
            collect2.put(k, list1);
        });

        Collection<List<InstanceStepEntity>> collect3 = collect2.values();
        List<List<InstanceStepEntity>> collect4 = collect3.stream().sorted(new Comparator<List<InstanceStepEntity>>() {
            @Override
            public int compare(List<InstanceStepEntity> o1, List<InstanceStepEntity> o2) {

                InstanceStepEntity instanceStepEntity1 = o1.get(0);
                InstanceStepEntity instanceStepEntity2 = o2.get(0);
                if (Objects.nonNull(instanceStepEntity1) && Objects.nonNull(instanceStepEntity2)) {

                    return instanceStepEntity1.getCreateTime().compareTo(instanceStepEntity2.getCreateTime());
                }

                return 0;
            }
        }).collect(Collectors.toList());

        Set<Long> instanceIds = new HashSet<>();

        collect4.forEach(v -> {
            v.forEach(instanceStep -> {
                Long instanceId = instanceStep.getInstanceId();
                instanceIds.add(instanceId);

                InstanceStepEntity one = new InstanceStepEntity();
                one.setId(instanceStep.getId());
                one.setBatchNo(i.get());

                stepService.updateById(one);
            });
            i.incrementAndGet();
        });

        // step6

//        List<InstanceTaskEntity> step6Task = allInstanceTasks.stream()
//                .filter(v -> v.getStepKey().equals("StepType.STEP6.getCode()"))
//                .filter(v -> instanceIds.contains(v.getInstanceId()))
//                .collect(Collectors.toList());

        List<InstanceTaskEntity> step6Task = instanceTaskService.lambdaQuery()
                .eq(InstanceTaskEntity::getStepKey, StepTypeEnum.STEP6.getCode())
                .in(InstanceTaskEntity::getInstanceId, instanceIds)
                .list();
        // 去重requestId
        List<String> requestIds = step6Task.stream().map(InstanceTaskEntity::getTaskRequestId).distinct().collect(Collectors.toList());

        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(requestIds)) {
            // in requestId 查询
//            List<InstanceTaskEntity> listByRequestIds = allInstanceTasks.stream()
//                    .filter(v -> requestIds.contains(v.getTaskRequestId()))
//                    .filter(v -> v.getExperimentGroupHistoryId().equals(experimentGroupHistoryId))
//                    .collect(Collectors.toList());

            List<InstanceTaskEntity> listByRequestIds = instanceTaskService.lambdaQuery()
                    .in(InstanceTaskEntity::getTaskRequestId, requestIds)
                    .eq(InstanceTaskEntity::getProcessRecordId, experimentGroupHistoryId)
                    .list();

            // 按照requestId 进行分组
            Map<String, List<InstanceTaskEntity>> collect = listByRequestIds.stream().collect(Collectors.groupingBy(InstanceTaskEntity::getTaskRequestId));

            if (!collect.isEmpty()) {
                collect.forEach((k, v) -> {
                    // 获取一个同一个requestId下的所有instanceId 即为一批
                    List<Long> instanceIds2 = v.stream().map(InstanceTaskEntity::getInstanceId).collect(Collectors.toList());

                    // 查询这一批instanceId 在step表数据
                    List<InstanceStepEntity> steps = stepService.lambdaQuery().in(InstanceStepEntity::getInstanceId, instanceIds2).list();
                    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(steps)) {
                        // 找到最大的批次号 即在Step12 刷进去的BatchNo
                        OptionalInt batchNo = steps.stream().filter(step -> Objects.nonNull(step.getBatchNo())).mapToInt(InstanceStepEntity::getBatchNo).distinct().max();
                        if (batchNo.isPresent()) {
                            // 过滤为空的 即Step6 的实例
                            // 将同一批次
                            steps.stream().filter(step -> Objects.isNull(step.getBatchNo())).forEach(step -> {
                                InstanceStepEntity step1 = new InstanceStepEntity();
                                step1.setId(step.getId());
                                step1.setBatchNo(batchNo.getAsInt());
                                stepService.updateById(step1);
                            });
                        }
                    }

                });

            }
        }

    }


}
