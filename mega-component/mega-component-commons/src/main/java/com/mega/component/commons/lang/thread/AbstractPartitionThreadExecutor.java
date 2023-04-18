package com.mega.component.commons.lang.thread;

import static com.mega.component.commons.date.DateUtil.formatDuration;
import static com.mega.component.commons.date.DateUtil.now;
import static com.mega.component.commons.lang.ObjectUtil.defaultIfNullOrEmpty;
import static org.apache.commons.lang3.ClassUtils.getSimpleName;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mega.component.commons.Validate;

/**
 * 抽象实现.
 *
 * <p>
 * 含公共校验以及日志
 * </p>
 *
 * @since 1.11.0
 */
public abstract class AbstractPartitionThreadExecutor implements PartitionThreadExecutor {

    /**
     * The Constant log.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPartitionThreadExecutor.class);

    //---------------------------------------------------------------

    /**
     * Excute.
     *
     * @param <T>                      the generic type
     * @param list                     the list
     * @param eachSize                 the each size
     * @param partitionRunnableBuilder the partition runnable builder
     */
    @Override
    public <T> void execute(List<T> list, int eachSize, PartitionRunnableBuilder<T> partitionRunnableBuilder) {
        execute(list, eachSize, null, partitionRunnableBuilder);
    }

    //---------------------------------------------------------------

    /**
     * Execute.
     *
     * @param <T>                      the generic type
     * @param list                     the list
     * @param eachSize                 the each size
     * @param paramsMap                the params map
     * @param partitionRunnableBuilder the partition runnable builder
     */
    @Override
    public <T> void execute(List<T> list, int eachSize, Map<String, ?> paramsMap, PartitionRunnableBuilder<T> partitionRunnableBuilder) {
        Validate.notEmpty(list, "list can't be null/empty!");
        Validate.notNull(partitionRunnableBuilder, "partitionRunnableBuilder can't be null!");

        Validate.isTrue(eachSize > 0, "eachSize must > 0");

        //---------------------------------------------------------------
        String partitionRunnableBuilderName = getName(partitionRunnableBuilder);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("begin [{}],list size:[{}],eachSize:[{}]", partitionRunnableBuilderName, list.size(), eachSize);
        }

        //---------------------------------------------------------------
        Date beginDate = now();

        actualExecute(list, eachSize, paramsMap, partitionRunnableBuilder);

        //---------------------------------------------------------------
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("end [{}],use time:[{}]", partitionRunnableBuilderName, formatDuration(beginDate));
        }
    }

    //---------------------------------------------------------------

    /**
     * 让实现类 focus 实现具体代码流程, 已经校验完参数.
     *
     * @param <T>                      the generic type
     * @param list                     执行解析的list
     *                                 <p>
     *                                 比如 100000个 User
     *                                 </p>
     * @param eachSize                 每个线程执行多少个对象
     *                                 <p>
     *                                 比如 一个线程解析 1000个 User, 那么程序内部 会自动创建 100000/1000个 线程去解析;
     *                                 </p>
     * @param paramsMap                自定义的相关参数
     *                                 <p>
     *                                 自定义的 <code>partitionRunnableBuilder</code>中使用,可能为null
     *                                 </p>
     * @param partitionRunnableBuilder 每个线程做的事情
     * @since 2.0.0 change name from actualExcute to actualExecute
     */
    protected abstract <T> void actualExecute(
            List<T> list,
            int eachSize,
            Map<String, ?> paramsMap,
            PartitionRunnableBuilder<T> partitionRunnableBuilder);

    //---------------------------------------------------------------

    /**
     * Gets the name.
     *
     * @param <T>                      the generic type
     * @param partitionRunnableBuilder the partition runnable builder
     * @return the name
     */
    protected static <T> String getName(PartitionRunnableBuilder<T> partitionRunnableBuilder) {
        return defaultIfNullOrEmpty(getSimpleName(partitionRunnableBuilder.getClass()), partitionRunnableBuilder.getClass().getName());
    }
}
