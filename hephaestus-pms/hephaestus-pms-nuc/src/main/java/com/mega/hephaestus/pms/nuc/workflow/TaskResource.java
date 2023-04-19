package com.mega.hephaestus.pms.nuc.workflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.Resources;
import com.mega.component.workflow.models.Task;
import com.mega.component.workflow.models.TaskId;
import com.mega.component.workflow.serialization.JsonSerializerMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class TaskResource {

    private final String json;

    private int taskSize;

    private Task task;

    public static TaskResource createWithFileName(String resourceName) throws IOException {
        String jsonString = Resources.toString(Resources.getResource(resourceName), Charset.defaultCharset());
        return new TaskResource(jsonString);
    }

    public TaskResource(String json) {
        this.json = json;
        parseResource();
    }

    private void parseResource() {
        try {
            JsonSerializerMapper jsonSerializerMapper = new JsonSerializerMapper();
            JsonNode jsonNode = jsonSerializerMapper.getMapper().readTree(json);

            JsonNode tasks = jsonNode.get("tasks");
            taskSize = tasks.size();
            task = jsonSerializerMapper.get(jsonNode, Task.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTaskSize() {
        return taskSize;
    }

    public Task getTask() {
        return task;
    }


    public Task getTask(Task task1, TaskId taskId) {
        return task1.getChildrenTasks().stream()
                .filter(v -> v.getTaskId().equals(taskId))
                .findFirst()
                .orElseGet(() ->
                    task1.getChildrenTasks().stream()
                        .map(v -> getTask(v, taskId))
                        .findFirst()
                        .orElse(null)
                );
    }

    @Override
    public String toString() {
        return "TaskResource{" +
                "json=" + json +
                ", taskSize=" + taskSize +
                ", task=" + task +
                '}';
    }
}
