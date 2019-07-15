package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javafx.application.Platform;

//TODO : Scores -> Compter nbres de trex + les tuer au bout d'un certain temps (timer) : Partie gagnée à 20 TREX // Moi
//TODO : Add a specie + lui faire manger les bananes // Flo
//TODO : GUI Score + Menu + Design boutons lvl 2 //Moi
//TODO : Eteindre le feu avec timer // Flo
//TODO : Tests


public class LifeThread implements Runnable {

	private boolean doStop = false;
	private boolean IsPaused = false;
	public WindowMainController controller;
	ArrayList<ElementClass> listAllElements = new ArrayList<ElementClass>();
	
	public LifeThread(WindowMainController controller_given) {
		controller_given.DisplayMap();
		this.controller = controller_given;

	}

	public synchronized void doStop() {
		// method to stop the running Thread
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
				SpreadFire();
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

	private void SpreadFire() {
		int[][] fire_on_map = Find(false, true);
		int spread_rate = 10; // % of chance to spread
		int number_of_try_per_fire = 10;
		int max_delay_to_stop_fire = 5;
		Random rand = new Random();
		ElementClass object_fire = new ElementClass(Element.FIRE);

		for (int count = 0; count < fire_on_map.length; count++) {
			int stop_fire = rand.nextInt(max_delay_to_stop_fire) +1;
			if (Map.GetElementClass(fire_on_map[count][0], fire_on_map[count][1]).getElement() == Element.FIRE
					&& Map.GetElementClass(fire_on_map[count][0], fire_on_map[count][1]).getTimer() % stop_fire == 0 && Map.GetElementClass(fire_on_map[count][0], fire_on_map[count][1]).getTimer() > 0) {
				System.out.printf("Empty fire");
				Map.GetElementClass(fire_on_map[count][0], fire_on_map[count][1]).setElement(Element.EMPTY);
			}
			for (int i = 0; i < number_of_try_per_fire; i++) {
				if (rand.nextInt(101) < spread_rate) {
					try {
						Map.SetElement(fire_on_map[count][0] + (rand.nextInt(3) - 1),
								fire_on_map[count][1] + (rand.nextInt(3) - 1), object_fire);
					} catch (java.lang.ArrayIndexOutOfBoundsException e) {

					}
				}
			}

		}
	}

	public void Respawn() {
		// use to spawn randomly things on map
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

	public static void RandomlyAddToMap(Element element_to_add) {
		// randomly add the element_to_add on map (mainly use for respawn)

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
				ElementClass element_object_to_add = new ElementClass(element_to_add);
				Map.SetElement(x, y, element_object_to_add);
				is_spawn = true;
			}
			number_of_spawn_try -= 1;
		}

	}

	public static Element SearchNearestEmptyCell(int x, int y) {
		int rho;
		boolean find = false;
		double theta;
		Element empty_cell = Map.GetElement(0, 0);
		int distance_of_seeing = 5;
		for (rho = 1; rho < distance_of_seeing; rho++) {
			for (theta = 0; theta <= 2 * Math.PI - 0.01; theta += Math.PI / (rho * 4)) {
				int cell_x = x + (int) Math.round(rho * Math.cos(theta));
				int cell_y = y + (int) Math.round(rho * Math.sin(theta));
				System.out.printf("cell check : %d %d \n",cell_x - x, cell_y - y);

				if (cell_x > 0 && cell_x < Map.GetWidth() && cell_y > 0 && cell_y < Map.GetHeight() && Map.GetElement(cell_x, cell_y) == Element.EMPTY) {
					empty_cell.setX(cell_x);
					empty_cell.setY(cell_y);
					empty_cell = Map.GetElement(cell_x, cell_y);
					find = true;
				}
				if(find) {break;}

			}
			if(find) {break;}
		}

		return empty_cell;

	}

	private int SearchNearest(int x, int y) {
		// overload to use target
		return SearchNearest(x, y, Map.GetElement(x, y).getTarget());
	}

	private static int SearchNearest(int x, int y, Element target) {
		int rho;
		double theta;
		Element elem_around;
		int distance_of_seeing = 12;
		int direction = -1;
		boolean found = false;
		// System.out.printf("Search nearest : %d %d %s \n \n",x,y,Map.GetElement(x,
		// y).getName());
		for (rho = 1; rho < distance_of_seeing; rho++) {
			for (theta = 0; theta <= 2 * Math.PI - 0.01; theta += Math.PI / (rho * 4)) {
				int cell_x = x + (int) Math.round(rho * Math.cos(theta));
				int cell_y = y + (int) Math.round(rho * Math.sin(theta));
				if (cell_x > 0 && cell_x < Map.GetWidth() && cell_y > 0 && cell_y < Map.GetHeight()) {
					elem_around = Map.GetElement(cell_x, cell_y);
					// elem_around.setX(cell_x);
					// elem_around.setY(cell_y);

					if (elem_around.getName() == target.getName()) {

						found = true;
						// System.out.printf("Target found : %d %d %s \n", cell_x, cell_y,
						// elem_around.getName());
						if (theta >= 7 * Math.PI / 4 || theta <= Math.PI / 4) {
							direction = 3;
							// RIGHT
						}
						if (theta >= Math.PI / 4 && theta <= 3 * Math.PI / 4) {
							direction = 0;

							// down
						}
						if (theta >= 3 * Math.PI / 4 && theta <= 5 * Math.PI / 4) {
							direction = 1;

							// LEFT
						}
						if (theta >= 5 * Math.PI / 4 && theta <= 7 * Math.PI / 4) {
							direction = 2;

							// up
						}
						if (found) {
							break;
						}

					}

				}
			}
			if (found) {
				break;
			}
		}
		return direction;
	}

	private int[][] Find(boolean animals, boolean fire) {
		
		//return an array with x y of every object we want to find on map( for the moment only fire and animals)
		int count = 0;


		// loop to count animals (needed to create static array next)
		// add each elements of type animal and fire in the arraylist
		for (int a = 0; a < Map.GetWidth(); a++) {
			for (int b = 0; b < Map.GetHeight(); b++) {
				
				
				if (Map.GetElement(a, b).is_animal() && animals) {
					count += 1;
					listAllElements.add(Map.GetElementClass(a, b));
				}

				if (Map.GetElement(a, b).getName() == "Fire" && fire) {
					count += 1;
					listAllElements.add(Map.GetElementClass(a, b));
				}

			}
		}
		
		//After each turn, timers of animals and fires are incremented
		LifeThread.incrementTimer(listAllElements);
		
		//At the end of each turn, bb-trex's timers are checked to know if they have to grow-up or not
		LifeThread.checkCounterFruitsBabyTREX(listAllElements);


		int objects_on_map[][] = new int[count][2];
		int count_of_objects = 0;
		int i, j;
		for (i = 0; i < Map.GetWidth(); i++) {
			for (j = 0; j < Map.GetHeight(); j++) {

				if (Map.GetElement(i, j).is_animal() && animals) {
					objects_on_map[count_of_objects][0] = i;
					objects_on_map[count_of_objects][1] = j;
					count_of_objects += 1;
				}
				if (Map.GetElement(i, j).getName() == "Fire" && fire) {
					objects_on_map[count_of_objects][0] = i;
					objects_on_map[count_of_objects][1] = j;
					count_of_objects += 1;
				}

			}
		}
		return objects_on_map;
	}
	
	public static void incrementTimer(ArrayList<ElementClass> elementsToIncrement){
		
		for(int i = 0; i < elementsToIncrement.size();i++){		
			elementsToIncrement.get(i).incrementTimer();
		}
	}
	
	//This method checks in the list of elements of the map if the element is a baby trex, and if he ate more than 3 fruits
	//If the bb trex ate more than 3 fruits, it will grow up and become TREX
	public static void checkCounterFruitsBabyTREX(ArrayList<ElementClass> allElementsMap){
		
		for(int i = 0; i<allElementsMap.size(); i++ ){
			
			if(allElementsMap.get(i) != null && allElementsMap.get(i).getElement() == Element.BABYTREX && allElementsMap.get(i).getCounterFruit() >= 3){
				allElementsMap.get(i).setElement(Element.TREX);
			}
		}
			
	}
	
	public static void checkTimerTREXMATE(int x, int y, int x1, int y1){
		
		/*for(int i = 0; i<allElementsMap.size(); i++){
		//If the timer of the object is a multiple of 30, the element will become a TREX instead of TREXMATE
			if(allElementsMap.get(i).getElement() == Element.TREXMATE && allElementsMap.get(i).getTimer()%30 == 0 && allElementsMap.get(i).getTimer()>0){
				System.out.println("Timer TREXMATE" + allElementsMap.get(i).getTimer());
				allElementsMap.get(i).setElement(Element.TREX);
			}
		}*/
		
		
		if (Map.GetElementClass(x, y).getElement() == Element.TREXMATE
				&& Map.GetElementClass(x, y).getTimer() % 30 == 0 && Map.GetElementClass(x, y).getTimer() > 0) {
			Map.GetElementClass(x, y).setElement(Element.TREX);
		}
		
		
		if (Map.GetElementClass(x1, y1).getElement() == Element.TREXMATE
				&& Map.GetElementClass(x1, y1).getTimer() % 30 == 0
				&& Map.GetElementClass(x1, y1).getTimer() > 0) {
			Map.GetElementClass(x1, y1).setElement(Element.TREX);
		}
		
	}

	public void MoveAnimals() {
		// search for every animal on the map and move them one by one
		Random rand = new Random();
		int[][] animals_on_map = Find(true, false);

		// for loop on the array and random number to decide direction
		for (int count_animal = 0; count_animal < animals_on_map.length; count_animal++) {
			boolean movement_done = false;
			int loop_count = 0;
			while (!movement_done && loop_count < 50) {
				int next_move = SearchNearest(animals_on_map[count_animal][0], animals_on_map[count_animal][1]);
				// -1 = no near target
				if (next_move == -1) {
					next_move = rand.nextInt(4);
				}
				// debug force movement
				// next_move = 3;
				movement_done = MoveAnimal(animals_on_map[count_animal][0], animals_on_map[count_animal][1], next_move);
				loop_count += 1;
			}
		}

	}

	public ElementClass Encounter(ElementClass first_element_obj, ElementClass second_element_obj) {
		// used to handle when an animal move into something
		// return winning animal or null if no winning animal
		ElementClass winner = null;

		// no winning animal if they are same animal
		// TODO add fire dead
		if (first_element_obj.getElement() == second_element_obj.getElement()) {
			winner = null;
		}

		if (second_element_obj.getElement() == Element.EMPTY) {
			winner = first_element_obj;
		}
		//TREX vs BRACHIO
		if ((first_element_obj.getElement() == Element.TREX && second_element_obj.getElement() == Element.BRACHIO)) {
			winner = first_element_obj;
		}
		// PTERO vs TREX
		if ((first_element_obj.getElement() == Element.PTERODACTYL && second_element_obj.getElement() == Element.TREX)) {
			winner = first_element_obj;
		}
		

		//BRACHIO vs PTERO
		if ((first_element_obj.getElement() == Element.BRACHIO && second_element_obj.getElement() == Element.PTERODACTYL)) {
			winner = first_element_obj;
		}
		// Incrementation of the counter of fruits if the one of the elements is a
		// baby-trex
		// TODO : Test more this feature
		if (first_element_obj.getElement() == Element.FRUIT && second_element_obj.getElement() == Element.BABYTREX) {

			second_element_obj.incrementCounterFruit();

		}
		

		// Incrementation of the counter of fruits if the one of the elements is a
		// baby-trex
		if (first_element_obj.getElement() == Element.BABYTREX && second_element_obj.getElement() == Element.FRUIT) {

			first_element_obj.incrementCounterFruit();
		}

		return winner;

	}

		
	public static void transformTREX(int x, int y, int x1, int y1, Element element){
		
		if ((Map.GetElement(x, y) == Element.TREX
				&& (Map.GetElement(x1, y1) == Element.TREXMATE || Map.GetElement(x1, y1) == Element.TREX))
				|| (Map.GetElement(x1, y1) == Element.TREX
						&& (Map.GetElement(x, y) == Element.TREXMATE || Map.GetElement(x, y) == Element.TREX))) {

			Mate(x, y, x1, y1);
			ElementClass element_object = new ElementClass(element);
			Map.SetElement(x, y, element_object);
			Map.SetElement(x1, y1, element_object);
		}	
	
	}
	
	
	public static void Mate(int x, int y, int x1, int y1) {

		Element emptyCell;
		Element baby_trex_to_add = Element.BABYTREX;
		baby_trex_to_add.setExist(false);
		emptyCell = SearchNearestEmptyCell(x, y);
		Map.AddOneElementToMap(baby_trex_to_add, emptyCell.getX(), emptyCell.getY());

	}

	public boolean MoveAnimal(int x, int y, int next_move) {
		// moving one animal just by receiving pos of animal and a random number for
		// direction
		// move down
		boolean is_movement_done = false;

		if (next_move == 0 && y < Map.GetHeight() - 1) {
			is_movement_done = true;
			ElementClass winner = Encounter(Map.GetElementClass(x, y), Map.GetElementClass(x, y + 1));

			if (winner != null) {
				Map.SetElement(x, y, new ElementClass(Element.EMPTY));
				Map.SetElement(x, y + 1, winner);
			}
			
			LifeThread.transformTREX(x, y, x, y + 1, Element.TREXMATE);
			LifeThread.checkTimerTREXMATE(x, y, x, y+1);


		}

		// move left
		if (next_move == 1 && x > 0 && x < Map.GetWidth() ) {


			is_movement_done = true;

			ElementClass winner = Encounter(Map.GetElementClass(x, y), Map.GetElementClass(x - 1, y));

			if (winner != null) {
				Map.SetElement(x, y, new ElementClass(Element.EMPTY));
				Map.SetElement(x - 1, y, winner);
			}
			
			LifeThread.transformTREX(x, y, x-1, y, Element.TREXMATE);
			LifeThread.checkTimerTREXMATE(x, y, x-1, y);

			// If the winner is a baby-trex and its counter of fruits is superior than 3
			// (It means that the baby ate more than 3 fruits) the bbtrex will become a trex
			if (winner != null && winner.getElement() == Element.BABYTREX && winner.getCounterFruit() >= 3) {
				System.out.println("bbtrex -> trex with counter of fruits = " + winner.getCounterFruit());
				winner.setElement(Element.TREX);
			}

		}
		
		//At the end of each turn, TREXMATE's timers are checked to know if they have to become again TREX
		

		// move up
		if (next_move == 2 && y > 0 && y < Map.GetHeight()) {


			is_movement_done = true;

			ElementClass winner = Encounter(Map.GetElementClass(x, y), Map.GetElementClass(x, y - 1));

			if (winner != null) {
				Map.SetElement(x, y, new ElementClass(Element.EMPTY));
				Map.SetElement(x, y - 1, winner);
			}
				
			LifeThread.transformTREX(x, y, x, y - 1, Element.TREXMATE);
			LifeThread.checkTimerTREXMATE(x, y, x, y-1);

		}
		
		

		// move right
		if (next_move == 3 && x < Map.GetWidth() - 1) {


			is_movement_done = true;

			ElementClass winner = Encounter(Map.GetElementClass(x, y), Map.GetElementClass(x + 1, y));

			if (winner != null) {
				Map.SetElement(x, y, new ElementClass(Element.EMPTY));
				Map.SetElement(x + 1, y, winner);
			}

			LifeThread.transformTREX(x, y, x+1, y, Element.TREXMATE);
			LifeThread.checkTimerTREXMATE(x, y, x+1, y);

		}
		
		return is_movement_done;

	}
}
