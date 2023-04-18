package com.mega.component.bioflow.task;


/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/23 14:09
 */
public enum TaskAction {

    BeforeAction,
    Action,
    AfterAction,

    ;

    public static TaskAction toEnum(final String value) {
        return TaskAction.valueOf(value);
    }

}
