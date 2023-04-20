package com.mega.hephaestus.pms.workflow.testutils;

import com.mega.hephaestus.pms.nuc.config.WorkProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TaskTimeRateService {

    @Resource
    private WorkProperties workProperties;

    /**
     * 时间缩放比例
     * @return 缩放比例, 任务时间率，最大100，默认100，最小1
     */
    public int getTaskTimeRate() {
        return workProperties.getTaskTimeRate();
    }

    /**
     * 缩小时间比例
     * @param source 原
     * @return 转换后的时间长度
     */
    public long getScaledDuration(int source) {
        double ceil = (long) Math.ceil(source * workProperties.getTaskTimeRate() / 100.0);
        return Double.valueOf(ceil).longValue();
    }

    /**
     * 缩小时间比例
     * @param source 原
     * @return 转换后的时间长度
     */
    public long getScaledDuration(long source) {
        double ceil = Math.ceil(source * workProperties.getTaskTimeRate() / 100.0);
        return Double.valueOf(ceil).longValue();
    }

    /**
     * 缩小时间比例
     * @param source 原
     * @return 转换后的时间长度
     */
    public long getScaledDuration(double source) {
        double ceil = Math.ceil(source * workProperties.getTaskTimeRate() / 100.0);
        return Double.valueOf(ceil).longValue();
    }

    /**
     * 还原时间比例
     * @param source 原
     * @return 转换后的时间长度
     */
    public long getRestoreDuration(int source) {
        double ceil = Math.ceil((double) source / workProperties.getTaskTimeRate() * 100.0);
        return Double.valueOf(ceil).longValue();
    }

    /**
     * 还原时间比例
     * @param source 原
     * @return 转换后的时间长度
     */
    public long getRestoreDuration(long source) {
        double ceil = Math.ceil((double) source / workProperties.getTaskTimeRate() * 100.0);
        return Double.valueOf(ceil).longValue();
    }

    /**
     * 还原时间比例
     * @param source 原
     * @return 转换后的时间长度
     */
    public long getRestoreDuration(double source) {
        double ceil = (long) Math.ceil(source / workProperties.getTaskTimeRate() * 100.0);
        return Double.valueOf(ceil).longValue();
    }

}
