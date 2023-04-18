package com.mega.component.task.task;

public class SystemOutLogging implements TaskLogger {

    @Override
    public void info(Object o) {
        System.out.println(o);
    }
}
