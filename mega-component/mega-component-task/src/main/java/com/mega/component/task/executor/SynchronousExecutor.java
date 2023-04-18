package com.mega.component.task.executor;

import com.mega.component.task.Task;
import com.mega.component.task.dispatcher.BlockingTaskDispatcher;
import com.mega.component.task.task.TaskLogger;
import com.mega.component.task.task.TaskManager;
import com.mega.component.task.task.TaskStatus;

public class SynchronousExecutor extends TaskExecutor<Task>  {

    public SynchronousExecutor() {
        super(defaultAliveTime, defaultCapacity);
        dispatcher = new BlockingTaskDispatcher<>(this, defaultAliveTime, defaultCapacity);
    }

    public SynchronousExecutor(int keepAliveTime, int capacity) {
        super(keepAliveTime, capacity);
        dispatcher = new BlockingTaskDispatcher<>(this, keepAliveTime, capacity);
    }

    public void setLogger(TaskLogger taskLogger) {
        this.dispatcher.setLogger(taskLogger);
    }

    @Override
    public synchronized void execute(Task task) {
        TaskManager.getInstance().update(new TaskStatus(task.getTaskId(), 50));
        task.runTask();
        finished(task);
    }

    public void start() {
        dispatcher.start();
    }

    @Override
    public void closeExecutor() {
        // TODO Auto-generated method stub
        dispatcher.getLogger().info( "SynchronousExecutor close executor.");
    }

}
