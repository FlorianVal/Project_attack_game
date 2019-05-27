package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

public enum Element {
	//TODO add image in enum
	trex("T-Rex","", 0,003),
	fruit("Fruit", "", 0,006),
	empty("Empty", "", 1);
	
	private String name;
	private Image visu;
	private double spawn_proba;
	
	//constructor name and path to img
	Element(String name, String path_to_img, double spawn_proba){
		
		this.name = name;
		this.visu = null;
		this.spawn_proba = spawn_proba;
		
		
		try {
			FileInputStream inputstream = new FileInputStream(path_to_img);
			this.visu = new Image(inputstream);
		}
		catch(FileNotFoundException e) {
			this.visu = null;

		}
	}
}