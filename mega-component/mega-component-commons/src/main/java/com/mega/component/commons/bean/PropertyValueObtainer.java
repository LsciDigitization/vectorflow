package com.mega.component.commons.bean;

import static com.mega.component.commons.bean.PropertyDescriptorUtil.getSpringPropertyDescriptor;
import static com.mega.component.commons.bean.PropertyDescriptorUtil.isUseSpringOperate;
import static com.mega.component.commons.util.CollectionsUtil.first;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;

import com.mega.component.commons.util.Slf4jUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mega.component.commons.DefaultRuntimeException;
import com.mega.component.commons.Validate;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 属性值获取器.
 *
 * @since 1.12.0
 */
public final class PropertyValueObtainer {

    /**
     * The Constant log.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyValueObtainer.class);

    /**
     * Don't let anyone instantiate this class.
     */
    private PropertyValueObtainer() {
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * Gets the property focus.
     *
     * @param <T>          the generic type
     * @param bean         the bean
     * @param propertyName the property name
     * @return the property focus
     */
    static <T> T obtain(Object bean, String propertyName) {
        if (PropertyDescriptorUtil.isUseSpringOperate(bean.getClass(), propertyName)) {
            return getDataUseSpring(bean, propertyName);
        }
        return getDataUseApache(bean, propertyName);
    }

    //---------------------------------------------------------------

    /**
     * Gets the data use apache.
     *
     * @param <T>          the generic type
     * @param bean         the bean
     * @param propertyName the property name
     * @return the data use apache
     */
    @SuppressWarnings("unchecked")
    private static <T> T getDataUseApache(Object bean, String propertyName) {
        try {
            return (T) PropertyUtils.getProperty(bean, propertyName);
        } catch (Exception e) {
            String pattern = "getProperty exception,bean:[{}],propertyName:[{}]";
            throw new BeanOperationException(Slf4jUtil.format(pattern, bean, propertyName), e);
        }
    }

    //---------------------------------------------------------------

    /**
     * Gets the data use spring.
     *
     * @param <T>          the generic type
     * @param bean         the bean
     * @param propertyName the property name
     * @return the data use spring
     */
    private static <T> T getDataUseSpring(Object bean, String propertyName) {
        LOGGER.trace("will use spring beanutils to execute:[{}],propertyName:[{}]", bean, propertyName);
        try {
            PropertyDescriptor propertyDescriptor = PropertyDescriptorUtil.getSpringPropertyDescriptor(bean.getClass(), propertyName);
            return getValue(bean, propertyDescriptor);
        } catch (Exception e) {
            String pattern = "getProperty exception,bean:[{}],propertyName:[{}]";
            throw new BeanOperationException(Slf4jUtil.format(pattern, bean, propertyName), e);
        }
    }

    //---------------------------------------------------------------

    /**
     * 循环<code>beanIterable</code>,调用 {@link PropertyUtil#getProperty(Object, String)} 获得 propertyName的值,塞到 <code>returnCollection</code>
     * 中返回.
     *
     * @param <T>              the generic type
     * @param <O>              the generic type
     * @param <K>              the key type
     * @param beanIterable     支持
     *
     *                         <ul>
     *                         <li>bean Iterable,比如List{@code <User>},Set{@code <User>}等</li>
     *                         <li>map Iterable,比如{@code List<Map<String, String>>}</li>
     *                         <li>list Iterable , 比如 {@code  List<List<String>>}</li>
     *                         <li>数组 Iterable ,比如 {@code  List<String[]>}</li>
     *                         </ul>
     * @param propertyName     泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *                         <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param returnCollection the return collection
     * @return 如果 <code>returnCollection</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>beanIterable</code> 是null或者empty,返回 <code>returnCollection</code><br>
     * 如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see PropertyUtil#getProperty(Object, String)
     * @see "org.apache.commons.beanutils.BeanToPropertyValueTransformer"
     * @since 1.0.8
     */
    public static <T, O, K extends Collection<T>> K getPropertyValueCollection(
            Iterable<O> beanIterable,
            String propertyName,
            K returnCollection) {
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");

        //---------------------------------------------------------------
        O o = first(beanIterable);
        Class<?> klass = o.getClass();
        //spring 操作
        if (isUseSpringOperate(klass, propertyName)) {
            PropertyDescriptor propertyDescriptor = getSpringPropertyDescriptor(klass, propertyName);
            for (O bean : beanIterable) {
                returnCollection.add(PropertyValueObtainer.<T, O>getValue(bean, propertyDescriptor));
            }
            return returnCollection;
        }

        //---------------------------------------------------------------
        for (O bean : beanIterable) {
            returnCollection.add(PropertyUtil.<T>getProperty(bean, propertyName));
        }
        return returnCollection;
    }

    //---------------------------------------------------------------

    /**
     * Gets the value.
     *
     * @param <T>                the generic type
     * @param <O>                the generic type
     * @param obj                the obj
     * @param propertyDescriptor the property descriptor
     * @return 如果 <code>obj</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>propertyDescriptor</code> 是null,抛出 {@link NullPointerException}<br>
     */
    @SuppressWarnings("unchecked")
    public static <T, O> T getValue(O obj, PropertyDescriptor propertyDescriptor) {
        Validate.notNull(obj, "obj can't be null!");
        Validate.notNull(propertyDescriptor, "propertyDescriptor can't be null!");

        //---------------------------------------------------------------
        Method readMethod = propertyDescriptor.getReadMethod();

        //---------------------------------------------------------------
        //since 1.12.2
        Validate.notNull(
                readMethod,
                "class:[%s],propertyDescriptor name:[%s],has no ReadMethod!!,pls check",
                obj.getClass().getCanonicalName(),
                propertyDescriptor.getDisplayName());

        //---------------------------------------------------------------
        //since 1.12.1
        readMethod = org.apache.commons.beanutils.MethodUtils.getAccessibleMethod(obj.getClass(), readMethod);

        try {
            return (T) readMethod.invoke(obj);
        } catch (ReflectiveOperationException e) {
            throw new DefaultRuntimeException(e);
        }
    }
}
