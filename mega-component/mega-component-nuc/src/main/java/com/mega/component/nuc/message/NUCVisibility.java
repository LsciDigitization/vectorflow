package com.mega.component.nuc.message;

public enum NUCVisibility {

    Visible(0),
    WindowHidden(1<<0),
    NotifyIconHidden(1<<1)
    ;

    private final int code;

    NUCVisibility(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }
}
