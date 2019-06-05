package modeles;

import modeles.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.scene.image.ImageView;

public abstract class Sprite {
	
	final static int SPRITE_HEIGHT = 64;
    final static int SPRITE_WIDTH = 32;
	private IntegerProperty xProperty;
	private IntegerProperty yProperty;
	private ImageView img;
	private Map colisionMap;	
	
	//Getter
	public int getX() {
		return xProperty.get();
	}
	
	//Méthodes
	public void move(int newX, int newY) {
		this.xProperty.set(this.xProperty.get() + newX);
		this.yProperty.set(this.yProperty.get() + newY);
	}
	
/*	//Retourne s'il y a colision à l'endroit où le personnage se trouve + nouveaux X / Y.
	public boolean colision(int newX, int newY) {
			//Regard angle gauche haut
			if(colisionMap.getBlock(calculationIndex((getX()+newX),(getY()+newY))).getColision()) {
				return true;
			}
			//Regard angle gauche bas
			else if (colisionMap.getBlock(calculationIndex((getX()+newX),(getY()+newY)+56)).getColision()) {
				return true;
			}
			//Regard angle droit haut
			else if (colisionMap.getBlock(calculationIndex((getX()+newX)+24,(getY()+newY))).getColision()) {
				return true;
			}
			//Regard angle droit bas
			else if (colisionMap.getBlock(calculationIndex((getX()+newX)+24,(getY()+newY)+56)).getColision()) {
				return true;
			}
			//Return
			else {
				return false;
			}
			
	}*/
	
}
