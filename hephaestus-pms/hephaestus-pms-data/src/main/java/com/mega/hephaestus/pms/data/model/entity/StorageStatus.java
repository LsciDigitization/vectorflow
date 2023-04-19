package com.mega.hephaestus.pms.data.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hephaestus_resource_storage_status")
public class StorageStatus {

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
     * 资源key
     */
    private String storageKey;

    /**
     * 设备key
     */
    private String deviceKey;

    /**
     * 实例id
     */
    private Long instanceId;
}
