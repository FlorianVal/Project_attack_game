package application;

import java.awt.event.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class WindowMainController {

    @FXML
    private GridPane GridMap;
    private int x_window;
    private int y_window;
    
    //TODO add method to put part of the map in the grid pane

    @FXML
    void MoveMapDown() {
    	Map.displayMap();
    }

    @FXML
    void MoveMapLeft() {

    }

    @FXML
    void MoveMapRight() {

    }

    @FXML
    void MoveMapUp() {

    }

}
