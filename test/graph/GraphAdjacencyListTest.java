package graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphAdjacencyListTest {
    private GraphAdjacencyList<Integer> graph;

    @BeforeEach
    void setUp() {
        this.graph = new GraphAdjacencyList<>(false);
    }

    @Test
    void testAddOneVertex() {
        // Arrange
        int vertices = 1;

        // Act
        this.graph.addVertex(1);

        // Assert
        assertEquals(vertices, this.graph.getVertices().size());
    }

    @Test
    void testAddTwoVertices() {
        // Arrange
        int vertices = 2;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);

        // Assert
        assertEquals(vertices, this.graph.getVertices().size());
    }

    @Test
    void testAddAVertexTwice() {
        // Act
        this.graph.addVertex(1);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.addVertex(1));
    }

    @Test
    void testAddOneEdge() {
        // Arrange
        int edges = 1;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addEdge(1, 2, 1);

        // Assert
        assertEquals(edges, this.graph.getVertices().get(0).getAdjacent().size());

        if (!this.graph.isDirected()) {
            assertEquals(edges, this.graph.getVertices().get(1).getAdjacent().size());
        } else {
            assertEquals(0, this.graph.getVertices().get(1).getAdjacent().size());
        }
    }

    @Test
    void testAddTwoEdges() {
        // Arrange
        int edges = 2;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addEdge(1, 2, 3);
        this.graph.addEdge(1, 3, 2);

        // Assert
        assertEquals(edges, this.graph.getVertices().get(0).getAdjacent().size());

        if (!this.graph.isDirected()) {
            assertEquals(1, this.graph.getVertices().get(1).getAdjacent().size());
            assertEquals(1, this.graph.getVertices().get(2).getAdjacent().size());
        } else {
            assertEquals(0, this.graph.getVertices().get(1).getAdjacent().size());
            assertEquals(0, this.graph.getVertices().get(2).getAdjacent().size());
        }
    }

    @Test
    void testAddAnEdgeTwice() {
        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addEdge(1, 2, 1);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.addEdge(1, 2, 1));
    }

    @Test
    void testRemoveOneVertex() {
        // Arrange
        int vertices = 0;

        // Act
        this.graph.addVertex(1);
        this.graph.removeVertex(1);

        // Assert
        assertEquals(vertices, this.graph.getVertices().size());
    }

    @Test
    void testRemoveTwoVertices() {
        // Arrange
        int vertices = 0;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.removeVertex(1);
        this.graph.removeVertex(2);

        // Assert
        assertEquals(vertices, this.graph.getVertices().size());
    }

    @Test
    void testRemoveAVertexDoesNotExist() {
        // Act
        this.graph.addVertex(1);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.removeVertex(2));
    }

    @Test
    void testRemoveOneEdge() {
        // Arrange
        int edges = 0;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addEdge(1, 2, 5);
        this.graph.removeEdge(1, 2);

        // Assert
        assertEquals(edges, this.graph.getVertices().get(0).getAdjacent().size());
        assertEquals(edges, this.graph.getVertices().get(1).getAdjacent().size());
    }

    @Test
    void testRemoveTwoEdges() {
        // Arrange
        int edges = 0;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addEdge(1, 2, 5);
        this.graph.addEdge(1, 3, 2);
        this.graph.removeEdge(1, 2);
        this.graph.removeEdge(1, 3);

        // Assert
        assertEquals(edges, this.graph.getVertices().get(0).getAdjacent().size());
        assertEquals(edges, this.graph.getVertices().get(1).getAdjacent().size());
        assertEquals(edges, this.graph.getVertices().get(2).getAdjacent().size());
    }

    @Test
    void testRemoveAnEdgeDoesNotExist() {
        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.removeEdge(1, 2));
    }

    @Test
    void testBFS() {
        // Arrange
        int distance = 3;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addVertex(4);
        this.graph.addVertex(5);

        this.graph.addEdge(1, 2, 4);
        this.graph.addEdge(1, 3, 2);
        this.graph.addEdge(2, 4, 1);
        this.graph.addEdge(3, 4, 5);
        this.graph.addEdge(4, 5, 3);

        this.graph.BFS(1);

        // Assert
        assertEquals(distance, this.graph.getVertices().get(4).getDistance());
    }

    @Test
    void testBFSWithAVertexDoesNotExist() {
        // Act
        this.graph.addVertex(1);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.BFS(2));
    }

    @Test
    void testBFSWithACyclicGraph() {
// Arrange
        int distance = this.graph.isDirected() ? 3 : 1;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addVertex(4);
        this.graph.addVertex(5);

        this.graph.addEdge(1, 2, 4);
        this.graph.addEdge(1, 3, 2);
        this.graph.addEdge(2, 4, 1);
        this.graph.addEdge(3, 4, 5);
        this.graph.addEdge(4, 5, 3);
        this.graph.addEdge(5, 1, 3);

        this.graph.BFS(1);

        // Assert
        assertEquals(distance, this.graph.getVertices().get(4).getDistance());
    }

    @Test
    void testDFS() {
        // Arrange
        int discoveryTime = 3;
        int finishingTime = this.graph.isDirected() ? 6 : 8;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addVertex(4);
        this.graph.addVertex(5);

        this.graph.addEdge(1, 2, 4);
        this.graph.addEdge(1, 3, 2);
        this.graph.addEdge(2, 4, 1);
        this.graph.addEdge(3, 4, 5);
        this.graph.addEdge(4, 5, 3);

        this.graph.DFS(1);

        // Assert
        assertEquals(discoveryTime, this.graph.getVertices().get(3).getDiscoveryTime());
        assertEquals(finishingTime, this.graph.getVertices().get(3).getFinishingTime());
    }

    @Test
    void testDFSWithAVertexDoesNotExist() {
        // Act
        this.graph.addVertex(1);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.DFS(2));
    }

    @Test
    void testDFSWithACyclicGraph() {
        // Arrange
        int discoveryTime = 3;
        int finishingTime = this.graph.isDirected() ? 6 : 8;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addVertex(4);
        this.graph.addVertex(5);

        this.graph.addEdge(1, 2, 4);
        this.graph.addEdge(1, 3, 2);
        this.graph.addEdge(2, 4, 1);
        this.graph.addEdge(3, 4, 5);
        this.graph.addEdge(4, 5, 3);
        this.graph.addEdge(5, 1, 3);

        this.graph.DFS(1);

        // Assert
        assertEquals(discoveryTime, this.graph.getVertices().get(3).getDiscoveryTime());
        assertEquals(finishingTime, this.graph.getVertices().get(3).getFinishingTime());
    }

    @Test
    void testDijkstra() {
        // Arrange
        int distance = 5;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addVertex(4);
        this.graph.addVertex(5);

        this.graph.addEdge(1, 2, 4);
        this.graph.addEdge(1, 3, 2);
        this.graph.addEdge(2, 4, 1);
        this.graph.addEdge(3, 4, 5);
        this.graph.addEdge(4, 5, 3);

        this.graph.dijkstra(1);

        // Assert
        assertEquals(distance, this.graph.getVertices().get(3).getDistance());
    }

    @Test
    void testDijkstraWithAVertexDoesNotExist() {
        // Act
        this.graph.addVertex(1);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.dijkstra(2));
    }

    @Test
    void testDijkstraWithACyclicGraph() {
        // Arrange
        int distance = this.graph.isDirected() ? 8 : 3;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addVertex(4);
        this.graph.addVertex(5);

        this.graph.addEdge(1, 2, 4);
        this.graph.addEdge(1, 3, 2);
        this.graph.addEdge(2, 4, 1);
        this.graph.addEdge(3, 4, 5);
        this.graph.addEdge(4, 5, 3);
        this.graph.addEdge(5, 1, 3);

        this.graph.dijkstra(1);

        // Assert
        assertEquals(distance, this.graph.getVertices().get(4).getDistance());
    }

    @Test
    void testFloydWarshall() {
        // Arrange
        int previous = 4;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addVertex(4);
        this.graph.addVertex(5);

        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 4);
        graph.addEdge(2, 4, 7);
        graph.addEdge(3, 4, 3);
        graph.addEdge(4, 5, 1);
        graph.addEdge(1, 5, 20);

        Vertex<Integer>[][] prevMatrix = this.graph.floydWarshall();

        // Assert
        assertEquals(previous, prevMatrix[0][4].getValue());
    }

    @Test
    void testFloydWarshallWithWeightedNegativeEdges() {
        // Arrange
        int previous = 1;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addVertex(4);
        this.graph.addVertex(5);

        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, -4);
        graph.addEdge(2, 4, 7);
        graph.addEdge(3, 4, 0);
        graph.addEdge(4, 5, 1);
        graph.addEdge(1, 5, -10);

        Vertex<Integer>[][] prevMatrix = this.graph.floydWarshall();

        // Assert
        assertEquals(previous, prevMatrix[0][4].getValue());
    }

    @Test
    void testFloydWarshallWithAVertex() {
        // Act
        this.graph.addVertex(1);

        Vertex<Integer>[][] prevMatrix = this.graph.floydWarshall();

        // Assert
        assertNull(prevMatrix[0][0]);
    }

    @Test
    void testPrim() {
        // Arrange
        int weight = 1;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addVertex(4);
        this.graph.addVertex(5);

        this.graph.addEdge(1, 2, 4);
        this.graph.addEdge(1, 3, 2);
        this.graph.addEdge(2, 4, 1);
        this.graph.addEdge(3, 4, 5);
        this.graph.addEdge(5, 2, 1);

        this.graph.prim(5);

        // Assert
        assertEquals(weight, this.graph.getVertices().get(3).getDistance());
        assertEquals(weight, this.graph.getVertices().get(1).getDistance());
    }

    @Test
    void testPrimWithAVertexDoesNotExist() {
        // Act
        this.graph.addVertex(1);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.prim(2));
    }

    @Test
    void testPrimWithNegativeWeight() {
        // Arrange
        int weight = 1;

        // Act
        this.graph.addVertex(1);
        this.graph.addVertex(2);
        this.graph.addVertex(3);
        this.graph.addVertex(4);
        this.graph.addVertex(5);

        this.graph.addEdge(1, 2, -1);
        this.graph.addEdge(1, 3, 2);
        this.graph.addEdge(2, 4, 1);
        this.graph.addEdge(3, 4, 5);
        this.graph.addEdge(5, 2, -3);

        this.graph.prim(5);

        // Assert
        assertEquals(weight, this.graph.getVertices().get(3).getDistance());
    }
}