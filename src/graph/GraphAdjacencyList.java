package graph;

import java.util.*;

/**
 * Class name: GraphAdjacencyList
 * General Description: This class represents a weighted graph using adjacency lists. It provides methods for adding vertices and edges, performing BFS and DFS paths, finding the shortest paths, and computing minimum spanning trees, among other operations.
 */
public class GraphAdjacencyList<T> implements IGraph<T> {
    private final ArrayList<Vertex_List<T>> vertices;
    private final boolean directed;
    private int time;

    /**
     * Method: GraphAdjacencyList - Creates an instance of the GraphAdjacencyList class with the specified configuration.
     *
     * @param directed The directed parameter indicates whether the network is directed (true) or undirected (false).
     */
    public GraphAdjacencyList(boolean directed) {
        this.vertices = new ArrayList<>();
        this.directed = directed;
    }

    /**
     * Method: addVertex - Adds a new vertex to the network.
     *
     * @param vertex The vertex parameter is the vertex to be added to the network.
     */
    @Override
    public void addVertex(T vertex) {
        if (getVertex(vertex) != null) {
            throw new IllegalArgumentException("Vertex already exists " + vertex);
        }

        this.vertices.add(new Vertex_List<>(vertex));
    }

    /**
     * Method: addEdge - Adds an edge between two vertices with a given weight.
     *
     * @param source      The vertex of origin of the edge.
     * @param destination The target vertex of the edge.
     * @param weight      The weight of the edge.
     */
    @Override
    public void addEdge(T source, T destination, int weight) {
        Vertex_List<T> vertexList1 = getVertex(source);
        Vertex_List<T> vertexList2 = getVertex(destination);

        if (vertexList1 == null || vertexList2 == null) {
            throw new IllegalArgumentException("Vertex does not exist " + source + " " + destination);
        }

        if (vertexList1.getAdjacent().containsKey(vertexList2)) {
            throw new IllegalArgumentException("Edge already exists");
        }

        vertexList1.addAdjacent(vertexList2, weight);
        if (!this.directed) {
            vertexList2.addAdjacent(vertexList1, weight);
        }
    }

    /**
     * Method: removeVertex - Removes a vertex and all its associated edges from the graph.
     *
     * @param vertex The vertex to be removed from the network.
     */
    @Override
    public void removeVertex(T vertex) {
        Vertex_List<T> v = getVertex(vertex);
        if (v == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        this.vertices.remove(v);
        for (Vertex_List<T> u : this.vertices) {
            u.removeAdjacent(v);
        }
    }

    /**
     * Method: removeEdge - Removes an edge between two vertices of the graph.
     * @param source The origin vertex of the edge to be deleted.
     * @param destination The target vertex of the edge to be deleted.
     */
    @Override
    public void removeEdge(T source, T destination) {
        Vertex_List<T> vertexList1 = getVertex(source);
        Vertex_List<T> vertexList2 = getVertex(destination);

        if (vertexList1 == null || vertexList2 == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        if (!vertexList1.getAdjacent().containsKey(vertexList2)) {
            throw new IllegalArgumentException("Edge does not exist");
        }

        vertexList1.removeAdjacent(vertexList2);
        if (!this.directed) {
            vertexList2.removeAdjacent(vertexList1);
        }
    }

    /**
     * Method: BFS - Performs a BFS (Breadth-First Search) path from a given source vertex.
     * @param source The source vertex from which the BFS path starts.
     */
    @Override
    public void BFS(T source) {
        Vertex_List<T> s = getVertex(source);
        if (s == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        for (Vertex_List<T> u : this.vertices) {
            if (!u.equals(s)) {
                u.setColor("white");
                u.setDistance(Integer.MAX_VALUE);
                u.setParent(null);
            }
        }

        s.setColor("gray");
        s.setDistance(0);
        s.setParent(null);

        Queue<Vertex_List<T>> queue = new LinkedList<>();
        queue.offer(s);

        while (!queue.isEmpty()) {
            Vertex_List<T> u = queue.poll();
            for (Vertex_List<T> v : u.getAdjacent().keySet()) {
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

    /**
     * Method: DFS - performs a DFS (Depth-First Search) path from a given source vertex.
     * @param source The source vertex from which the DFS path starts.
     */
    @Override
    public void DFS(T source) {
        Vertex_List<T> s = getVertex(source);
        if (s == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        for (Vertex_List<T> u : this.vertices) {
            u.setColor("white");
            u.setParent(null);
        }

        this.time = 0;

        DFSVisit(s);
    }

    /**
     * Method: DFSVisit - This method performs a recursive DFS visit from a given starting vertex. It marks the starting vertex as visited ("gray"), updates its discovery time, and then iterates over adjacent unvisited vertices. For each adjacent unvisited vertex, it sets its parent as the start vertex and makes a recursive call to DFSVisit with that vertex as the start. After visiting all adjacent vertices, it marks the starting vertex as completed ("black") and updates its completion time.
     * @param start The starting vertex from which the DFS visit is made.
     */
    private void DFSVisit(Vertex_List<T> start) {
        this.time++;
        start.setDiscoveryTime(this.time);
        start.setColor("gray");

        for (Vertex_List<T> u : start.getAdjacent().keySet()) {
            if (u.getColor().equals("white")) {
                u.setParent(start);
                DFSVisit(u);
            }
        }

        start.setColor("black");
        this.time++;
        start.setFinishingTime(this.time);
    }

    /**
     * Method: dijkstra - Find the shortest path from a given source vertex to all other vertices using Dijkstra's algorithm.
     * @param source The source vertex from which Dijkstra's algorithm starts.
     * @return 'Map <Vertex<T>, Vertex<T>>' Returns a map containing the preceding vertices on the shortest path from the source vertex to each of the other vertices.
     */
    @Override
    public Map<Vertex<T>, Vertex<T>> dijkstra(T source) {
        Vertex<T> s = getVertex(source);

        if (s == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        Map<Vertex<T>, Vertex<T>> previous = new HashMap<>();
        PriorityQueue<Vertex_List<T>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex_List::getDistance));
        s.setDistance(0);

        for (Vertex_List<T> u : this.vertices) {
            if (!u.equals(s)) {
                u.setDistance(Integer.MAX_VALUE);
            }
            previous.put(u, null);
            queue.add(u);
        }

        while (!queue.isEmpty()) {
            Vertex_List<T> u = queue.poll();
            for (Vertex_List<T> v : u.getAdjacent().keySet()) {
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

    /**
     * Method: floydWarshall - Find the shortest paths between all pairs of vertices using the Floyd-Warshall algorithm.
     * @return Vertex_List<T>[][] - Returns a two-dimensional array representing the preceding vertices on the shortest paths between all pairs of vertices.
     */
    @Override
    public Vertex_List<T>[][] floydWarshall() {
        int[][] dist = new int[this.vertices.size()][this.vertices.size()];
        Vertex_List<T>[][] prev = new Vertex_List[this.vertices.size()][this.vertices.size()];

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
            Vertex_List<T> u = this.vertices.get(i);
            for (Vertex_List<T> v : u.getAdjacent().keySet()) {
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

    /**
     * Method: prim - Find the minimum spanning tree using Prim's algorithm.
     * @param source The source vertex from which the Prim algorithm starts.
     */
    @Override
    public void prim(T source) {
        Vertex_List<T> s = getVertex(source);

        if (s == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        for (Vertex_List<T> u : this.vertices) {
            u.setDistance(Integer.MAX_VALUE);
            u.setParent(null);
            u.setColor("white");
        }

        s.setDistance(0);
        s.setParent(null);

        PriorityQueue<Vertex_List<T>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex_List::getDistance));
        queue.addAll(this.vertices);

        while (!queue.isEmpty()) {
            Vertex_List<T> u = queue.poll();
            for (Vertex_List<T> v : u.getAdjacent().keySet()) {
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

    /**
     * Method: getVertex - This method finds and returns the Vertex_List<T> object corresponding to the specified value. It goes through the list of vertices and compares the value of each vertex with the value provided. If it finds a vertex with the same value, it returns it. If no vertex with the specified value is found, it returns null.
     * @param value The value of the vertex to be searched.
     * @return Vertex_List<T> - Returns the Vertex_List<T> object corresponding to the specified value if found. If no vertex with the specified value is found, returns null.
     */
    public Vertex_List<T> getVertex(T value) {
        for (Vertex_List<T> vertexList : this.vertices) {
            if (vertexList.getValue().equals(value)) {
                return vertexList;
            }
        }
        return null;
    }

    /**
     * Method: isDirected - This method returns a boolean value indicating whether the network is directed or not. It checks the directed attribute of the network and returns its value.
     * @return boolean - Returns true if the network is directed, and false if it is not.
     */
    public boolean isDirected() {
        return this.directed;
    }

    /**
     * Method: getVertices - This method returns the list of vertices of the network.
     * @return ArrayList<Vertex_List<T>> - Returns the list of vertices of the network as an ArrayList<Vertex_List<T>> object.
     */
    public ArrayList<Vertex_List<T>> getVertices() {
        return this.vertices;
    }

    /**
     * Method: isConnected - This method checks if the network is connected. It goes through the list of vertices and checks if any vertex has no adjacent. If it finds any vertex with no adjacent, it returns false. If all vertices have at least one adjacent, it returns true.
     * @return boolean - Returns true if the network is connected, that is, if all vertices have at least one adjacent. It returns false if there are any vertices with no adjacent vertices.
     */
    public boolean isConnected() {
        for (Vertex_List<T> vertexList : this.vertices) {
            if (vertexList.getAdjacent().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
