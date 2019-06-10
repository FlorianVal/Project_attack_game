package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Map {
	
	private static Element map[][];
	private int width;
	private int height;
	
	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		this.map = new Element[width][height];
		InitRandomMap(map);
	}

	private static void InitRandomMap(Element[][] map) {
		//list of element to put on map
		ArrayList<Element> elist = new ArrayList<Element>(Arrays.asList(Element.values()));
		//fill with empty
		EmptyMap();
		//then add objects
		for(Element elem: elist) {
			int number_of_element = (int) (map.length * map[0].length * elem.getSpawn_proba());
			System.out.print(elem.getName());
			System.out.print(": number of element :");
			System.out.println(number_of_element); 
			System.out.println(elem.getSpawn_proba());
			AddToMap(elem, number_of_element);
			//random shuffle
			ShuffleMap();
			
		}
		
		
	}
	
	private static void ShuffleMap() {
		    Random random = new Random();

		    for (int i = map.length - 1; i > 0; i--) {
		        for (int j = map[i].length - 1; j > 0; j--) {
		            int m = random.nextInt(i + 1);
		            int n = random.nextInt(j + 1);

		            Element temp = map[i][j];
		            map[i][j] = map[m][n];
		            map[m][n] = temp;
		        }
		    }
		}
	
	private static void EmptyMap() {
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[i].length; j++) {
				map[i][j] = Element.EMPTY;				
			}
		}
	}
	
	private static void AddToMap(Element elem_to_add, int count) {
		int inner_count = 0;
		int i =0;
		int j =0;
		while(inner_count<count) {
			if(map[i][j] == Element.EMPTY) {
				map[i][j] = elem_to_add;
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
	
	public static void displayMap(){
		int i, j;
        for(i=0; i<map.length; i++){
            for(j=0; j<map.length ; j++){
                System.out.print(map[i][j].getName());
                System.out.print(" ");
            }
            System.out.println(" ");
        }
	}
	

	
	
}