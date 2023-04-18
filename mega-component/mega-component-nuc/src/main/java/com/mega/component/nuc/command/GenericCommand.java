package com.mega.component.nuc.command;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/22 00:20
 */
public class GenericCommand implements Command {

    private final String name;

    public GenericCommand(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static GenericCommand toEnum(final String value) {
        return new GenericCommand(value);
    }

}
