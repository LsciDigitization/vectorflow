package com.mega.component.task.dispatcher;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.mega.component.task.Dispatch;
import com.mega.component.task.Task;
import com.mega.component.task.task.SystemOutLogging;
import com.mega.component.task.task.TaskLogger;
import com.mega.component.task.task.TaskManager;
import com.mega.component.task.task.TaskStatus;

public abstract class Dispatcher<T extends Task> implements Dispatch<T> {
    protected LinkedBlockingQueue<T> queue;
    protected final AtomicBoolean isShutdown = new AtomicBoolean(false);
    private Thread dispatcher;
    private final static String DispatcherName = "Task dispatcher thread";
    protected Timer timer;
    protected TaskLogger logger;

    public Dispatcher(int keepAliveTime, int capacity) {
        init(keepAliveTime, capacity);
        logger = new SystemOutLogging();
    }

    public TaskLogger getLogger() {
        return logger;
    }

    protected void init(int keepAliveTime, int capacity) {
        this.queue = new LinkedBlockingQueue<T>(capacity);

        this.dispatcher = new Thread(DispatcherName) {
            @Override
            public void run() {
                while (!isShutdown.get()) {
                    T t = getTask();
                    if (null == t) {
                        continue;
                    }
                    TaskManager.getInstance().update(new TaskStatus(t.getTaskId(), 20));
                    dispatch(t);
                }
            }
        };

        this.dispatcher.start();

        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                shutdown();
                timer.cancel();
            }
        }, keepAliveTime * 1000L);
    }

    protected T getTask() {
        T task = null;
        try {
            task = queue.poll(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            logger.info(DispatcherName + " by interrupted.");
        }
        return task;
    }

    public abstract void dispatch(T t);

    @Override
    public void add(T t) {
        this.queue.add(t);
    }

    @Override
    public void remove(T t) {
        boolean remove = this.queue.remove(t);
    }

    //关闭执行器的方法，调度器自动关闭时系统调用
    public abstract void shutdownExecutor();

    @Override
    public void shutdown() {
        this.isShutdown.set(true);
        shutdownExecutor();
        logger.info(DispatcherName + " shut down.");
    }
}
