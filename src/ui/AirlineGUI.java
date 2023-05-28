package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import model.Airline;

public class AirlineGUI {

    // Select Graph
    @FXML
    private BorderPane bpSelectGraph;

    @FXML
    private Button btnGraphAL;

    @FXML
    private Button btnGraphAM;

    private Airline airline;

    public AirlineGUI(Airline airline) {
        this.airline = airline;
    }

    @FXML
    private void handleGraphAL() {

    }

    @FXML
    private void handleGraphAM() {

    }

}
