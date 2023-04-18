package com.mega.component.commons.lang.reflect;

import java.lang.reflect.Constructor;

import com.mega.component.commons.Validate;
import com.mega.component.commons.lang.ClassUtil;
import com.mega.component.commons.util.Slf4jUtil;
import org.apache.commons.lang3.reflect.ConstructorUtils;

/**
 * 使用反射请求构造函数创建新实例的工具类,focused on constructors.
 *
 * <h3>方法介绍:</h3>
 *
 * <blockquote>
 * <ol>
 * <li>{@link #newInstance(Class, Object...)} 获得实例</li>
 * <li>{@link #newInstance(Class, Object[], Class[])} 获得实例</li>
 * </ol>
 * <p>
 * 上面2个方法,底层均调用了 {@link ConstructorUtils#invokeConstructor(Class, Object[], Class[])} ,此方法 会
 * {@link ConstructorUtils#getMatchingAccessibleConstructor(Class, Class...)} 自动根据类型活动最匹配的构造函数,并且将异常转成了 {@link ReflectException} 以便调用的时候使用
 *
 * <p>
 * 因此,不用担心 int{@code -->}Integer 原始类型参数和包装类型参数,以及父类/子类 这样的参数不匹配带来的问题
 * </p>
 *
 * </blockquote>
 *
 * <h3>如果不想使用自动匹配的特性,</h3>
 *
 * <blockquote>
 *
 * <p>
 * 您可以使用原生方法,下面两个是获得精准的构造函数并实例,如果参数类型不匹配,那么就会抛异常
 * </p>
 *
 * <ol>
 * <li>{@link ConstructorUtils#invokeExactConstructor(Class, Object...)} 获得实例</li>
 * <li>{@link ConstructorUtils#invokeExactConstructor(Class, Object[], Class[])} 获得实例</li>
 * </ol>
 * <p>
 * 下面还有3个方法是获得构造函数而不实例
 * <ol>
 * <li>{@link ConstructorUtils#getAccessibleConstructor(Constructor)}</li>
 * <li>{@link ConstructorUtils#getAccessibleConstructor(Class, Class...)}</li>
 * <li>{@link ConstructorUtils#getMatchingAccessibleConstructor(Class, Class...)}</li>
 * </ol>
 *
 * </blockquote>
 *
 * @see org.apache.commons.lang3.reflect.ConstructorUtils
 * @see "org.springframework.beans.BeanUtils.instantiateClass"
 * @since 1.0.7
 */
public final class ConstructorUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private ConstructorUtil() {
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    // [start] newInstance

    /**
     * 新建实例,返回指定类型 <code>klass</code> 的实例,使用正确的构造函数使用参数类型<code>parameterValues</code>.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * User user = ConstructorUtil.newInstance(User.class)
     * </pre>
     * <p>
     * 调用无参的构造函数,返回User对象,你还可以:
     *
     * <pre class="code">
     *
     * User user1 = ConstructorUtil.newInstance(User.class, 100L);
     * </pre>
     * <p>
     * 返回 id 是100的user对象构造函数
     * </blockquote>
     *
     * @param <T>             the generic type
     * @param klass           类
     * @param parameterValues 构造函数的参数值, 比如100L
     * @return 如果 <code>klass</code> 是null,抛出 {@link NullPointerException}<br>
     * 有任何异常(比如 NoSuchMethodException 找不到相关参数的构造函数),将抛出 {@link ReflectException}
     * @see ClassUtil#toClass(Object...)
     * @see Class#getConstructor(Class...)
     * @see Constructor#newInstance(Object...)
     * @see org.apache.commons.lang3.reflect.ConstructorUtils#invokeConstructor(Class, Object...)
     * @see "org.springframework.beans.BeanUtils.instantiateClass(Constructor<T>, Object...)"
     */
    public static <T> T newInstance(Class<T> klass, Object... parameterValues) {
        Validate.notNull(klass, "klass can't be null!");

        Class<?>[] parameterTypes = ClassUtil.toClass(parameterValues);
        return newInstance(klass, parameterValues, parameterTypes);
    }

    //---------------------------------------------------------------

    /**
     * 返回指定类型 <code>klass</code>,指定参数 <code>parameterValues</code> 和指定参数类型 <code>parameterTypes</code>的构造函数示例.
     *
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>定位并调用一个构造函数。构造函数的签名必须与赋值兼容的参数类型相匹配。</li>
     * <li>和 {@link #newInstance(Class, Object...)}的区别, 在于 如果一个类有些重载的构造函数,并且参数类型相似, 此时使用这个方法可以精准定位到需要的构造函数</li>
     * </ol>
     * </blockquote>
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * User user = ConstructorUtil.newInstance(User.class,null,null)
     * </pre>
     * <p>
     * 调用无参的构造函数,返回User对象,你还可以:
     *
     * <pre class="code">
     *
     * User user1 = ConstructorUtil.newInstance(User.class, toArray(100L), toArray(Long.class));
     * </pre>
     * <p>
     * 返回 id 是100的user对象构造函数
     * </blockquote>
     *
     * @param <T>             the generic type
     * @param klass           the class to be constructed, not {@code null}
     * @param parameterValues the array of arguments, {@code null} treated as empty
     * @param parameterTypes  the array of parameter types, {@code null} treated as empty
     * @return 如果 <code>klass</code> 是null,抛出 {@link NullPointerException}<br>
     * 有任何异常(比如 NoSuchMethodException 找不到相关参数的构造函数),将抛出 {@link ReflectException}
     * @see org.apache.commons.lang3.reflect.ConstructorUtils#invokeConstructor(Class, Object[], Class[])
     * @see "org.springframework.beans.BeanUtils.instantiateClass(Constructor<T>, Object...)"
     */
    public static <T> T newInstance(Class<T> klass, Object[] parameterValues, Class<?>[] parameterTypes) {
        Validate.notNull(klass, "klass can't be null!");

        //---------------------------------------------------------------
        try {
            return ConstructorUtils.invokeConstructor(klass, parameterValues, parameterTypes);
        } catch (Exception e) {
            String pattern = "invokeConstructor exception,class:[{}].args:[{}],parameterTypes:[{}]";
            String message = Slf4jUtil.format(pattern, klass, parameterValues, parameterTypes);
            throw new ReflectException(message, e);
        }
    }
}