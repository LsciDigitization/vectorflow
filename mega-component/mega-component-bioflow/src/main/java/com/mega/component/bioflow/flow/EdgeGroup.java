package com.mega.component.bioflow.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/4 18:29
 */
public class EdgeGroup {
    private List<Edge> edges = new ArrayList<>();

    public EdgeGroup() {
    }

    public EdgeGroup(List<Edge> edges) {
        this.edges = edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void addEdges(List<Edge> edges) {
        this.edges.addAll(edges);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public void removeEdges(List<Edge> edges) {
        this.edges.removeAll(edges);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return "EdgeGroup{" +
                "edges=" + edges +
                '}';
    }
}
