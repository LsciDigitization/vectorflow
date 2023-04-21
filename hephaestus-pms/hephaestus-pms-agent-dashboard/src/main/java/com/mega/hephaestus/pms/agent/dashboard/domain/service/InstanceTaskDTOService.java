package com.mega.hephaestus.pms.agent.dashboard.domain.service;

import com.google.common.collect.Lists;
import com.mega.component.commons.DatePattern;
import com.mega.component.commons.date.DateUtil;
import com.mega.component.utils.exception.CustomException;
import com.mega.hephaestus.pms.agent.dashboard.config.DashboardProjectProperties;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.InstanceLabwareDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.ProcessRecordDTOManager;
import com.mega.hephaestus.pms.agent.dashboard.domain.manager.model.InstanceLabwareModel;
import com.mega.hephaestus.pms.agent.dashboard.domain.mapstruct.HephaestusInstanceTaskStructMapper;
import com.mega.hephaestus.pms.agent.dashboard.domain.resp.*;
import com.mega.hephaestus.pms.data.model.entity.*;
import com.mega.hephaestus.pms.data.model.enums.InstanceTaskStatusEnum;
import com.mega.hephaestus.pms.data.model.service.*;
import com.mega.hephaestus.pms.data.runtime.entity.ProcessRecordEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceEntity;
import com.mega.hephaestus.pms.data.runtime.entity.InstanceTaskEntity;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceService;
import com.mega.hephaestus.pms.data.runtime.service.IProcessRecordService;
import com.mega.hephaestus.pms.data.runtime.service.IInstanceTaskService;
import com.mega.hephaestus.pms.data.runtime.view.HephaestusInstanceTaskView;
import com.mega.component.nuc.plate.PlateTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yinyse
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InstanceTaskDTOService {

    private final IInstanceTaskService instanceTaskService;

    private final IInstanceService instanceService;

    private final IResourceService resourceService;

    private final IProcessService hephaestusExperimentGroupService;

    private final IHephaestusExperimentGroupPoolService groupPoolService;

    private final DashboardProjectProperties dashboardProjectProperties;

    private final InstanceLabwareDTOManager instanceLabwareDTOManager;

    private final ProcessRecordDTOManager processRecordDTOManager;

    private final IProcessRecordService groupHistoryService;
    public InstanceTaskInfo info2(Long instanceId) {
        if (Objects.isNull(instanceId)) {
            throw new CustomException("实例不存在");
        }
        InstanceTaskInfo info = new InstanceTaskInfo();
        info.setInstanceId(instanceId);
        List<InstanceTaskInfo.TaskInfo> taskInfos;

        List<InstanceTaskEntity> tasks = instanceTaskService.listByInstanceId(instanceId);
        if (CollectionUtils.isEmpty(tasks)) {
            return info;
        }
        // 获取板子类型
        InstanceTaskEntity instanceTask = tasks.get(0);
        info.setPoolType(instanceTask.getExperimentPoolType());

        // task 按照创建时间进行排序
        tasks = tasks.stream().sorted(Comparator.comparing(InstanceTaskEntity::getTaskNo, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());

        taskInfos = instanceTaskTOTaskInfo(tasks);

        long waitDurationTotal = taskInfos.stream().mapToLong(InstanceTaskInfo.TaskInfo::getWaitDuration).sum();
        long runDurationTotal = taskInfos.stream().mapToLong(InstanceTaskInfo.TaskInfo::getRunDuration).sum();
        long durationTotal = waitDurationTotal + runDurationTotal;

        info.setWaitDurationTotal(waitDurationTotal);
        info.setRunDurationTotal(runDurationTotal);
        info.setDurationTotal(durationTotal);
        String durationTotalFormat = DateUtil.formatDuration(durationTotal * 1000);
        info.setDurationTotalFormat(durationTotalFormat);
        info.setTaskList(taskInfos);

        return info;
    }


    /**
     * instanceTask 转换成 TaskInfo 集合
     *
     * @param tasks 实例任务
     * @return TaskInfo
     */
    public List<InstanceTaskInfo.TaskInfo> instanceTaskTOTaskInfo(List<InstanceTaskEntity> tasks) {
        List<InstanceTaskInfo.TaskInfo> newTasks = new ArrayList<>();
        int size = tasks.size();
        for (int i = 0; i < size; i++) {

            // 最后一个任务 直接插入进去
            if (i == size - 1) {
                InstanceTaskInfo.TaskInfo taskInfo = new InstanceTaskInfo.TaskInfo();
                InstanceTaskEntity current = tasks.get(i);
                instanceTaskToTaskInfo(current, taskInfo);
                taskInfo.setVirtual(false);
                newTasks.add(taskInfo);
            } else {
                // 复制当前任务
                InstanceTaskEntity current = tasks.get(i);
                InstanceTaskInfo.TaskInfo taskInfo = new InstanceTaskInfo.TaskInfo();
                instanceTaskToTaskInfo(current, taskInfo);
                taskInfo.setVirtual(false);

                // 插入虚拟任务 即 A任务和B任务中间的等待任务
                InstanceTaskEntity next = tasks.get(i + 1);
                InstanceTaskInfo.TaskInfo virtual = new InstanceTaskInfo.TaskInfo();
                instanceTaskToTaskInfo(next, virtual);
                virtual.setVirtual(true);
                virtual.setStartTime(null);
                virtual.setEndTime(null);

                // 计算等待时间 下一任务开始 和当前任务结束 之间的差
                if (Objects.nonNull(current.getEndTime()) && Objects.nonNull(next.getStartTime())) {
                    long intervalTime = DateUtil.getIntervalTime(next.getStartTime(), current.getEndTime()) / 1000;
                    virtual.setWaitDuration(intervalTime);
                }

                newTasks.add(taskInfo);
                newTasks.add(virtual);
            }

        }

        return newTasks;
    }

    /**
     * 实例对象转换成taskInfo
     *
     * @param instanceTask 实例对象
     * @param taskInfo     taskInfo
     */
    public void instanceTaskToTaskInfo(InstanceTaskEntity instanceTask, InstanceTaskInfo.TaskInfo taskInfo) {

        taskInfo.setTaskName(instanceTask.getTaskName());
        taskInfo.setTaskStatus(instanceTask.getTaskStatus());
        taskInfo.setStartTime(instanceTask.getStartTime());
        taskInfo.setEndTime(instanceTask.getEndTime());
        taskInfo.setCreateTime(instanceTask.getCreateTime());
        if (Objects.nonNull(instanceTask.getStartTime()) && Objects.nonNull(instanceTask.getEndTime())) {
            taskInfo.setRunDuration(Duration.between(instanceTask.getStartTime().toInstant(), instanceTask.getEndTime().toInstant()).getSeconds());
        }
        if (Objects.nonNull(instanceTask.getStartTime())) {
            taskInfo.setStartTimeFormatted(DateUtil.toString(instanceTask.getStartTime(), DatePattern.COMMON_TIME));
        }
        if (Objects.nonNull(instanceTask.getEndTime())) {
            taskInfo.setEndTimeFormatted(DateUtil.toString(instanceTask.getEndTime(), DatePattern.COMMON_TIME));
        }

        taskInfo.setDeviceKey(instanceTask.getDeviceKey());
        taskInfo.setStepKey(instanceTask.getStepKey());
        taskInfo.setTaskNo(instanceTask.getTaskNo());
    }


    public GanttChart ganttByRunning() {

        Optional<ProcessRecordEntity> last = processRecordDTOManager.getLast();
        return last.map(processRecordEntity -> gantt(processRecordEntity.getId())).orElse(null);
    }

    /**
     * 甘特图
     *
     * @param processRecordId 实验组历史ID
     * @return GanttChart
     */
    public GanttChart gantt(long processRecordId) {

        ProcessRecordEntity groupHistory = groupHistoryService
                .getById(processRecordId);
        GanttChart chart = new GanttChart();
        List<InstanceEntity> instances = instanceService.listByExperimentGroupHistoryId(processRecordId, null);
        if (CollectionUtils.isEmpty(instances)) {
            return chart;
        }

        List<Long> ids = instances.stream().map(InstanceEntity::getId).collect(Collectors.toList());
        Set<Long> idSetList = new HashSet<>(ids);
        List<HephaestusInstanceTaskView> tasks = instanceTaskService.listInstanceTaskViewByInstanceIds(idSetList);
        if (CollectionUtils.isEmpty(tasks)) {
            return chart;
        }

        List<InstanceLabwareModel> plates = instanceLabwareDTOManager.listByInstanceIds(ids, processRecordId);
        if (CollectionUtils.isEmpty(plates)) {
            return chart;
        }

        List<HephaestusExperimentGroupPool> groupPools = groupPoolService
                .listByExperimentGroupId(groupHistory.getProcessId());
        Map<String, HephaestusExperimentGroupPool> groupPoolMap = groupPools.stream()
                .collect(Collectors.toMap(HephaestusExperimentGroupPool::getStoragePoolType, m -> m));

        Map<Long, InstanceEntity> instanceMap = instances.stream().collect(Collectors.toMap(InstanceEntity::getId, m -> m));
        Map<Long, List<HephaestusInstanceTaskView>> taskMap = tasks.stream().collect(Collectors.groupingBy(HephaestusInstanceTaskView::getInstanceId));
        Map<Long, InstanceLabwareModel> plateMap = plates.stream().collect(Collectors.toMap(InstanceLabwareModel::getInstanceId, v -> v));
        Map<String, String> color = deviceColor(processRecordId);
        return initChart(instanceMap, taskMap, plateMap, color, chart, groupPoolMap);
    }

    /**
     * 构建甘特图数据
     *
     * @param instanceMap 实例
     * @param taskMap     任务
     * @param chart       图标
     * @return GanttChart GanttChart
     */
    public GanttChart initChart(Map<Long, InstanceEntity> instanceMap, Map<Long, List<HephaestusInstanceTaskView>> taskMap, Map<Long, InstanceLabwareModel> plateMap, Map<String, String> color, GanttChart chart, Map<String, HephaestusExperimentGroupPool> groupPoolMap) {
        List<GanttChart.GanttInstanceInfo> instances = new ArrayList<>();
        Date start = null, end = null;
        Set<Long> keys = instanceMap.keySet();
        for (Long key : keys) {
            InstanceEntity instance = instanceMap.get(key);
            GanttChart.GanttInstanceInfo info = new GanttChart.GanttInstanceInfo();
            List<GanttChart.GanttTaskInfo> taskInfos = new ArrayList<>();
            info.setInstanceName(instance.getInstanceTitle());
            info.setCreateTime(instance.getCreateTime());
            info.setInstanceId(String.valueOf(instance.getId()));

            instances.add(info);
            InstanceLabwareModel plate = plateMap.get(key);
            if (Objects.nonNull(plate)) {

                info.setPlateNo(plate.getIterationNo());
                info.setExperimentPoolType(plate.getLabwareType());
                info.setExperimentPoolTypeName(PlateTypeEnum.toEnum(plate.getLabwareType()).getLabel());
                // 设置排序
                HephaestusExperimentGroupPool groupPool = groupPoolMap
                        .get(plate.getLabwareType());
                if (Objects.nonNull(groupPool)) {
                    log.info("类型：{},序号：{}", plate.getLabwareType(), groupPool.getSortOrder());
                    info.setSortOrder(groupPool.getSortOrder());
                }

            }
            List<HephaestusInstanceTaskView> tasks = taskMap.get(key);
            if (CollectionUtils.isNotEmpty(tasks)) {
                List<Date> starts = tasks.stream().map(HephaestusInstanceTaskView::getStartTime).filter(Objects::nonNull).collect(Collectors.toList());
                List<Date> ends = tasks.stream().map(HephaestusInstanceTaskView::getEndTime).filter(Objects::nonNull).collect(Collectors.toList());
                Date min = CollectionUtils.isEmpty(starts) ? new Date() : Collections.min(starts);
                Date max = CollectionUtils.isEmpty(ends) ? new Date() : Collections.max(ends);
                if (Objects.isNull(start) || min.before(start)) start = min;
                if (Objects.isNull(end) || max.after(end)) end = max;
                for (HephaestusInstanceTaskView task : tasks) {
                    taskInfos.add(new GanttChart.GanttTaskInfo(String.valueOf(task.getTaskId()), task.getStartTime(), task.getEndTime(), task.getTaskName(), task.getDeviceKey(), task.getDeviceType(), task.getTaskStatus(),
                            task.getCreateTime(), StringUtils.isBlank(color.get(task.getDeviceKey())) ? "" : color.get(task.getDeviceKey())));
                }

                // 设置批次号
                info.setBatchNo(tasks.get(0).getBatchNo());

            }


            if (CollectionUtils.isNotEmpty(taskInfos))
                taskInfos = taskInfos.stream().sorted(Comparator.comparing(GanttChart.GanttTaskInfo::getCreateTime)).collect(Collectors.toList());
            info.setTasks(taskInfos);

        }

        chart.setStartTime(start);
        chart.setEndTime(end);
        chart.setExperimentGroupName(dashboardProjectProperties.getProjectName());
        long durationTime = DateUtil.getIntervalTime(start, end);
        chart.setDurationTimeFormat(DateUtil.formatDuration(durationTime));
        chart.setStartTimeFormat(DateUtil.toString(start, "HH:mm:ss"));
        chart.setEndTimeFormat(DateUtil.toString(end, "HH:mm:ss"));


        if (CollectionUtils.isNotEmpty(instances))
            instances = instances.stream().sorted(
                    Comparator.nullsLast(Comparator.comparing(GanttChart.GanttInstanceInfo::getBatchNo, Comparator.nullsLast(Integer::compareTo))
                            .thenComparing(GanttChart.GanttInstanceInfo::getPlateNo, Comparator.nullsLast(Integer::compareTo))
                            .thenComparing(GanttChart.GanttInstanceInfo::getSortOrder, Comparator.nullsLast(Integer::compareTo))
                            //       .thenComparing(GanttChart.GanttInstanceInfo::getInstanceName, Comparator.nullsLast(String::compareTo))
                            .thenComparing(Comparator.nullsLast(Comparator.comparing(GanttChart.GanttInstanceInfo::getCreateTime))))

            ).collect(Collectors.toList());
        chart.setInstances(instances);
        return chart;
    }


    private Map<String, String> deviceColor(Long experimentGroupHistoryId) {
        ProcessRecordEntity processRecordEntity = groupHistoryService.getById(experimentGroupHistoryId);
        if (Objects.isNull(processRecordEntity)) throw new CustomException("不存在该历史组");
        ProcessEntity processEntity = hephaestusExperimentGroupService.getById(processRecordEntity.getProcessId());
        if (Objects.isNull(processEntity)) throw new CustomException("不存在该历史组关联的实验组");
        List<ResourceEntity> resources = resourceService.listByProjectId(processEntity.getProjectId());
        if (CollectionUtils.isEmpty(resources)) throw new CustomException("不存在该历史组关联的设备资源");
        return resources.stream().collect(Collectors.toMap(ResourceEntity::getDeviceKey, ResourceEntity::getResourceColor));
    }


    /**
     * 查询正在运行或者等待的任务
     *
     * @return instanceTask
     */
    public List<InstanceTaskDTO> instanceTaskDynamicList() {

        Optional<ProcessRecordEntity> lastOptional = processRecordDTOManager.getLast();

        if (lastOptional.isPresent()) {
            ArrayList<String> statusArray = Lists.newArrayList(
                    InstanceTaskStatusEnum.Running.getValue(),
                    InstanceTaskStatusEnum.Await.getValue()
            );
            List<InstanceTaskEntity> instanceTaskList = instanceTaskService.listByTaskStatusesAndHistoryId(statusArray, lastOptional.get().getId());

            List<InstanceTaskEntity> collect = instanceTaskList.stream().peek(instanceTask -> {
                        if (InstanceTaskStatusEnum.Running.getValue().equals(instanceTask.getTaskStatus())) {
                            instanceTask.setTransientSort(1);
                        }

                        if (InstanceTaskStatusEnum.Await.getValue().equals(instanceTask.getTaskStatus())) {
                            instanceTask.setTransientSort(2);
                        }

                    })
                    .sorted(Comparator.comparing(InstanceTaskEntity::getTransientSort)
                            .thenComparing(InstanceTaskEntity::getPlateNo)
                            .thenComparing(InstanceTaskEntity::getCreateTime))
                    .collect(Collectors.toList());

            return HephaestusInstanceTaskStructMapper.INSTANCE.entity2InstanceTaskDTO(collect);
        }

        return List.of();
    }

    /**
     * 获取正在运行中的实例动态
     *
     * @return 实例动态
     */
    public List<InstanceTaskInfo> instanceTaskDynamicByRunning() {

        return instanceTaskDynamic();
    }

    /**
     * 根据历史实验组id 查询实例任务
     */
    public List<InstanceTaskInfo> instanceTaskDynamic() {
        Optional<ProcessRecordEntity> last = processRecordDTOManager.getLast();
        List<InstanceLabwareModel> instancePlateList = new ArrayList<>();
        if (last.isPresent()) {
            instancePlateList = instanceLabwareDTOManager.getAllInstanceLabware(last.get().getId());
        }


        if (CollectionUtils.isNotEmpty(instancePlateList)) {

            Set<Long> instanceIds = instancePlateList.stream().map(InstanceLabwareModel::getInstanceId).filter(Objects::nonNull).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(instanceIds)) {

                List<InstanceEntity> instanceList = instanceService.listByIds(instanceIds);
                if (CollectionUtils.isNotEmpty(instanceList)) {

                    // 实例按照id分组 key:id,value:instance对象
                    Map<Long, List<InstanceEntity>> instanceListMap = instanceList.stream().collect(Collectors.groupingBy(InstanceEntity::getId));

                    List<HephaestusInstanceTaskView> list = instanceTaskService.listInstanceTaskViewByInstanceIds(instanceIds);

                    if (CollectionUtils.isNotEmpty(list)) {
                        list = list.stream().sorted(Comparator.comparing(HephaestusInstanceTaskView::getCreateTime, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
                        // 按照实例id 进行分组 k:实例id,v: 任务集合
                        Map<Long, List<HephaestusInstanceTaskView>> instanceTaskGtoup = list.stream().collect(Collectors.groupingBy(HephaestusInstanceTaskView::getInstanceId));

                        List<InstanceTaskInfo> result = new ArrayList<>();

                        instanceTaskGtoup.forEach((k, v) -> {
                            InstanceTaskInfo info = new InstanceTaskInfo();


                            List<InstanceTaskEntity> collect = v.stream().map(instanceTaskView -> {
                                InstanceTaskEntity instanceTaskEntity = new InstanceTaskEntity();

                                BeanUtils.copyProperties(instanceTaskView, instanceTaskEntity);
                                return instanceTaskEntity;
                            }).collect(Collectors.toList());
                            // 构造tasks
                            List<InstanceTaskInfo.TaskInfo> taskInfos = instanceTaskTOTaskInfo(collect);
                            info.setInstanceId(k);

                            List<InstanceEntity> instance = instanceListMap.get(k);
                            if (CollectionUtils.isNotEmpty(instance)) {
                                info.setInstanceTitle(instance.get(0).getInstanceTitle());
                                info.setInstanceStatus(instance.get(0).getInstanceStatus().getValue());
                            }
                            if (CollectionUtils.isNotEmpty(v)) {
                                info.setPoolType(PlateTypeEnum.toEnum(v.get(0).getExperimentPoolType()).getLabel());

                            }
                            info.setPlateNo(v.get(0).getPlateNo());
                            info.setBatchNo(v.get(0).getBatchNo());
                            info.setTaskList(taskInfos);
                            result.add(info);
                        });
                        return result.stream()
                                .sorted(Comparator.comparing(InstanceTaskInfo::getBatchNo, Comparator.nullsLast(Integer::compareTo))
                                        .thenComparing(InstanceTaskInfo::getPlateNo, Comparator.nullsLast(Integer::compareTo))
                                        .thenComparing(InstanceTaskInfo::getInstanceTitle, Comparator.nullsLast(String::compareTo))
                                        .thenComparing(Comparator.nullsLast(Comparator.comparing(task -> task.getTaskList().get(0).getCreateTime())))
                                )
                                .collect(Collectors.toList());
                    }
                }


                return List.of();
            }
            return List.of();
        }

        return List.of();
    }


    public Optional<InstanceTaskCountDTO> count() {
        Optional<ProcessRecordEntity> lastOptional = processRecordDTOManager.getLast();
        if (lastOptional.isPresent()) {

            List<InstanceTaskEntity> instanceTaskList = instanceTaskService.listByHistoryId(lastOptional.get().getId());
            if (CollectionUtils.isNotEmpty(instanceTaskList)) {

                InstanceTaskCountDTO dto = new InstanceTaskCountDTO();
                dto.setTotal(instanceTaskList.size());

                long runningCount = instanceTaskList.stream().filter(v -> InstanceTaskStatusEnum.Running.getValue().equals(v.getTaskStatus())).count();
                dto.setRunningTotal(runningCount);

                long finishedCount = instanceTaskList.stream().filter(v -> InstanceTaskStatusEnum.Finished.getValue().equals(v.getTaskStatus())).count();
                dto.setFinishedTotal(finishedCount);

                return Optional.ofNullable(dto);
            }
            return Optional.empty();
        }

        return Optional.empty();
    }


}
