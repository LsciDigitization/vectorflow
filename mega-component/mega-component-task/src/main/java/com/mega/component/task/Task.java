package com.mega.component.task;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import com.mega.component.task.task.TaskManager;
import com.mega.component.task.task.TaskStatus;

public abstract class Task {
    private String taskName = "";
    private final AtomicBoolean isInterrupted = new AtomicBoolean(false);
    private long taskId;
    private TimerTask timerTask = null;

    protected Task(String taskName) {
        this.taskName = taskName;
        init();
    }

    protected Task(String taskName, long taskId) {
        this.taskName = taskName;
        this.taskId = taskId;
    }

    protected Task() {
        init();
    }

    protected void init() {
        this.taskId = TaskManager.applyTaskId();
    }

    public void createTimerTask() {
        this.timerTask = new TimerTask() {
            public void run() {
                try {
                    update(TaskStatus.S_TIMEOUT, 100, "timeout");
                    taskFinished();
                    timeOutAction();
                    TaskManager.getInstance().removeTask(taskId);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        };
    }


    public void update(int status, int progress) {
        getTaskStatus().update(status, progress);
    }

    public void update(int status, int progress, String desc) {
        getTaskStatus().update(status, progress, desc);
    }

    public TaskStatus getTaskStatus() {
        TaskStatus ts = TaskManager.getInstance().getTaskStatus(taskId);
        if (ts == null) {
            ts = new TaskStatus(taskId, TaskStatus.S_READY);
            TaskManager.getInstance().update(ts);
        }
        return ts;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isInterrupted() {
        return isInterrupted.get();
    }

    /**
     * 尝试中断任务，任务开始执行后，中断会失败
     */
    public void interrupt() {
        isInterrupted.set(true);
    }

    public long getTaskId() {
        return taskId;
    }


    /**
     * 任务执行内容
     */
    abstract public void runTask();

    /**
     * 任务超时处理
     */
    abstract public void timeOutAction();

    /**
     * 任务结束时，后置处理
     */
    abstract public void taskFinished();
}
