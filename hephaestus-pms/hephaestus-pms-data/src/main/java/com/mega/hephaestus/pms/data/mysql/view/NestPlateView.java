package com.mega.hephaestus.pms.data.mysql.view;

import lombok.Data;

@Data
public class NestPlateView {

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
     * 描述
     */
    private String description;


    /**
     * 托架组ID
     */
    private Long deviceNestGroupId;


    /**
     * 位置
     */
    private String position;


    /**
     * 索引
     */
    private String nestIndex;


    /**
     * 状态
     */
    private String status;


    /**
     * 耗材组，逗号分割
     */
    private String labwares;


    /**
     * 耗材类型id
     */
    private Long labwareTypeId;


    /**
     * 设备key
     */
    private String deviceKey;

    /**
     * 板id
     */
    private Long plateId;

    /**
     * 板类型
     */
    private String plateType;

    /**
     * 条形码
     */
    private String barCode;
}
