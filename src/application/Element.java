package application;

import javafx.scene.image.Image;

public enum Element {
	// element ( formal name, path to image of visu, spawn probability, is animal,
	//letter to display(until there is image to display), probability of spawning randomly on the map)


	TREX("T-Rex","file:src/images/trex.png", 0.006, true, 0, false), 
	FRUIT("Fruit", "file:src/images/fruit.png", 0.006, false, 0.02, false), 
	EMPTY("Empty", "file:src/images/empty.png", 0, false, 0, false),
	BABYTREX("Baby-T-Rex","file:src/images/babytrex.png",0,true,0, false),
	BRACHIO("Brachiosaur","file:src/images/brachio.png",0.03, true, 0, false),
	BANANA("Banana","file:src/images/mur.jpg",0,false,0, false); //TODO : Draw an image for banana
	
	private String name = "";
	private Image visu;
	private double spawn_proba;
	private boolean is_animal;
	private String letter;
	private double respawn_proba;
	private int x;
	private int y;
	private boolean mate;
	
	//constructor name and path to img
	Element(String name, String path_to_img, double spawn_proba, boolean is_animal, double respawn_proba, boolean mate){
		
		this.name = name;
		this.visu = null;
		this.spawn_proba = spawn_proba;
		this.is_animal = is_animal;
		this.respawn_proba = respawn_proba;
		this.mate = mate;
		
		
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
	
	public int getX(){
		return x;
	}
	
	public void setX(int x1){
		x = x1;
	}
	
	public boolean getMate(){
		return mate;
	}
	
	public void setMate(boolean bool){
		mate = bool;
	}
	

	
}