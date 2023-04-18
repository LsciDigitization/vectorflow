package com.mega.component.bioflow.flow;

import lombok.Data;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/4 13:47
 */
@Data
public class Edge {
    private String id;
    private String source;
    private String target;
    private String label;
    private Type type;
    private MarkerEnd markerEnd;
    private boolean animated = true;
    private EdgeStyle style = new EdgeStyle();


    public Edge(String id, String source, String target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public Edge(String id, String source, String target, String label, Type type) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.label = label;
        this.type = type;
    }

    public Edge(String id, String source, String target, String label, Type type, MarkerEnd markerEnd) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.label = label;
        this.type = type;
        this.markerEnd = markerEnd;
    }

    public enum Type {
        STEP("step"),
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

    public enum MarkerType {
        Arrow("arrow"),
        ArrowClosed("arrowclosed"),
        ;

        private final String value;

        MarkerType(String value) {
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

    @Data
    public static class MarkerEnd {
        private MarkerType type;

        public MarkerEnd(MarkerType type) {
            this.type = type;
        }
    }

    @Data
    public static class EdgeStyle {
        private String stroke;
        private int strokeWidth = 2;
        private String strokeDasharray;
        private String strokeDashoffset;
        private String strokeLinecap;
        private String strokeOpacity;
        private String fill;
        private String fillOpacity;
        private String fillRule;
        private String markerEnd;

    }

}
