package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirlineTest {

    private Airline airline;

    @BeforeEach
    void setUp() {
        airline = new Airline();
    }

    @Test
    void testLoadCitiesWithGraphAL() {
        // Arrange
        int vertices = 50;

        // Act
        airline.loadCities(1);

        // Assert
        assertEquals(vertices, airline.getCitiesGraphAL().getVertices().size());
    }

    @Test
    void testLoadCitiesWithGraphAM() {
        // Arrange
        int vertices = 50;

        // Act
        airline.loadCities(2);

        // Assert
        assertEquals(vertices, airline.getCitiesGraphAM().getVertices().size());
    }

    @Test
    void testLoadConnectionsWithGraphALAndFirstWeight() {
        // Act
        airline.loadCities(1);
        airline.loadConnections(0, 1);

        // Assert
        assertTrue(airline.getCitiesGraphAL().isConnected());
    }

    @Test
    void testLoadConnectionsWithGraphAMAndFirstWeight() {
        // Act
        airline.loadCities(2);
        airline.loadConnections(0, 2);

        // Assert
        assertTrue(airline.getCitiesGraphAM().isConnected());
    }

    @Test
    void testLoadConnectionsWithGraphALAndSecondWeight() {
        // Act
        airline.loadCities(1);
        airline.loadConnections(1, 1);

        // Assert
        assertTrue(airline.getCitiesGraphAL().isConnected());
    }

    @Test
    void testLoadConnectionsWithGraphAMAndSecondWeight() {
        // Act
        airline.loadCities(2);
        airline.loadConnections(1, 2);

        // Assert
        assertTrue(airline.getCitiesGraphAM().isConnected());
    }

    @Test
    void testOptimizeWithGraphALWithFirstWeight() {
        // Act
        airline.loadCities(1);
        airline.loadConnections(0, 1);
        airline.optimize(0, 1);

        // Assert
        assertTrue(airline.getCitiesGraphAL().isConnected());
    }

    @Test
    void testOptimizeWithGraphALWithSecondWeight() {
        // Act
        airline.loadCities(1);
        airline.loadConnections(1, 1);
        airline.optimize(1, 1);

        // Assert
        assertTrue(airline.getCitiesGraphAL().isConnected());
    }

    @Test
    void testOptimizeWithGraphAMWithFirstWeight() {
        // Act
        airline.loadCities(2);
        airline.loadConnections(0, 2);
        airline.optimize(0, 2);

        // Assert
        assertTrue(airline.getCitiesGraphAM().isConnected());
    }

    @Test
    void testOptimizeWithGraphAMWithSecondWeight() {
        // Act
        airline.loadCities(2);
        airline.loadConnections(1, 2);
        airline.optimize(1, 2);

        // Assert
        assertTrue(airline.getCitiesGraphAM().isConnected());
    }
}