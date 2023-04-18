package com.mega.component.nuc.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BootConfig {

    @JsonProperty("IsBoot")
    private Boolean isBoot;

    @JsonProperty("NUCVisibility")
    private NUCVisibility nucVisibility;

}
