package com.mega.component.nuc.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Plate {

    @JsonProperty("PlateId")
    private Integer plateId;

    @JsonProperty("PlateType")
    private String plateType;

    @JsonProperty("PlateBarCode")
    private String plateBarCode;

    @JsonProperty("PlateName")
    private String plateName;

    @JsonProperty("PlateInDatetime")
    private String plateInDatetime;

    @JsonProperty("PlateOutDatetime")
    private String plateOutDatetime;

    @JsonProperty("PlateWarnings")
    private String plateWarnings;

    @JsonProperty("Slot")
    private String slot;

    @JsonProperty("Level")
    private String level;

}
