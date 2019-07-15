package application;

import java.util.ArrayList;
import java.util.Arrays;

public enum Element {
	//TODO add fence
	FENCE("fence","file:src/images/fence.png",0,false,0,"", false),
	TREX("T-Rex","file:src/images/t-t-rex.png", 0.01, true, 0, "Brachiosaur", false),
	TREXMATE("T-Rex-Mated","file:src/images/mur.jpg", 0, true, 0, "Brachiosaur", true), //TODO : Edit the picture when all tests will be ok
	FRUIT("Fruit", "file:src/images/fruit.png", 0.026, false, 0.02, "", false), 
	EMPTY("Empty", "file:src/images/empty.png", 0, false, 0, "", false),
	EMPTY2("Empty", "file:src/images/empty2.png", 0, false, 0, "", false), // only to draw
	BABYTREX("Baby-T-Rex","file:src/images/babytrex.png",0,true,0, "Fruit", false),
	BRACHIO("Brachiosaur","file:src/images/brachio.png",0.01, true, 0,"Fruit", false),
	BANANA("Banana","file:src/images/banana.png",0.006,false,0,"", false),
	FIRE("Fire","file:src/images/fire.png",0.01,false,0,"", false);
	
	private String name = "";
	
	public void setSpawn_proba(double spawn_proba) {
		this.spawn_proba = spawn_proba;
	}

	public void setRespawn_proba(double respawn_proba) {
		this.respawn_proba = respawn_proba;
	}

	private ImageSprite visu;
	private double spawn_proba;
	private boolean is_animal;
	private String letter;
	private double respawn_proba;
	private int x;
	private int y;
	private boolean mate;
	private boolean exist;
	private String target;
	private int count;
	
	//constructor name and path to img
	Element(String name, String path_to_img, double spawn_proba, boolean is_animal, double respawn_proba, String target, boolean mate){
		
		this.name = name;
		this.visu = null;
		this.spawn_proba = spawn_proba;
		this.is_animal = is_animal;
		this.respawn_proba = respawn_proba;
		this.target = target;
		this.mate = mate;
		this.exist = false;
		
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
	
	public void setY(int y1){
		y = y1;
	}
	
	public boolean getMate(){
		return mate;
	}
	
	public boolean getExist(){
		return exist;
	}
	
	public void setExist(boolean exist2){
		exist = exist2;
	}
	
	public int getCount(){
		return count;
	}
	
	public void setCount(int count2){
		count = count2;
	}

	public void setWontExist() {
		spawn_proba = 0;
		respawn_proba = 0;
	} 
	
}