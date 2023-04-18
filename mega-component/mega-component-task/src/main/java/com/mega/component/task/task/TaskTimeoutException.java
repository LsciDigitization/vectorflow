package com.mega.component.task.task;

public class TaskTimeoutException extends RuntimeException {

    public TaskTimeoutException() {
    }

    public TaskTimeoutException(String message) {
        super(message);
    }

    public TaskTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskTimeoutException(Throwable cause) {
        super(cause);
    }

    public TaskTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
