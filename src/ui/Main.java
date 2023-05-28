package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Airline;

public class Main extends Application {
    private Airline airline;
    private AirlineGUI airlineGUI;

    public static void main(String[] args) {
        launch();
    }

    public Main() {
        this.airline = new Airline();
        this.airlineGUI = new AirlineGUI(this.airline);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("selectGraph.fxml"));
        loader.setController(airlineGUI);

        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Boarding System");
        primaryStage.show();
    }
}
