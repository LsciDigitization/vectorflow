package com.mega.hephaestus.pms.workflow.work.platehole;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/3 19:31
 */
public class TransferAction {
    private Well sourceWell;
    private Well destinationWell;
    private double volume;
    private String unit;

    public TransferAction(Well sourceWell, Well destinationWell, double volume, String unit) {
        this.sourceWell = sourceWell;
        this.destinationWell = destinationWell;
        this.volume = volume;
        this.unit = unit;
    }

    public Well getSourceWell() {
        return sourceWell;
    }

    public Well getDestinationWell() {
        return destinationWell;
    }

    public double getVolume() {
        return volume;
    }

    public String getUnit() {
        return unit;
    }
}
