package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import com.mega.hephaestus.pms.data.mysql.entity.LabwareWellEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HoleDataDTO {

    // 动态key
    private List<String> keys;

    // 对应数据
    private List<Map<String, LabwareWellEntity>> dataSources;

    private String plateName;

    private String plateKey;

    private Integer holeRows;

    private Integer holeCols;


}
