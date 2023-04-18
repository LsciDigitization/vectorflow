package com.mega.component.mybatis.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author 胡贤明
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseFullDTO extends BaseDTO {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;
    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private Long createBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private Date updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(hidden = true)
    private Long updateBy;

    @ApiModelProperty("更新人")
    private String updateName;
    @ApiModelProperty("创建人")
    private String createName;


    @ApiModelProperty("备注")
    private String remarks;


    /**
     * 是否删除 0 否 1是
     */
    @ApiModelProperty("是否删除 0 否 1是")
    private String isDeleted;
}