package application;

import java.util.Random;
import javafx.application.Platform;


public class LifeThread implements Runnable {

	public Element[][] map;
	private boolean doStop = false;
	final WindowMainController controller;


	
	public LifeThread(Element[][] map, WindowMainController controller_given) {
		this.map = map;
		controller_given.DisplayMap();
		this.controller = controller_given;
		
	}
	
    public synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }

    @Override
    public void run() {
        while(keepRunning()) {
        	System.out.println("Running");
            MoveAnimals();
            Platform.runLater(new Runnable() {
                @Override public void run() {
                	controller.DisplayMap();
                	
                }

            });
            //this.controller.DisplayMap();

            try {
                Thread.sleep(3L * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void MoveAnimals() {
    	Random rand = new Random();
    	int animal_count = 0;
    	//count animal
    	for(int a=0; a<map.length; a++){
            for(int b=0; b<map[a].length ; b++){
            	if(map[a][b].is_animal()) {
            		animal_count +=1;
            		
            	}
            }
        }
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
    	for(int count_animal=0; count_animal<animals_on_map.length; count_animal++) {
    		int next_move = rand.nextInt(4);
    		MoveAnimal(animals_on_map[count_animal][0],animals_on_map[count_animal][1] , next_move);
    	}
    	Map.setMap(map);
    }
    
    public void MoveAnimal(int x, int y, int next_move) {
    	
    	//move down
    	if(next_move == 0 && y+1 < map[0].length) {
    		Element animal = map[x][y];
        	map[x][y] = Element.EMPTY;
    		map[x][y+1] = animal;
    	}
    	//move left
    	if(next_move == 1 && x+1 > 0) {
    		Element animal = map[x][y];
        	map[x][y] = Element.EMPTY;
    		map[x-1][y] = animal;
    	}
    	//move up
    	if(next_move == 2 && y > 0) {
    		Element animal = map[x][y];
        	map[x][y] = Element.EMPTY;
    		map[x][y-1] = animal;
    	}
    	//move right
    	if(next_move == 3 && x+1 < map.length) {
    		Element animal = map[x][y];
        	map[x][y] = Element.EMPTY;
    		map[x+1][y] = animal;
    	}
    	
    }
}
