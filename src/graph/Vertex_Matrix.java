package graph;

public class Vertex_Matrix<T> extends Vertex<T> {

    private Vertex_Matrix<T> parent;

    public Vertex_Matrix(T value) {
        super(value);
    }


    public Vertex_Matrix<T> getParent() {
        return this.parent;
    }

    public void setParent(Vertex_Matrix<T> parent) {
        this.parent = parent;
    }

}
