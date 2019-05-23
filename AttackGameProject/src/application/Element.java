package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

public enum Element {
	//TODO add image in enum
	trex("T-Rex",""),
	fruit("Fruit", "");
	
	private String name;
	private Image visu;
	
	//constructor name and path to img
	Element(String name, String path_to_img){
		
		this.name = name;
		this.visu = null;
		
		try {
			FileInputStream inputstream = new FileInputStream(path_to_img);
			this.visu = new Image(inputstream);
		}
		catch(FileNotFoundException e) {
			this.visu = null;

		}
	}
}