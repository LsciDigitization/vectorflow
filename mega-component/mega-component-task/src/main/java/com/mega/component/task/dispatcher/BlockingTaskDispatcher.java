package com.mega.component.task.dispatcher;

import com.mega.component.task.Task;
import com.mega.component.task.executor.TaskExecutor;
import com.mega.component.task.task.TaskLogger;
import com.mega.component.task.task.TaskManager;
import com.mega.component.task.task.TaskStatus;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingTaskDispatcher<T extends Task> extends Dispatcher<T> {
    private final TaskExecutor<T> taskExecutor;

    private int keepAliveTime;

    public BlockingTaskDispatcher(TaskExecutor<T> taskExecutor, int keepAliveTime, int capacity) {
        super(keepAliveTime, capacity);
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void setLogger(TaskLogger taskLogger) {
        this.logger = taskLogger;
    }

    @Override
    protected void init(int keepAliveTime, int capacity) {
        this.queue = new LinkedBlockingQueue<T>(capacity);
        this.keepAliveTime = keepAliveTime;
    }

    public void start() {
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                shutdown();
                timer.cancel();
            }
        }, keepAliveTime * 1000L);

        // Running...
        run();
    }

    protected void run() {
        while (!isShutdown.get() && queue.size() > 0) {
            T t = getTask();
            if (null == t) {
                continue;
            }
            TaskManager.getInstance().update(new TaskStatus(t.getTaskId(), 20));
            dispatch(t);
        }
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
