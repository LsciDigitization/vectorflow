package com.mega.component.commons.util.transformer;

import java.io.Serializable;

import org.apache.commons.collections4.Transformer;

import com.mega.component.commons.NumberPattern;
import com.mega.component.commons.lang.NumberUtil;
import com.mega.component.commons.Validate;

/**
 * 数字转成字符串的转换器.
 *
 * @since 1.10.7
 */
public class NumberToStringTransformer implements Transformer<Number, String>, Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -8811898394834078637L;

    /**
     * 模式,可以使用 {@link NumberPattern}.
     */
    private final String pattern;

    //---------------------------------------------------------------

    /**
     * Instantiates a new number to string transformer.
     *
     * <p>
     * 如果 <code>pattern</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>pattern</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * </p>
     *
     * @param pattern 模式,可以使用 {@link NumberPattern}
     */
    public NumberToStringTransformer(String pattern) {
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
    public String transform(Number value) {
        return null == value ? null : NumberUtil.toString(value, pattern);
    }
}
