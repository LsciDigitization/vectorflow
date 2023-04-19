package com.mega.hephaestus.pms.data.runtime.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 开盖器定义
 *
 * @author xianming.hu
 */
@Data
@TableName("hephaestus_instance_cap")
public class InstanceCapEntity {

    /**
     * 数据编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 实例id
     */
    private Long instanceId;

    /**
     * 记录Id
     */
    private Long processRecordId;
    /**
     * deviceKey
     */
    private String deviceKey;

    /**
     * 设备类型
     */
    private String deviceType;


    /**
     * 开盖时间
     */
    private Date openCapTime;

    /**
     * 关闭时间
     */
    private Date closeCapTime;

    /**
     * 0 未开
     * 1 已开
     * 2 已关
     */
    private Integer deviceStatus;
}
