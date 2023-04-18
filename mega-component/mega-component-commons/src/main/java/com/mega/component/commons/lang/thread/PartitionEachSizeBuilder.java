package com.mega.component.commons.lang.thread;

/**
 * 用来计算 each size 大小的.
 *
 * @since 2.0.0
 */
public interface PartitionEachSizeBuilder {

    /**
     * Builds the.
     *
     * @param totalSize the total size
     * @return 如果 totalSize{@code <=}0 是empty,抛出 {@link IllegalArgumentException}<br>
     */
    int build(int totalSize);
}
