package com.mega.hephaestus.pms.workflow.work.workplate;

public interface WorkPlateConsumer<T> {

    /**
     * 放入队列
     * @param anEntry 实体数据
     */
    void push(T anEntry);

    /**
     * 从队列中取出实体
     * @return 实体对象
     */
    T pop(long historyId);


    /**
     * 从队列中取出实体 指定序号
     * @return 实体对象
     */
    T pop(long historyId,int plateNo);
    /**
     * 剩余队列大小
     * @return 队列大小
     */
    long size(long historyId);

    /**
     * 完整队列大小
     * @return 队列大小
     */
    long fullSize(long historyId);


}
