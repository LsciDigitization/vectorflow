package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 胡贤明
 */
@Data
@ToString(callSuper = true)
@ApiModel("HephaestusExperimentInstanceLoggingDTO")
public class HephaestusExperimentInstanceLoggingDTO {

    private String objectId;

    private String fetchTime;

    // 实验实例ID
    private Long instanceId;

    private String instanceName;

    // 实验ID
    private Long experimentId;

    private String experimentName;
    // 阶段ID
    private Long stageId;

    private String stageName;
    // 任务ID
    private Long taskId;

    private String taskName;
    // 所在线程
    private String thread;

    // 日志消息
    private Object message;

}
