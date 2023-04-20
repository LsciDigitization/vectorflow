package com.mega.hephaestus.pms.workflow.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hephaestus.project")
@Data
public class ExperimentProperties {

    /**
     * 项目类型
     * dmpk, viablife，shiyao
     */
    private String code = "dmpk";

    /**
     * 流程Id
     */
    private Long processId = 0L;


}
