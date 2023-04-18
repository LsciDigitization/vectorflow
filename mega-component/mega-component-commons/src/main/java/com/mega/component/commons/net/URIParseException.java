package com.mega.component.commons.net;

import com.mega.component.commons.DefaultRuntimeException;

/**
 * 解析url/uri出现异常.
 *
 * @since 1.0.8
 */
public final class URIParseException extends DefaultRuntimeException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -1699987643831455524L;

    //---------------------------------------------------------------

    /**
     * The Constructor.
     *
     * @param message the message
     * @param cause   the cause
     */
    public URIParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
