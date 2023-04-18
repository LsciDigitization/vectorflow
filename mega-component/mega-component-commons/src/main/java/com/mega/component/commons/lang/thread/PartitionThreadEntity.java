package com.mega.component.commons.lang.thread;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 在每个线程中 {@link PartitionRunnableBuilder},你可以使用到的参数.
 *
 * @since 1.10.3
 */
public class PartitionThreadEntity implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 6507966437017227236L;

    /**
     * 线程的名字.
     */
    private final String name;

    //---------------------------------------------------

    /**
     * 总数,list 的总size.
     */
    private final int totalListCount;

    /**
     * 分配大小.
     */
    private final int eachSize;

    /**
     * 批次,当前线程序号,从0开始.
     */
    private final int batchNumber;

    /**
     * 当前线程执行数量,=perBatchList size.
     */
    private final int currentListSize;

    //---------------------------------------------------

    /**
     * Instantiates a new group thread entity.
     *
     * @param name            线程的名字
     * @param totalListCount  总数,list 的总size
     * @param eachSize        分配大小
     * @param batchNumber     批次,当前线程序号,从0开始
     * @param currentListSize 当前线程执行数量,=perBatchList size
     */
    public PartitionThreadEntity(String name, int totalListCount, int eachSize, int batchNumber, int currentListSize) {
        super();
        this.name = name;
        this.totalListCount = totalListCount;
        this.eachSize = eachSize;
        this.batchNumber = batchNumber;
        this.currentListSize = currentListSize;
    }

    //---------------------------------------------------------------

    /**
     * 获得 线程的名字.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * 获得 总数,list 的总size.
     *
     * @return the totalListCount
     */
    public int getTotalListCount() {
        return totalListCount;
    }

    /**
     * 获得 批次,当前线程序号,从0开始.
     *
     * @return the batchNumber
     */
    public int getBatchNumber() {
        return batchNumber;
    }

    /**
     * 获得 当前线程执行数量,=perBatchList size.
     *
     * @return the currentListSize
     */
    public int getCurrentListSize() {
        return currentListSize;
    }

    /**
     * 获得 分配大小.
     *
     * @return the eachSize
     */
    public int getEachSize() {
        return eachSize;
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}
