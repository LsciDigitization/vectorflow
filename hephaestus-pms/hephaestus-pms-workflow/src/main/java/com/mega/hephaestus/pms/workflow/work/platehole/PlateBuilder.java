package com.mega.hephaestus.pms.workflow.work.platehole;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/24 17:08
 */
public class PlateBuilder {
    private final String id;
    private final WellType type;

    public PlateBuilder(String id, WellType type) {
        this.id = id;
        this.type = type;
    }

    public NormalPlate build() {
        switch (type) {
            case TYPE_8WELL:
                return new NormalPlate(id, 2, 4, type);
            case TYPE_96WELL:
                return new NormalPlate(id, 8, 12, type);
            case TYPE_384WELL:
                return new NormalPlate(id, 16, 24, type);
            case TYPE_1536WELL:
                return new NormalPlate(id, 32, 48, type);
            default:
                throw new IllegalArgumentException("Invalid plate type");
        }
    }
}
