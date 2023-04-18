package com.mega.component.bioflow.step;

import lombok.Data;

import java.util.HashSet;
import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/6 21:06
 */
@Data
public class Branch {
    private String id;
    private String name;
    private String description;
    private String branchKey;
    private String branchName;
    private List<PlateLine> plates;

    public Branch() {
    }

    public Branch(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Branch(String id, String name, String description, String branchKey, String branchName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.branchKey = branchKey;
        this.branchName = branchName;
    }

    public void addPlate(PlateLine plate) {
        this.plates.add(plate);
    }

    public void addPlates(List<PlateLine> plates) {
        this.plates.addAll(plates);
    }

    public void removePlate(PlateLine plate) {
        this.plates.remove(plate);
    }

    public void removePlate(String plateKey) {
        this.plates.removeIf(plate -> plate.getCode().equals(plateKey));
    }

    public void removePlates(List<PlateLine> plates) {
        this.plates.removeAll(plates);
    }

    public boolean containsPlate(String plateKey) {
        return this.plates.stream().anyMatch(plate -> plate.getCode().equals(plateKey));
    }

    public boolean containsPlate(PlateLine plate) {
        return this.plates.contains(plate);
    }

    public boolean containsPlates(List<PlateLine> plates) {
        return new HashSet<>(this.plates).containsAll(plates);
    }
    
}
