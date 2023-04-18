package com.mega.component.bioflow.task;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/23 11:44
 */
public class StepKey {

    private final String name;

    public StepKey(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static StepKey toEnum(final String value) {
        return new StepKey(value);
    }

}
