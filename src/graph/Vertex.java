package graph;

public class Vertex<T> {
    private final T value;
    private String color;
    private int distance;
    private int discoveryTime;
    private int finishingTime;

    public Vertex(T value) {
        this.value = value;
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


    @Override
    public String toString() {
        return this.value.toString();
    }
}
