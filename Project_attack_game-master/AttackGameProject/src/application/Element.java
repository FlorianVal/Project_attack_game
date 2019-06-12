package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

public enum Element {
	//TODO add image in enum
	
	TREX("T-Rex","t-rex.jpg", 0.003, true, "T"), 
	FRUIT("Fruit", "", 0.006, false, "F"), 
	EMPTY("Empty", "", 0, false, "");
	
	private String name = "";
	private Image visu;
	private double spawn_proba;
	private boolean is_animal;
	private String letter;
	
	//constructor name and path to img
	Element(String name, String path_to_img, double spawn_proba, boolean is_animal, String letter){
		
		this.name = name;
		this.visu = null;
		this.spawn_proba = spawn_proba;
		this.is_animal = is_animal;
		this.letter = letter;
		
		
		
		try {
			FileInputStream inputstream = new FileInputStream(path_to_img);
			this.visu = new Image(inputstream);
		}
		catch(FileNotFoundException e) {
			this.visu = null;

		}
	}

	public String getName() {
		return name;
	}

	public String getLetter() {
		return letter;
	}

	public Image getVisu() {
		return visu;
	}

	public double getSpawn_proba() {
		return spawn_proba;
	}
	
	public boolean is_animal() {
		return is_animal;
	}

	
}