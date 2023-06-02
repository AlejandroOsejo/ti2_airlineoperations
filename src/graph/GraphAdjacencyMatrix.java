package graph;

import java.util.*;

/**
 * Class name: GraphAdjacencyMatrix
 * General Description: An implementation of the IGraph<T> interface that represents a graph using an adjacency matrix.
 */
public class GraphAdjacencyMatrix<T> implements IGraph<T> {
    private final ArrayList<Vertex_Matrix<T>> vertices;
    private int[][] adjacencyMatrix;
    private final boolean directed;
    private int time;

    /**
     * Method: GraphAdjacencyMatrix - Creates an instance of the GraphAdjacencyMatrix class.
     * @param directed A boolean value indicating whether the graph is directed (true) or undirected (false).
     */
    public GraphAdjacencyMatrix(boolean directed) {
        this.vertices = new ArrayList<>();
        this.adjacencyMatrix = new int[0][0];
        this.directed = directed;
    }

    /**
     * Method: addVertex - Adds a new vertex to the network.
     * @param vertex The vertex to be added to the network.
     */
    @Override
    public void addVertex(T vertex) {
        if (getVertex(vertex) != null) {
            throw new IllegalArgumentException("Vertex already exists " + vertex);
        }

        vertices.add(new Vertex_Matrix<>(vertex));

        int[][] newAdjacencyMatrix = new int[vertices.size()][vertices.size()];

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newAdjacencyMatrix[i], 0, adjacencyMatrix[i].length);
        }

        this.adjacencyMatrix = newAdjacencyMatrix;
    }

    /**
     * Method: addEdge - Adds an edge to the network between the source vertex and the destination vertex.
     * @param source The source vertex of the edge.
     * @param destination The destination vertex of the edge.
     * @param weight The weight or cost of the edge.
     */
    @Override
    public void addEdge(T source, T destination, int weight) {
        int sourceIndex = getIndex(source);
        int destinationIndex = getIndex(destination);

        if (sourceIndex == -1 || destinationIndex == -1) {
            throw new IllegalArgumentException("Vertex does not exist " + source + " " + destination);
        }

        if (adjacencyMatrix[sourceIndex][destinationIndex] != 0) {
            throw new IllegalArgumentException("Edge already exists");
        }

        adjacencyMatrix[sourceIndex][destinationIndex] = weight;

        if (!directed) {
            adjacencyMatrix[destinationIndex][sourceIndex] = weight;
        }
    }

    /**
     * Method: removeVertex - Removes a vertex and all its associated edges from the network.
     * @param vertex The vertex to be removed from the network.
     */
    @Override
    public void removeVertex(T vertex) {
        int vertexIndex = getIndex(vertex);

        if (vertexIndex == -1) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        vertices.remove(vertexIndex);

        for (int i = 0; i < vertices.size(); i++) {
            adjacencyMatrix[vertexIndex][i] = 0;
            adjacencyMatrix[i][vertexIndex] = 0;
        }

        int[][] newAdjacencyMatrix = new int[vertices.size()][vertices.size()];

        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                newAdjacencyMatrix[i][j] = adjacencyMatrix[i][j];
            }
        }

        this.adjacencyMatrix = newAdjacencyMatrix;
    }

    /**
     * Method: removeEdge - Removes the edge between the source vertex and the target vertex of the network.
     * @param vertex1 The source vertex of the edge.
     * @param vertex2 The target vertex of the edge.
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int vertex1Index = getIndex(vertex1);
        int vertex2Index = getIndex(vertex2);

        if (vertex1Index == -1 || vertex2Index == -1) {
            throw new IllegalArgumentException("Vertex does not exist " + vertex1 + " " + vertex2);
        }

        if (adjacencyMatrix[vertex1Index][vertex2Index] == 0) {
            throw new IllegalArgumentException("Edge does not exist");
        }

        adjacencyMatrix[vertex1Index][vertex2Index] = 0;

        if (!directed) {
            adjacencyMatrix[vertex2Index][vertex1Index] = 0;
        }
    }

    /**
     * Method: BFS - Performs a BFS (Breadth-First Search) path in the network starting from the specified starting vertex.
     * @param source The starting vertex of the BFS path.
     */
    @Override
    public void BFS(T source) {
        Vertex_Matrix<T> vertex = getVertex(source);

        if (vertex == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        for (Vertex_Matrix<T> v : vertices) {
            if (!v.equals(vertex)) {
                v.setColor("White");
                v.setDistance(Integer.MAX_VALUE);
                v.setParent(null);
            }
        }

        vertex.setColor("Gray");
        vertex.setDistance(0);
        vertex.setParent(null);

        Queue<Vertex_Matrix<T>> queue = new LinkedList<>();
        queue.offer(vertex);

        while (!queue.isEmpty()) {
            Vertex_Matrix<T> u = queue.poll();
            for (int i = 0; i < vertices.size(); i++) {
                if (adjacencyMatrix[vertices.indexOf(u)][i] != 0) {
                    Vertex_Matrix<T> v = vertices.get(i);
                    if (v.getColor().equals("White")) {
                        v.setColor("Gray");
                        v.setDistance(u.getDistance() + 1);
                        v.setParent(u);
                        queue.offer(v);
                    }
                }
            }
            u.setColor("Black");
        }
    }

    /**
     * Method: DFS - Performs a DFS (Depth-First Search) path in the network, starting from the origin vertex.
     * @param source The value of the origin vertex.
     */
    @Override
    public void DFS(T source) {
        Vertex_Matrix<T> s = getVertex(source);
        if (s == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        for (Vertex_Matrix<T> u : this.vertices) {
            u.setColor("white");
            u.setParent(null);
        }

        this.time = 0;

        DFSVisit(s);
    }

    /**
     * Method: DFSVisit - Performs a DFS (Depth-First Search) path by visiting the starting vertex and exploring all its adjacent vertices recursively. all its adjacent vertices recursively.
     * @param start The starting vertex to perform the DFS path.
     */
    private void DFSVisit(Vertex_Matrix<T> start) {
        this.time++;
        start.setDiscoveryTime(this.time);
        start.setColor("gray");

        for (int i = 0; i < vertices.size(); i++) {
            if (adjacencyMatrix[vertices.indexOf(start)][i] != 0) {
                Vertex_Matrix<T> v = vertices.get(i);
                if (v.getColor().equals("white")) {
                    v.setParent(start);
                    DFSVisit(v);
                }
            }
        }

        start.setColor("black");
        this.time++;
        start.setFinishingTime(this.time);
    }

    /**
     * Method: dijkstra - Calculates the minimum distances from the source vertex to all other vertices using Dijkstra's algorithm. Returns a map containing each vertex and its parent vertex on the shortest path.
     * @param source The source vertex from which the algorithm starts.
     * @return Map<Vertex<T>, Vertex<T>> - A map containing each vertex and its respective parent on the shortest path.
     */
    @Override
    public Map<Vertex<T>, Vertex<T>> dijkstra(T source) {
        Vertex_Matrix<T> s = getVertex(source);
        if (s == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        for (Vertex_Matrix<T> u : this.vertices) {
            u.setDistance(Integer.MAX_VALUE);
            u.setParent(null);
        }

        s.setDistance(0);

        PriorityQueue<Vertex_Matrix<T>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex_Matrix::getDistance));
        queue.offer(s);

        while (!queue.isEmpty()) {
            Vertex_Matrix<T> u = queue.poll();
            for (int i = 0; i < vertices.size(); i++) {
                if (adjacencyMatrix[vertices.indexOf(u)][i] != 0) {
                    Vertex_Matrix<T> v = vertices.get(i);
                    if (v.getDistance() > u.getDistance() + adjacencyMatrix[vertices.indexOf(u)][i]) {
                        v.setDistance(u.getDistance() + adjacencyMatrix[vertices.indexOf(u)][i]);
                        v.setParent(u);
                        queue.remove(v);
                        queue.offer(v);
                    }
                }
            }
        }

        Map<Vertex<T>, Vertex<T>> map = new HashMap<>();
        for (Vertex_Matrix<T> v : vertices) {
            map.put(v, v.getParent());
        }

        return map;
    }

    /**
     * Method: floydWarshall - This method uses the Floyd-Warshall algorithm to find all shortest paths between all pairs of vertices in a weighted graph.
     * @return Vertex_Matriz<T>[][] - A vertex array representing the previous vertices in the shortest paths encountered.
     */
    @Override
    public Vertex_Matrix<T>[][] floydWarshall() {
        int n = vertices.size();
        int[][] dist = new int[n][n];
        Vertex_Matrix<T>[][] prev = new Vertex_Matrix[n][n];

        for (int i = 0; i < n; i++) {
            dist[i] = adjacencyMatrix[i].clone();
            for (int j = 0; j < n; j++) {
                if (dist[i][j] != 0) {
                    prev[i][j] = vertices.get(i);
                }
            }
        }

        for (int k = 0; k < n; k++) {
            int[][] dk = new int[n][n];
            Vertex_Matrix<T>[][] pk = new Vertex_Matrix[n][n];

            for (int i = 0; i < n; i++) {
                dk[i] = dist[i].clone();
                pk[i] = prev[i].clone();
            }

            for (int i = 0; i < n; i++) {
                if (i == k || dk[i][k] == 0) {
                    continue;
                }
                for (int j = 0; j < n; j++) {
                    if (j == k || dk[k][j] == 0) {
                        continue;
                    }
                    if (dk[i][j] == 0 || dk[i][j] > dk[i][k] + dk[k][j]) {
                        dk[i][j] = dk[i][k] + dk[k][j];
                        pk[i][j] = pk[k][j];
                    }
                }
            }

            dist = dk;
            prev = pk;
        }

        return prev;
    }

    /**
     * Method: prim - This method implements Prim's algorithm to find a minimum spanning tree in an undirected weighted graph.
     * @param source The value of the source vertex from which the Prim algorithm will start.
     */
    @Override
    public void prim(T source) {
        Vertex_Matrix<T> s = getVertex(source);
        if (s == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        for (Vertex_Matrix<T> u : this.vertices) {
            u.setDistance(Integer.MAX_VALUE);
            u.setParent(null);
            u.setColor("white");
        }

        s.setDistance(0);
        s.setParent(null);

        PriorityQueue<Vertex_Matrix<T>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex_Matrix::getDistance));
        queue.addAll(this.vertices);

        while (!queue.isEmpty()) {
            Vertex_Matrix<T> u = queue.poll();
            for (int i = 0; i < vertices.size(); i++) {
                if (adjacencyMatrix[vertices.indexOf(u)][i] != 0) {
                    Vertex_Matrix<T> v = vertices.get(i);
                    if (queue.contains(v) && adjacencyMatrix[vertices.indexOf(u)][i] < v.getDistance()) {
                        v.setDistance(adjacencyMatrix[vertices.indexOf(u)][i]);
                        v.setParent(u);
                        queue.remove(v);
                        queue.offer(v);
                    }
                }
            }
            u.setColor("black");
        }
    }

    /**
     * Method: getIndex - This method searches for the index of a vertex in the list of vertices of the network.
     * @param vertex The value of the vertex to search for.
     * @return int - The index of the vertex in the vertex list, or -1 if the vertex is not in the list.
     */
    private int getIndex(T vertex) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getValue().equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Method: getVertex - Returns the index of a vertex in the vertex list.
     * @param vertex The vertex for which the index is sought.
     * @return Vertex_Matrix<T> - The index of the vertex in the vertex list, or -1 if the vertex is not found in the list.
     */
    public Vertex_Matrix<T> getVertex(T vertex) {
        for (Vertex_Matrix<T> v : vertices) {
            if (v.getValue().equals(vertex)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Method: isDirected - This method returns a boolean value indicating whether the network is directed or undirected.
     * @return boolean - true if the network is directed, false if it is not.
     */
    public boolean isDirected() {
        return this.directed;
    }

    /**
     * Method: getVertices - This method returns a list of all vertices present in the network.
     * @return ArrayList<Vertex_Matrix<T>> - List of network vertices.
     */
    public ArrayList<Vertex_Matrix<T>> getVertices() {
        return this.vertices;
    }

    /**
     * Method: isConnected - This method checks if the network is connected, that is, if there is a path between all pairs of vertices in the network.
     * @return boolean - Returns true if the network is connected, otherwise it returns false.
     */
    public boolean isConnected() {
        for (Vertex_Matrix<T> v : vertices) {
            for (int i = 0; i < vertices.size(); i++) {
                if (adjacencyMatrix[vertices.indexOf(v)][i] != 0) {
                    break;
                }
                if (i == vertices.size() - 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method: getAdjacencyMatrix - This method returns the adjacency matrix of the network. The adjacency matrix represents the connections between the vertices of the network, where each entry in the matrix indicates the existence of an edge between two vertices.
     * @return int[][] - The adjacency matrix of the network.
     */
    public int[][] getAdjacencyMatrix() {
        return this.adjacencyMatrix;
    }
}
