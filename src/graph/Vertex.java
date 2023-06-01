package graph;

/**
 * Class name: Vertex
 * General Description: This class represents a vertex in a graph. It has additional fields such as color, distance, discoveryTime and finishingTime that are used in certain graph search and traversal algorithms.
 */
public class Vertex<T> {
    private final T value;
    private String color;
    private int distance;
    private int discoveryTime;
    private int finishingTime;

    /**
     * Method: Vertex - This constructor creates a Vertex<T> object with the specified value.
     * @param value The value of the vertex.
     */
    public Vertex(T value) {
        this.value = value;
    }

    /**
     * Method: getValue - This method returns the value of the vertex.
     * @return T
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Method: getColor - This method returns the color of the vertex.
     * @return String - Returns a string representing the color of the vertex.
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Method: setColor - This method sets the vertex color.
     * @param color The color to be set for the vertex.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Method: getDistance - This method establishes the distance associated with the vertex.
     * @return int - Returns an integer representing the distance associated with the vertex.
     */
    public int getDistance() {
        return this.distance;
    }

    /**
     * Method: setDistance - This method establishes the distance associated with the vertex.
     * @param distance The distance to be set for the vertex.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Method: getDiscoveryTime - This method returns the vertex discovery time.
     * @return int - Returns an integer representing the vertex discovery time.
     */
    public int getDiscoveryTime() {
        return this.discoveryTime;
    }

    /**
     * Method: setDiscoveryTime - This method sets the discovery time for the vertex.
     * @param discoveryTime The discovery time to be set for the vertex.
     */
    public void setDiscoveryTime(int discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    /**
     * Method: getFinishingTime - This method returns the finishing time of the vertex.
     * @return int - Returns an integer representing the finishing time of the vertex.
     */
    public int getFinishingTime() {
        return this.finishingTime;
    }

    /**
     * Method: setFinishingTime - This method sets the finishing time for the vertex.
     * @param finishingTime The finishing time to be set for the vertex.
     */
    public void setFinishingTime(int finishingTime) {
        this.finishingTime = finishingTime;
    }

    /**
     * Method: toString - This method returns a string representation of the vertex.
     * @return String - Returns a string representing the value of the vertex.
     */
    @Override
    public String toString() {
        return this.value.toString();
    }
}
