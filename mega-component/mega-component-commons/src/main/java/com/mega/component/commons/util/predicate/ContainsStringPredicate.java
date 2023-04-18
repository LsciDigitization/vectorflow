package com.mega.component.commons.util.predicate;

import org.apache.commons.lang3.StringUtils;

/**
 * 判断字符串是否包含指定的 searchCharSequence.
 *
 * @since 1.14.3
 */
public class ContainsStringPredicate implements StringPredicate {

    /**
     * The search char sequence.
     */
    private CharSequence searchCharSequence;

    //---------------------------------------------------------------

    /**
     * Instantiates a new contains string predicate.
     */
    public ContainsStringPredicate() {
        super();
    }

    /**
     * Instantiates a new contains string predicate.
     *
     * @param searchCharSequence the search char sequence
     */
    public ContainsStringPredicate(CharSequence searchCharSequence) {
        super();
        this.searchCharSequence = searchCharSequence;
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     *
     * @see org.apache.commons.collections4.Predicate#evaluate(java.lang.Object)
     */
    @Override
    public boolean evaluate(String value) {
        return StringUtils.contains(value, searchCharSequence);
    }

    //---------------------------------------------------------------

    /**
     * Gets the search char sequence.
     *
     * @return the searchCharSequence
     */
    public CharSequence getSearchCharSequence() {
        return searchCharSequence;
    }

    /**
     * Sets the search char sequence.
     *
     * @param searchCharSequence the searchCharSequence to set
     */
    public void setSearchCharSequence(CharSequence searchCharSequence) {
        this.searchCharSequence = searchCharSequence;
    }

}
