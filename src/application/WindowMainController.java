package application;

//import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyEvent;
//import java.awt.event.MouseEvent;
//import java.lang.invoke.MethodHandles;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
//import javafx.application.Platform;

public class WindowMainController {

    @FXML
    private GridPane GridMap;
    private int x_window = 0;
    private int y_window = 0;
	private Thread thread;
	private LifeThread lifethread;
    
    //TODO add method to put part of the map in the grid pane
    @FXML
    void initialize(WindowMainController controller,int width, int height) {
    	
        assert GridMap != null : "fx:id=\"GridMap\" was not injected: check your FXML file 'Level1.fxml'.";
		new Map(width,height);
        launch_life_thread(controller);

    }
    
    private void launch_life_thread(WindowMainController controller_given) {
		//clean last thread
		if(this.thread != null){
			this.lifethread.doStop();
			try {
				this.thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.lifethread = new LifeThread(controller_given);
	    this.thread = new Thread(lifethread);
	    this.thread.start();
	}
    
	public void PauseThread() {
		this.lifethread.doPause();
	}
    
    @FXML
    void keyPressed(KeyEvent event) {
    	System.out.println("Key event");
    	System.out.println(event.getCode());
    	switch(event.getCode().toString()) {
    	case "RIGHT":
    		MoveMapRight();
    		break;
    	case "LEFT":
    		MoveMapLeft();
    		break;
    	case "UP":
    		MoveMapUp();
    		break;
    	case "DOWN":
    		MoveMapDown();
    		break;
    	case "P":
    		PauseThread();
    		break;
    		
    	}
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
    
    
    public void AddImage(ImageSprite image, int x, int y){
            ImageView iv = new ImageView(image.getImage());
            //do the sprite with rectangle of view moving on the image
            iv.setViewport(new Rectangle2D(image.getstate_of_sprite()*10, 0, 10, 10));
            GridMap.add(iv, x, y);
    }
     
    public void DisplayMap() {
    	System.out.print(this.x_window);
    	System.out.print(" : ");
    	System.out.println(this.y_window);
    	Element[][] partial_map = Map.ReturnPartialMap(this.x_window, this.y_window, this.GridMap.impl_getColumnCount(), GridMap.impl_getRowCount());
    	this.GridMap.getChildren().clear();

    	for(int i=0; i<partial_map.length; i++) {
    		for(int j=0; j<partial_map[i].length; j++) {
    			
    		AddImage(partial_map[i][j].getVisu(),i,j);

			}
		}	
    }
    
    public void AddBananaRandomOnMap(){
    	LifeThread.RandomlyAddToMap(Element.BANANA);
    }
    
}


