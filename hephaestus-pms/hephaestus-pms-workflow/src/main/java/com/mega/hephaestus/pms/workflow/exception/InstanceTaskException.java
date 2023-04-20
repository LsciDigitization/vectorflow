package com.mega.hephaestus.pms.workflow.exception;

public class InstanceTaskException extends RuntimeException {
    public InstanceTaskException() {
    }

    public InstanceTaskException(String message) {
        super(message);
    }

    public InstanceTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceTaskException(Throwable cause) {
        super(cause);
    }

    public InstanceTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
