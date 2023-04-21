package com.mega.hephaestus.pms.agent.dashboard.domain.manager.model;

import lombok.Data;

@Data
public class ProcessModel {

    /**
     * 流程Id
     */
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型 dmpk CellPainting
     */
    private String type;
    /**
     * 状态  运行中 已完成
     */
    private String status;

    /**
     * 状态code  10 15 20
     */
    private String statusCode;
}
