package graph;

import java.util.*;

public class Vertex<T> {
    private final T value;
    private String color;
    private int distance;
    private int discoveryTime;
    private int finishingTime;
    private Vertex<T> parent;
    private final Map<Vertex<T>, Integer> adjacent;

    public Vertex(T value) {
        this.value = value;
        this.adjacent = new HashMap<>();
    }

    public T getValue() {
        return this.value;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDiscoveryTime() {
        return this.discoveryTime;
    }

    public void setDiscoveryTime(int discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    public int getFinishingTime() {
        return this.finishingTime;
    }

    public void setFinishingTime(int finishingTime) {
        this.finishingTime = finishingTime;
    }

    public Vertex<T> getParent() {
        return this.parent;
    }

    public void setParent(Vertex<T> parent) {
        this.parent = parent;
    }

    public void addAdjacent(Vertex<T> vertex, int weight) {
        this.adjacent.put(vertex, weight);
    }

    public void removeAdjacent(Vertex<T> vertex) {
        this.adjacent.remove(vertex);
    }

    public Map<Vertex<T>, Integer> getAdjacent() {
        return this.adjacent;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
