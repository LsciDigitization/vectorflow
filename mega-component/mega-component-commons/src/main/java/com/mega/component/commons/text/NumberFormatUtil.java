package com.mega.component.commons.text;

import static com.mega.component.commons.lang.ObjectUtil.defaultIfNull;
import static java.math.RoundingMode.HALF_UP;

import java.math.RoundingMode;
import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mega.component.commons.NumberPattern;
import com.mega.component.commons.Validate;

/**
 * {@link NumberFormat}是所有数值格式的抽象基类,此类提供格式化和解析数值的接口.
 *
 * <p>
 * 直接已知子类: {@link ChoiceFormat}, {@link DecimalFormat}.<br>
 * 注意:<span style="color:red">{@link DecimalFormat}不是同步的 </span>,建议为每个线程创建独立的格式实例.(见JAVA API 文档)
 * </p>
 *
 * @see Format
 * @see NumberFormat
 * @see DecimalFormat
 * @see NumberPattern
 * @since 1.0.2
 */
public final class NumberFormatUtil {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberFormatUtil.class);

    /**
     * Don't let anyone instantiate this class.
     */
    private NumberFormatUtil() {
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 将 {@link Number} 使用 {@link RoundingMode} <code>numberPattern</code>格式化.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * NumberFormatUtil.format(toBigDecimal(1.15), "#####.#",null)     =   1.2
     * NumberFormatUtil.format(toBigDecimal(1.25), "#####.#",null)     =   1.3
     * NumberFormatUtil.format(toBigDecimal(1.251), "#####.#",null)    =   1.3
     *
     * NumberFormatUtil.format(toBigDecimal(-1.15), "#####.#",null)    =   -1.2
     * NumberFormatUtil.format(toBigDecimal(-1.25), "#####.#",null)    =   -1.3
     * NumberFormatUtil.format(toBigDecimal(-1.251), "#####.#",null)   =   -1.3
     *
     *
     * NumberFormatUtil.format(toBigDecimal(1.15), "#####.#", RoundingMode.HALF_EVEN)     =   1.2
     * NumberFormatUtil.format(toBigDecimal(1.25), "#####.#", RoundingMode.HALF_EVEN)     =   1.2
     * NumberFormatUtil.format(toBigDecimal(1.251), "#####.#", RoundingMode.HALF_EVEN)    =   1.3
     *
     * NumberFormatUtil.format(toBigDecimal(-1.15), "#####.#", RoundingMode.HALF_EVEN)    =   -1.2
     * NumberFormatUtil.format(toBigDecimal(-1.25), "#####.#", RoundingMode.HALF_EVEN)    =   -1.2
     * NumberFormatUtil.format(toBigDecimal(-1.251), "#####.#", RoundingMode.HALF_EVEN)   =   -1.3
     * </pre>
     *
     * </blockquote>
     *
     * <h3>关于参数 <code>value</code>:</h3>
     *
     *
     * <h3>关于参数 <code>roundingMode</code></h3>
     *
     * <blockquote>
     * <p>
     * 虽然{@link DecimalFormat},默认使用的是银行家舍入法 {@link RoundingMode#HALF_EVEN},参见
     * <a href="../util/NumberUtil.html#RoundingMode_HALF_EVEN">关于 RoundingMode#HALF_EVEN</a>
     * </p>
     *
     * <p>
     * 但是我们常用标准的四舍五入,为了保持和 {@link com.mega.component.commons.lang.NumberUtil#toString(Number, String)}一致性,如果 roundingMode 为null,使用常用的
     * {@link RoundingMode#HALF_UP}来进行格式化
     * </p>
     * </blockquote>
     *
     * @param value         the value
     * @param numberPattern 格式化数字格式,可以参见或者使用{@link NumberPattern}
     * @param roundingMode  舍入模式{@link RoundingMode},如果 为null,使用常用的 {@link RoundingMode#HALF_UP}
     * @return 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>numberPattern</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>numberPattern</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see DecimalFormat
     * @see <a href="../util/NumberUtil.html#RoundingMode">JAVA 8种舍入法</a>
     */
    public static String format(Number value, String numberPattern, RoundingMode roundingMode) {
        Validate.notNull(value, "value can't be null!");
        Validate.notBlank(numberPattern, "numberPattern can't be null!");

        //该构造方法内部 调用了applyPattern(pattern, false)
        DecimalFormat decimalFormat = new DecimalFormat(numberPattern);
        decimalFormat.setRoundingMode(defaultIfNull(roundingMode, HALF_UP));

        String result = decimalFormat.format(value);

        if (LOGGER.isTraceEnabled()) {
            String message = "input:[{}],with:[{}]=[{}],localizedPattern:[{}]";
            LOGGER.trace(message, value, numberPattern, result, decimalFormat.toLocalizedPattern());
        }
        return result;
    }
}
