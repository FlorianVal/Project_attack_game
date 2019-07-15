package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseDragEvent;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class WindowMainController {

	@FXML
	private GridPane GridMap;
	private int x_window = 0;
	private int y_window = 0;
	private Element button_state = null;
	private static Thread thread;
	private static LifeThread lifethread;
	private int is_mouse_pressed;
	
	@FXML 
	Label label;

	@FXML
	void initialize(WindowMainController controller, int width, int height) {

		assert GridMap != null : "fx:id=\"GridMap\" was not injected: check your FXML file 'Level1.fxml'.";
		new Map(width, height);
		launch_life_thread(controller);

	}

	private void launch_life_thread(WindowMainController controller_given) {
		// clean last thread
		if (this.thread != null) {
			this.lifethread.doStop();
			try {
				this.thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.lifethread = new LifeThread(controller_given);
		this.thread = new Thread(lifethread);
		this.thread.start();
	}

	public void PauseThread() {
		this.lifethread.doPause();
	}

	@FXML
	void keyPressed(KeyEvent event) {
		System.out.println("Key event");
		System.out.println(event.getCode());
		switch (event.getCode().toString()) {
		case "RIGHT":
			MoveMapRight();
			break;
		case "LEFT":
			MoveMapLeft();
			break;
		case "UP":
			MoveMapUp();
			break;
		case "DOWN":
			MoveMapDown();
			break;
		case "P":
			PauseThread();
			break;

		}
	}

	@FXML
	void OnMouseMoved(MouseEvent event) {
		if (button_state != null) {
			ElementClass button_state_obj = new ElementClass(button_state);
			System.out.print(is_mouse_pressed);
			if (button_state_obj.getElement() == Element.FENCE && is_mouse_pressed == 1) {
				System.out.print("mouse move");

				int[] cell = GetCell(event.getSceneX(), event.getSceneY());
				if (cell[0] != -1 && cell[1] != -1 && button_state != null) {
					Map.SetElement(cell[0], cell[1], button_state_obj);
				}
			}
		}
	}

	@FXML
	void OnMouseClicked(MouseEvent event) {
		ElementClass button_state_obj = new ElementClass(button_state);
		int[] cell = GetCell(event.getSceneX(), event.getSceneY());

		if (button_state_obj.getElement() == Element.FENCE) {
			if (is_mouse_pressed == 0) {
				is_mouse_pressed = 1;
			} else {
				is_mouse_pressed = 0;
				button_state = null;
			}
		}

		if (cell[0] != -1 && cell[1] != -1 && button_state != null) {
			Map.SetElement(cell[0], cell[1], button_state_obj);
			if (button_state_obj.getElement() != Element.FENCE) {

				button_state = null;
			}
		}
	}
	
	
	public int[] GetCell(double x, double y) {
		int[] cell = new int[2];
		cell[0] = -1;
		cell[1] = -1;
		if (x >= 20 && x <= 420 && y >= 20 && y <= 270) {
			cell[0] = (int) (x - 20) / 10 + this.y_window;
			cell[1] = (int) (y - 20) / 10 + this.x_window;

		}
		return cell;
	}

	@FXML
	void MoveMapDown() {
		if (x_window < Map.GetHeight() - GridMap.impl_getRowCount()) {
			x_window += 1;
		}
		DisplayMap();

	}

	@FXML
	void MoveMapLeft() {
		if (y_window > 0) {
			y_window -= 1;
		}
		DisplayMap();

	}

	@FXML
	void MoveMapRight() {
		if (y_window < Map.GetWidth() - GridMap.impl_getColumnCount()) {
			y_window += 1;
		}
		DisplayMap();

	}

	@FXML
	void MoveMapUp() {
		if (x_window > 0) {
			x_window -= 1;
		}
		DisplayMap();

	}

	private void AddImage(ImageSprite image, int x, int y) {
		ImageView iv = new ImageView(image.getImage());
		// do the sprite with rectangle of view moving on the image
		iv.setViewport(new Rectangle2D(image.getstate_of_sprite() * 10, 0, 10, 10));
		GridMap.add(iv, x, y);
	}

	private void DrawEmpty(int i, int j, int[][] empty_map) {
		if (empty_map[i][j] == 0) {
			AddImage(Element.EMPTY.getVisu(), i, j);
		}
		if (empty_map[i][j] == 1) {
			AddImage(Element.EMPTY2.getVisu(), i, j);
		}
	}

	public void DisplayMap() {

		ElementClass[][] partial_map = Map.ReturnPartialMap(this.x_window, this.y_window,
				this.GridMap.impl_getColumnCount(), GridMap.impl_getRowCount());
		int[][] partial_empty = Map.ReturnPartialEmptyMap(this.x_window, this.y_window,
				this.GridMap.impl_getColumnCount(), GridMap.impl_getRowCount());
		this.GridMap.getChildren().clear();
		ArrayList<Element> elist = new ArrayList<Element>(Arrays.asList(Element.values()));
		for(Element elem: elist) {
			elem.getVisu().increm_sprite();
		}
		for (int i = 0; i < partial_map.length; i++) {
			for (int j = 0; j < partial_map[i].length; j++) {
				if (partial_map[i][j].getElement() != Element.EMPTY) {
					DrawEmpty(i, j, partial_empty);
					AddImage(partial_map[i][j].getElement().getVisu(), i, j);
				} else {
					DrawEmpty(i, j, partial_empty);
				}
			}
		}
	}
	

	public void AddBananaRandomOnMap() {
		LifeThread.RandomlyAddToMap(Element.BANANA);
	}

	public void BurnRandomMap() {
		LifeThread.RandomlyAddToMap(Element.FIRE);
	}

	public void AddBananaOnMap() {
		button_state = Element.BANANA;
	}

	public void BurnMap() {
		button_state = Element.FIRE;
	}

	public void AddFence() {
		button_state = Element.FENCE;
	}
	
	public void AddPtero() {
		button_state = Element.PTERODACTYL;
	}

	@FXML
	void AddBrachioOnMap() {
		button_state = Element.BRACHIO;
	}

	@FXML
	void AddFruitOnMap() {
		button_state = Element.FRUIT;

	}

	@FXML
	void AddTrexOnMap() {
		button_state = Element.TREX;

	}
}
