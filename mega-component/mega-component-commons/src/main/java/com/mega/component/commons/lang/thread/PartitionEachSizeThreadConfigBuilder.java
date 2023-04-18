package com.mega.component.commons.lang.thread;

import static com.mega.component.commons.bean.ConvertUtil.toInteger;

import java.math.RoundingMode;

import com.mega.component.commons.lang.NumberUtil;
import com.mega.component.commons.Validate;

/**
 * 用来计算 each size 大小的.
 *
 * @since 2.0.0
 */
public class PartitionEachSizeThreadConfigBuilder implements PartitionEachSizeBuilder {

    /**
     * The partition thread config.
     */
    private PartitionThreadConfig partitionThreadConfig;

    //---------------------------------------------------------------

    /**
     * Instantiates a new partition each size thread config builder.
     */
    public PartitionEachSizeThreadConfigBuilder() {
        super();
    }

    /**
     * Instantiates a new partition each size thread config builder.
     *
     * @param partitionThreadConfig the partition thread config
     */
    public PartitionEachSizeThreadConfigBuilder(PartitionThreadConfig partitionThreadConfig) {
        super();
        this.partitionThreadConfig = partitionThreadConfig;
    }

    //---------------------------------------------------------------

    /**
     * 构造每个分区大小.
     *
     * @param totalSize the total size
     * @return 如果 totalSize{@code <=}0 ,抛出 {@link IllegalArgumentException}<br>
     * 如果 <code>partitionConfig</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 partitionConfig.maxThreadCount{@code <=}0 是empty,抛出 {@link IllegalArgumentException}<br>
     * 如果 partitionConfig.minPerThreadHandlerCount{@code <=}0 是empty,抛出 {@link IllegalArgumentException}<br>
     * <p>
     * 如果 <code>totalSize</code> 小于等于 <code>minPerThreadHandlerCount</code>(每个线程最少处理数量),那么直接返回<code>totalSize</code>,也就是说接下来开 1
     * 个线程就足够了<br>
     */
    @Override
    public int build(int totalSize) {
        Validate.isTrue(totalSize > 0, "totalSize must >0,totalSize:%s", totalSize);
        Validate.notNull(partitionThreadConfig, "partitionConfig can't be null!");
        //---------------------------------------------------------------
        //启动最大线程数
        int maxThreadCount = partitionThreadConfig.getMaxThreadCount();
        // 每个线程最少处理数量
        int minPerThreadHandlerCount = partitionThreadConfig.getMinPerThreadHandlerCount();

        Validate.isTrue(maxThreadCount > 0, "maxThreadCount must >0,totalSize:%s", maxThreadCount);
        Validate.isTrue(minPerThreadHandlerCount > 0, "minPerThreadHandlerCount must >0,totalSize:%s", minPerThreadHandlerCount);
        //---------------------------------------------------------------
        //如果 totalSize 小于等于  minPerThreadHandlerCount(每个线程最少处理数量), 那么直接返回totalSize ,也就是说接下来开 1 个线程就足够了
        if (totalSize <= minPerThreadHandlerCount) {
            return totalSize;
        }

        //---------------------------------------------------------------
        //用总数  totalSize 除以  minPerThreadHandlerCount (每个线程最少处理数量),向上取整
        int threadCount = toInteger(NumberUtil.getDivideValue(totalSize, minPerThreadHandlerCount, 0, RoundingMode.UP));
        //如果 算出来的线程数 要超过 maxThreadCount,比如 总数 100 条, 但是设置的minPerThreadHandlerCount (每个线程最少处理数量) 是 20, 最大线程数是 4 ,此时 100/20 是 5 ,大约最大线程数 4 

        //那么最大只能开启 4 个线程, 返回的每个线程处理的数量是 100/4 =25
        if (threadCount >= maxThreadCount) {
            return toInteger(NumberUtil.getDivideValue(totalSize, maxThreadCount, 0, RoundingMode.UP));
        }

        //否则返回  threadCount
        return threadCount;
    }

    //---------------------------------------------------------------

    /**
     * Sets the partition thread config.
     *
     * @param partitionThreadConfig the partitionThreadConfig to set
     */
    public void setPartitionThreadConfig(PartitionThreadConfig partitionThreadConfig) {
        this.partitionThreadConfig = partitionThreadConfig;
    }

}
