package com.mega.component.bioflow.flow;


import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/7 14:41
 */
public interface StepTypeFilter<T> {

    List<T> getSteps();

    T getFirstStep();

    T getLastStep();

}
