package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

public enum Element {
	//TODO add image in enum
	// element ( formal name, path to image of visu, spawn probability, is animal,
	//letter to display(until there is image to display), probability of spawning randomly on the map)
	TREX("T-Rex","file:src/images/trex.png", 0.002, true, 0), 
	FRUIT("Fruit", "file:src/images/fruit.png", 0.006, false, 0.005), 
	EMPTY("Empty", "file:src/images/empty.png", 0, false, 0);
	
	private String name = "";
	private Image visu;
	private double spawn_proba;
	private boolean is_animal;
	private String letter;
	private double respawn_proba;
	
	//constructor name and path to img
	Element(String name, String path_to_img, double spawn_proba, boolean is_animal, double respawn_proba){
		
		this.name = name;
		this.visu = null;
		this.spawn_proba = spawn_proba;
		this.is_animal = is_animal;
		this.respawn_proba = respawn_proba;
		
		
		if(path_to_img != null){
			this.visu = new Image(path_to_img);
		}
		else{
			this.visu = null;
		}
	}

	public double getRespawn_proba() {
		return respawn_proba;
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