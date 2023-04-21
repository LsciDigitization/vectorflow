package com.mega.hephaestus.pms.agent.dashboard.domain.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author 胡贤明
 * 最近一次实验数据
 */
@Data
@ToString(callSuper = true)
@ApiModel("HephaestusLastInstanceInfoDTO")
public class HephaestusLastInstanceInfoDTO {

    private String id;

    private String name;

    private List<StageTaskInfo> children;

    @Data
    public static class StageTaskInfo{

        private String id;

        private String name;

        private Date start;

        private Date end;

        private String parentId;

        private int status;
    }
}
