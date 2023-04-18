package com.mega.component.commons.lang.thread;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 分区控制.
 *
 * @since 2.0.0
 */
public class PartitionThreadConfig {

    /**
     * Static instance.
     */
    // the static instance works for all types
    public static final PartitionThreadConfig INSTANCE = new PartitionThreadConfig();

    //---------------------------------------------------------------

    /**
     * 启动最大线程数.
     */
    private int maxThreadCount = 100;

    /**
     * 每个线程最少处理数量.
     */
    private int minPerThreadHandlerCount = 20;

    //---------------------------------------------------------------

    /**
     * Instantiates a new partition each size entity.
     */
    public PartitionThreadConfig() {
        super();
    }

    /**
     * Instantiates a new partition each size entity.
     *
     * @param minPerThreadHandlerCount 每个线程最少处理数量
     */
    public PartitionThreadConfig(int minPerThreadHandlerCount) {
        super();
        this.minPerThreadHandlerCount = minPerThreadHandlerCount;
    }

    /**
     * Instantiates a new partition each size entity.
     *
     * @param maxThreadCount           启动最大线程数
     * @param minPerThreadHandlerCount 每个线程最少处理数量
     */
    public PartitionThreadConfig(int maxThreadCount, int minPerThreadHandlerCount) {
        super();
        this.maxThreadCount = maxThreadCount;
        this.minPerThreadHandlerCount = minPerThreadHandlerCount;
    }

    //---------------------------------------------------------------

    /**
     * 获得 启动最大线程数.
     *
     * @return the maxThreadCount
     */
    public int getMaxThreadCount() {
        return maxThreadCount;
    }

    /**
     * 设置 启动最大线程数.
     *
     * @param maxThreadCount the maxThreadCount to set
     */
    public void setMaxThreadCount(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * 获得 每个线程最少处理数量.
     *
     * @return the minPerThreadHandlerCount
     */
    public int getMinPerThreadHandlerCount() {
        return minPerThreadHandlerCount;
    }

    /**
     * 设置 每个线程最少处理数量.
     *
     * @param minPerThreadHandlerCount the minPerThreadHandlerCount to set
     */
    public void setMinPerThreadHandlerCount(int minPerThreadHandlerCount) {
        this.minPerThreadHandlerCount = minPerThreadHandlerCount;
    }

    //---------------------------------------------------------------
    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
