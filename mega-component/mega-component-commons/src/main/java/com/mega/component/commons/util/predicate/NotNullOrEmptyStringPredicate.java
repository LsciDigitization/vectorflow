package com.mega.component.commons.util.predicate;

import static com.mega.component.commons.Validator.isNotNullOrEmpty;

/**
 * 不是null or empty StringPredicate.
 *
 * @see org.apache.commons.collections4.functors.NullPredicate
 * @see org.apache.commons.collections4.functors.NullIsExceptionPredicate
 * @see org.apache.commons.collections4.functors.NullIsFalsePredicate
 * @see org.apache.commons.collections4.functors.NullIsTruePredicate
 * @see org.apache.commons.collections4.functors.NonePredicate
 * @see org.apache.commons.collections4.functors.NotNullPredicate
 * @since 1.13.2
 */
public class NotNullOrEmptyStringPredicate implements StringPredicate {

    /**
     * Static instance.
     */
    // the static instance works for all types
    public static final StringPredicate INSTANCE = new NotNullOrEmptyStringPredicate();

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     *
     * @see org.apache.commons.collections4.Predicate#evaluate(java.lang.Object)
     */
    @Override
    public boolean evaluate(String value) {
        return isNotNullOrEmpty(value);
    }
}
