package com.mega.component.commons.lang;

import static com.mega.component.commons.bean.ConvertUtil.toList;
import static com.mega.component.commons.util.MapUtil.newLinkedHashMap;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.mega.component.commons.util.Slf4jUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mega.component.commons.Validate;
import com.mega.component.commons.net.URLUtil;

/**
 * {@link ClassLoader ClassLoader}工具类.
 *
 * @see ClassLoader
 * @see java.net.URLClassLoader
 * @see "org.springframework.core.io.ClassPathResource#ClassPathResource(String, ClassLoader)"
 * @since 1.0.0
 */
public final class ClassLoaderUtil {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoaderUtil.class);

    /**
     * Don't let anyone instantiate this class.
     */
    private ClassLoaderUtil() {
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 获得给定名称 <code>resourceName</code> 的资源.
     *
     * @param resourceName the resource name
     * @return 如果 <code>resourceName</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果找不到该资源,或者调用者没有足够的权限获取该资源,则返回 null
     * @see "org.apache.commons.lang3.ClassPathUtils#toFullyQualifiedPath(Package, String)"
     * @see #getResource(ClassLoader, String)
     * @see #getClassLoaderByClass(Class)
     */
    public static URL getResource(String resourceName) {
        return getResource(getClassLoaderByClass(ClassLoaderUtil.class), resourceName);
    }

    /**
     * 查找具有给定名称的资源,资源是可以通过类代码以与代码基无关的方式访问的一些数据(图像、声音、文本等).
     *
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>如果 <code>resourceName</code> 是以 斜杆 "/" 开头,那么会被截取, 因为 ClassLoader解析方式不需要 开头的斜杆, 请参见
     * <code>org.springframework.core.io.ClassPathResource#ClassPathResource(String, ClassLoader)</code></li>
     * <li>"",表示classes 的根目录</li>
     * </ol>
     * </blockquote>
     *
     * @param classLoader  the class loader
     * @param resourceName the resource name
     * @return 如果 <code>classLoader</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>resourceName</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果找不到该资源,或者调用者没有足够的权限获取该资源,则返回 null
     * @since 1.2.1
     */
    private static URL getResource(ClassLoader classLoader, String resourceName) {
        Validate.notNull(classLoader, "classLoader can't be null!");
        Validate.notNull(resourceName, "resourceName can't be null!");

        boolean startsWithSlash = resourceName.startsWith("/");
        String usePath = startsWithSlash ? StringUtil.substring(resourceName, 1) : resourceName;
        URL result = classLoader.getResource(usePath);

        LOGGER.trace("search resource:[\"{}\"] in [{}],result:[{}]", resourceName, classLoader, result);
        return result;
    }

    //---------------------------------------------------------------

    /**
     * 加载资源 <code>resourceName</code>为 InputStream.
     *
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>如果 <code>resourceName</code> 是以 斜杆 "/" 开头,那么会被截取, 因为 ClassLoader解析方式不需要 开头的斜杆, 请参见
     * <code>org.springframework.core.io.ClassPathResource#ClassPathResource(String, ClassLoader)</code></li>
     * <li>"",表示classes 的根目录</li>
     * </ol>
     * </blockquote>
     *
     * @param resourceName The name of the resource to load
     * @param callingClass The Class object of the calling object
     * @return 如果 <code>resourceName</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果查找不到资源,那么返回 null
     * @see #getResourceInAllClassLoader(String, Class)
     * @see "org.apache.velocity.util.ClassUtils#getResourceAsStream(Class, String)"
     */
    public static InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
        URL url = getResourceInAllClassLoader(resourceName, callingClass);
        return URLUtil.openStream(url);
    }

    /**
     * Load a given resource.
     *
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>如果 <code>resourceName</code> 是以 斜杆 "/" 开头,那么会被截取,因为 ClassLoader解析方式不需要开头的斜杆, 请参见
     * <code>org.springframework.core.io.ClassPathResource#ClassPathResource(String, ClassLoader)</code></li>
     * <li>"",表示classes 的根目录</li>
     * <li>
     * <p>
     * This method will try to load the resource using the following methods (in order):
     * </p>
     * <ul>
     * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
     * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}
     * <li>From {@link Class#getClassLoader() callingClass.getClassLoader() } (如果 callingClass 不是null)
     * </ul>
     * </li>
     * </ol>
     * </blockquote>
     *
     * @param resourceName The name of the resource to load
     * @param callingClass The Class object of the calling object
     * @return 如果 <code>resourceName</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>callingClass</code> 是null,将会忽略此参数<br>
     * 如果在所有的{@link ClassLoader}里面都查不到资源,那么返回null
     * @since 1.6.2
     */
    public static URL getResourceInAllClassLoader(String resourceName, Class<?> callingClass) {
        Validate.notNull(resourceName, "resourceName can't be null!");

        List<ClassLoader> classLoaderList = getAllClassLoaderList(callingClass);
        for (ClassLoader classLoader : classLoaderList) {
            URL url = getResource(classLoader, resourceName);
            if (null == url) {
                LOGGER.trace(getLogInfo(resourceName, classLoader, false));
            } else {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(getLogInfo(resourceName, classLoader, true));
                }
                return url;
            }
        }
        LOGGER.debug("not found:[{}] in all ClassLoader,return null", resourceName);
        return null;
    }

    //---------------------------------------------------------------

    /**
     * 获得 all class loader list.
     *
     * <p>
     * This method will try to get ClassLoader list using the following methods (in order):
     * </p>
     * <ul>
     * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
     * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}
     * <li>From {@link Class#getClassLoader() callingClass.getClassLoader() } (如果 <code>callingClass</code> 不是null)
     * </ul>
     *
     * @param callingClass the calling class
     * @return the all class loader
     * @since 1.6.2
     */
    private static List<ClassLoader> getAllClassLoaderList(Class<?> callingClass) {
        List<ClassLoader> list = toList(//
                getClassLoaderByCurrentThread(),
                getClassLoaderByClass(ClassLoaderUtil.class));
        if (null != callingClass) {
            list.add(getClassLoaderByClass(callingClass));
        }
        return list;
    }

    /**
     * 通过 {@link Thread#getContextClassLoader()} 获得 {@link ClassLoader}.
     *
     * @return the class loader by current thread
     */
    private static ClassLoader getClassLoaderByCurrentThread() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[Thread.currentThread()].getContextClassLoader:{}", formatClassLoader(classLoader));
        }
        return classLoader;
    }

    /**
     * 通过类来获得 {@link ClassLoader}.
     *
     * @param callingClass the calling class
     * @return 如果 <code>callingClass</code> 是null,抛出 {@link NullPointerException}<br>
     * @see Class#getClassLoader()
     */
    private static ClassLoader getClassLoaderByClass(Class<?> callingClass) {
        Validate.notNull(callingClass, "callingClass can't be null!");
        ClassLoader classLoader = callingClass.getClassLoader();
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[{}].getClassLoader():{}", callingClass.getSimpleName(), formatClassLoader(classLoader));
        }
        return classLoader;
    }

    //---------------------------------------------------------------

    /**
     * 获得 log info.
     *
     * @param resourceName the resource name
     * @param classLoader  the class loader
     * @param isFouned     the is founed
     * @return the log info
     * @since 1.6.2
     */
    private static String getLogInfo(String resourceName, ClassLoader classLoader, boolean isFouned) {
        String message = "{}found [{}],in ClassLoader:[{}]";
        return Slf4jUtil.format(message, isFouned ? "" : "not ", resourceName, formatClassLoader(classLoader));
    }

    /**
     * Format class loader.
     *
     * @param classLoader the class loader
     * @return the string
     * @since 1.6.2
     */
    private static String formatClassLoader(ClassLoader classLoader) {
        Map<String, Object> map = newLinkedHashMap(2);
        map.put("classLoader[CanonicalName]", classLoader.getClass().getCanonicalName());
        map.put("classLoader[Root Classpath]", "" + getResource(classLoader, ""));
        return map.toString();
    }

}