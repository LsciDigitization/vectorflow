package com.mega.hephaestus.pms.workflow.work.platehole;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/24 17:10
 */
public class NormalPlate extends AbstractPlate {

    private final WellType type;

    public NormalPlate(String id, int rows, int columns) {
        super(id, rows, columns);
        this.type = WellType.TYPE_CUSTOM;
    }

    public NormalPlate(String id, int rows, int columns, WellType type) {
        super(id, rows, columns);
        this.type = type;

        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= columns; col++) {
                String wellId = getWellId(row, col);
                this.wells.put(wellId, new Well(wellId, row, col));
            }
        }
    }

    public WellType getType() {
        return type;
    }

    private String getWellId(int row, int col) {
        switch (type) {
            case TYPE_8WELL:
                return String.format("%d", (row - 1) * 4 + col);
            default:
                return String.format("%c%d", 'A' + row - 1, col);
        }
    }

    @Override
    public Iterator<Well> iterator() {
        return new NormalPlateIterator();
    }

    public class NormalPlateIterator implements Iterator<Well> {
        private int index = 0;
        private List<Well> wellList = new ArrayList<>(wells.values());
        @Override
        public boolean hasNext() {
            return index < wellList.size();
        }
        @Override
        public Well next() {
            return wellList.get(index++);
        }
    }

    @Override
    public void process() {
        System.out.println("Processing normal plate...");
    }
}
