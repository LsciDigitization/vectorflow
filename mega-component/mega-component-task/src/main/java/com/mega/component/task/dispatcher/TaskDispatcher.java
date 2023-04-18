package com.mega.component.task.dispatcher;

import com.mega.component.task.Task;
import com.mega.component.task.executor.TaskExecutor;

public class TaskDispatcher<T extends Task> extends Dispatcher<T> {
    private final TaskExecutor<T> taskExecutor;

    public TaskDispatcher(TaskExecutor<T> taskExecutor, int keepAliveTime, int capacity) {
        super(keepAliveTime, capacity);
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void dispatch(T task) {
        if (task.isInterrupted()) {
            logger.info("Task is interrupted by dispatcher,ID: " + task.getTaskId());
            return;
        }
        taskExecutor.execute(task);
    }

    //自动关闭执行器时系统调用
    @Override
    public void shutdownExecutor() {
        taskExecutor.closeExecutor();
    }
}
