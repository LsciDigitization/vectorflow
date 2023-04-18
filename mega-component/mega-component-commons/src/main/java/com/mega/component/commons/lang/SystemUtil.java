package com.mega.component.commons.lang;

import static com.mega.component.commons.bean.ConvertUtil.toMap;
import static com.mega.component.commons.util.SortUtil.sortMapByKeyAsc;

import java.util.Map;
import java.util.Properties;

import com.mega.component.commons.Validate;
import com.mega.component.commons.bean.ConvertUtil;

/**
 * {@link System}工具类.
 *
 * @see System
 * @see "org.apache.commons.lang3.SystemUtils#USER_HOME"
 * @see "org.apache.commons.lang3.SystemUtils#FILE_ENCODING"
 * @see "org.apache.commons.lang3.SystemUtils#FILE_SEPARATOR"
 * @see "org.apache.commons.lang3.SystemUtils#JAVA_IO_TMPDIR"
 * @see "org.springframework.util.SystemPropertyUtils"
 * @since 1.0.7
 */
public final class SystemUtil {

    /**
     * The System property key for the user home directory.
     *
     * @since 3.0.0
     */
    private static final String USER_HOME_KEY = "user.home";

    /**
     * The {@code user.home} System Property. User's home directory.
     *
     * <p>
     * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
     * not exist.
     * </p>
     *
     * <p>
     * This value is initialized when the class is loaded. If {@link System#setProperty(String, String)} or
     * {@link System#setProperties(Properties)} is called after this class is loaded, the value will be out of
     * sync with that System property.
     * </p>
     *
     * @since Java 1.1
     * @since 3.0.0
     */
    public static final String USER_HOME = getSystemProperty(USER_HOME_KEY);

    /**
     * Don't let anyone instantiate this class.
     */
    private SystemUtil() {
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // -----------------------------------------------------------------------

    /**
     * <p>
     * Gets a System property, defaulting to {@code null} if the property cannot be read.
     * </p>
     * <p>
     * If a {@code SecurityException} is caught, the return value is {@code null} and a message is written to
     * {@code System.err}.
     * </p>
     *
     * @param property the system property name
     * @return the system property value or {@code null} if a security problem occurs
     * @since 3.0.0
     */
    private static String getSystemProperty(final String property) {
        try {
            return System.getProperty(property);
        } catch (final SecurityException ex) {
            // we are not allowed to look at this property
            return null;
        }
    }
    //---------------------------------------------------------------

    /**
     * 取到 {@link System#getProperty(String)},转成 {@link java.util.TreeMap},以遍输出log的时候,会顺序显示.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * LOGGER.debug(JsonUtil.format(SystemUtil.getPropertiesMap()));
     * </pre>
     *
     * </blockquote>
     *
     * @return the properties map for log
     * @see System#getProperties()
     * @see ConvertUtil#toMap(Properties)
     * @see "org.springframework.core.env.AbstractEnvironment#getSystemProperties()"
     * @since 1.8.0 change name
     */
    public static Map<String, String> getPropertiesMap() {
        return toMap(System.getProperties());
    }

    /**
     * 取到 {@link System#getenv()},转成 {@link java.util.TreeMap},以遍输出log的时候,会顺序显示.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     *
     * LOGGER.debug(JsonUtil.format(SystemUtil.getEnvMap()));
     *
     * </pre>
     *
     * </blockquote>
     *
     * @return the env map for log
     * @see System#getenv()
     * @see "org.springframework.core.env.AbstractEnvironment#getSystemEnvironment()"
     * @since 1.8.0 change name
     */
    public static Map<String, String> getEnvMap() {
        return sortMapByKeyAsc(System.getenv());
    }

    //---------------------------------------------------------------

    /**
     * 循环 <code>map</code> ,设置到系统属性 {@link System#setProperty(String, String)}.
     *
     * <p>
     * 如果 <code>map</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>map</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     * </p>
     *
     * @param map the properties from map
     * @see System#setProperty(String, String)
     * @since 1.2.0
     */
    public static void setPropertiesFromMap(Map<String, String> map) {
        Validate.notEmpty(map, "map can't be null/empty!");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.setProperty(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 设置 properties from properties.
     *
     * <p>
     * 如果 <code>properties</code> 是null,抛出 {@link NullPointerException}<br>
     * </p>
     *
     * @param properties the properties from properties
     * @see ConvertUtil#toMap(Properties)
     * @see #setPropertiesFromMap(Map)
     * @see System#setProperties(Properties)
     * @since 1.2.0
     */
    public static void setPropertiesFromProperties(Properties properties) {
        Validate.notNull(properties, "properties can't be null!");
        setPropertiesFromMap(toMap(properties));
    }
}
