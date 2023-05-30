package graph;

import java.util.*;

public class Vertex_List<T> extends Vertex<T> {
    private Vertex_List<T> parent;
    private final Map<Vertex_List<T>, Integer> adjacent;

    public Vertex_List(T value) {
        super(value);
        this.adjacent = new HashMap<>();
    }


    public Vertex_List<T> getParent() {
        return this.parent;
    }

    public void setParent(Vertex_List<T> parent) {
        this.parent = parent;
    }

    public void addAdjacent(Vertex_List<T> vertexList, int weight) {
        this.adjacent.put(vertexList, weight);
    }

    public void removeAdjacent(Vertex_List<T> vertexList) {
        this.adjacent.remove(vertexList);
    }

    public Map<Vertex_List<T>, Integer> getAdjacent() {
        return this.adjacent;
    }

}
