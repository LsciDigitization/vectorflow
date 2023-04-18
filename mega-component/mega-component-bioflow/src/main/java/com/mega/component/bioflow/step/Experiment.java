package com.mega.component.bioflow.step;

import lombok.Data;

import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 15:42
 */
@Data
public class Experiment {
    private List<ExperimentStep> steps;
}
