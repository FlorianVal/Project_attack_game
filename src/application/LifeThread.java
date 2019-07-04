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
	
	public static Element SearchNearestEmptyCell(int x, int y){
		int rho;
		double theta;
		Element empty_cell = Map.GetElement(0, 0);
		int distance_of_seeing = 5;
		
		for(rho = 1; rho < distance_of_seeing; rho ++) {
			for(theta = 0; theta <= 2*Math.PI-0.01; theta+=Math.PI/(rho * 4)) {
				int cell_x = x + (int) Math.round(rho * Math.cos(theta));
				int cell_y = y + (int) Math.round(rho * Math.sin(theta));
				
				if(cell_x > 0 && cell_x < Map.GetWidth() && cell_y >0 && cell_y <Map.GetHeight()){
					empty_cell.setX(cell_x);
					empty_cell.setY(cell_y);
					empty_cell = Map.GetElement(cell_x, cell_y);
				}
				
			}
		}
		
		return empty_cell;
		
		
	}
	
	
	// overload to use target
	private int SearchNearest(int x ,int y) {
		return SearchNearest(x, y, Map.GetElement(x, y).getTarget());
	}
	
	
	private static int SearchNearest(int x ,int y, Element target) {
		int rho;
		double theta;
		Element elem_around;
		int distance_of_seeing = 12;
		int direction = -1;
		boolean found = false;
		//System.out.printf("Search nearest : %d %d %s \n \n",x,y,Map.GetElement(x, y).getName());
		for(rho = 1; rho < distance_of_seeing; rho ++) {
			for(theta =0; theta <= 2*Math.PI-0.01; theta+=Math.PI/(rho * 4)) {
				int cell_x = x + (int) Math.round(rho * Math.cos(theta));
				int cell_y = y + (int) Math.round(rho * Math.sin(theta));
				if(cell_x > 0 && cell_x < Map.GetWidth() && cell_y >0 && cell_y <Map.GetHeight()) {
					elem_around = Map.GetElement(cell_x, cell_y);
					//elem_around.setX(cell_x);
					//elem_around.setY(cell_y);
					
					if(elem_around.getName() == target.getName()) {
						
						found = true;
						//System.out.printf("Target found : %d %d %s \n", cell_x, cell_y, elem_around.getName());
						if(theta >= 7*Math.PI/4 || theta <= Math.PI/4) {
							direction = 3;
							//RIGHT
						}
						if(theta >= Math.PI/4 && theta <= 3*Math.PI/4) {
							direction = 0;

							//down
						}
						if(theta >= 3*Math.PI/4 && theta <= 5*Math.PI/4) {
							direction = 1;

							//LEFT
						}
						if(theta >= 5*Math.PI/4 && theta <= 7*Math.PI/4) {
							direction = 2;

							//up
						}
						if(found) {
							break;
						}
						
					}
					
					}
			}
			if(found) {
				break;
			}
		}
		return direction;		
	}
	
	// search for every animal on the map and move them one by one
	public void MoveAnimals() {
		Random rand = new Random();
		int animal_count = 0;
		//Element[][] updatedMap = new Element[Map.GetWidth()][Map.GetHeight()];
		int count = 0;
		// loop to count animals (needed to create static array next)
		for (int a = 0; a < Map.GetWidth(); a++) {
			for (int b = 0; b < Map.GetHeight(); b++) {
				count+=1;
				Map.GetElement(a, b).setCount(count);
				if (Map.GetElement(a, b).is_animal()) {
					animal_count += 1;
					
				}
			}
		}
		//System.out.printf("number of animals on map : %d \n",animal_count);
		// create array with pos (x,y) of every animal on the map
		int animals_on_map[][] = new int[animal_count][2];
		animal_count = 0;
		int i, j;
		for (i = 0; i < Map.GetWidth(); i++) {
			for (j = 0; j < Map.GetHeight(); j++) {
				
				if (Map.GetElement(i, j).is_animal()) {
					animals_on_map[animal_count][0] = i;
					animals_on_map[animal_count][1] = j;
					animal_count += 1;
				}
				
			}
		}
		// for loop on the array and random number to decide direction
		for (int count_animal = 0; count_animal < animals_on_map.length; count_animal++) {
			int next_move = SearchNearest(animals_on_map[count_animal][0], animals_on_map[count_animal][1]);
			if(next_move == -1) {
				//System.out.println("Random move");
				next_move = rand.nextInt(4);}
			// debug force movement
			// next_move = 3;
			MoveAnimal(animals_on_map[count_animal][0], animals_on_map[count_animal][1], next_move);
		}
		// actualize map after moves
		
		
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
    	if(first_element == Element.TREX && second_element == Element.BRACHIO ) {
    		winner = first_element;
    	}
		return winner;
		
    }
    
    public static void Mate(int x, int y, int x1, int y1){
    	System.out.println("Method mate launched");
    	Element emptyCell;
    	Element baby_trex_to_add = Element.BABYTREX;
    	baby_trex_to_add.setExist(false);
    	emptyCell = SearchNearestEmptyCell(x,y);
    	Map.AddOneElementToMap(baby_trex_to_add, emptyCell.getX(), emptyCell.getY());
    	System.out.println("bbtrex added");
    	
    }

	// moving one animal just by receiving pos of animal and a random number for
	// direction
	public void MoveAnimal(int x, int y, int next_move) {
		
		// move down
		if (next_move == 0 && y < Map.GetHeight() -1) {
			Element winner = Encounter(Map.GetElement(x, y), Map.GetElement(x, y + 1));
			
			if (winner != null) {
				Map.SetElement(x, y, Element.EMPTY);
				Map.SetElement(x, y + 1, winner);
			}
			
			
			if((Map.GetElement(x, y) == Element.TREX && (Map.GetElement(x, y+1) == Element.TREXMATE
				|| Map.GetElement(x, y+1) == Element.TREX)) || (Map.GetElement(x, y+1) == Element.TREX && (Map.GetElement(x, y) == Element.TREXMATE || Map.GetElement(x, y) == Element.TREX))){
				
					Mate(x,y, x, y+1);
					Element.transformTREX(x,y, x, y+1, Element.TREXMATE);
				
			}
			
		}
		
		// move left
		if (next_move == 1 && x > 0 && x < Map.GetWidth() - 1) {
			
			Element winner = Encounter(Map.GetElement(x, y), Map.GetElement(x - 1, y));
			
			if (winner != null) {
				Map.SetElement(x, y, Element.EMPTY);
				Map.SetElement(x - 1, y, winner);
			}
			
			if((Map.GetElement(x, y) == Element.TREX && (Map.GetElement(x-1, y) == Element.TREXMATE || Map.GetElement(x-1, y) == Element.TREX)) || (Map.GetElement(x-1, y) == Element.TREX && (Map.GetElement(x, y) == Element.TREXMATE || Map.GetElement(x, y) == Element.TREX))){
					Mate(x,y, x-1, y);
					Element.transformTREX(x,y, x-1, y, Element.TREXMATE);
				
			}
			 
		}
		
		
		
		// move up
		if (next_move == 2 && y > 0 && y < Map.GetHeight() - 1) {
			Element winner = Encounter(Map.GetElement(x, y), Map.GetElement(x, y - 1));
			
			if (winner != null) {
				Map.SetElement(x, y, Element.EMPTY);
				Map.SetElement(x, y - 1, winner);
			}
				
			if((Map.GetElement(x, y) == Element.TREX &&
			(Map.GetElement(x, y-1) == Element.TREXMATE || Map.GetElement(x, y-1) == Element.TREX)) 
			|| (Map.GetElement(x, y-1) == Element.TREX && (Map.GetElement(x, y) == Element.TREXMATE || Map.GetElement(x, y) == Element.TREX))){
					
				Mate(x,y, x, y-1);
				Element.transformTREX(x,y,x, y-1, Element.TREXMATE);
			}
			
		}
		
		
		
		// move right
		if (next_move == 3 && x < Map.GetWidth()-1) {
			Element winner = Encounter(Map.GetElement(x, y), Map.GetElement(x + 1, y));
			
			if (winner != null) {
				Map.SetElement(x, y, Element.EMPTY);
				Map.SetElement(x + 1, y, winner);
			}
			
			
			if((Map.GetElement(x, y) == Element.TREX && (Map.GetElement(x+1, y) == Element.TREXMATE 
			|| Map.GetElement(x+1, y) == Element.TREX)) || (Map.GetElement(x+1, y) == Element.TREX && (Map.GetElement(x, y) == Element.TREXMATE 
			|| Map.GetElement(x, y) == Element.TREX))){
					Mate(x,y, x+1, y);
					Element.transformTREX(x,y, x+1, y, Element.TREXMATE);
			}
		}
		
		

	}
}
