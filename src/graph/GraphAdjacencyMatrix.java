package graph;

import java.util.*;

public class GraphAdjacencyMatrix<T> implements IGraph<T> {
    private final ArrayList<Vertex_Matrix<T>> vertices;
    private int[][] adjacencyMatrix;
    private final boolean directed;
    private int time;

    public GraphAdjacencyMatrix(boolean directed) {
        this.vertices = new ArrayList<>();
        this.adjacencyMatrix = new int[0][0];
        this.directed = directed;
    }

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

    @Override
    public void prim(T source) {
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

    private int getIndex(T vertex) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getValue().equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    public Vertex_Matrix<T> getVertex(T vertex) {
        for (Vertex_Matrix<T> v : vertices) {
            if (v.getValue().equals(vertex)) {
                return v;
            }
        }
        return null;
    }

    public boolean isDirected() {
        return this.directed;
    }

    public ArrayList<Vertex_Matrix<T>> getVertices() {
        return this.vertices;
    }

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

    public int[][] getAdjacencyMatrix() {
        return this.adjacencyMatrix;
    }
}
