package com.mega.hephaestus.pms.workflow.work.platehole;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.function.Predicate;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/24 17:01
 */
public interface Plate extends Iterable<Well> {

    String getId();

    int getRows();

    int getColumns();

    void addWell(Well well);

    Well getWell(int row, int column);

    void removeWell(int row, int column);

    List<Well> findWells(Predicate<Well> condition);

    void process();


    public enum Type {
        NORMAL,
        GUN
    }

}
