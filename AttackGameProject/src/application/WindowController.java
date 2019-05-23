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
    
    
    

    @FXML
    void OpenLevelTwo(ActionEvent event) {

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
    
    @FXML
    void OpenLevelThree(ActionEvent event) {
    	
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


    

    //@Override
    //public void initialize(URL arg0, ResourceBundle arg1) {
//    	// TODO Auto-generated method stub
    	
    //}
    

}
