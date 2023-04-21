package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import lombok.Data;

import java.util.List;

@Data
public class DeviceNestGroupDTO {

    private Long id;

    /**
     * 项目ID
     */
    private Long projectId;


    /**
     * 名称
     */
    private String name;

    /**
     * 塔序号
     */
    private Integer towerNo;

    private List<DeviceNestResultDTO> nestList;

}
