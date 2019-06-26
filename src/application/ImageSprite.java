package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ImageSprite {
	
	private int state_in_sprite;
	private int number_of_sprite;
	Image image;
	
	ImageSprite(String path_to_img){
		image = new Image(path_to_img);
		if(image.getWidth()>10) {
			number_of_sprite = (int) image.getWidth()/10;
		}
		state_in_sprite = 0;
	}

	public Image getImage() {
		return image;
	}
	public int getstate_of_sprite() {
		if(state_in_sprite < number_of_sprite) {
			state_in_sprite+=1;
		}
		if(state_in_sprite == number_of_sprite) {
			state_in_sprite = 0;
		}
		return state_in_sprite;
	}

}
