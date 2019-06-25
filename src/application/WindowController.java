package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    		
	   		OpenMainWindow("/application/Level1.fxml", "Level 1");
	    }
    	else {
    		ShowMessageError();
    		//System.out.println("incorrect params");
    	}
    		
    }
 
    @FXML
    void OpenLevelTwo(ActionEvent event) {
    	if(CheckParameters()) {
	   		OpenMainWindow("/application/Level2.fxml", "Level 2");
    	}
    	else {
    		ShowMessageError();
    		//System.out.println("incorrect params");
    	}
    	
    }
    
    @FXML
    void OpenLevelThree(ActionEvent event) {
    	if(CheckParameters()) {
	   		OpenMainWindow("/application/Level3.fxml", "Level 3");
    	}
    	else {
    		ShowMessageError();
    		//System.out.println("incorrect params");
    	}

    }
    
    void OpenMainWindow(String level_fxml_file, String name) {
	    try {	
			FXMLLoader loader1 = new FXMLLoader(getClass().getResource(level_fxml_file));
			Parent root = loader1.load();
			WindowMainController captureWindowController = loader1.getController();
			captureWindowController.initialize(captureWindowController, Integer.parseInt(width.getText()), Integer.parseInt(height.getText()));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle(name);
			stage.setScene(scene);
			stage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void ShowMessageError(){

		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setHeaderText("The size of the map has to be at least 40x25");
		errorAlert.setTitle("Parameters entered not valid");
		errorAlert.showAndWait();
    	
    }
    
    
    boolean CheckParameters() {
    	//check if field not empty
    	if((width.getText() == null || width.getText().trim().isEmpty()) || ((height.getText() == null || height.getText().trim().isEmpty()))) {
    		return false;
    	}
    	else {
    		// if not empty check value in field (if int(try catch) and if size >(80,50))
    		try {
		    	if(Integer.parseInt(width.getText()) >= 40 && Integer.parseInt(height.getText()) >= 25) {
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
