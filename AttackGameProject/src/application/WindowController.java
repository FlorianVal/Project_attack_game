package application;

import java.awt.event.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class WindowController {

    @FXML
    private TextField width;

    @FXML
    private TextField height;

    @FXML
    void OpenLevelOne() {
    	System.out.print(width.getText());

    }

    @FXML
    void OpenLevelThree() {

    }

    @FXML
    void OpenLevelTwo() {

    }

}
