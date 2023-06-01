package graph;

import java.util.*;

/**
 * Class name: Vertex_List
 * General Description: This class extends the base class Vertex<T> and represents a vertex in an adjacency list in a graph. It has additional parent and adjacent fields that are used in certain graph search and traversal algorithms.
 */
public class Vertex_List<T> extends Vertex<T> {
    private Vertex_List<T> parent;
    private final Map<Vertex_List<T>, Integer> adjacent;

    /**
     * Method: Vertex_List - This constructor creates a Vertex_List<T> object with the specified value. It calls the constructor of the base class Vertex<T> to initialize the vertex value. It also initializes the adjacent field as a new HashMap.
     * @param value The value of the vertex.
     */
    public Vertex_List(T value) {
        super(value);
        this.adjacent = new HashMap<>();
    }

    /**
     * Method: getParent - This method returns the parent of the vertex in the context of certain graph search and traversal algorithms.
     * @return Vertex_List<T> - Returns the Vertex_List<T> object representing the parent of the vertex.
     */
    public Vertex_List<T> getParent() {
        return this.parent;
    }

    /**
     * Method: setParent - This method establishes the parent vertex of the current vertex in the context of certain graph search and traversal algorithms.
     * @param parent The parent vertex to be established.
     */
    public void setParent(Vertex_List<T> parent) {
        this.parent = parent;
    }

    /**
     * Method: addAdjacent - This method adds a vertex adjacent to the current vertex in the adjacency list.
     * @param vertexList The adjacent vertex to be added.
     * @param weight The weight of the edge between the current vertex and the adjacent vertex.
     */
    public void addAdjacent(Vertex_List<T> vertexList, int weight) {
        this.adjacent.put(vertexList, weight);
    }

    /**
     * Method: removeAdjacent - This method removes an adjacent vertex from the current vertex in the adjacency list.
     * @param vertexList The adjacent vertex to be removed.
     */
    public void removeAdjacent(Vertex_List<T> vertexList) {
        this.adjacent.remove(vertexList);
    }

    /**
     * Method: getAdjacent - This method returns a map representing the adjacent vertices of the current vertex, along with the edge weights.
     * @return Map<Vertex_List<T>, Integer> - Returns a map of type Map<Vertex_List<T>, Integer>, where the keys are the adjacent vertices and the values are the weights of the edges connecting them to the current vertex.
     */
    public Map<Vertex_List<T>, Integer> getAdjacent() {
        return this.adjacent;
    }

}
