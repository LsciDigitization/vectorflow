package com.mega.component.commons.lang.reflect;

import com.mega.component.commons.DefaultRuntimeException;

/**
 * 反射时出现的异常.
 *
 * @since 1.0.7
 */
public final class ReflectException extends DefaultRuntimeException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -1699987643831455524L;

    /**
     * Instantiates a new reflect exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new reflect exception.
     *
     * @param cause the cause
     */
    public ReflectException(Throwable cause) {
        super(cause);
    }
}