package com.mega.component.commons.util.transformer;

import static com.mega.component.commons.bean.ConvertUtil.convert;

import java.io.Serializable;

import org.apache.commons.collections4.Transformer;

import com.mega.component.commons.bean.ConvertUtil;
import com.mega.component.commons.Validate;

/**
 * 简单的将对象转成指定 <code>targetType</code> 类型的转换器.
 *
 * @param <T> 原来的类型
 * @param <V> 转成的结果类型
 * @see ConvertUtil#convert(Object, Class)
 * @since 1.9.2
 */
public class SimpleClassTransformer<T, V> implements Transformer<T, V>, Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 809439581555072949L;

    /**
     * 需要被转成什么目标类型.
     */
    private final Class<V> targetType;

    //---------------------------------------------------------------

    /**
     * Instantiates a new convert transformer.
     *
     * <p>
     * 如果 <code>targetType</code> 是null,抛出 {@link NullPointerException}<br>
     * </p>
     *
     * @param targetType 需要被转成什么目标类型
     */
    public SimpleClassTransformer(Class<V> targetType) {
        Validate.notNull(targetType, "targetType can't be null!");
        this.targetType = targetType;
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     *
     * @see org.apache.commons.collections4.Transformer#transform(java.lang.Object)
     */
    @Override
    public V transform(final T input) {
        return convert(input, targetType);
    }
}