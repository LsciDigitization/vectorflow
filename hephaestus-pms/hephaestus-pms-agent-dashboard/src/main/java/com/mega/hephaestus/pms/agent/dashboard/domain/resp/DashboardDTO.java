package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("仪表盘DTO")
public class DashboardDTO {

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目状态")
    private String projectStatus;

    @ApiModelProperty("项目描述")
    private String projectDescription;

    @ApiModelProperty("相关链接")
    private List<Link> links;

    @Data
    @ApiModel("链接")
    public static class Link{

        @ApiModelProperty("链接地址")
        private String url;

        @ApiModelProperty("链接名称")
        private String linkName;
    }
}
