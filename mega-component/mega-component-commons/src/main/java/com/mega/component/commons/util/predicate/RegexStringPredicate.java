package com.mega.component.commons.util.predicate;

import com.mega.component.commons.util.RegexUtil;

/**
 * 正则表达式类型的 StringPredicate.
 *
 * @see RegexUtil#matches(String, CharSequence)
 * @since 1.13.2
 */
public class RegexStringPredicate implements StringPredicate {

    /**
     * The regex pattern.
     */
    private final String regexPattern;

    //---------------------------------------------------------------

    /**
     * Instantiates a new regex string predicate.
     *
     * @param regexPattern the regex pattern
     */
    public RegexStringPredicate(String regexPattern) {
        super();
        this.regexPattern = regexPattern;
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     *
     * @see org.apache.commons.collections4.Predicate#evaluate(java.lang.Object)
     */
    @Override
    public boolean evaluate(String value) {
        return RegexUtil.matches(regexPattern, value);
    }

}
