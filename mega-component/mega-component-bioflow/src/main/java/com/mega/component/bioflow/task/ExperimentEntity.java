package com.mega.component.bioflow.task;

import com.mega.component.nuc.message.EnumConst;
import lombok.Data;

import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/23 17:03
 */
@Data
public class ExperimentEntity {

    /**
     * 实验ID
     */
    private ExperimentId id;

    private String experimentName;

    private String experimentDescription;

    private List<StageEntity> stages;


}
