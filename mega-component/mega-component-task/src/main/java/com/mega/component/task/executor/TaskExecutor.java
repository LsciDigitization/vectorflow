package com.mega.component.task.executor;

import com.mega.component.task.Dispatch;
import com.mega.component.task.Executor;
import com.mega.component.task.Task;
import com.mega.component.task.dispatcher.TaskDispatcher;
import com.mega.component.task.task.TaskManager;
import com.mega.component.task.task.TaskStatus;

/**
 * @author toto
 * 执行器抽象父类，内部持有一个调度器
 */
//将任务是否中断，放到调度器中去判断，中断则不进行执行。
public abstract class TaskExecutor<T extends Task> implements Executor<T> {
    protected Dispatch<T> dispatcher;

    public TaskExecutor(int keepAliveTime, int capacity) {
        dispatcher = new TaskDispatcher<>(this, keepAliveTime, capacity);
    }

    @Override
    public void add(T t) {
        dispatcher.add(t);
    }

    @Override
    public void remove(T t) {
        dispatcher.remove(t);
    }

    public abstract void closeExecutor();

    //主动关闭调度器和执行器方法
    @Override
    public void close() {
        //调度器关闭时，会级联关闭相关执行器
        dispatcher.shutdown();
    }

    @Override
    public void finished(T t) {
        t.taskFinished();
        TaskManager.getInstance().update(new TaskStatus(t.getTaskId(), TaskStatus.S_SUCCEED));
    }

}
