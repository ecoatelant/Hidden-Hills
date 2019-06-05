package modeles;

import javafx.beans.property.DoubleProperty;
import javafx.scene.image.ImageView;

public abstract class Sprite {
	
	final static int SPRITE_HEIGHT = 64;
    final static int SPRITE_WIDTH = 32;
	private int x;
	private int y;
	private ImageView img;
	
	public void move(double newX, double newY) {
		this.x += newX;
		this.y += newY;
	}
	
}
