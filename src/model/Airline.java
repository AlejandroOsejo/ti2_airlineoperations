package model;

import graph.*;

import java.io.*;
import java.util.*;

/**
 * Class name: Airline
 * General Description: This class represents the airline and contains the methods and attributes.
 */
public class Airline {
    private final GraphAdjacencyList<String> citiesGraphAL;
    private final GraphAdjacencyMatrix<String> citiesGraphAM;

    /**
     * Method: Airline - Constructor of the Airline class. Initializes city charts with adjacency lists and adjacency matrices.
     * @param : None
     * @return : None
     */
    public Airline() {
        this.citiesGraphAL = new GraphAdjacencyList<>(false);
        this.citiesGraphAM = new GraphAdjacencyMatrix<>(false);
    }

    /**
     * Method: loadCitties
     * Loads the cities from a file and adds them to the corresponding chart according to the selected option.
     * @param graphOption - An integer representing the selected chart option.
     * @return : Void
     */
    public void loadCities(int graphOption) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources\\cities.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (graphOption == 1) {
                    this.citiesGraphAL.addVertex(line);
                } else {
                    this.citiesGraphAM.addVertex(line);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: loadConnections
     * Loads the connections between cities from a file and adds them to the corresponding graph according to the selected options.
     * @param weightOption - An integer representing the selected weight option.
     * @param graphOption - An integer representing the selected chart option.
     * @return : void
     */
    public void loadConnections(int weightOption, int graphOption) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources\\connections.txt"));
            String line;
            boolean isConnected = graphOption == 1 ? this.citiesGraphAL.isConnected() : this.citiesGraphAM.isConnected();
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" - ");
                String city1 = split[0];
                String city2 = split[1];
                String weight;
                if (weightOption == 0) {
                    weight = split[2];
                } else {
                    weight = split[3].substring(1);
                }
                if (isConnected) {
                    if (graphOption == 1) {
                        this.citiesGraphAL.removeEdge(city1, city2);
                    } else {
                        this.citiesGraphAM.removeEdge(city1, city2);
                    }
                }
                if (graphOption == 1) {
                    this.citiesGraphAL.addEdge(city1, city2, Integer.parseInt(weight));
                } else {
                    this.citiesGraphAM.addEdge(city1, city2, Integer.parseInt(weight));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: showCities
     * Displays the cities of the selected chart.
     * @param graphOption - An integer representing the selected chart option.
     * @return : void
     */
    public void showCities(int graphOption) {
        if (graphOption == 1) {
            ArrayList<Vertex_List<String>> vertices = this.citiesGraphAL.getVertices();
            for (Vertex_List<String> vertex : vertices) {
                System.out.println(vertex.getValue());
            }
        } else {
            ArrayList<Vertex_Matrix<String>> vertices = this.citiesGraphAM.getVertices();
            for (Vertex_Matrix<String> vertex : vertices) {
                System.out.println(vertex.getValue());
            }
        }
    }

    /**
     * Method: showConnections
     * Displays the connections between cities in the selected chart.
     * @param weightOption - An integer representing the selected weight option.
     * @param graphOption - An integer representing the selected chart option.
     * @return : void
     */
    public void showConnections(int weightOption, int graphOption) {
        if (graphOption == 1) {
            ArrayList<Vertex_List<String>> vertices = this.citiesGraphAL.getVertices();
            for (Vertex_List<String> vertex : vertices) {
                Map<Vertex_List<String>, Integer> adjacent = vertex.getAdjacent();
                for (Map.Entry<Vertex_List<String>, Integer> entry : adjacent.entrySet()) {
                    if (weightOption == 0) {
                        System.out.println(vertex.getValue() + " --> " + entry.getValue() + " minutes --> " + entry.getKey().getValue());
                    } else {
                        System.out.println(vertex.getValue() + " --> $" + entry.getValue() + " --> " + entry.getKey().getValue());
                    }
                }
            }
        } else {
            int[][] connections = this.citiesGraphAM.getAdjacencyMatrix();
            ArrayList<Vertex_Matrix<String>> vertices = this.citiesGraphAM.getVertices();
            for (int i = 0; i < connections.length; i++) {
                for (int j = 0; j < connections.length; j++) {
                    if (connections[i][j] != 0) {
                        if (weightOption == 0) {
                            System.out.println(vertices.get(i).getValue() + " --> " + connections[i][j] + " minutes --> " + vertices.get(j).getValue());
                        } else {
                            System.out.println(vertices.get(i).getValue() + " --> $" + connections[i][j] + " --> " + vertices.get(j).getValue());
                        }
                    }
                }
            }
        }
    }

    /**
     * Method: optimize
     * Optimizes the selected graph using the Prim algorithm and displays the minimum spanning tree from the city "New York City".
     * @param weightOption - An integer representing the selected weight option.
     * @param graphOption - An integer representing the selected chart option.
     * @return : void
     */
    public void optimize(int weightOption, int graphOption) {
        System.out.println("\nMinimum spanning tree from New York City: \n");
        if (graphOption == 1) {
            this.citiesGraphAL.prim("New York City");
            for (Vertex_List<String> vertex : citiesGraphAL.getVertices()) {
                if (weightOption == 0) {
                    System.out.println(vertex.getParent() + " --> " + vertex.getDistance() + " minutes --> " + vertex.getValue());
                } else {
                    System.out.println(vertex.getParent() + " --> $" + vertex.getDistance() + " --> " + vertex.getValue());
                }
            }
        } else {
            this.citiesGraphAM.prim("New York City");
            for (Vertex_Matrix<String> vertex : citiesGraphAM.getVertices()) {
                if (weightOption == 0) {
                    System.out.println(vertex.getParent() + " --> " + vertex.getDistance() + " minutes --> " + vertex.getValue());
                } else {
                    System.out.println(vertex.getParent() + " --> $" + vertex.getDistance() + " --> " + vertex.getValue());
                }
            }
        }
    }

    /**
     * Method: getShortestPath - Finds and displays the shortest route from an origin city to a destination city using Dijkstra's algorithm.
     * @param source A chain representing the city of origin.
     * @param destination A string representing the destination city.
     * @param weightOption An integer representing the selected weight option.
     * @param graphOption An integer representing the selected chart option.
     * @return : void
     */
    public void getShortestPath(String source, String destination, int weightOption, int graphOption) {
        System.out.println("\nShortest path from " + source + " to " + destination + ": \n");
        loadConnections(weightOption, graphOption);
        if (graphOption == 1) {
            Map<Vertex<String>, Vertex<String>> dijkstra = citiesGraphAL.dijkstra(source);
            if (weightOption == 0) {
                System.out.println(dijkstra.get(citiesGraphAL.getVertex(destination)) + " --> " + citiesGraphAL.getVertex(destination).getDistance() + " minutes --> " + citiesGraphAL.getVertex(destination).getValue());
                Vertex<String> prev = dijkstra.get(citiesGraphAL.getVertex(destination));
                while (prev != null && dijkstra.get(prev) != null) {
                    System.out.println(dijkstra.get(prev) + " --> " + prev.getDistance() + " minutes --> " + prev.getValue());
                    prev = dijkstra.get(prev);
                }
            } else {
                System.out.println(dijkstra.get(citiesGraphAL.getVertex(destination)) + " --> $" + citiesGraphAL.getVertex(destination).getDistance() + " --> " + citiesGraphAL.getVertex(destination).getValue());
                Vertex<String> prev = dijkstra.get(citiesGraphAL.getVertex(destination));
                while (prev != null && dijkstra.get(prev) != null) {
                    System.out.println(dijkstra.get(prev) + " --> $" + prev.getDistance() + " --> " + prev.getValue());
                    prev = dijkstra.get(prev);
                }
            }
        } else {
            this.citiesGraphAM.dijkstra(source);
            if (weightOption == 0) {
                System.out.println(citiesGraphAM.getVertex(destination).getParent() + " --> " + citiesGraphAM.getVertex(destination).getDistance() + " minutes --> " + citiesGraphAM.getVertex(destination).getValue());
                Vertex_Matrix<String> prev = citiesGraphAM.getVertex(destination).getParent();
                while (prev != null && prev.getParent() != null) {
                    System.out.println(prev.getParent() + " --> " + prev.getDistance() + " minutes --> " + prev.getValue());
                    prev = prev.getParent();
                }
            } else {
                System.out.println(citiesGraphAM.getVertex(destination).getParent() + " --> $" + citiesGraphAM.getVertex(destination).getDistance() + " --> " + citiesGraphAM.getVertex(destination).getValue());
                Vertex_Matrix<String> prev = citiesGraphAM.getVertex(destination).getParent();
                while (prev != null && prev.getParent() != null) {
                    System.out.println(prev.getParent() + " --> $" + prev.getDistance() + " --> " + prev.getValue());
                    prev = prev.getParent();
                }
            }
        }
    }

    /**
     * Method: getCitiesGraphAL - Returns the cities graph based on adjacency lists.
     * @return A GraphAdjacencyList<String> object representing the graph of cities based on adjacency lists.
     */
    public GraphAdjacencyList<String> getCitiesGraphAL() {
        return citiesGraphAL;
    }

    /**
     * Method: getCitiesGraphAM - Returns the cities graph based on adjacency matrices.
     * @return A GraphAdjacencyMatrix<String> object representing the city graph based on adjacency matrices.
     */
    public GraphAdjacencyMatrix<String> getCitiesGraphAM() {
        return citiesGraphAM;
    }
}
