package application;

import javafx.scene.control.Label;
import java.awt.event.MouseEvent;
import java.lang.invoke.MethodHandles;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.application.Platform;

public class WindowMainController {

    @FXML
    private GridPane GridMap;
    private int x_window = 0;
    private int y_window = 0;
    //private LifeThread life_thread;
    
    //TODO add method to put part of the map in the grid pane
    @FXML
    void initialize() {
        assert GridMap != null : "fx:id=\"GridMap\" was not injected: check your FXML file 'Level1.fxml'.";
    }
    
    @FXML
    void MoveMapDown() {
    	if(x_window<Map.GetHeight() - GridMap.impl_getRowCount()) {
        	x_window +=1;
    	}
    	DisplayMap();


    }

    @FXML
    void MoveMapLeft() {
    	if(y_window>0) {
        	y_window -=1;
    	}
    	DisplayMap();

    }

    @FXML
    void MoveMapRight() {
    	if(y_window<Map.GetWidth() - GridMap.impl_getColumnCount()) {
        	y_window +=1;
    	}
    	DisplayMap();

    }

    @FXML
    void MoveMapUp() {
    	if(x_window>0) {
        	x_window -=1;
    	}
    	DisplayMap();

    }
    
    
    public void DisplayMap() {
    	Element[][] partial_map = Map.ReturnPartialMap(this.x_window, this.y_window, this.GridMap.impl_getColumnCount(), GridMap.impl_getRowCount());
    	// This suppress the cadrillage there might be a better way to do this
    	this.GridMap.getChildren().clear();

    	for(int i=0; i<partial_map.length; i++) {
			for(int j=0; j<partial_map[i].length; j++) {
				this.GridMap.add(new Label(partial_map[i][j].getLetter()), i, j);	
			}
    	}
    	}
    }


