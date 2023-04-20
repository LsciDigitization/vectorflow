package com.mega.hephaestus.pms.workflow.work.platehole;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/3/24 14:44
 */
public class Well {

    private String id;
    private int row;
    private int column;
    private Sample sample;

    public Well(String id, int row, int column) {
        this.id = id;
        this.row = row;
        this.column = column;
    }

//    public Well(String id, int row, int column) {
//        this.id = id;
//        this.row = String.valueOf(row);
//        this.column = column;
//    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Sample getSample() {
        return sample;
    }

    public void removeSample() {
        this.sample = null;
    }

}
