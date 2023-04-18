package com.mega.component.mybatis.common.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author 胡贤明
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("PageResult")
public class PageResult <T> implements Serializable {
    private static final long serialVersionUID = -275582248840137389L;
    /**
     * 数据总数
     */
    @ApiModelProperty("数据总数")
    private Long count;
    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private Long totalPage;
    /**
     * 当前页
     */
    @ApiModelProperty("总页数")
    private Long currentPage;
    /**
     * 每页数据条数
     */
    @ApiModelProperty("每页数据数")
    private Long pageSize;
    /**
     * 当前页结果集
     */
    @ApiModelProperty("当前页结果集")
    private List<T> list;
}