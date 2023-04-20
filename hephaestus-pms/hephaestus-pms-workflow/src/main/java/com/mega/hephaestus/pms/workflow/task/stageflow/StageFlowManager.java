package com.mega.hephaestus.pms.workflow.task.stageflow;

import com.mega.component.workflow.executor.TaskExecutionStatus;
import com.mega.component.workflow.models.*;
import com.mega.component.nuc.exceptions.StageException;
import com.mega.hephaestus.pms.nuc.workflow.TaskResource;
import com.mega.hephaestus.pms.workflow.task.tasktype.TaskTypePool;
import com.mega.hephaestus.pms.workflow.task.tasktype.TaskTypeRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Slf4j
@Component
public class StageFlowManager {

    @Resource
    private TaskTypePool taskTypePool;

    private static final TaskType nullTaskType = new TaskType("", "", false);

    public void start(TaskResource taskResource) {
        start(taskResource, null, null);
    }

    public void start(TaskResource taskResource, Consumer<RunId> consumer) {
        start(taskResource, null, consumer);
    }

    public void start(TaskResource taskResource, String taskId, Consumer<RunId> consumer) {
        // 获取任务
        Task task;
        if (taskId == null) {
            // 从头开始执行任务
            task = taskResource.getTask();
        } else {
            // 指定从哪个任务开始执行
            task = getStageTask(taskResource, new TaskId(taskId));
        }

        RunId runId = new RunId();
        if (consumer != null) {
            consumer.accept(runId);
        }

        // 执行任务
        runTask(runId, task);
    }

    private void runTask(RunId runId, Task task) {
        ExecutableTask executableTask = new ExecutableTask(runId, task.getTaskId(), task.isExecutable() ? task.getTaskType() : nullTaskType, task.getMetaData(), task.isExecutable());

        AtomicReference<TaskExecutionResult> taskExecutionResultReference = new AtomicReference<>();

        if (taskTypePool.has(task.getTaskType().getType())) {
            Optional<TaskTypeRegister> taskTypeRegisterOptional = taskTypePool.get(task.getTaskType().getType());
            taskTypeRegisterOptional.ifPresent(v -> {
                try {
                    log.info("执行Stage类型 {} 任务 {}", v.getTaskExecutor().getClass(), executableTask.getTaskId());
                    TaskExecutionResult taskExecutionResult = v.getTaskExecutor().doRun(executableTask);
                    taskExecutionResultReference.set(taskExecutionResult);
                } catch (InterruptedException e) {
                    log.error("taskExecutionResult InterruptedException {}", e.toString());
                    throw new RuntimeException(e);
                }
            });
        } else {
            log.error("任务类型未找到 {}", task.getTaskType());
            TaskExecutionResult taskExecutionResult = new TaskExecutionResult(TaskExecutionStatus.FAILED_STOP, "任务执行失败");
            taskExecutionResultReference.set(taskExecutionResult);
        }


//        taskTypePool.all().forEach((k, v) -> {
//            log.info("task {}", task.getTaskType().getType());
//            log.info("task-v {}", v.getTaskType().getType());
//            if (task.getTaskType().getType().equals(v.getTaskType().getType())) {
//
//            } else {
//
//            }
//        });

        // 未匹配到补偿

//        if (Objects.isNull(taskExecutionResult)) {
//
//        }

        TaskExecutionResult taskExecutionResult = taskExecutionResultReference.get();
        if (taskExecutionResult.getStatus() != TaskExecutionStatus.SUCCESS) {
            log.info(taskExecutionResult.getMessage());
            throw new StageException(taskExecutionResult.getMessage());
        }

        // 运行子任务
        List<Task> childrenTasks = task.getChildrenTasks();
        int size = childrenTasks.size();
        if (size == 1) {
            Task subTask = childrenTasks.get(0);
            runTask(runId, subTask);
        }
    }

    /**
     * 获取指定的任务
     *
     * @param taskResource TaskResource
     * @param taskId       TaskId
     * @return Task
     */
    private Task getStageTask(TaskResource taskResource, TaskId taskId) {
        Task task = taskResource.getTask();
        return taskResource.getTask(task, taskId);
    }

}
