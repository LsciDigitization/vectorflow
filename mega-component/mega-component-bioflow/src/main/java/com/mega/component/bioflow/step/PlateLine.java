package com.mega.component.bioflow.step;

import com.mega.component.nuc.plate.PlateType;
import lombok.Data;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/7 10:36
 */
@Data
public class PlateLine implements PlateType {

    private String id;
    private String name;
    private String description;
    private String plateKey;
    private String plateName;

    public PlateLine() {
    }

    public PlateLine(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public PlateLine(String id, String name, String description, String plateKey, String plateName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.plateKey = plateKey;
        this.plateName = plateName;
    }


    @Override
    public String name() {
        return plateKey;
    }

    @Override
    public String getLabel() {
        return plateName;
    }

    @Override
    public String getCode() {
        return plateKey;
    }
}
