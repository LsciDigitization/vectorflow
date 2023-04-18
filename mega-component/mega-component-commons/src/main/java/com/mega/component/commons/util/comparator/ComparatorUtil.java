package com.mega.component.commons.util.comparator;

import java.util.Comparator;
import java.util.List;

import com.mega.component.commons.Validate;
import org.apache.commons.collections4.comparators.FixedOrderComparator;
import org.apache.commons.collections4.comparators.FixedOrderComparator.UnknownObjectBehavior;

/**
 * {@link Comparator} 工具类.
 *
 * @see "org.apache.commons.collections4.ComparatorUtils"
 * @see FixedOrderComparator
 * @since 1.14.3
 */
public final class ComparatorUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private ComparatorUtil() {
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * Builds the.
     *
     * <p>
     * 默认使用的是 {@link UnknownObjectBehavior#AFTER} ,不在指定固定顺序的元素将排在后面
     * </p>
     *
     * @param <T>  the value type
     * @param list the property values
     * @return 如果 <code>list</code> 是null,抛出 {@link NullPointerException}<br>
     */
    public static <T> FixedOrderComparator<T> buildFixedOrderComparator(List<T> list) {
        return buildFixedOrderComparator(list, UnknownObjectBehavior.AFTER);
    }

    /**
     * Builds the.
     *
     * @param <T>                   the value type
     * @param list                  the property values
     * @param unknownObjectBehavior the unknown object behavior
     * @return 如果 <code>list</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>unknownObjectBehavior</code> 是null,抛出 {@link NullPointerException}<br>
     */
    public static <T> FixedOrderComparator<T> buildFixedOrderComparator(List<T> list, UnknownObjectBehavior unknownObjectBehavior) {
        Validate.notNull(list, "propertyValues can't be null!");
        Validate.notNull(unknownObjectBehavior, "unknownObjectBehavior can't be null!");

        //---------------------------------------------------------------
        FixedOrderComparator<T> fixedOrderComparator = new FixedOrderComparator<>(list);
        fixedOrderComparator.setUnknownObjectBehavior(unknownObjectBehavior);
        return fixedOrderComparator;
    }
}
