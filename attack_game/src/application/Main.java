package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			primaryStage.setTitle("Menu Attack Game");
			//VBox vbox = new VBox();
			BorderPane root = new BorderPane();
			Button lvl1 = new Button("Level 1");
			Button lvl2 = new Button("Level 2");
			Button lvl3 = new Button("Level 3");
			
			//vbox.getChildren().add(lvl1);
			//vbox.setAlignment(Pos.TOP_CENTER);
			//vbox.setMargin(lvl1,new Insets(10, 10, 10, 10));
			
			//vbox.getChildren().add(lvl2);
			//vbox.setAlignment(Pos.CENTER);
			//vbox.setMargin(lvl2,new Insets(10, 10, 10, 10));
			
			//vbox.getChildren().add(lvl3);
			//vbox.setAlignment(Pos.BOTTOM_CENTER);
			//vbox.setMargin(lvl3,new Insets(10, 10, 10, 10));
			
			root.setTop(lvl1);
			root.setCenter(lvl2);
			root.setBottom(lvl3);
			
			Scene scene = new Scene(root, 400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
