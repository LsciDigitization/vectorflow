package com.mega.hephaestus.pms.workflow.exception;

public class DeviceTaskException extends RuntimeException {
    public DeviceTaskException() {
    }

    public DeviceTaskException(String message) {
        super(message);
    }

    public DeviceTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceTaskException(Throwable cause) {
        super(cause);
    }

    public DeviceTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
