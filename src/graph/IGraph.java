package graph;

import java.util.Map;

public interface IGraph<T> {

    void addVertex(T vertex);

    void addEdge(T source, T destination, int weight);

    void removeVertex(T vertex);

    void removeEdge(T vertex1, T vertex2);

    void BFS(T source);

    void DFS(T source);

    Map<Vertex<T>, Vertex<T>> dijkstra(T source);

    Vertex<T>[][] floydWarshall();

    void prim(T source);
}
