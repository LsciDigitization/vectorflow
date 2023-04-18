package com.mega.component.commons.bean;

import static com.mega.component.commons.DatePattern.COMMON_DATE_AND_TIME;
import static com.mega.component.commons.bean.ConvertUtil.convert;
import static com.mega.component.commons.bean.ToStringConfig.DEFAULT_CONNECTOR;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.collections4.Transformer;

import com.mega.component.commons.lang.ClassUtil;
import com.mega.component.commons.util.transformer.DateToStringTransformer;
import com.mega.component.commons.util.transformer.NumberToStringTransformer;

/**
 * 处理值转换.
 *
 * @since 1.14.0
 */
public final class ToStringHandler {

    /**
     * 默认的 日期转字符串转换器.
     */
    private static final Transformer<Date, String> DEFAULT_DATETOSTRING_TRANSFORMER = new DateToStringTransformer(COMMON_DATE_AND_TIME);

    /**
     * 默认的 数字转字符串转换器.
     */
    private static final Transformer<Number, String> DEFAULT_NUMBERTOSTRING_TRANSFORMER = new NumberToStringTransformer(
            com.mega.component.commons.NumberPattern.TWO_DECIMAL_POINTS);

    //---------------------------------------------------------------

    /**
     * Don't let anyone instantiate this class.
     */
    private ToStringHandler() {
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * To string value.
     *
     * @param toBeConvertedValue the value
     * @return 如果 <code>toBeConvertedValue</code> 是null,返回 null<br>
     * 如果 <code>toBeConvertedValue</code> 是 {@link CharSequence},直接 toString返回<br>
     * 如果 <code>toBeConvertedValue</code> 是 数组,那么调用 {@link ConvertUtil#toString(Object[], String)}<br>
     * 如果 <code>toBeConvertedValue</code> 是 {@link Collection},那么调用 {@link ConvertUtil#toString(Object[], String)}<br>
     * 如果 <code>toBeConvertedValue</code> 是 {@link Date},那么返回 {@link com.mega.component.commons.DatePattern#COMMON_DATE_AND_TIME} 格式字符串<br>
     * 如果 <code>toBeConvertedValue</code> 是 {@link BigDecimal}或者是{@link Float}或者是 {@link Double},那么返回
     * {@link com.mega.component.commons.NumberPattern#TWO_DECIMAL_POINTS} 2 位小数点格式字符串<br>
     * 其他调用 {@link com.mega.component.commons.bean.ConvertUtil#convert(Object, Class)}
     */
    static String toStringValue(Object toBeConvertedValue) {
        if (null == toBeConvertedValue) {
            return null;
        }

        //---------------------------------------------------------------
        //CharSequence
        //since 1.14.0
        if (ClassUtil.isInstance(toBeConvertedValue, CharSequence.class)) {
            return ((CharSequence) toBeConvertedValue).toString();
        }

        //---------------------------------------------------------------

        //数组
        //since 1.14.0
        if (com.mega.component.commons.lang.ObjectUtil.isArray(toBeConvertedValue)) {
            return ConvertUtil.toString((Object[]) toBeConvertedValue, DEFAULT_CONNECTOR);
        }

        //集合
        //since 1.14.0
        if (ClassUtil.isInstance(toBeConvertedValue, Collection.class)) {
            return ConvertUtil.toString((Collection<?>) toBeConvertedValue, DEFAULT_CONNECTOR);
        }

        //---------------------------------------------------------------
        //日期
        if (ClassUtil.isInstance(toBeConvertedValue, Date.class)) {
            return DEFAULT_DATETOSTRING_TRANSFORMER.transform((Date) toBeConvertedValue);
        }
        //Calendar
        if (ClassUtil.isInstance(toBeConvertedValue, Calendar.class)) {
            return DEFAULT_DATETOSTRING_TRANSFORMER.transform(((Calendar) toBeConvertedValue).getTime());
        }

        //---------------------------------------------------------------
        //数字
        if (ClassUtil.isInstanceAnyClass(toBeConvertedValue, BigDecimal.class, Double.class, Float.class)) {
            return DEFAULT_NUMBERTOSTRING_TRANSFORMER.transform((Number) toBeConvertedValue);
        }

        //---------------------------------------------------------------
        return convert(toBeConvertedValue, String.class);
    }
}
