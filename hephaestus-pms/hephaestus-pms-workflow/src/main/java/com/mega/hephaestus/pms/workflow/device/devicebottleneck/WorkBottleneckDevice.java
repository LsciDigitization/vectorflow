package com.mega.hephaestus.pms.workflow.device.devicebottleneck;

public interface WorkBottleneckDevice {

    /**
     * Wait在瓶颈资源的所有任务数量
     */
    int getBottleneckWaitTaskCount();

    /**
     * Wait在瓶颈资源的所有任务的平均等待时间
     * 或 取上一组任务的Wait在瓶颈资源的所有任务的平均等待时间
     * @return 平均等待时间
     */
    long getBottleneckWaitTimeDuration();

    /**
     * 瓶颈资源执行时间
     */
    long getBottleneckExecutionTimeDuration();

    /**
     * 到达瓶颈资源前的所有任务
     */
    int getBottleneckBeforeTaskCount();

    /**
     * 瓶颈资源当前执行剩余时间（有多个则取平均值）
     */
    long getBottleneckAverageRemainTimeDuration();

    /**
     * 瓶颈资源数量
     */
    int getBottleneckDeviceCount();

    /**
     * 瓶颈资源带板数
     */
    int getBottleneckWithPlateNumber();

    /**
     * 启动新线程抵达瓶颈资源所需时间
     */
    long getBottleneckArrivalTimeDuration();

    /**
     * 计算启动所需时间
     */
    double requiredStartTime();

}
