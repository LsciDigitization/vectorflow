package com.mega.hephaestus.pms.workflow.manager.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mega.component.workflow.models.TaskType;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStage;
import com.mega.hephaestus.pms.data.model.service.IHephaestusStageService;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentStageManager;
import com.mega.hephaestus.pms.workflow.task.taskbuild.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/2/6 11:40
 */
@Component
@Deprecated(since = "20230323")
public class ExperimentStageManagerImpl implements ExperimentStageManager {

    @Autowired
    private IHephaestusStageService stageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Resource
    private TaskType stageTaskType;
    @Resource
    private TaskType startTaskType;
    @Resource
    private TaskType endTaskType;
    @Resource
    private TaskType awaitTaskType;
    @Resource
    private TaskType gatewayTaskType;

    private final Map<String, TaskType> taskTypeMap = new HashMap<>();

    @PostConstruct
    public void init() {
        taskTypeMap.put(startTaskType.getType(), startTaskType);
        taskTypeMap.put(endTaskType.getType(), endTaskType);
        taskTypeMap.put(stageTaskType.getType(), stageTaskType);
        taskTypeMap.put(awaitTaskType.getType(), awaitTaskType);
        taskTypeMap.put(gatewayTaskType.getType(), gatewayTaskType);
    }

    /**
     * 获取一个实验的所有stages
     * @param experimentId 实验ID
     * @return HephaestusStage list
     */
    @Deprecated(since = "20230323")
    public List<HephaestusStage> getStages(Long experimentId) {
        return stageService.listByExperimentId(experimentId);
    }

    /**
     * 获取一个实验的所有Stages编排对象
     * @param experimentId 实验ID
     * @return Task.RootTask
     */
    @Deprecated(since = "20230323")
    public Task.RootTask getStageRootTask(Long experimentId) {
        List<HephaestusStage> stages = getStages(experimentId);
        List<Task> tasks = buildStageToTask(stages);
        return buildStageRootTask(tasks);
    }

    /**
     * 获取一个实验的所有Stages编排对象
     * @param experimentId 实验ID
     * @return Json字符串
     * @throws JsonProcessingException JSON异常
     */
    @Deprecated(since = "20230323")
    public String getStageRootTaskAsJson(Long experimentId) throws JsonProcessingException {
        List<HephaestusStage> stages = getStages(experimentId);
        List<Task> tasks = buildStageToTask(stages);
        Task.RootTask rootTask = buildStageRootTask(tasks);
        return toJson(rootTask);
    }

    @Deprecated(since = "20230323")
    public String getStageRootTaskAsJson(Long experimentId, SpecialMeta specialMeta) throws JsonProcessingException {
        List<HephaestusStage> stages = getStages(experimentId);
        List<Task> tasks = buildStageToTask(stages);
        tasks.forEach(v -> {
            v.setMetaData(specialMeta.getMetaData());
        });
        tasks.forEach(System.out::println);
        Task.RootTask rootTask = buildStageRootTask(tasks);
        return toJson(rootTask);
    }


    @Deprecated(since = "20230323")
    public Map<String, HephaestusStage> getStageToMap(List<HephaestusStage> hephaestusStages) {
        return hephaestusStages.stream()
                .collect(Collectors.toMap(HephaestusStage::getStageName, Function.identity()));
    }

    @Deprecated(since = "20230323")
    public List<Task> buildStageToTask(List<HephaestusStage> hephaestusStages) {
        List<String> taskIds = hephaestusStages.stream()
                .map(HephaestusStage::getStageName)
                .collect(Collectors.toList());

        Map<String, HephaestusStage> stageToMap = getStageToMap(hephaestusStages);

        Iterator<String> iterator = taskIds.stream().iterator();

        List<Task> tasks = new ArrayList<>();
        // add root node
        Task rootBuild = TaskRootBuilder.builder().build(startTaskType);
        tasks.add(rootBuild);

        // add task node
        recursiveTask(stageToMap, tasks, iterator, rootBuild);

        // add end node
        Task endBuild = TaskEndBuilder.builder().build(endTaskType);
        tasks.add(endBuild);

        return tasks;
    }

    @Deprecated(since = "20230323")
    public Task.RootTask buildStageRootTask(List<Task> tasks) {
        return Task.RootTask.builder()
                .rootTaskId("root")
                .tasks(tasks)
                .build();
    }

    @Deprecated(since = "20230323")
    public String toJson(Task.RootTask rootTask) throws JsonProcessingException {
        return objectMapper.writeValueAsString(rootTask);
    }

    // 递归遍历方法
    @Deprecated(since = "20230323")
    private void recursiveTask(Map<String, HephaestusStage> stageToMap, List<Task> tasks, Iterator<String> iterator, Task build) {
        if (! iterator.hasNext()) {
            if (build != null) {
                build.addChildrenTaskId("end");
            }
            return;
        }

        String next = iterator.next();

        if (build != null) {
            build.addChildrenTaskId(next);
        }

        TaskType taskType = stageTaskType;
        if (stageToMap.containsKey(next)) {
            HephaestusStage stage = stageToMap.get(next);
            String stageType = stage.getStageType();
            if (taskTypeMap.containsKey(stageType)) {
                taskType = taskTypeMap.get(stageType);
            }
        }

        Task build2 = TaskStageBuilder.builder(next).build(taskType);
        tasks.add(build2);

        recursiveTask(stageToMap, tasks, iterator, build2);
    }

}
