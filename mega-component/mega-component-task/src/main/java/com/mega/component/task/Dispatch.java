package com.mega.component.task;

import com.mega.component.task.task.SystemOutLogging;
import com.mega.component.task.task.TaskLogger;

public interface Dispatch<T> {

    /**
     * 默认调度器调度能力，超过能力的部分，会被阻塞，等待调度
     */
    int defaultCapacity = 1 * 1000;

    /**
     * 默认调度器生命周期（秒），超过生命周期，调度器自动关闭
     */
    int defaultAliveTime = 20;

    /**
     * 添加待调度T
     */
    void add(T t);

    /**
     * 移除待调度T
     */
    void remove(T t);

    /**
     * 启动任务，按需实现
     */
    default void start() {}

    /**
     * 调度
     */
    void dispatch(T t);

    /**
     * 调度器关闭接口，抽象实现Dispatcher中，会级联关闭相关执行器
     */
    void shutdown();

    /**
     * 日志打印抽象
     * @return TaskLogger
     */
    default TaskLogger getLogger() {
        return new SystemOutLogging();
    }

    default void setLogger(TaskLogger logger) {
    }

}
