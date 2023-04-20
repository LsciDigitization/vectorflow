package com.mega.hephaestus.pms.workflow.task.taskbuild;

import com.mega.component.workflow.models.TaskType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ToString
public class Task {

    private String taskId;

    private List<String> childrenTaskIds = new ArrayList<>();

    private Map<String, String> metaData = new HashMap<>();

    private TaskType taskType;

    public void addChildrenTaskId(String taskId) {
        childrenTaskIds.add(taskId);
    }

    @Data
    @ToString
    @Builder
    public static class RootTask {
        private String rootTaskId;
        private List<Task> tasks;
    }

}
