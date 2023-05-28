package graph;

import java.util.*;

public class GraphAdjacencyList<T> implements IGraph<T> {
    private final ArrayList<Vertex<T>> vertices;
    private final boolean directed;
    private int time;

    public GraphAdjacencyList(boolean directed) {
        this.vertices = new ArrayList<>();
        this.directed = directed;
    }

    @Override
    public void addVertex(T vertex) {
        if (getVertex(vertex) != null) {
            throw new IllegalArgumentException("Vertex already exists " + vertex);
        }

        this.vertices.add(new Vertex<>(vertex));
    }

    @Override
    public void addEdge(T source, T destination, int weight) {
        Vertex<T> vertex1 = getVertex(source);
        Vertex<T> vertex2 = getVertex(destination);

        if (vertex1 == null || vertex2 == null) {
            throw new IllegalArgumentException("Vertex does not exist " + source + " " + destination);
        }

        if (vertex1.getAdjacent().containsKey(vertex2)) {
            throw new IllegalArgumentException("Edge already exists");
        }

        vertex1.addAdjacent(vertex2, weight);
        if (!this.directed) {
            vertex2.addAdjacent(vertex1, weight);
        }
    }

    @Override
    public void removeVertex(T vertex) {
        Vertex<T> v = getVertex(vertex);
        if (v == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        this.vertices.remove(v);
        for (Vertex<T> u : this.vertices) {
            u.removeAdjacent(v);
        }
    }

    @Override
    public void removeEdge(T source, T destination) {
        Vertex<T> vertex1 = getVertex(source);
        Vertex<T> vertex2 = getVertex(destination);

        if (vertex1 == null || vertex2 == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        if (!vertex1.getAdjacent().containsKey(vertex2)) {
            throw new IllegalArgumentException("Edge does not exist");
        }

        vertex1.removeAdjacent(vertex2);
        if (!this.directed) {
            vertex2.removeAdjacent(vertex1);
        }
    }

    @Override
    public void BFS(T source) {
        Vertex<T> s = getVertex(source);
        if (s == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        for (Vertex<T> u : this.vertices) {
            if (!u.equals(s)) {
                u.setColor("white");
                u.setDistance(Integer.MAX_VALUE);
                u.setParent(null);
            }
        }

        s.setColor("gray");
        s.setDistance(0);
        s.setParent(null);

        Queue<Vertex<T>> queue = new LinkedList<>();
        queue.offer(s);

        while (!queue.isEmpty()) {
            Vertex<T> u = queue.poll();
            for (Vertex<T> v : u.getAdjacent().keySet()) {
                if (v.getColor().equals("white")) {
                    v.setColor("gray");
                    v.setDistance(u.getDistance() + 1);
                    v.setParent(u);
                    queue.add(v);
                }
            }
            u.setColor("black");
        }
    }

    @Override
    public void DFS(T source) {
        Vertex<T> s = getVertex(source);
        if (s == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        for (Vertex<T> u : this.vertices) {
            u.setColor("white");
            u.setParent(null);
        }

        this.time = 0;

        DFSVisit(s);
    }

    private void DFSVisit(Vertex<T> start) {
        this.time++;
        start.setDiscoveryTime(this.time);
        start.setColor("gray");

        for (Vertex<T> u : start.getAdjacent().keySet()) {
            if (u.getColor().equals("white")) {
                u.setParent(start);
                DFSVisit(u);
            }
        }

        start.setColor("black");
        this.time++;
        start.setFinishingTime(this.time);
    }

    @Override
    public Map<Vertex<T>, Vertex<T>> dijkstra(T source) {
        Vertex<T> s = getVertex(source);

        if (s == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        Map<Vertex<T>, Vertex<T>> previous = new HashMap<>();
        PriorityQueue<Vertex<T>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
        s.setDistance(0);

        for (Vertex<T> u : this.vertices) {
            if (!u.equals(s)) {
                u.setDistance(Integer.MAX_VALUE);
            }
            previous.put(u, null);
            queue.add(u);
        }

        while (!queue.isEmpty()) {
            Vertex<T> u = queue.poll();
            for (Vertex<T> v : u.getAdjacent().keySet()) {
                int alt = u.getDistance() + u.getAdjacent().get(v);
                if (alt < v.getDistance()) {
                    v.setDistance(alt);
                    previous.put(v, u);
                    queue.remove(v);
                    queue.add(v);
                }
            }
        }

        return previous;
    }

    @Override
    public Vertex<T>[][] floydWarshall() {
        int[][] dist = new int[this.vertices.size()][this.vertices.size()];
        Vertex<T>[][] prev = new Vertex[this.vertices.size()][this.vertices.size()];

        for (int i = 0; i < this.vertices.size(); i++) {
            for (int j = 0; j < this.vertices.size(); j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = Integer.MAX_VALUE;
                }
                prev[i][j] = null;
            }
        }

        for (int i = 0; i < this.vertices.size(); i++) {
            Vertex<T> u = this.vertices.get(i);
            for (Vertex<T> v : u.getAdjacent().keySet()) {
                dist[i][this.vertices.indexOf(v)] = u.getAdjacent().get(v);
                prev[i][this.vertices.indexOf(v)] = u;
            }
        }

        for (int k = 0; k < this.vertices.size(); k++) {
            for (int i = 0; i < this.vertices.size(); i++) {
                for (int j = 0; j < this.vertices.size(); j++) {
                    if (dist[i][k] == Integer.MAX_VALUE || dist[k][j] == Integer.MAX_VALUE) {
                        continue;
                    }
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        prev[i][j] = prev[k][j];
                    }
                }
            }
        }

        return prev;
    }

    @Override
    public void prim(T source) {
        Vertex<T> s = getVertex(source);

        if (s == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        for (Vertex<T> u : this.vertices) {
            u.setDistance(Integer.MAX_VALUE);
            u.setParent(null);
            u.setColor("white");
        }

        s.setDistance(0);
        s.setParent(null);

        PriorityQueue<Vertex<T>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
        queue.addAll(this.vertices);

        while (!queue.isEmpty()) {
            Vertex<T> u = queue.poll();
            for (Vertex<T> v : u.getAdjacent().keySet()) {
                if (queue.contains(v) && u.getAdjacent().get(v) < v.getDistance()) {
                    v.setParent(u);
                    v.setDistance(u.getAdjacent().get(v));
                    queue.remove(v);
                    queue.add(v);
                }
            }
            u.setColor("black");
        }
    }

    public Vertex<T> getVertex(T value) {
        for (Vertex<T> vertex : this.vertices) {
            if (vertex.getValue().equals(value)) {
                return vertex;
            }
        }
        return null;
    }

    public boolean isDirected() {
        return this.directed;
    }

    public ArrayList<Vertex<T>> getVertices() {
        return this.vertices;
    }

    public boolean isConnected() {
        for (Vertex<T> vertex : this.vertices) {
            if (vertex.getAdjacent().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
