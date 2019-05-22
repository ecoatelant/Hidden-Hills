package modeles;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Personnage extends Map {
	
	//Attributs
	private int pv;
	private DoubleProperty xProperty;
	private DoubleProperty yProperty;
    final static int PERSO_LARGEUR = 32;
    final static int PERSO_HAUTEUR = 64;
	
	//Constructeur////////////////////////////////////////////////////////////////////////////////////////
	public Personnage() {
		this.pv=100;
		this.xProperty = new SimpleDoubleProperty(0);
		this.yProperty = new SimpleDoubleProperty(0);
	}
	
	//Getter///////////////////////////////////////////////////////////////////////////////////////////////
	public int getPV() {
		return this.pv;
	}
	
	public DoubleProperty xProperty() {
		return this.xProperty;
	}
	
	public DoubleProperty yProperty() {
		return this.yProperty;
	}
	
	public double getX() {
		return this.xProperty.get();
	}
	
	public double getY() {
		return this.yProperty.get();
	}
	
	public int getIndexSprite() {
		return (int) (((int) (getY()/TAILLE_BLOC))*NBR_BLOC_LARGEUR+(getX()/TAILLE_BLOC));
	}
	
	//Setter///////////////////////////////////////////////////////////////////////////////////////////////
	public void gainVie(int gain) {
		this.pv=+gain;
	}
	
	public void perteVie(int attaque) {
		this.pv=-attaque;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void move(double newX, double newY) {
		this.xProperty.set(this.xProperty.get() + newX);
		this.yProperty.set(this.yProperty.get() + newY);
	}
	
	public boolean mort() {
		return this.pv<=0;
	}
	
	public void setX (double x) {
		this.xProperty.set(x);
	}
	
	public void setY (double y) {
		this.yProperty.set(y);
	}

	//Retourne s'il y a collision à l'endroit où le personnage se trouve + nouveaux X et Y.
	public boolean colision(int newX, int newY) {
		Map map = new Map();
		//Regard angle gauche haut
		if(map.getBlock(calculationIndex((getX()+newX),(getY()+newY))).getCollision()) {
			return true;
		}
		//Regard angle gauche bas
		else if (map.getBlock(calculationIndex((getX()+newX),(getY()+newY)+56)).getCollision()) {
			return true;
		}
		//Regard angle droit haut
		else if (map.getBlock(calculationIndex((getX()+newX)+24,(getY()+newY))).getCollision()) {
			return true;
		}
		//Regard angle droit bas
		else if (map.getBlock(calculationIndex((getX()+newX)+24,(getY()+newY)+56)).getCollision()) {
			return true;
		}
		//Return
		else {
			return false;
		}
	}
	
}
