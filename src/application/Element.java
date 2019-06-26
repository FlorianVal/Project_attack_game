package application;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Arrays;

public enum Element {
	// element ( 
		//formal name, 
		//path to image of visu, 
		//spawn probability, 
		//is animal,
		//letter to display(until there is image to display), 
		//probability of spawning randomly on the map
		//target )

	TREX("T-Rex","file:src/images/trex.png", 0.000, true, 0, "Brachiosaur",false), 
	FRUIT("Fruit", "file:src/images/fruit.png", 0.026, false, 0.02, "",false), 
	EMPTY("Empty", "file:src/images/empty.png", 0, false, 0, "",false),
	BABYTREX("Baby-T-Rex","file:src/images/babytrex.png",0,true,0, "Fruit",false),
	BRACHIO("Brachiosaur","file:src/images/brachio.png",0.001, true, 0,"Fruit",false),
	BANANA("Banana","file:src/images/banana.png",0.006,false,0,"",false),
	FIRE("Fire","file:src/images/fire.png",0.01,false,0,"",false);
	
	private String name = "";
	private ImageSprite visu;
	private double spawn_proba;
	private boolean is_animal;
	private String letter;
	private double respawn_proba;
	private int x;
	private int y;
	private boolean mate;
	private String target;
	//constructor name and path to img
	//(String name, String path_to_img, double spawn_proba, boolean is_animal, double respawn_proba, boolean mate){

	

	
	//constructor name and path to img
	Element(String name, String path_to_img, double spawn_proba, boolean is_animal, double respawn_proba, String target, boolean mate){
		
		this.name = name;
		this.visu = null;
		this.spawn_proba = spawn_proba;
		this.is_animal = is_animal;
		this.respawn_proba = respawn_proba;
		this.mate = mate;
		this.target = target;
		
		if(path_to_img != null){
			this.visu = new ImageSprite(path_to_img);
		}
		else{
			this.visu = null;
		}
	}
	
	//return the Element targeted
		public Element getTarget() {
			ArrayList<Element> elist = new ArrayList<Element>(Arrays.asList(Element.values()));
			Element targ = Element.EMPTY;
			for(Element elem: elist) {
				if(elem.getName() == target) {
					targ = elem;}
			}
			return targ;
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

	public ImageSprite getVisu() {
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
	public int getY(){
		return y;
	}
	
	public boolean getMate(){
		return mate;
	}
	
	public void setMate(boolean bool){
		mate = bool;
	}
	

	
}