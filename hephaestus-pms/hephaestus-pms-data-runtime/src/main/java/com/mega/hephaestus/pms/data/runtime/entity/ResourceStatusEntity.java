package com.mega.hephaestus.pms.data.runtime.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hephaestus_resource_status")
public class ResourceStatusEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 记录ID
     */
    private Long processRecordId;
    /**
     * 设备key
     */
    private String deviceKey;

    /**
     * 状态
     */
    private String resourceStatus;
}
