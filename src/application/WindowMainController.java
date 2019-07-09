package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class WindowMainController {

    @FXML
    private GridPane GridMap;
    private int x_window = 0;
    private int y_window = 0;
    private Element button_state = null;
	private static Thread thread;
	private static LifeThread lifethread;
    
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
    void OnMouseClicked(MouseEvent event) {
    	ElementClass button_state_obj = new ElementClass(button_state);
    	int[] cell = GetCell(event.getSceneX(),event.getSceneY());
    	if(cell[0] != -1 && cell[1] != -1 && button_state != null) {
    		Map.SetElement(cell[0], cell[1], button_state_obj);
			button_state = null;
    	}
    }
    
    public int[] GetCell(double x, double y) {
    	int[] cell = new int[2];
    	cell[0] = -1;
    	cell[1] = -1;
    	if(x >= 20 && x <= 420 && y >= 20 && y <= 270) {
    		cell[0] = (int) (x - 20)/10;
    		cell[1] = (int) (y - 20)/10;
    		
    	}
    	return cell;
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
    	ElementClass[][] partial_map = Map.ReturnPartialMap(this.x_window, this.y_window, this.GridMap.impl_getColumnCount(), GridMap.impl_getRowCount());
    	this.GridMap.getChildren().clear();

    	for(int i=0; i<partial_map.length; i++) {
    		for(int j=0; j<partial_map[i].length; j++) {
    			AddImage(partial_map[i][j].getElement().getVisu(),i,j);
			}
		}	
    }
    
    public void AddBananaRandomOnMap(){
    	LifeThread.RandomlyAddToMap(Element.BANANA);
    }
    public void BurnRandomMap() {
    	LifeThread.RandomlyAddToMap(Element.FIRE);
    }
    public void AddBananaOnMap() {
    	button_state = Element.BANANA;
    }
    public void BurnMap() {
    	System.out.print("burn");
    	button_state = Element.FIRE;
    }
    @FXML
    void AddBrachioOnMap() {
    	button_state = Element.BRACHIO;
    }

    @FXML
    void AddFruitOnMap() {
    	System.out.print("done");
    	button_state = Element.FRUIT;

    }

    @FXML
    void AddTrexOnMap() {
    	button_state = Element.TREX;

    }
}


