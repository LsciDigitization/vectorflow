package com.mega.component.nuc.exceptions;

public class StageException extends RuntimeException {
    public StageException() {
    }

    public StageException(String message) {
        super(message);
    }

    public StageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StageException(Throwable cause) {
        super(cause);
    }

    public StageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
