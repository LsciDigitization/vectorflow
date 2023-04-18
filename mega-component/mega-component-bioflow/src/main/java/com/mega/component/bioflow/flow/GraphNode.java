package com.mega.component.bioflow.flow;

import lombok.Data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

@Data
public class GraphNode {
    private String id;
    private Type type;
    private Point position;
    private Style style;
    private Data data;
    private String parentNode;
    private Extent extent;
    private CustomData customData;
    private Position targetPosition = Position.TOP;
    private Position sourcePosition = Position.BOTTOM;
    private List<GraphNode> children;

    public GraphNode(String id, Type type, Point position, Style style, Data data, String parentNode, Extent extent) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.style = style;
        this.data = data;
        this.parentNode = parentNode;
        this.extent = extent;
        this.children = new ArrayList<>();
    }

    public GraphNode(String id, Type type, Point position, Style style, Data data, String parentNode, Extent extent, CustomData customData, Position targetPosition, Position sourcePosition) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.style = style;
        this.data = data;
        this.parentNode = parentNode;
        this.extent = extent;
        this.customData = customData;
        this.targetPosition = targetPosition;
        this.sourcePosition = sourcePosition;
    }

    // methods to add and remove children

    public void addChild(GraphNode child) {
        this.children.add(child);
    }

    public void removeChild(GraphNode child) {
        this.children.remove(child);
    }

    // inner classes for style and data

    /**
     * @author wangzhengdong
     * @version 1.0
     * @date 2023/4/4 13:47
     */
    @lombok.Data
    public static class Data {
        private String label;

        public Data(String label) {
            this.label = label;
        }

    }

    @lombok.Data
    public static class CustomData {

        private String plateKey;

        public CustomData(String plateKey) {
            this.plateKey = plateKey;
        }
    }

    /**
     * @author wangzhengdong
     * @version 1.0
     * @date 2023/4/4 13:46
     */
    @lombok.Data
    public static class Style {
        private int width;
        private int height;

        public Style(int width, int height) {
            this.width = width;
            this.height = height;
        }

    }

    public enum Type {
        GROUP("group"),
        INPUT("input"),
        OUTPUT("output"),
        DEFAULT("default"),
        ;

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Extent {
        PARENT("parent"),
        NONE("none"),
        ;

        private final String value;

        Extent(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Position {
        TOP("top"),
        BOTTOM("bottom"),
        LEFT("left"),
        RIGHT("right");
        ;

        private final String value;

        Position(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
