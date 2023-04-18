package com.mega.component.bioflow.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/4 14:40
 */
public class GraphNodeGroup {
    private List<GraphNode> nodes = new ArrayList<>();

    public GraphNodeGroup() {
    }

    public GraphNodeGroup(List<GraphNode> nodes) {
        this.nodes = nodes;
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public void addNode(GraphNode node) {
        nodes.add(node);
    }

    public void addNodes(List<GraphNode> nodes) {
        this.nodes.addAll(nodes);
    }

    public void removeNode(GraphNode node) {
        nodes.remove(node);
    }

    public void removeNodes(List<GraphNode> nodes) {
        this.nodes.removeAll(nodes);
    }

    /**
     * 将层级结构的GraphNode转换为扁平结构
     *
     * @return 扁平结构的节点列表
     */
    public List<FlatNode> toFlatNodes() {
        List<FlatNode> flatNodes = new ArrayList<>();
        nodes.forEach(node -> dfsToFlatNodes(node, flatNodes));
        return flatNodes;
    }

    /**
     * 深度优先遍历，将节点转换为扁平结构并加入列表中
     *
     * @param node      当前遍历的节点
     * @param flatNodes 扁平结构的节点列表
     */
    private void dfsToFlatNodes(GraphNode node, List<FlatNode> flatNodes) {
        flatNodes.add(convertGraphNodeToFlatNode(node));
        if (node.getChildren() != null) {
            for (GraphNode child : node.getChildren()) {
                child.setParentNode(node.getId());
                child.setExtent(GraphNode.Extent.PARENT);
                dfsToFlatNodes(child, flatNodes);
            }
        }
    }

    private String convertGraphNodeToString(GraphNode node) {
        return node.getId();
    }

    private FlatNode convertGraphNodeToFlatNode(GraphNode node) {
        return new FlatNode(
                node.getId(),
                node.getType(),
                node.getPosition(),
                node.getStyle(),
                node.getData(),
                node.getParentNode(),
                node.getExtent(),
                node.getTargetPosition(),
                node.getSourcePosition()
        );
    }

    @Override
    public String toString() {
        return "GraphNodeGroup{" +
                "nodes=" + nodes +
                '}';
    }
}
