package com.mega.component.commons.lang.annotation;

import java.lang.annotation.Annotation;

/**
 * 将 {@link Annotation} 转成 {@link String} 的接口.
 *
 * @param <T> the generic type
 * @see org.apache.commons.lang3.AnnotationUtils#toString(Annotation)
 * @see org.apache.commons.lang3.builder.Builder
 * @see org.apache.commons.lang3.builder.ToStringBuilder
 * @since 1.10.4
 */
public interface AnnotationToStringBuilder<T extends Annotation> {

    /**
     * 将制定的 {@link Annotation} 转成 {@link String}.
     *
     * @param annotation the annotation
     * @return 如果 <code>annotation</code> 是null,返回 EMPTY String<br>
     */
    String build(T annotation);
}