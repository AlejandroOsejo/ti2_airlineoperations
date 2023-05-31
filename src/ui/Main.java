package ui;

import model.Airline;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static Airline airline;

    public static void main(String[] args) {
        airline = new Airline();

        selectGraph();
    }

    public static void selectGraph() {
        System.out.println("---------- Select a graph ----------");
        System.out.println("1. Graph with Adjacency List");
        System.out.println("2. Graph with Adjacency Matrix");
        System.out.println("------------------------------------");

        String input = sc.nextLine();
        switch (input) {
            case "1":
                airline.loadCities(1);
                menu(1);
                break;
            case "2":
                airline.loadCities(2);
                menu(2);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    public static void menu(int graphOption) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n---------- Select an option ----------");
            System.out.println("1. Show cities");
            System.out.println("2. Show connections");
            System.out.println("3. Show shortest path by time");
            System.out.println("4. Show shortest path by cost");
            System.out.println("5. Show minimum spanning tree by time");
            System.out.println("6. Show minimum spanning tree by cost");
            System.out.println("0. Exit");
            System.out.println("--------------------------------------");

            String input = sc.nextLine();
            switch (input) {
                case "1":
                    airline.showCities(graphOption);
                    break;
                case "2":
                    airline.showConnections(graphOption);
                    break;
                case "3":
                    // Terminar de implementar bien
                    searchPath(graphOption);
                    break;
                case "4":
                    // Terminar de implementar bien
                    searchPath(graphOption);
                    break;
                case "5":
                    airline.loadConnections(0, graphOption);
                    airline.optimize(0, graphOption);
                    break;
                case "6":
                    airline.loadConnections(1, graphOption);
                    airline.optimize(1, graphOption);
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    public static void searchPath(int graphOption) {
        System.out.println("\nSelect a source city:");
        String source = sc.nextLine();
        System.out.println("\nSelect a destination city:");
        String destination = sc.nextLine();
        airline.getShortestPath(source, destination, graphOption);
    }
}
