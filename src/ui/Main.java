package ui;

import model.Airline;

import java.util.Scanner;

/**
* Class name: Main
* General Description: This class represents the entry point of the airline program.
*/
public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static Airline airline;

/**
* Method: main
* @return type: void
* @param : args (String[])
* General description: This method is the main entry point of the program.
* Parameter description: args - An array of strings representing the command line arguments.
* @return value: None
 */
    public static void main(String[] args) {
        airline = new Airline();
        selectGraph();
    }

/**
* Method: selectGraph
* @return type: void
* @param: None
* General description: This method displays a menu to select the type of graph and loads the corresponding cities.
* Parameter description: None
* @return value: None
*/
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
/**
* Method: menu
* @return type: void
* @param : graphOption (int)
* General description: This method displays a menu of options and processes the selected option.
* Parameter description: graphOption - An integer representing the graph option selected.
* @return value: None
*/
    public static void menu(int graphOption) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n---------- Select an option ----------");
            System.out.println("1. Show cities");
            System.out.println("2. Show connections between cities by time");
            System.out.println("3. Show connections between cities by cost");
            System.out.println("4. Show shortest path by time");
            System.out.println("5. Show shortest path by cost");
            System.out.println("6. Show minimum spanning tree by time");
            System.out.println("7. Show minimum spanning tree by cost");
            System.out.println("0. Exit");
            System.out.println("--------------------------------------");

            String input = sc.nextLine();
            switch (input) {
                case "1":
                    airline.showCities(graphOption);
                    break;
                case "2":
                    airline.loadConnections(0, graphOption);
                    airline.showConnections(0,graphOption);
                    break;
                case "3":
                    airline.loadConnections(1, graphOption);
                    airline.showConnections(1,graphOption);
                    break;
                case "4":
                    // Terminar de implementar bien
                    searchPath(graphOption);
                    break;
                case "5":
                    // Terminar de implementar bien
                    searchPath(graphOption);
                    break;
                case "6":
                    airline.loadConnections(0, graphOption);
                    airline.optimize(0, graphOption);
                    break;
                case "7":
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

/**
* Method: searchPath
* @return type: void
* @param: graphOption (int)
* General description: This method prompts the user to select an origin city and a destination city to search for the shortest route.
* Parameter description: graphOption - An integer representing the graph option selected.
* @return value: None
 */
    public static void searchPath(int graphOption) {
        System.out.println("\nSelect a source city:");
        String source = sc.nextLine();
        System.out.println("\nSelect a destination city:");
        String destination = sc.nextLine();
        airline.getShortestPath(source, destination, graphOption);
    }
}
