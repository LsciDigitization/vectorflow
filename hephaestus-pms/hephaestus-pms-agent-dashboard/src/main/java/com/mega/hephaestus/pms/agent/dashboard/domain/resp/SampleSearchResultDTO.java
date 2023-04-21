package com.mega.hephaestus.pms.agent.dashboard.domain.resp;


import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel("样品DTO")
public class SampleSearchResultDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 样品名称
     */
    private String name;

    /**
     * 样品描述
     */
    private String description;

    /**
     * 样品类型
     */
    private String type;


}
