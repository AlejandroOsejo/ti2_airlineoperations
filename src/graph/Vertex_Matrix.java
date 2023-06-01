package graph;

/**
 * Class name: Vertex_Matrix
 * General Description: This class extends the base class Vertex<T> and represents a vertex in an adjacency matrix in a graph. It has an additional parent field that is used in certain graph search and traversal algorithms.
 */
public class Vertex_Matrix<T> extends Vertex<T> {

    private Vertex_Matrix<T> parent;

    /**
     * Method: Vertex_Matrix - This constructor creates a Vertex_Matrix<T> object with the specified value. It calls the constructor of the base class Vertex<T> to initialize the vertex value.
     * @param value The value of the vertex.
     */
    public Vertex_Matrix(T value) {
        super(value);
    }

    /**
     * Method: getParent - This method returns the parent of the vertex in the context of certain graph search and traversal algorithms.
     * @return Vertex_Matrix<T> - Returns the Vertex_Matrix<T> object representing the parent of the vertex.
     */
    public Vertex_Matrix<T> getParent() {
        return this.parent;
    }

    /**
     * Method: setParent - This method establishes the parent vertex of the current vertex in the context of certain graph search and traversal algorithms.
     * @param parent The parent vertex to be established.
     */
    public void setParent(Vertex_Matrix<T> parent) {
        this.parent = parent;
    }

}
