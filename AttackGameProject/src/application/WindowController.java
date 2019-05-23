package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WindowController {

    @FXML
    private TextField width;

    @FXML
    private TextField height;

    @FXML
    void OpenLevelOne(ActionEvent event){
    	if(CheckParameters()) {
	   		OpenMainWindow();
	    }
    	else {
    		// TODO show message on window
    		System.out.println("incorrect params");
    	}
    		
    }
 
    @FXML
    void OpenLevelTwo(ActionEvent event) {
    	if(CheckParameters()) {
	    	try {
	    		
	    		Parent root = FXMLLoader.load(getClass().getResource("/application/Level2.fxml"));
	    		Scene scene = new Scene(root);
	    		Stage stage = new Stage();
	    		stage.setTitle("Level 2");
	    		stage.setScene(scene);
	    		stage.show();
	    		
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
    	}
    	else {
    		// TODO show message on window
    		System.out.println("incorrect params");
    	}
    	
    }
    
    @FXML
    void OpenLevelThree(ActionEvent event) {
    	if(CheckParameters()) {
	    	try {
	    		
	    		Parent root = FXMLLoader.load(getClass().getResource("/application/Level3.fxml"));
	    		Scene scene = new Scene(root);
	    		Stage stage = new Stage();
	    		stage.setTitle("Level 3");
	    		stage.setScene(scene);
	    		stage.show();
	    		
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
    	}
    	else {
    		// TODO show message on window
    		System.out.println("incorrect params");
    	}

    }
    
    void OpenMainWindow() {
	    try {	
			Parent root = FXMLLoader.load(getClass().getResource("/application/Level1.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Level 1");
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    boolean CheckParameters() {
    	//check if field not empty
    	if((width.getText() == null || width.getText().trim().isEmpty()) || ((height.getText() == null || height.getText().trim().isEmpty()))) {
    		return false;
    	}
    	else {
    		// if not empty check value in field (if int(try catch) and if size >(80,50))
    		try {
		    	if(Integer.parseInt(width.getText()) >= 80 && Integer.parseInt(height.getText()) >= 50) {
		    		return true;
		    	}
		    	else {
		    		return false;
		    	}
    		}
    		catch(NumberFormatException e) {
    			System.out.println("Problem converting to Int");
    			return false;
    		}
    	}
    }
    
}
