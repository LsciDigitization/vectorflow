package com.mega.component.mybatis.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 胡贤明
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BasePageDTO extends BaseDTO {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;
    /**
     * 当前页，默认第1页
     */
    @ApiModelProperty("当前页，默认第1页")
    private Integer pageNum = 1;
    /**
     * 每页显示条数，默认10条
     */
    @ApiModelProperty("每页显示条数，默认10条")
    private Integer pageSize = 10;

    @ApiModelProperty("更新人")
    private String updateName;
    @ApiModelProperty("创建人")

    private String createName;

    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private Long createBy;
}
