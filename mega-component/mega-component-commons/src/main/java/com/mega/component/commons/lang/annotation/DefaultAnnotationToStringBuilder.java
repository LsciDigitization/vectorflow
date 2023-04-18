package com.mega.component.commons.lang.annotation;

import static com.mega.component.commons.lang.StringUtil.EMPTY;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.AnnotationUtils;

/**
 * 默认直接调用 {@link org.apache.commons.lang3.AnnotationUtils#toString(Annotation)}.
 *
 * <h3>格式:</h3>
 *
 * <blockquote>
 *
 * <pre class="code">
 *
 * 16:26:30 INFO  (ContextRefreshedEventClientCacheListener.java:123) onApplicationEvent() - url And ClientCache info:    {
 *         "/clientcache": "@com.mega.spring.web.servlet.interceptor.clientcache.ClientCache(value=20)",
 *         "/item/{itemid}": "@com.mega.spring.web.servlet.interceptor.clientcache.ClientCache(value=300)",
 *         "/noclientcache": "@com.mega.spring.web.servlet.interceptor.clientcache.ClientCache(value=0)"
 *     }
 *
 * </pre>
 *
 * </blockquote>
 *
 * @param <T> the generic type
 * @see org.apache.commons.lang3.AnnotationUtils#toString(Annotation)
 * @since 1.10.4
 */
public class DefaultAnnotationToStringBuilder<T extends Annotation> implements AnnotationToStringBuilder<T> {

    /**
     * Static instance.
     */
    // the static instance works for all types
    @SuppressWarnings("rawtypes")
    public static final DefaultAnnotationToStringBuilder DEFAULT_ANNOTATION_TO_STRING_BUILDER = new DefaultAnnotationToStringBuilder();

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     *
     * @see com.mega.spring.web.method.AnnotationToStringBuilder#build(java.lang.annotation.Annotation)
     */
    @Override
    public String build(T annotation) {
        if (null == annotation) {
            return EMPTY;
        }

        return AnnotationUtils.toString(annotation);
    }

    //---------------------------------------------------------------

    /**
     * Instance.
     *
     * @param <T> the generic type
     * @return the default annotation to string builder
     */
    @SuppressWarnings("unchecked")
    public static final <T extends Annotation> DefaultAnnotationToStringBuilder<T> instance() {
        return DEFAULT_ANNOTATION_TO_STRING_BUILDER;
    }
}
