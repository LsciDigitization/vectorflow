package com.mega.hephaestus.pms.workflow.exception;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/3 19:35
 */
public class TransferException extends RuntimeException {
    public TransferException() {
    }

    public TransferException(String message) {
        super(message);
    }

    public TransferException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferException(Throwable cause) {
        super(cause);
    }

    public TransferException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
