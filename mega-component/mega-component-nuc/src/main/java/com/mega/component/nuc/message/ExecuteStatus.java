package com.mega.component.nuc.message;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExecuteStatus {

    Success("0", "Success"),
    Event("1", "Event"),
    Reject("2", "Reject"),
    Aborted("3", "Aborted")
    ;


    private final String code;
    private final String label;

    ExecuteStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return code;
    }

    @JsonValue
    public String toValue() {
        return code;
    }
}
