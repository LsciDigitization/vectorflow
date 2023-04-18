package com.mega.component.task.executor;

import java.util.Timer;
import java.util.TimerTask;

import com.mega.component.task.Task;

/**
 * 定时/延时执行器，到达指定时间后，所有任务并发执行。
 */
public class TimerExecutor extends TaskExecutor<Task> {
    private long delaySeconds;
    private Timer timer;

    public TimerExecutor(long delaySeconds) {
        super(defaultAliveTime, defaultCapacity);
        Init(delaySeconds);
    }

    public TimerExecutor(long delaySeconds, int keepAliveTime, int capacity) {
        super(keepAliveTime, capacity);
        Init(delaySeconds);
    }

    private void Init(long delaySeconds) {
        this.delaySeconds = delaySeconds;
        this.timer = new Timer();
    }

    @Override
    public void execute(final Task task) {
        if (task.isInterrupted()) {
            dispatcher.getLogger().info("Task is interrupted,ID: " + task.getTaskId());
            return;
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.runTask();
                finished(task);
            }
        }, delaySeconds * 1000);
    }

    @Override
    public void closeExecutor() {
        // TODO Auto-generated method stub
        timer.cancel();
        dispatcher.getLogger().info("TimerExecutor shut down.");
    }
}