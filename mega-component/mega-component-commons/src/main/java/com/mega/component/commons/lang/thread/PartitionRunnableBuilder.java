package com.mega.component.commons.lang.thread;

import java.util.List;
import java.util.Map;

/**
 * {@link Runnable}构造器.
 *
 * @param <T> the generic type
 * @since 1.10.3
 */
public interface PartitionRunnableBuilder<T> {

    /**
     * 构造{@link Runnable}.
     *
     * @param perBatchList          自动分组之后,每个对象list组的数据
     * @param partitionThreadEntity 线程执行此组list 的时候,可以使用的 thread参数信息
     * @param paramsMap             自定义的参数map
     * @return the runnable
     */
    Runnable build(List<T> perBatchList, PartitionThreadEntity partitionThreadEntity, Map<String, ?> paramsMap);

}
