package graph;

import java.util.ArrayList;
import java.util.Map;

public class GraphAdjacencyMatrix<T> implements IGraph<T> {
    private final ArrayList<Vertex_Matrix<T>> vertices;
    private final int[][] adjacencyMatrix;
    private final boolean directed;

    public GraphAdjacencyMatrix(boolean directed) {
        this.vertices = new ArrayList<>();
        this.adjacencyMatrix = new int[vertices.size()][vertices.size()];
        this.directed = directed;
    }

    @Override
    public void addVertex(T vertex) {
        throw new UnsupportedOperationException("No se pueden añadir vértices directamente a la matriz de adyacencia. Inicializa la matriz con todos los vértices a la vez.");
    }

    @Override
    public void addEdge(T source, T destination, int weight) {
        int sourceIndex = getIndex(source);
        int destinationIndex = getIndex(destination);

        if (sourceIndex == -1 || destinationIndex == -1) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen en el grafo.");
        }

        adjacencyMatrix[sourceIndex][destinationIndex] = weight;

        if (!directed) {
            adjacencyMatrix[destinationIndex][sourceIndex] = weight;
        }
    }

    @Override
    public void removeVertex(T vertex) {
        throw new UnsupportedOperationException("No se puede eliminar un vértice directamente de la matriz de adyacencia. Considera crear una nueva matriz en su lugar.");
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int vertex1Index = getIndex(vertex1);
        int vertex2Index = getIndex(vertex2);

        if (vertex1Index == -1 || vertex2Index == -1) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen en el grafo.");
        }

        adjacencyMatrix[vertex1Index][vertex2Index] = 0;

        if (!directed) {
            adjacencyMatrix[vertex2Index][vertex1Index] = 0;
        }
    }

    @Override
    public void BFS(T source) {
        // Implementar BFS
    }

    @Override
    public void DFS(T source) {
        // Implementar DFS
    }

    @Override
    public Map<Vertex<T>, Vertex<T>> dijkstra(T source) {
        // Implementar Dijkstra
        return null;
    }

    @Override
    public Vertex<T>[][] floydWarshall() {
        // Implementar Floyd-Warshall
        return null;
    }

    @Override
    public void prim(T source) {
        // Implementar Prim
    }

    private int getIndex(T vertex) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(vertex)) {
                return i;
            }
        }
        return -1;
    }
}
