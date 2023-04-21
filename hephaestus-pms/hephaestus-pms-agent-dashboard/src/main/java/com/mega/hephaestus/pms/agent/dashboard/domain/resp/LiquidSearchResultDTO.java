package com.mega.hephaestus.pms.agent.dashboard.domain.resp;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("液体DTO")
public class LiquidSearchResultDTO {

    /**
     * 主键
     */
    private Long id;


    /**
     * 液体名称
     */
    private String name;

    /**
     * 液体描述
     */
    private String description;

    /**
     * 液体总容积
     */
    private Float volume;



}
