package com.mega.component.commons.lang.thread;

import java.util.List;
import java.util.Map;

/**
 * 分区中的每个线程执行.
 *
 * @param <T> the generic type
 * @see com.mega.component.commons.lang.thread.DefaultPartitionRunnableBuilder
 * @since 2.0.0
 */
public interface PartitionPerHandler<T> {

    /**
     * Handle.
     *
     * @param perBatchList          自动分组之后,每个对象list组的数据
     * @param partitionThreadEntity 线程执行此组list 的时候,可以使用的 thread参数信息
     * @param paramsMap             自定义的参数map
     */
    void handle(List<T> perBatchList, PartitionThreadEntity partitionThreadEntity, Map<String, ?> paramsMap);
}
