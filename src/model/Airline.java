package model;

import graph.*;

import java.io.*;
import java.util.*;

public class Airline {
    private final GraphAdjacencyList<String> citiesGraphAL;
    private final GraphAdjacencyMatrix<String> citiesGraphAM;

    public Airline() {
        this.citiesGraphAL = new GraphAdjacencyList<>(false);
        this.citiesGraphAM = new GraphAdjacencyMatrix<>(false);
    }

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

    public void showConnections(int graphOption) {
        if (graphOption == 1) {
            ArrayList<Vertex_List<String>> vertices = this.citiesGraphAL.getVertices();
            for (Vertex_List<String> vertex : vertices) {
                System.out.println(vertex.getValue() + " " + vertex.getDistance() + " " + vertex.getParent());
            }
        } else {
            ArrayList<Vertex_Matrix<String>> vertices = this.citiesGraphAM.getVertices();
            for (Vertex_Matrix<String> vertex : vertices) {
                System.out.println(vertex.getValue() + " " + vertex.getDistance() + " " + vertex.getParent());
            }
        }
    }

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

    public void getShortestPath(String source, String destination, int graphOption) {
        System.out.println("\nShortest path from " + source + " to " + destination + ": \n");
        if (graphOption == 1) {
            Map<Vertex<String>, Vertex<String>> dijkstra = citiesGraphAL.dijkstra(source);
            System.out.println(dijkstra.get(citiesGraphAL.getVertex(destination)) + " --> " + citiesGraphAL.getVertex(destination).getDistance() + " --> " + citiesGraphAL.getVertex(destination).getValue());
            Vertex<String> prev = dijkstra.get(citiesGraphAL.getVertex(destination));
            while (prev != null) {
                System.out.println(prev + " --> " + prev.getDistance() + " --> " + prev.getValue());
                prev = dijkstra.get(prev);
            }
        } else {

        }
    }

    public ArrayList<Vertex_List<String>> getVertices() {
        return this.citiesGraphAL.getVertices();
    }
}
