package com.mega.hephaestus.pms.workflow.work.platehole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/24 16:58
 */
public class GunPlate extends AbstractPlate {
    private GunPlateType type;

    public GunPlate(String id, int rows, int columns, GunPlateType type) {
        super(id, rows, columns);
        this.type = type;
    }

    public GunPlateType getType() {
        return type;
    }

    @Override
    public void process() {
        System.out.println("Processing gun plate...");
    }
}
