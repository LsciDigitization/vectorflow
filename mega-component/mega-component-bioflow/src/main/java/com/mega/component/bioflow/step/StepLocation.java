package com.mega.component.bioflow.step;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/7 16:55
 */
public enum StepLocation {
    // start
    START,
    // end
    END,
    // middle
    MIDDLE,

    ;

    public static StepLocation toEnum(final String value) {
        return StepLocation.valueOf(value);
    }

}
