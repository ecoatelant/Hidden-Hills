package modeles;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Personnage {
	
	//Attributs
	private int pv;
	private DoubleProperty xProperty;
	private DoubleProperty yProperty;
	
	//Constructeur
	public Personnage() {
		this.pv=100;
		this.xProperty = new SimpleDoubleProperty(32);
		this.yProperty = new SimpleDoubleProperty(32);
	}
	
	//Getter
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
	
	//Setter
	public void gainVie(int gain) {
		this.pv=+gain;
	}
	
	public void perteVie(int attaque) {
		this.pv=-attaque;
	}
	
	public void move(double newX, double newY) {
		this.xProperty.set(this.xProperty.get() + newX);
		this.yProperty.set(this.yProperty.get() + newY);
	}
	
<<<<<<< HEAD
	/*public boolean collision(double newX, double newY) {
		Map map = new Map();
		if(this.xProperty.get()+newX == )
	}*/
=======
	public boolean collision(double newX, double newY) {
		Map map = new Map();
		return map.getBlock(calculIndice((this.getX()+newX),(this.getY()+newY))).getCollision();
	}
>>>>>>> 4a92afd3a33ea0cffa49d6057e63bacc9c86f521
	
	public boolean mort() {
		return this.pv<=0;
	}
	
<<<<<<< HEAD
	
=======
	//Dans la map, il y a 40 blocs de largeur et 60 blocs de hauteur
	public int calculIndice(double x, double y) {
		return (int) ((y*40)+x);
	}
>>>>>>> 4a92afd3a33ea0cffa49d6057e63bacc9c86f521
/*	//Dans la map, il y a 40 blocs de largeur et 60 blocs de hauteur
	public int getIndice() {
		return (this.xProperty.get()*40)
	}
*/	
}
