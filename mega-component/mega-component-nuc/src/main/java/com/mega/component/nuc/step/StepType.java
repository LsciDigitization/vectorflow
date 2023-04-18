package com.mega.component.nuc.step;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/30 11:19
 */
public interface StepType {

    String name();

    String toString();

    String getLabel();

    String getCode();

    long getValue();

    long getStepTotal();

}
