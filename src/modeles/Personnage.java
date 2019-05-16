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
		this.xProperty = new SimpleDoubleProperty(20);
		this.yProperty = new SimpleDoubleProperty(20);
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
	
	public boolean mort() {
		return this.pv<=0;
	}
	
	
}