package com.mega.hephaestus.pms.workflow.task.stagetask;

import com.mega.component.bioflow.task.InstanceId;
import com.mega.component.bioflow.task.StageTaskEntity;
import com.mega.component.task.executor.SynchronousExecutor;
import com.mega.component.task.task.DefaultTaskListener;
import com.mega.component.task.task.TaskListener;
import com.mega.component.task.task.TaskManager;
import com.mega.hephaestus.pms.workflow.task.tasklog.StageTaskLogger;

import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/22 15:46
 */
public class DynamicStageRunner {
    private final InstanceId instanceId;
    private final TaskManager taskManager;
    private final SynchronousExecutor executor;
    private final TaskListener listener;
    private final StageTaskLogger stageTaskLogger;
    private final List<StageTaskEntity> tasks;
    private final DynamicStageRunnerTaskResource runnerTaskResource;

    public DynamicStageRunner(InstanceId instanceId,
                              List<StageTaskEntity> tasks,
                              DynamicStageRunnerTaskResource runnerTaskResource) {
        this.instanceId = instanceId;
        this.tasks = tasks;
        this.runnerTaskResource = runnerTaskResource;

        int capacity = tasks.size();
        this.executor = new SynchronousExecutor(18000, capacity);

        this.stageTaskLogger = new StageTaskLogger(instanceId.getId());
        this.listener = new DefaultTaskListener(stageTaskLogger);
        this.taskManager = TaskManager.getInstance();
    }

    public DynamicStageRunner build() {
        tasks.forEach(v -> {
            DynamicStageRunnerTask runnerTask = new DynamicStageRunnerTask(instanceId, v, stageTaskLogger, runnerTaskResource);
            taskManager.addTask(runnerTask, executor, listener);
        });
        return this;
    }

    public void running() {
        executor.start();
    }

}
