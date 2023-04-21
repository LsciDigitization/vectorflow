package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import com.mega.component.mybatis.common.constant.BooleanEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("实例耗材DTO")
public class InstanceIterationDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 流程id
     */
    private Long processId;

    /**
     * 通量编号
     */
    private Integer iterationNo;

    /**
     *  消费表状态
     */
    private Long consumeId;



    /**
     * 是否消费 0 否 1 是
     */
    private BooleanEnum isConsumed;

    /**
     * 消费时间
     */
    private Date consumeTime;

    /**
     * 是否完成 0 否 1 是
     */
    private BooleanEnum isFinished;

    /**
     * 完成时间
     */
    private Date finishTime;

    List<LabwareDTO> labwares;

    @Data
    public static class LabwareDTO{

        private Long id;

        private String labwareType;

        private String labwareName;

        private String labwareColor;


    }


}
