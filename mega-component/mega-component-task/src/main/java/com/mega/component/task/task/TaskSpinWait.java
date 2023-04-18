package com.mega.component.task.task;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 任务自旋等待
 */
public class TaskSpinWait {

    // 等待间隔时间，单位秒
    private int sleepSeconds = 5;

    private int timeoutSeconds = 600;

    private static final Timer timer = new Timer();

    private volatile boolean isStopWait;

    public TaskSpinWait() {
    }

    public TaskSpinWait(int sleepSeconds, int timeoutSeconds) {
        this.sleepSeconds = sleepSeconds;
        this.timeoutSeconds = timeoutSeconds;
    }

    /**
     * 默认600s超时，每5秒休眠一次
     * @return TaskSpinWait
     */
    public static TaskSpinWait of() {
        return new TaskSpinWait();
    }

    /**
     * 默认最大超时，每5秒休眠一次
     * @return TaskSpinWait
     */
    public static TaskSpinWait ofMax() {
        return new TaskSpinWait(5, Integer.MAX_VALUE);
    }

    public static TaskSpinWait of(int sleepSeconds, int timeoutSeconds) {
        return new TaskSpinWait(sleepSeconds, timeoutSeconds);
    }

    public void stop() {
        this.isStopWait = true;
    }

    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stop();
//                timer.cancel();
            }
        },  timeoutSeconds * 1000L);
    }

    /**
     * 无异常等待
     * @param callable 回调执行
     */
    public void wait(Callable<Boolean> callable) {
        startTimer();

        while (! isStopWait) {
            try {
                if (callable.call()) {
                    break;
                }
                TimeUnit.SECONDS.sleep(sleepSeconds);
            } catch (InterruptedException e) {
                // ignore
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        // 正常返回
    }

    /**
     * 有异常返回
     * @param callable 回调执行
     */
    public void waitWithThrow(Callable<Boolean> callable) {
        startTimer();

        while (! isStopWait) {
            try {
                if (callable.call()) {
                    break;
                }
                TimeUnit.SECONDS.sleep(sleepSeconds);
            } catch (InterruptedException e) {
                // ignore
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        if (isStopWait) {
            throw new TaskTimeoutException(String.format("任务等待超时%s秒", timeoutSeconds));
        }
    }

    public void wait(Callable<Boolean> callable, int sleep) {
        startTimer();

        while (! isStopWait) {
            try {
                if (callable.call()) {
                    break;
                }
                TimeUnit.SECONDS.sleep(sleep);
            } catch (InterruptedException e) {
                // ignore
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

    }

    public void waitWithThrow(Callable<Boolean> callable, int sleep) {
        startTimer();

        while (! isStopWait) {
            try {
                if (callable.call()) {
                    break;
                }
                TimeUnit.SECONDS.sleep(sleep);
            } catch (InterruptedException e) {
                // ignore
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        if (isStopWait) {
            throw new TaskTimeoutException(String.format("任务等待超时%s秒", timeoutSeconds));
        }
    }

}
