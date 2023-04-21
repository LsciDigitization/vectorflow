package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * @author xianming.hu
 * 实例动态DTO
 */
@Data
@ApiModel("实例动态")
public class HephaestusInstanceDynamicDTO{

    /**
     * 实例名称
     */
    @ApiModelProperty("实例名称")
    private String instanceTitle ="";

    private List<StageTaskEntityDTO> stageTaskList;

    @Data
    public static class StageTaskEntityDTO{

        private String stageName;

        private String stageTaskName;

        private String taskStatus;

        private Date taskStartTime;


    }


}
