package model;

import graph.GraphAdjacencyList;
import graph.GraphAdjacencyMatrix;
import graph.Vertex;
import graph.Vertex_List;

import java.io.*;
import java.util.*;

public class Airline {
    private final GraphAdjacencyList<String> citiesGraphAL;
    private final GraphAdjacencyMatrix<String> citiesGraphAM;

    public Airline() {
        this.citiesGraphAL = new GraphAdjacencyList<>(false);
        this.citiesGraphAM = new GraphAdjacencyMatrix<>(false);
    }

    public void loadCities() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources\\cities.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                this.citiesGraphAL.addVertex(line);
                this.citiesGraphAM.addVertex(line);
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

    public void optimize() {
        this.citiesGraphAL.prim("New York City");
    }

    public ArrayList<Vertex_List<String>> getShortestPath(String source, String destination) {
        Map<Vertex<String>, Vertex<String>> dijkstra = this.citiesGraphAL.dijkstra(source);
        ArrayList<Vertex_List<String>> shortestPath = new ArrayList<>();
        Vertex<String> prev = dijkstra.get(this.citiesGraphAL.getVertex(destination));
        while (prev != null) {
            shortestPath.add((Vertex_List<String>) prev);
            prev = dijkstra.get(prev);
        }
        return shortestPath;
    }

    public ArrayList<Vertex_List<String>> getVertices() {
        return this.citiesGraphAL.getVertices();
    }
}
