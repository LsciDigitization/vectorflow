package com.mega.component.commons.util.equator;

import java.io.Serializable;

import org.apache.commons.collections4.Equator;

import org.apache.commons.lang3.StringUtils;

/**
 * 忽视大小写的实现.
 *
 * @see org.apache.commons.collections4.functors.DefaultEquator
 * @since 1.10.1
 */
public class IgnoreCaseEquator implements Equator<String>, Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 5700113137952086493L;

    /**
     * Static instance.
     */
    // the static instance works for all types
    public static final IgnoreCaseEquator INSTANCE = new IgnoreCaseEquator();

    //---------------------------------------------------------------

    /**
     * Restricted constructor.
     */
    private IgnoreCaseEquator() {
        super();
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     *
     * @see org.apache.commons.collections4.Equator#equate(java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean equate(final String s1, final String s2) {
        return StringUtils.equalsIgnoreCase(s1, s2);
    }

    //---------------------------------------------------------------

    /**
     * {@inheritDoc}
     *
     * @return <code>s.hashCode()</code> if <code>s</code> is non-<code>null</code>, else -1.
     */
    @Override
    public int hash(final String s) {
        return s == null ? -1 : s.hashCode();
    }

}
