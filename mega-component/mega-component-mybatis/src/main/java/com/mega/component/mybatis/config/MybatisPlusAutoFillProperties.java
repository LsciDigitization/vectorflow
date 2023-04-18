package com.mega.component.mybatis.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus自动填充配置
 */
@Data
@Configuration
public class MybatisPlusAutoFillProperties {
    /**
     * 是否开启自动填充字段
     */
    private Boolean enabled = true;

    /**
     * 是否开启了插入填充
     */
    private Boolean enableInsertFill = true;

    /**
     * 是否开启了更新填充
     */
    private Boolean enableUpdateFill = true;

    /**
     * 主键字段名
     */
    private String idField = "id";

    /**
     * 状态字段名
     */
    private String isDeletedField = "isDeleted";

    /**
     * 租户字段名
     */
    private String tenantIdField = "tenantId";

    /**
     * 创建时间字段名
     */
    private String createTimeField = "createTime";

    /**
     * 创建人字段名
     */
    private String createByField = "createBy";

    /**
     * 更新时间字段名
     */
    private String updateTimeField = "updateTime";

    /**
     * 更新人字段名
     */
    private String updateByField = "updateBy";

    /**
     * 备注字段名
     */
    private String remarksField = "remarks";

    /**
     * 创建人名称字段名
     */
    private String createNameField = "createName";
    /**
     * 更新人名称字段名
     */
    private String updateNameField = "updateName";
}
