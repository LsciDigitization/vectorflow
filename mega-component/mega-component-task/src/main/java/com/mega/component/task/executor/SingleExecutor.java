package com.mega.component.task.executor;

import com.mega.component.task.Task;
import com.mega.component.task.task.TaskManager;
import com.mega.component.task.task.TaskStatus;

/**
 * @author toto
 * 串行执行器，所有任务串行执行
 */
public class SingleExecutor extends TaskExecutor<Task> {

    public SingleExecutor() {
        super(defaultAliveTime, defaultCapacity);
    }

    public SingleExecutor(int keepAliveTime, int capacity) {
        super(keepAliveTime, capacity);
    }

    @Override
    public synchronized void execute(Task task) {
        TaskManager.getInstance().update(new TaskStatus(task.getTaskId(), 50));
        task.runTask();
        finished(task);
    }

    @Override
    public void closeExecutor() {
        // TODO Auto-generated method stub
        dispatcher.getLogger().info( "SingleExecutor close executor.");
    }
}