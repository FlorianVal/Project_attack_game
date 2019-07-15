package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//method in Map should only init the map and return info about the map
//moving animals is in the life Thread and displaying the map is only in the controller

public class Map {
	
	private static ElementClass map[][];
	private static int emptymap[][];
	private static int width;
	private static int height;
	
	public Map(int width_given, int height_given) {
		width = width_given;
		height = height_given;
		map = new ElementClass[width][height];
		emptymap = new int[width][height];
		InitRandomMap(map);
		System.out.println("Map inited");
	}
	
	public static Element GetElement(int x, int y) {
		return map[x][y].getElement();
	}
	
	public static ElementClass GetElementClass(int x, int y){
		return map[x][y];
	}
	
	public static void SetElement(int x, int y, ElementClass elem) {
		map[x][y] = elem;
	}
	
	public static void SetMap(ElementClass[][] map) {
		Map.map = map;
	}
	
	public static ElementClass[][] GetMap(){
		return Map.map;
	}
	
	public static int GetEmpty(int i, int j) {
		return emptymap[i][j];
	}

	private static void InitRandomMap(ElementClass[][] map) {
		//list of element to put on map
		ArrayList<Element> elist = new ArrayList<Element>(Arrays.asList(Element.values()));
		//fill with empty
		EmptyMap();
		//then add objects
		for(Element elem: elist) {
			int number_of_element = (int) (map.length * map[0].length * elem.getSpawn_proba());
			AddAnimalsToMap(elem, number_of_element);
	
			//random shuffle
			ShuffleMap();
			
		}
		
		
	}
	
	private static void ShuffleMap() {
		    Random random = new Random();

		    for (int i = map.length - 1; i > 0; i--) {
		        for (int j = map[i].length - 1; j > 0; j--) {
		            int m = random.nextInt(map.length);
		            int n = random.nextInt(map[0].length);

		            ElementClass temp = map[i][j];
		            map[i][j] = map[m][n];
		            map[m][n] = temp;
		        }
		    }
	}
	
	private static void EmptyMap() {
		Random rand = new Random();
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[i].length; j++) {
				emptymap[i][j] = rand.nextInt(2);
				ElementClass emptyElement = new ElementClass(Element.EMPTY);
				map[i][j] = emptyElement;				
			}
		}
	}
	
	// used to init the map
	// add animal side by side on the map before shuffling
	private static void AddAnimalsToMap(Element elem_to_add, int count) {
		int x,y;
		int inner_count = 0;
		int i =0;
		int j =0;
		
		while(inner_count<count) {
			if(map[i][j].getElement() == Element.EMPTY) {
				ElementClass elem_object_to_add = new ElementClass(elem_to_add);
				map[i][j] = elem_object_to_add;
				inner_count +=1;
			}
			i +=1;
			if(i == map.length) {
				i=0;
				j+=1;
			}
			if(j == map[0].length) {
				throw new OutOfMemoryError();
			}
		
		}
		
	}
	
	
	public static void AddOneElementToMap(Element elementToAdd, int x, int y){		
		
		int nbBbAdded = 0;
		
		if(map[x][y].getElement() == Element.EMPTY){
			while(elementToAdd.getExist() == false){
				ElementClass element_object_to_add = new ElementClass(elementToAdd);
				map[x][y] = element_object_to_add;
				nbBbAdded+=1;
				elementToAdd.setExist(true);
			}
			System.out.println("Nombre bb ajout�s : " +  nbBbAdded);
			
		}
		
		
		if(map[x][y].getElement() != Element.EMPTY){
			System.out.println("There is already an element");
		}
	}
	
	//console version
	public static void displayMap(Element[][] map_to_display){
		int i, j;
        for(i=0; i<map_to_display.length; i++){
            for(j=0; j<map_to_display[i].length ; j++){
            	System.out.print(i);
            	System.out.print(j);
                System.out.print(map_to_display[i][j]);
                System.out.print(" ");
            }
            System.out.println(" ");
        }
	}
	
	public static int GetWidth() {
		return width;
	}
	
	public static int GetHeight() {
		return height;
	}
	
	public static ElementClass[][] ReturnPartialMap(int top_x, int top_y, int width, int height){
		ElementClass[][] display_map = new ElementClass[width][height];
		try {
			int i_display = 0;
			int j_display = 0;
			for(int i=top_y; i<width+top_y; i++) {
				for(int j=top_x; j<height+top_x; j++) {
					display_map[i_display][j_display] = map[i][j];
					j_display+=1;
				}
				j_display=0;
				i_display+=1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return display_map;
		
	}
	
	public static int[][] ReturnPartialEmptyMap(int top_x, int top_y, int width, int height){
		int[][] display_map = new int[width][height];
		try {
			int i_display = 0;
			int j_display = 0;
			for(int i=top_y; i<width+top_y; i++) {
				for(int j=top_x; j<height+top_x; j++) {
					display_map[i_display][j_display] = emptymap[i][j];
					j_display+=1;
				}
				j_display=0;
				i_display+=1;
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return display_map;
		
	}
}