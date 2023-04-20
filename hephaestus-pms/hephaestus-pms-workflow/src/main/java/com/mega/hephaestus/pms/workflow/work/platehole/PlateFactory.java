package com.mega.hephaestus.pms.workflow.work.platehole;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/24 17:08
 */
public class PlateFactory {
    public static NormalPlate create8WellPlate(String id) {
        return new PlateBuilder(id, WellType.TYPE_8WELL).build();
    }

    public static NormalPlate create96WellPlate(String id) {
        return new PlateBuilder(id, WellType.TYPE_96WELL).build();
    }

    public static NormalPlate create384WellPlate(String id) {
        return new PlateBuilder(id, WellType.TYPE_384WELL).build();
    }

    public static NormalPlate create1536WellPlate(String id) {
        return new PlateBuilder(id, WellType.TYPE_1536WELL).build();
    }
}
