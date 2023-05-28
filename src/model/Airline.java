package model;

import graph.GraphAdjacencyList;
import graph.Vertex;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class Airline {
    private final GraphAdjacencyList<String> citiesGraphAL;

    public Airline() {
        this.citiesGraphAL = new GraphAdjacencyList<>(false);
    }

    public void loadCities() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources\\cities.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                this.citiesGraphAL.addVertex(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConnections() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources\\connections.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" - ");
                String city1 = split[0];
                String city2 = split[1];
                int distance = Integer.parseInt(split[2]);
                this.citiesGraphAL.addEdge(city1, city2, distance);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void optimize() {
        this.citiesGraphAL.prim("New York City");
    }

    public ArrayList<Vertex<String>> getShortestPath(String source, String destination) {
        Map<Vertex<String>, Vertex<String>> dijkstra = this.citiesGraphAL.dijkstra(source);
        ArrayList<Vertex<String>> shortestPath = new ArrayList<>();
        Vertex<String> prev = dijkstra.get(this.citiesGraphAL.getVertex(destination));
        while (prev != null) {
            shortestPath.add(prev);
            prev = dijkstra.get(prev);
        }
        return shortestPath;
    }

    public ArrayList<Vertex<String>> getVertices() {
        return this.citiesGraphAL.getVertices();
    }
}
