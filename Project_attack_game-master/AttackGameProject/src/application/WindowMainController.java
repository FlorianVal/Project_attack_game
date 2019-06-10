package application;

import javafx.scene.control.Label;
import java.awt.event.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class WindowMainController {

    @FXML
    private GridPane GridMap;
    private int x_window = 0;
    private int y_window = 0;
    
    //TODO add method to put part of the map in the grid pane

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
    	Label label = new Label(Integer.toString(1));
        GridMap.add(label, 0, 0);

    }
    
    public void DisplayMap() {
    	System.out.print(this.x_window);
    	System.out.print(" : ");
    	System.out.println(this.y_window);
    	Element[][] partial_map = Map.ReturnPartialMap(this.x_window, this.y_window, GridMap.impl_getColumnCount(), GridMap.impl_getRowCount());
    	GridMap.getChildren().clear();

    	for(int i=0; i<partial_map.length; i++) {
			for(int j=0; j<partial_map[i].length; j++) {
				if(partial_map[i][j] != Element.EMPTY) {
					GridMap.add(new Label("A"), i, j);
				}
			}
		}
    	
    }

}
