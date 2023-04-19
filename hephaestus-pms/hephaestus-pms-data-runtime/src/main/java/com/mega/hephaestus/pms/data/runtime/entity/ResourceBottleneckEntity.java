package com.mega.hephaestus.pms.data.runtime.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 设备瓶颈
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_resource_bottleneck")
public class ResourceBottleneckEntity {

    /**
     * 数据编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String deviceType;

    private String poolType;

    private Date calculatingTime;

    // 计算需要的时间总和
    private Long requiredTimeOfSum;

    // Wait在瓶颈资源的所有任务数量
    private Long waitTaskCount;
    // Wait在等待瓶颈资源的所有任务等待时间
    private long waitTimeDuration;

    // 瓶颈资源执行时间
    private Long executionTime;

    // 到达瓶颈资源前的所有任务 * （到达瓶颈资源前所需的时间 + 瓶颈资源执行时间）
    private Long beforeTaskCount;

    // 瓶颈资源当前执行剩余时间（有多个则取平均值）
    private Long averageTime;

    // 瓶颈资源数量
    private Long deviceCount;

    // 瓶颈资源带板数
    private Long deviceWithPlateNumber;

    /**
     * 记录Id
     */
    private Long processRecordId;
}
