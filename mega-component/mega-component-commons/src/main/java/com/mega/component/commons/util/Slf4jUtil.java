package com.mega.component.commons.util;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * 对 slf4j 的封装提取.
 *
 * @since 1.4.0
 */
public final class Slf4jUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private Slf4jUtil() {
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 格式化字符串,此方法是抽取slf4j的核心方法.
     *
     * <p>
     * 在java中,常会拼接字符串生成新的字符串值,在字符串拼接过程中容易写错或者位置写错<br>
     * <br>
     * slf4j的log支持格式化输出log,比如:<br>
     * </p>
     *
     * <ul>
     * <li>LOGGER.debug("{}","test");</li>
     * <li>LOGGER.info("{},{}","test","hello");</li>
     * </ul>
     * <p>
     * 这些写法非常简洁且有效,不易出错
     *
     * <br>
     * 因此,你可以在代码中出现这样的写法:
     * <p>
     * <pre class="code">
     * throw new IllegalArgumentException(Slf4jUtil.format(
     *  "callbackUrl:[{}] ,length:[{}] can't {@code >}{}",
     *  callbackUrl,
     *  callbackUrlLength,
     *  callbackUrlMaxLength)
     * </pre>
     * <p>
     * 又或者
     * <p>
     * <pre class="code">
     * return Slf4jUtil.format("{} [{}]", encode, encode.length());
     * </pre>
     *
     * @param messagePattern message的格式,比如 callbackUrl:[{}] ,length:[{}]
     * @param args           参数
     * @return 如果 <code>messagePattern</code> 是null,返回 null<br>
     * 如果 <code>args</code> 是null,返回 <code>messagePattern</code><br>
     * @see org.slf4j.helpers.FormattingTuple
     * @see org.slf4j.helpers.MessageFormatter#arrayFormat(String, Object[])
     * @see org.slf4j.helpers.FormattingTuple#getMessage()
     * @since 1.6.0
     */
    public static String format(String messagePattern, Object... args) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(messagePattern, args);
        return formattingTuple.getMessage();
    }
}