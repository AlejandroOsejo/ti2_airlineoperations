package graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphAdjacencyListTest {
    private GraphAdjacencyList<String> graph;

    @BeforeEach
    void setUp() {
        this.graph = new GraphAdjacencyList<>(false);
    }

    @Test
    void testAddOneVertex() {
        // Arrange
        int vertices = 1;

        // Act
        this.graph.addVertex("New York City");

        // Assert
        assertEquals(vertices, this.graph.getVertices().size());
    }

    @Test
    void testAddTwoVertices() {
        // Arrange
        int vertices = 2;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");

        // Assert
        assertEquals(vertices, this.graph.getVertices().size());
    }

    @Test
    void testAddAVertexTwice() {
        // Act
        this.graph.addVertex("New York City");

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.addVertex("New York City"));
    }

    @Test
    void testAddOneEdge() {
        // Arrange
        int edges = 1;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addEdge("New York City", "Los Angeles", 1);

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
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addEdge("New York City", "Los Angeles", 3);
        this.graph.addEdge("New York City", "Chicago", 2);

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
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addEdge("New York City", "Los Angeles", 1);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.addEdge("New York City", "Los Angeles", 1));
    }

    @Test
    void testRemoveOneVertex() {
        // Arrange
        int vertices = 0;

        // Act
        this.graph.addVertex("New York City");
        this.graph.removeVertex("New York City");

        // Assert
        assertEquals(vertices, this.graph.getVertices().size());
    }

    @Test
    void testRemoveTwoVertices() {
        // Arrange
        int vertices = 0;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.removeVertex("New York City");
        this.graph.removeVertex("Los Angeles");

        // Assert
        assertEquals(vertices, this.graph.getVertices().size());
    }

    @Test
    void testRemoveAVertexDoesNotExist() {
        // Act
        this.graph.addVertex("New York City");

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.removeVertex("Los Angeles"));
    }

    @Test
    void testRemoveOneEdge() {
        // Arrange
        int edges = 0;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addEdge("New York City", "Los Angeles", 5);
        this.graph.removeEdge("New York City", "Los Angeles");

        // Assert
        assertEquals(edges, this.graph.getVertices().get(0).getAdjacent().size());
        assertEquals(edges, this.graph.getVertices().get(1).getAdjacent().size());
    }

    @Test
    void testRemoveTwoEdges() {
        // Arrange
        int edges = 0;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addEdge("New York City", "Los Angeles", 5);
        this.graph.addEdge("New York City", "Chicago", 2);
        this.graph.removeEdge("New York City", "Los Angeles");
        this.graph.removeEdge("New York City", "Chicago");

        // Assert
        assertEquals(edges, this.graph.getVertices().get(0).getAdjacent().size());
        assertEquals(edges, this.graph.getVertices().get(1).getAdjacent().size());
        assertEquals(edges, this.graph.getVertices().get(2).getAdjacent().size());
    }

    @Test
    void testRemoveAnEdgeDoesNotExist() {
        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.removeEdge("New York City", "Los Angeles"));
    }

    @Test
    void testBFS() {
        // Arrange
        int distance = 3;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addVertex("Denver");
        this.graph.addVertex("Miami");

        this.graph.addEdge("New York City", "Los Angeles", 4);
        this.graph.addEdge("New York City", "Chicago", 2);
        this.graph.addEdge("Los Angeles", "Denver", 1);
        this.graph.addEdge("Chicago", "Denver", 5);
        this.graph.addEdge("Denver", "Miami", 3);

        this.graph.BFS("New York City");

        // Assert
        assertEquals(distance, this.graph.getVertices().get(4).getDistance());
    }

    @Test
    void testBFSWithAVertexDoesNotExist() {
        // Act
        this.graph.addVertex("New York City");

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.BFS("Los Angeles"));
    }

    @Test
    void testBFSWithACyclicGraph() {
// Arrange
        int distance = this.graph.isDirected() ? 3 : 1;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addVertex("Denver");
        this.graph.addVertex("Miami");

        this.graph.addEdge("New York City", "Los Angeles", 4);
        this.graph.addEdge("New York City", "Chicago", 2);
        this.graph.addEdge("Los Angeles", "Denver", 1);
        this.graph.addEdge("Chicago", "Denver", 5);
        this.graph.addEdge("Denver", "Miami", 3);
        this.graph.addEdge("Miami", "New York City", 3);

        this.graph.BFS("New York City");

        // Assert
        assertEquals(distance, this.graph.getVertices().get(4).getDistance());
    }

    @Test
    void testDFS() {
        // Arrange
        int discoveryTime = 3;
        int finishingTime = this.graph.isDirected() ? 6 : 8;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addVertex("Denver");
        this.graph.addVertex("Miami");

        this.graph.addEdge("New York City", "Los Angeles", 4);
        this.graph.addEdge("New York City", "Chicago", 2);
        this.graph.addEdge("Los Angeles", "Denver", 1);
        this.graph.addEdge("Chicago", "Denver", 5);
        this.graph.addEdge("Denver", "Miami", 3);

        this.graph.DFS("New York City");

        // Assert
        assertEquals(discoveryTime, this.graph.getVertices().get(3).getDiscoveryTime());
        assertEquals(finishingTime, this.graph.getVertices().get(3).getFinishingTime());
    }

    @Test
    void testDFSWithAVertexDoesNotExist() {
        // Act
        this.graph.addVertex("New York City");

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.DFS("Los Angeles"));
    }

    @Test
    void testDFSWithACyclicGraph() {
        // Arrange
        int discoveryTime = 3;
        int finishingTime = this.graph.isDirected() ? 6 : 8;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addVertex("Denver");
        this.graph.addVertex("Miami");

        this.graph.addEdge("New York City", "Los Angeles", 4);
        this.graph.addEdge("New York City", "Chicago", 2);
        this.graph.addEdge("Los Angeles", "Denver", 1);
        this.graph.addEdge("Chicago", "Denver", 5);
        this.graph.addEdge("Denver", "Miami", 3);
        this.graph.addEdge("Miami", "New York City", 3);

        this.graph.DFS("New York City");

        // Assert
        assertEquals(discoveryTime, this.graph.getVertices().get(3).getDiscoveryTime());
        assertEquals(finishingTime, this.graph.getVertices().get(3).getFinishingTime());
    }

    @Test
    void testDijkstra() {
        // Arrange
        int distance = 5;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addVertex("Denver");
        this.graph.addVertex("Miami");

        this.graph.addEdge("New York City", "Los Angeles", 4);
        this.graph.addEdge("New York City", "Chicago", 2);
        this.graph.addEdge("Los Angeles", "Denver", 1);
        this.graph.addEdge("Chicago", "Denver", 5);
        this.graph.addEdge("Denver", "Miami", 3);

        this.graph.dijkstra("New York City");

        // Assert
        assertEquals(distance, this.graph.getVertices().get(3).getDistance());
    }

    @Test
    void testDijkstraWithAVertexDoesNotExist() {
        // Act
        this.graph.addVertex("New York City");

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.dijkstra("Los Angeles"));
    }

    @Test
    void testDijkstraWithACyclicGraph() {
        // Arrange
        int distance = this.graph.isDirected() ? 8 : 3;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addVertex("Denver");
        this.graph.addVertex("Miami");

        this.graph.addEdge("New York City", "Los Angeles", 4);
        this.graph.addEdge("New York City", "Chicago", 2);
        this.graph.addEdge("Los Angeles", "Denver", 1);
        this.graph.addEdge("Chicago", "Denver", 5);
        this.graph.addEdge("Denver", "Miami", 3);
        this.graph.addEdge("Miami", "New York City", 3);

        this.graph.dijkstra("New York City");

        // Assert
        assertEquals(distance, this.graph.getVertices().get(4).getDistance());
    }

    @Test
    void testFloydWarshall() {
        // Arrange
        String previous = "Denver";

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addVertex("Denver");
        this.graph.addVertex("Miami");

        graph.addEdge("New York City", "Los Angeles", 2);
        graph.addEdge("New York City", "Chicago", 4);
        graph.addEdge("Los Angeles", "Denver", 7);
        graph.addEdge("Chicago", "Denver", 3);
        graph.addEdge("Denver", "Miami", 1);
        graph.addEdge("New York City", "Miami", 20);

        Vertex_List<String>[][] prevMatrix = this.graph.floydWarshall();

        // Assert
        assertEquals(previous, prevMatrix[0][4].getValue());
    }

    @Test
    void testFloydWarshallWithWeightedNegativeEdges() {
        // Arrange
        String previous = "New York City";

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addVertex("Denver");
        this.graph.addVertex("Miami");

        graph.addEdge("New York City", "Los Angeles", 2);
        graph.addEdge("New York City", "Chicago", -4);
        graph.addEdge("Los Angeles", "Denver", 7);
        graph.addEdge("Chicago", "Denver", 0);
        graph.addEdge("Denver", "Miami", 1);
        graph.addEdge("New York City", "Miami", -10);

        Vertex_List<String>[][] prevMatrix = this.graph.floydWarshall();

        // Assert
        assertEquals(previous, prevMatrix[0][4].getValue());
    }

    @Test
    void testFloydWarshallWithAVertex() {
        // Act
        this.graph.addVertex("New York City");

        Vertex_List<String>[][] prevMatrix = this.graph.floydWarshall();

        // Assert
        assertNull(prevMatrix[0][0]);
    }

    @Test
    void testPrim() {
        // Arrange
        int weight = 1;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addVertex("Denver");
        this.graph.addVertex("Miami");

        this.graph.addEdge("New York City", "Los Angeles", 4);
        this.graph.addEdge("New York City", "Chicago", 2);
        this.graph.addEdge("Los Angeles", "Denver", 1);
        this.graph.addEdge("Chicago", "Denver", 5);
        this.graph.addEdge("Miami", "Los Angeles", 1);

        this.graph.prim("Miami");

        // Assert
        assertEquals(weight, this.graph.getVertices().get(3).getDistance());
        assertEquals(weight, this.graph.getVertices().get(1).getDistance());
    }

    @Test
    void testPrimWithAVertexDoesNotExist() {
        // Act
        this.graph.addVertex("New York City");

        // Assert
        assertThrows(IllegalArgumentException.class, () -> this.graph.prim("Los Angeles"));
    }

    @Test
    void testPrimWithNegativeWeight() {
        // Arrange
        int weight = 1;

        // Act
        this.graph.addVertex("New York City");
        this.graph.addVertex("Los Angeles");
        this.graph.addVertex("Chicago");
        this.graph.addVertex("Denver");
        this.graph.addVertex("Miami");

        this.graph.addEdge("New York City", "Los Angeles", -1);
        this.graph.addEdge("New York City", "Denver", 2);
        this.graph.addEdge("Los Angeles", "Denver", 1);
        this.graph.addEdge("Denver", "Denver", 5);
        this.graph.addEdge("Miami", "Los Angeles", -3);

        this.graph.prim("Miami");

        // Assert
        assertEquals(weight, this.graph.getVertices().get(3).getDistance());
    }
}