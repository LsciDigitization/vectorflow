package com.mega.hephaestus.pms.workflow.task.taskbuild;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mega.component.bioflow.task.ExperimentEntity;
import com.mega.component.bioflow.task.StageEntity;
import com.mega.component.bioflow.task.StageType;
import com.mega.component.workflow.models.TaskType;
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
 * @date 2023/3/23 17:22
 */
@Component
public class TaskBuildManager {

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
    private void init() {
        taskTypeMap.put(startTaskType.getType(), startTaskType);
        taskTypeMap.put(endTaskType.getType(), endTaskType);
        taskTypeMap.put(stageTaskType.getType(), stageTaskType);
        taskTypeMap.put(awaitTaskType.getType(), awaitTaskType);
        taskTypeMap.put(gatewayTaskType.getType(), gatewayTaskType);
    }

    /**
     * 获取一个实验的所有Stages编排对象
     * @param experiment 实验ID
     * @return Task.RootTask
     */
    public Task.RootTask buildExperimentRootTask(ExperimentEntity experiment) {
        List<StageEntity> stages = experiment.getStages();
        List<Task> tasks = buildStageToTask(stages);
        return buildStageRootTask(tasks);
    }

    /**
     * 获取一个实验的所有Stages编排对象
     * @param experiment 实验ID
     * @return Json字符串
     * @throws JsonProcessingException JSON异常
     */
    public String buildExperimentRootTaskAsJson(ExperimentEntity experiment) throws JsonProcessingException {
        List<StageEntity> stages = experiment.getStages();
        List<Task> tasks = buildStageToTask(stages);
        Task.RootTask rootTask = buildStageRootTask(tasks);
        return toJson(rootTask);
    }

    /**
     * 获取一个实验的所有Stages编排对象
     * @param experiment 实验ID
     * @return Json字符串
     * @throws JsonProcessingException JSON异常
     */
    public String buildExperimentRootTaskAsJson(ExperimentEntity experiment, SpecialMeta specialMeta) throws JsonProcessingException {
        List<StageEntity> stages = experiment.getStages();
        List<Task> tasks = buildStageToTask(stages);
        tasks.forEach(v -> {
            v.setMetaData(specialMeta.getMetaData());
        });
        Task.RootTask rootTask = buildStageRootTask(tasks);
        return toJson(rootTask);
    }

    public String toJson(Task.RootTask rootTask) throws JsonProcessingException {
        return objectMapper.writeValueAsString(rootTask);
    }

    protected Task.RootTask buildStageRootTask(List<Task> tasks) {
        return Task.RootTask.builder()
                .rootTaskId("root")
                .tasks(tasks)
                .build();
    }

    protected List<Task> buildStageToTask(List<StageEntity> stageEntities) {
        List<String> taskIds = stageEntities.stream()
                .map(StageEntity::getStageName)
                .collect(Collectors.toList());

        Map<String, StageEntity> stageToMap = getStageToMap(stageEntities);

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

    protected Map<String, StageEntity> getStageToMap(List<StageEntity> stageEntities) {
        return stageEntities.stream()
                .collect(Collectors.toMap(StageEntity::getStageName, Function.identity()));
    }

    // 递归遍历方法
    protected void recursiveTask(Map<String, StageEntity> stageToMap, List<Task> tasks, Iterator<String> iterator, Task build) {
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
            StageEntity stage = stageToMap.get(next);
            StageType stageType = stage.getStageType();
            if (taskTypeMap.containsKey(stageType.toString())) {
                taskType = taskTypeMap.get(stageType.toString());
            }
        }

        Task build2 = TaskStageBuilder.builder(next).build(taskType);
        tasks.add(build2);

        recursiveTask(stageToMap, tasks, iterator, build2);
    }

}
