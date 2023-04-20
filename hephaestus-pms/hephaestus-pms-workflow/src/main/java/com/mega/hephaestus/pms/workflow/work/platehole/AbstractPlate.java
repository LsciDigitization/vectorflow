package com.mega.hephaestus.pms.workflow.work.platehole;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/24 17:06
 */
public abstract class AbstractPlate implements Plate {

    protected String id;
    protected int rows;
    protected int columns;
    protected Map<String, Well> wells;

    public AbstractPlate(String id, int rows, int columns) {
        this.id = id;
        this.rows = rows;
        this.columns = columns;
        this.wells = new HashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public void addWell(Well well) {
        String position = well.getRow() + "-" + well.getColumn();
        wells.put(position, well);
    }

    @Override
    public void removeWell(int row, int column) {
        String position = row + "-" + column;
        Well well = wells.remove(position);
    }

    @Override
    public Well getWell(int row, int column) {
        String position = row + "-" + column;
        return wells.get(position);
    }

    @Override
    public List<Well> findWells(Predicate<Well> condition) {
        List<Well> result = new ArrayList<>();

        for (Well well : wells.values()) {
            if (condition.test(well)) {
                result.add(well);
            }
        }

        return result;
    }

    public void forEachWell(Consumer<Well> action) {
        for (Well well : wells.values()) {
            action.accept(well);
        }
    }

    public Iterator<Well> iterator() {
        return wells.values().iterator();
    }

    private String getWellId(int row, int col) {
        return String.format("%c%d", 'A' + row - 1, col);
    }

    public void printPlateMatrix() {
        System.out.print(" ");
        for (int col = 1; col <= columns; col++) {
            System.out.print(" " + col + " ");
        }
        System.out.println();
        for (int row = 1; row <= rows; row++) {
            System.out.print((char)('A' + row - 1) + " ");
            for (int col = 1; col <= columns; col++) {
                String wellId = getWellId(row, col);
                if (wells.get(wellId).getSample() == null) {
                    System.out.print("[ ]");
                } else {
                    System.out.print("[X]");
                }
            }
            System.out.println();
        }
    }

    public void addSample(String wellId, Sample sample) {
        this.wells.get(wellId).setSample(sample);
    }

    public void removeSample(String wellId) {
        this.wells.get(wellId).removeSample();
    }

    public Sample getSample(String wellId) {
        return this.wells.get(wellId).getSample();
    }

}
