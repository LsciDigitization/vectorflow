package com.mega.component.commons.util.transformer;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.collections4.Transformer;

import com.mega.component.commons.DatePattern;
import com.mega.component.commons.date.DateUtil;
import com.mega.component.commons.Validate;

/**
 * 日期转成字符串的转换器.
 *
 * @since 1.10.7
 */
public class DateToStringTransformer implements Transformer<Date, String>, Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -4826259468102686057L;

    /**
     * 模式,可以使用 {@link DatePattern}.
     */
    private final String pattern;

    //---------------------------------------------------------------

    /**
     * Instantiates a new date to string transformer.
     *
     * <p>
     * 如果 <code>pattern</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>pattern</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * </p>
     *
     * @param pattern 模式,可以使用 {@link DatePattern}
     */
    public DateToStringTransformer(String pattern) {
        Validate.notBlank(pattern, "pattern can't be blank!");
        this.pattern = pattern;
    }

    //---------------------------------------------------------------
    /*
     * (non-Javadoc)
     *
     * @see org.apache.commons.collections4.Transformer#transform(java.lang.Object)
     */
    @Override
    public String transform(Date value) {
        return null == value ? null : DateUtil.toString(value, pattern);
    }
}
