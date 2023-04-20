package com.mega.hephaestus.pms.workflow.event;

import com.mega.component.nuc.device.DeviceType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.util.Date;

@Getter
public class BottleneckRequiredTimeOfSumEvent extends ApplicationEvent {

    private final DeviceType deviceType;

//    @Deprecated(since = "20230316")
//    private final PoolTypeEnum poolType;

    private final String poolType;

    private final Date calculatingTime;

    // 计算需要的时间总和
    private final long requiredTimeOfSum;

    private final BottleneckValue bottleneckValue;

    public BottleneckRequiredTimeOfSumEvent(DeviceType deviceType, String poolType, Date calculatingTime, long requiredTimeOfSum, BottleneckValue bottleneckValue) {
        super(deviceType);
        this.deviceType = deviceType;
        this.poolType = poolType;
        this.calculatingTime = calculatingTime;
        this.requiredTimeOfSum = requiredTimeOfSum;
        this.bottleneckValue = bottleneckValue;
    }

    @Builder
    @Getter
    @ToString
    public static class BottleneckValue {
        // Wait在瓶颈资源的所有任务数量
        private long waitTaskCount;

        // Wait在等待瓶颈资源的所有任务等待时间
        private long waitTimeDuration;

        // 瓶颈资源执行时间
        private long executionTime;

        // 到达瓶颈资源前的所有任务数量
        private long beforeTaskCount;

        // 瓶颈资源当前执行剩余时间（有多个则取平均值）
        private long averageTime;

        // 瓶颈资源数量
        private long deviceCount;

        // 瓶颈资源带板数
        private long deviceWithPlateNumber;

        // 历史ID
        private final Long processRecordId;
    }

    @Override
    public String toString() {
        return "BottleneckRequiredTimeOfSumEvent{" +
                "deviceType=" + deviceType +
                ", poolType=" + poolType +
                ", calculatingTime=" + calculatingTime +
                ", requiredTimeOfSum=" + requiredTimeOfSum +
                ", bottleneckValue=" + bottleneckValue +
                "} " + super.toString();
    }
}
