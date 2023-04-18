package com.mega.component.task.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mega.component.task.Task;
import com.mega.component.task.task.TaskManager;
import com.mega.component.task.task.TaskStatus;

/**
 * @author toto
 * 并行执行器，指定并行数，然后按照指定并行数，并发执行
 */
public class ConcurrentExecutor extends TaskExecutor<Task> {

    private ExecutorService threadPool;
    private final static String ConcurrentExecutor = "Concurrent Executor";

    public ConcurrentExecutor(int maxParallelNumber, int keepAliveTime, int capacity) {
        super(keepAliveTime, capacity);
        init(maxParallelNumber);
    }

    public ConcurrentExecutor(int maxParallelNumber) {
        super(defaultAliveTime, defaultCapacity);
        init(maxParallelNumber);
    }

    private void init(int maxParallelNumber) {
        this.threadPool = Executors.newFixedThreadPool(maxParallelNumber, new ThreadFactory(ConcurrentExecutor));
        ((ThreadPoolExecutor) this.threadPool).setKeepAliveTime(10, TimeUnit.SECONDS);
        ((ThreadPoolExecutor) this.threadPool).allowCoreThreadTimeOut(true); // 设置允许超时回收
    }

    @Override
    public void execute(final Task task) {
        if (task.isInterrupted()) {
            dispatcher.getLogger().info("Task is interrupted by executor,ID: " + task.getTaskId());
            return;
        }
        TaskManager.getInstance().update(new TaskStatus(task.getTaskId(), 50));
        threadPool.submit(new Runnable() {
            public void run() {
                task.runTask();
                finished(task);
            }
        });
    }

    @Override
    public void closeExecutor() {
        threadPool.shutdown();
        dispatcher.getLogger().info(ConcurrentExecutor + " shut down.");
    }
}
