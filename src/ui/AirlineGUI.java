package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Airline;

import java.io.IOException;

public class AirlineGUI {

    // Select Graph
    @FXML
    private BorderPane bpSelectGraph;

    @FXML
    private Button btnGraphAL;

    @FXML
    private Button btnGraphAM;

    // Main
    @FXML
    private Button btnOptimize;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField tfDestination;

    @FXML
    private TextField tfSource;

    private Airline airline;

    public AirlineGUI(Airline airline) {
        this.airline = airline;
    }

    @FXML
    private void handleGraphAL(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Airline Connections");
        stage.show();

        Stage stage2 = (Stage) btnGraphAL.getScene().getWindow();
        stage2.close();

    }

    @FXML
    private void handleGraphAM(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Airline Connections");
        stage.show();

        Stage stage2 = (Stage) btnGraphAM.getScene().getWindow();
        stage2.close();
    }

    @FXML
    private void handleOptimize(ActionEvent event) {

    }

    @FXML
    private void handleSearch(ActionEvent event) {

    }

}
