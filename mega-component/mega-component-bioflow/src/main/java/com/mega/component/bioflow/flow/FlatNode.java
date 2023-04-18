package com.mega.component.bioflow.flow;

import lombok.Data;

import java.awt.*;

/**
 * 扁平结构的GraphNode节点
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/4 16:08
 */
@Data
public class FlatNode {
    private String id;
    private GraphNode.Type type;
    private Point position;
    private GraphNode.Style style;
    private GraphNode.Data data;
    private String parentNode;
    private GraphNode.Extent extent;
    private GraphNode.Position targetPosition;
    private GraphNode.Position sourcePosition;

    public FlatNode() {
    }

    public FlatNode(String id, GraphNode.Type type, Point position, GraphNode.Style style, GraphNode.Data data, String parentNode, GraphNode.Extent extent) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.style = style;
        this.data = data;
        this.parentNode = parentNode;
        this.extent = extent;
    }

    public FlatNode(String id, GraphNode.Type type, Point position, GraphNode.Style style, GraphNode.Data data, String parentNode, GraphNode.Extent extent, GraphNode.Position targetPosition, GraphNode.Position sourcePosition) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.style = style;
        this.data = data;
        this.parentNode = parentNode;
        this.extent = extent;
        this.targetPosition = targetPosition;
        this.sourcePosition = sourcePosition;
    }
}
