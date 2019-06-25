package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javafx.application.Platform;

public class LifeThread implements Runnable {

	private boolean doStop = false;
	private boolean IsPaused = false;
	public WindowMainController controller;

	// constructor include map and controller of window
	public LifeThread(WindowMainController controller_given) {
		controller_given.DisplayMap();
		this.controller = controller_given;

	}

	// method to stop the running Thread
	public synchronized void doStop() {
		this.doStop = true;
	}

	public synchronized void doPause() {
		if (this.IsPaused) {
			this.IsPaused = false;
		} else {
			this.IsPaused = true;
		}
	}

	private synchronized boolean keepRunning() {
		return this.doStop == false;
	}

	@Override
	public void run() {
		while (keepRunning()) {
			if (this.IsPaused == false) {
				MoveAnimals();
				Respawn();
				// run later is needed to run display map in life thread
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						controller.DisplayMap();

					}

				});
			}
			// Thread wait for the next movement
			try {
				Thread.sleep(3L * 100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	// use to spawn randomly things on map
	public void Respawn() {
		// list all elements
		Random rand = new Random();
		ArrayList<Element> elist = new ArrayList<Element>(Arrays.asList(Element.values()));

		for (Element elem : elist) {
			double spawn_or_not = rand.nextDouble();
			if (spawn_or_not < elem.getRespawn_proba()) {
				RandomlyAddToMap(elem);
			}

		}
	}

	// randomly add the element_to_add on map (mainly use for respawn)

	public static void RandomlyAddToMap(Element element_to_add) {
		Random rand = new Random();
		boolean is_spawn = false;
		// used if map is full to avoid blocking and also to avoid non_spawning due to
		// already an element on case
		int number_of_spawn_try = 4;
		while (is_spawn == false && number_of_spawn_try >= 0) {
			// choose a random case on map
			int x = rand.nextInt(Map.GetWidth());
			int y = rand.nextInt(Map.GetHeight());
			// spawn if the randomly chosen case is empty else retry
			if (Map.GetElement(x, y) == Element.EMPTY) {
				Map.SetElement(x, y, element_to_add);
				is_spawn = true;
			}
			number_of_spawn_try -= 1;
		}

	}

	// search for every animal on the map and move them one by one
	public void MoveAnimals() {
		Random rand = new Random();
		int animal_count = 0;
		//Element[][] updatedMap = new Element[Map.GetWidth()][Map.GetHeight()];
		
		// loop to count animals (needed to create static array next)
		for (int a = 0; a < Map.GetWidth() - 1; a++) {
			for (int b = 0; b < Map.GetHeight() - 1 ; b++) {
				if (Map.GetElement(a, b).is_animal()) {
					animal_count += 1;

				}
			}
		}
		// create array with pos (x,y) of every animal on the map
		int animals_on_map[][] = new int[animal_count][2];
		animal_count = 0;
		int i, j;
		for (i = 0; i < Map.GetWidth() - 1; i++) {
			for (j = 0; j < Map.GetHeight() - 1; j++) {
				
				if (Map.GetElement(i, j).is_animal()) {
					animals_on_map[animal_count][0] = i;
					animals_on_map[animal_count][1] = j;
					animal_count += 1;
				}
				
			}
		}
		// for loop on the array and random number to decide direction
		for (int count_animal = 0; count_animal < animals_on_map.length; count_animal++) {
			int next_move = rand.nextInt(4);
			// debug force movement
			// next_move = 3;
			MoveAnimal(animals_on_map[count_animal][0], animals_on_map[count_animal][1], next_move);
		}
		// actualize map after moves
		//Map.setMap(updatedMap);
		
		
	}
    
    // used to handle when an animal move into something
    //return winning animal or null if no winning animal
    public Element Encounter(Element first_element, Element second_element) {
    	Element winner = null;
    	//no winning animal if they are same animal
    	if(first_element == second_element) {
    		winner = null;
    	}
    	
    	if(first_element == Element.EMPTY || first_element == Element.FRUIT) {
    		winner = second_element;
    	}
    	if(second_element == Element.EMPTY || second_element == Element.FRUIT) {
    		winner = first_element;
    	}
		return winner;
		
    }
    
    public static void Mate(int x, int y){
    	
    	//1. Instanciation of the new bbtrex that will be added
    	Element baby_trex_to_add = Element.BABYTREX;
    	
    	//2. This boolean will be false until an empty element will be found to place our new bbtrex
    	boolean empty = false;
    	
    	//3. Those integer will allow to get the values of coordinates of the empty cell if the cell just below the mating is not empty (to place the new bbtrex)
    	int a = 0;
    	int b = 0;	
    	
    	//If the element just below the mating is empty, the new baby trex will be placed there
    		if(Map.GetElement(x, y+1) == Element.EMPTY && x < Map.GetWidth() - 1 && y+1 < Map.GetHeight()){
    				Map.AddOneElementToMap(baby_trex_to_add, x, y+1);
    				System.out.println("A Baby TREX was added at the coordinates (" + x + "," + y+1 + ")" );
    		}
    			
    	//If the element just below is not empty, all the column will be covered in order to find an empty element
    			else{
    				
    				while(empty == false){
    					
    					y+=1;
    					if(Map.GetElement(x, y) == Element.EMPTY && x < Map.GetWidth() - 1 && y < Map.GetHeight() - 1){
    						empty = true;
    						a = x;
    						b = y;
    						
    					}
    					Map.AddOneElementToMap(baby_trex_to_add,a,b);
    				}
    				
    			}
    			
    	}
    		
    			
    //}
   

	// moving one animal just by receiving pos of animal and a random number for
	// direction
	public void MoveAnimal(int x, int y, int next_move) {

		// move down
		if (next_move == 0 && y + 1 < Map.GetHeight() - 1) {
			Element winner = Encounter(Map.GetElement(x, y), Map.GetElement(x, y + 1));
			
			if (winner != null) {
				Map.SetElement(x, y, Element.EMPTY);
				Map.SetElement(x, y + 1, winner);
			}
			
			if(Map.GetElement(x, y) == Element.TREX && Map.GetElement(x, y+1) == Element.TREX){
				Mate(x,y);
			}
		}
		
		// move left
		if (next_move == 1 && x > 0) {
			
			Element winner = Encounter(Map.GetElement(x, y), Map.GetElement(x - 1, y));
			
			if (winner != null) {
				Map.SetElement(x, y, Element.EMPTY);
				Map.SetElement(x - 1, y, winner);
			}
		}
		
		if(Map.GetElement(x, y) == Element.TREX && Map.GetElement(x, y+1) == Element.TREX){
			Mate(x,y);
		}
		
		// move up
		if (next_move == 2 && y > 0) {
			Element winner = Encounter(Map.GetElement(x, y), Map.GetElement(x, y - 1));
			if (winner != null) {
				Map.SetElement(x, y, Element.EMPTY);
				Map.SetElement(x, y - 1, winner);
			}
		}
		
		if(Map.GetElement(x, y) == Element.TREX && Map.GetElement(x, y).getMate() == false && Map.GetElement(x, y+1) == Element.TREX && Map.GetElement(x, y+1).getMate() == false){
			Mate(x,y);

		}
		
		// move right
		if (next_move == 3 && x + 1 < Map.GetWidth() - 1) {
			Element winner = Encounter(Map.GetElement(x, y), Map.GetElement(x + 1, y));
			if (winner != null) {
				Map.SetElement(x, y, Element.EMPTY);
				Map.SetElement(x + 1, y, winner);
			}
		}
		
		if(Map.GetElement(x, y) == Element.TREX && Map.GetElement(x, y+1) == Element.TREX){
			Mate(x,y);
		}

	}
}
