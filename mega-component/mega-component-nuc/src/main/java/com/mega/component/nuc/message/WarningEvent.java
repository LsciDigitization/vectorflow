package com.mega.component.nuc.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WarningEvent extends EventBase {

    //是否有Warning
    @JsonProperty("Flag")
    private Boolean flag;

}
