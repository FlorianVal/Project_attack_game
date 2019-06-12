package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Map {
	
	private static Element map[][];
	private static int width;
	private static int height;
	private static WindowMainController controller;
	private static Thread thread;
	private static LifeThread lifethread;
	
	public Map(int width_given, int height_given, WindowMainController controller_given) {
		//clean last thread
		if(thread != null){
			lifethread.doStop();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		width = width_given;
		height = height_given;
		map = new Element[width][height];
		controller = controller_given;
		InitRandomMap(map);
		System.out.println("Map inited");
		launch_life_thread();

		
	}

	private void launch_life_thread() {
		lifethread = new LifeThread(map, controller);
	    thread = new Thread(lifethread);
	    thread.start();
	}
	
	public static void setMap(Element[][] map) {
		Map.map = map;
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
	
	//console version
	public static void displayMap(Element[][] map_to_display){
		int i, j;
		System.out.print("Map to display :");
		System.out.print(map_to_display.length);
		System.out.println(map_to_display[0].length);
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
	
	public static Element[][] ReturnPartialMap(int top_x, int top_y, int width, int height){
		Element[][] display_map = new Element[width][height];
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
	
}