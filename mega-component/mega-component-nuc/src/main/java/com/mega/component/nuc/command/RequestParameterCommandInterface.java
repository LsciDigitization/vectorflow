package com.mega.component.nuc.command;

public interface RequestParameterCommandInterface<T> extends RequestCommandInterface {

    T getParameters();

}
