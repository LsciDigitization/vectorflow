package com.mega.component.commons.util.predicate;

import org.apache.commons.collections4.Predicate;

import com.mega.component.commons.Validate;
import com.mega.component.commons.bean.PropertyUtil;

/**
 * 调用 {@link PropertyUtil#getProperty(Object, String)} 匹配属性值.
 *
 * @param <T> the generic type
 * @see "org.apache.commons.beanutils.BeanPredicate"
 * @see "org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate"
 * @see org.apache.commons.collections4.functors.ComparatorPredicate
 * @since 1.8.0
 */
//XXX 如果{@link org.apache.commons.beanutils.BeanPredicate}支持泛型且支持 commons-collections4之后,那么这么类将会废弃
public class BeanPredicate<T> implements Predicate<T> {

    /**
     * 泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     * <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>.
     */
    private final String propertyName;

    /**
     * The value predicate.
     */
    @SuppressWarnings("rawtypes")
    private final Predicate valuePredicate;

    //---------------------------------------------------------------

    /**
     * The Constructor.
     *
     * <p>
     * 如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * 如果 <code>valuePredicate</code> 是null,抛出 {@link NullPointerException}<br>
     * </p>
     *
     * @param propertyName   泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *                       <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param valuePredicate the predicate
     */
    public BeanPredicate(String propertyName, @SuppressWarnings("rawtypes") Predicate valuePredicate) {
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        Validate.notNull(valuePredicate, "predicate can't be null!");

        this.propertyName = propertyName;
        this.valuePredicate = valuePredicate;
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     *
     * @see org.apache.commons.collections4.Predicate#evaluate(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean evaluate(T object) {
        Object currentPropertyValue = PropertyUtil.getProperty(object, propertyName);
        return valuePredicate.evaluate(currentPropertyValue);
    }
}