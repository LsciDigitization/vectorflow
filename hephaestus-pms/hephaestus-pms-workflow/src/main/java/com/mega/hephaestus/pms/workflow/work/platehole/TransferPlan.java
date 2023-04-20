package com.mega.hephaestus.pms.workflow.work.platehole;

import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/3 19:30
 */
public class TransferPlan {

    private String id;
    private String name;
    private Plate sourcePlate;
    private Plate destinationPlate;
    private List<TransferAction> actions;

    public TransferPlan(String id, String name, Plate sourcePlate, Plate destinationPlate, List<TransferAction> actions) {
        this.id = id;
        this.name = name;
        this.sourcePlate = sourcePlate;
        this.destinationPlate = destinationPlate;
        this.actions = actions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Plate getSourcePlate() {
        return sourcePlate;
    }

    public Plate getDestinationPlate() {
        return destinationPlate;
    }

    public List<TransferAction> getActions() {
        return actions;
    }

}
