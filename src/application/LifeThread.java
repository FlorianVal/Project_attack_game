package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javafx.application.Platform;


public class LifeThread implements Runnable {

	public Element[][] map;
	private boolean doStop = false;
	public WindowMainController controller;


	//constructor include map and controller of window
	public LifeThread(Element[][] map, WindowMainController controller_given) {
		this.map = map;
		controller_given.DisplayMap();
		this.controller = controller_given;
		
	}
	
	// method to stop the running Thread
    public synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }

    @Override
    public void run() {
        while(keepRunning()) {
            MoveAnimals();
            Respawn();
            //run later is needed to run display map in life thread
            Platform.runLater(new Runnable() {
                @Override public void run() {
                	controller.DisplayMap();
                	
                }

            });
            // Thread wait for the next movement
            try {
                Thread.sleep(3L * 100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    
    //use to spawn randomly things on map
    public void Respawn() {
    	//list all elements
    	Random rand = new Random();
		ArrayList<Element> elist = new ArrayList<Element>(Arrays.asList(Element.values()));
		
		for(Element elem: elist) {
    		double spawn_or_not = rand.nextDouble();
    		if(spawn_or_not < elem.getRespawn_proba()) {
    			RandomlyAddToMap(elem);
    		}
			
		}
    }
    
    //randomly add the element_to_add on map (mainly use for respawn)
    public void RandomlyAddToMap(Element element_to_add) {
    	Random rand = new Random();
    	boolean is_spawn = false;
    	// used if map is full to avoid blocking and also to avoid non_spawning due to already an element on case
    	int number_of_spawn_try = 4;
    	while(is_spawn == false || number_of_spawn_try >= 0) {
    		//choose a random case on map
    		int x = rand.nextInt(map.length);
    		int y = rand.nextInt(map[0].length);
    		// spawn if the randomly chosen case is empty else retry
    		if(map[x][y] == Element.EMPTY) {
    			map[x][y] = element_to_add;
    			is_spawn = true;
    		}
    		number_of_spawn_try -=1;
    	}
    	
    }
    
    //search for every animal on the map and move them one by one
    public void MoveAnimals() {
    	Random rand = new Random();
    	int animal_count = 0;
    	//loop to count animal (needed to create static array next)
    	for(int a=0; a<map.length; a++){
            for(int b=0; b<map[a].length ; b++){
            	if(map[a][b].is_animal()) {
            		animal_count +=1;
            		
            	}
            }
        }
    	// create array with pos (x,y) of every animal on the map
    	int animals_on_map[][] = new int[animal_count][2];
    	animal_count = 0;
    	int i, j;
    	for(i=0; i<map.length; i++){
            for(j=0; j<map[i].length ; j++){
            	if(map[i][j].is_animal()) {
            		animals_on_map[animal_count][0] = i;
            		animals_on_map[animal_count][1] = j;
            		animal_count +=1;
            		
            	}
            }
        }
    	// for loop on the array and random number to decide direction
    	for(int count_animal=0; count_animal<animals_on_map.length; count_animal++) {
    		int next_move = rand.nextInt(4);
    		//debug force movement
    		//next_move = 3;
    		MoveAnimal(animals_on_map[count_animal][0],animals_on_map[count_animal][1] , next_move);
    	}
    	//actualize map after moves
    	Map.setMap(map);
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
    
    //moving one animal just by receiving pos of animal and a random number for direction
    public void MoveAnimal(int x, int y, int next_move) {
    	
    	//move down
    	if(next_move == 0 && y+1 < map[0].length) {
    		Element winner = Encounter(map[x][y], map[x][y+1]);
    		if(winner != null) {
    			map[x][y] = Element.EMPTY;
        		map[x][y+1] = winner;
    		}
    	}
    	//move left
    	if(next_move == 1 && x > 0) {
    		Element winner = Encounter(map[x][y], map[x-1][y]);
    		if(winner != null) {
    			map[x][y] = Element.EMPTY;
        		map[x-1][y] = winner;
    		}
    	}
    	//move up
    	if(next_move == 2 && y > 0) {
    		Element winner = Encounter(map[x][y], map[x][y-1]);
    		if(winner != null) {
    			map[x][y] = Element.EMPTY;
        		map[x][y-1] = winner;
    		}
    	}
    	//move right
    	if(next_move == 3 && x+1 < map.length) {
    		Element winner = Encounter(map[x][y], map[x+1][y]);
    		if(winner != null) {
    			map[x][y] = Element.EMPTY;
        		map[x+1][y] = winner;
    		}
    	}
    	
    }
}