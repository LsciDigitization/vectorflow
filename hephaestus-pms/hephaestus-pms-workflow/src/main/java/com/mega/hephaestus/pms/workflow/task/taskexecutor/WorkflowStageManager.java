package com.mega.hephaestus.pms.workflow.task.taskexecutor;

import com.mega.component.workflow.WorkflowManager;
import com.mega.component.workflow.WorkflowManagerBuilder;
import com.mega.component.workflow.admin.StandardAutoCleaner;
import com.mega.component.workflow.models.RunId;
import com.mega.component.workflow.models.Task;
import com.mega.component.workflow.models.TaskId;
import com.mega.component.workflow.models.TaskType;
import com.mega.component.nuc.timing.Timing;
import com.mega.hephaestus.pms.nuc.workflow.TaskResource;
import com.mega.hephaestus.pms.workflow.task.tasklog.TaskLoggerService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.function.Consumer;

@Component
@Deprecated(since = "20221018")
public class WorkflowStageManager {

    @Autowired(required = false)
    private CuratorFramework curator;

    @Autowired
    private DynamicStageTaskExecutorResource stageTaskExecutorResource;

    @Autowired
    private StartTaskExecutorResource startTaskExecutorResource;

    @Autowired
    private EndTaskExecutorResource endTaskExecutorResource;

    @Resource
    private TaskType stageTaskType;
    @Resource
    private TaskType startTaskType;
    @Resource
    private TaskType endTaskType;
    @Resource
    private TaskLoggerService stageLoggerService;

    protected final Timing timing = new Timing();

    public void start(TaskResource taskResource) {
        start(taskResource, null, null);
    }

    public void start(TaskResource taskResource, Consumer<RunId> consumer) {
        start(taskResource, null, consumer);
    }

    public void start(TaskResource taskResource, String taskId, Consumer<RunId> consumer) {
        DynamicStageTaskExecutor dynamicStageTaskExecutor = new DynamicStageTaskExecutor(taskResource.getTaskSize(), stageTaskExecutorResource, stageLoggerService);
        StartTaskExecutor startTaskExecutor = new StartTaskExecutor(1, startTaskExecutorResource, stageLoggerService);
        EndTaskExecutor endTaskExecutor = new EndTaskExecutor(1, endTaskExecutorResource, stageLoggerService);

        WorkflowManager workflowManager = WorkflowManagerBuilder.builder()
                .addingTaskExecutor(dynamicStageTaskExecutor, taskResource.getTaskSize() + 10, stageTaskType)
                .addingTaskExecutor(startTaskExecutor, 1, startTaskType)
                .addingTaskExecutor(endTaskExecutor, 1, endTaskType)
                .withCurator(curator, "stage", "1")
                .withInstanceName("hephaestus")
                .withAutoCleaner(new StandardAutoCleaner(Duration.ofSeconds(2)), Duration.ofSeconds(1))
                .build();

        try {
            workflowManager.start();

            Task task;
            if (taskId == null) {
                // 从头开始执行任务
                task = taskResource.getTask();
            } else {
                // 指定从哪个任务开始执行
                task = getStageTask(taskResource, new TaskId(taskId));
            }

            RunId runId = workflowManager.submitTask(task);

            if (consumer != null) {
                consumer.accept(runId);
            }

//            timing.awaitLatch(dynamicStageTaskExecutor.getLatch());

        } finally {
            CloseableUtils.closeQuietly(workflowManager);
        }
    }

    /**
     * 获取指定的任务
     * @param taskResource TaskResource
     * @param taskId TaskId
     * @return Task
     */
    public Task getStageTask(TaskResource taskResource, TaskId taskId) {
        Task task = taskResource.getTask();
        return taskResource.getTask(task, taskId);
    }

}
