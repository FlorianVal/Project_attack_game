package application;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    
    private final String sprites_path = "file:AttackGameProject/src/images/";
    private final String trex = sprites_path + "caisse.jpg";
    private final String fruit = sprites_path + "mur.gif";
    private final String empty = sprites_path + "zone.gif";
    
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
    
    
    public void AddImage(String tilePath, int x, int y){
    	if (tilePath != null) {
    		Image image = new Image(tilePath);
            ImageView iv = new ImageView(image);
            GridMap.add(iv, x, y);
        }
    }
    
    public void DisplayMap() {
    	System.out.print(this.x_window);
    	System.out.print(" : ");
    	System.out.println(this.y_window);
    	Element[][] partial_map = Map.ReturnPartialMap(this.x_window, this.y_window, this.GridMap.impl_getColumnCount(), GridMap.impl_getRowCount());
    	// This suppress the cadrillage there might be a better way to do this
    	this.GridMap.getChildren().clear();
    	String tilePath = "";

    	for(int i=0; i<partial_map.length; i++) {
    		for(int j=0; j<partial_map[i].length; j++) {
				
    			if(partial_map[i][j] == Element.TREX){
					tilePath = trex;
			    }
			   
				if(partial_map[i][j] == Element.FRUIT){
					tilePath = fruit;	
				}
				
				if(partial_map[i][j] == Element.EMPTY){
					tilePath = empty;
				}
				
				AddImage(tilePath,i,j);
	
			}
		}	
    }
}


