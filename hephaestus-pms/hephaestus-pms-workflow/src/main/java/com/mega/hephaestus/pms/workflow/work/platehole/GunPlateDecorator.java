package com.mega.hephaestus.pms.workflow.work.platehole;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/24 17:02
 */
public class GunPlateDecorator extends AbstractPlate {

    private Plate plate;

    public GunPlateDecorator(Plate plate) {
        super(plate.getId(), plate.getRows(), plate.getColumns());
        this.plate = plate;
    }

    @Override
    public void addWell(Well well) {
        plate.addWell(well);
    }

    @Override
    public Well getWell(int row, int column) {
        return plate.getWell(row, column);
    }

    @Override
    public void removeWell(int row, int column) {
        plate.removeWell(row, column);
    }

    @Override
    public List<Well> findWells(Predicate<Well> condition) {
        return plate.findWells(condition);
    }

    @Override
    public void process() {
        System.out.println("Firing the gun...");
        plate.process();
    }

    @Override
    public Iterator<Well> iterator() {
        return new GunPlateIterator();
    }
    private class GunPlateIterator implements Iterator<Well> {
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

}
