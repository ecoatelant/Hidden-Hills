package modeles;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Personnage {

	final static int SPRITE_HEIGHT = 64;
	final static int SPRITE_WIDTH = 32;
	private DoubleProperty xProperty;
	private DoubleProperty yProperty;
	private Map collisionMap;
	private IntegerProperty pv;
	private String imgdroite;
	private String imgGauche;

	// Taille de la map
	public final static int TAILLE_BLOC = 32; // Les blocs sont carrés en 32 pixels

	public Personnage(Map collisionMap, String imgGauche, String imgDroite) {
		this.pv = new SimpleIntegerProperty(100);
		this.xProperty = new SimpleDoubleProperty(0);
		this.yProperty = new SimpleDoubleProperty(0);
		this.collisionMap = collisionMap;
		this.imgGauche = imgGauche;
		this.imgdroite = imgDroite;
	}

	public void move(int newX, int newY) {
		this.xProperty.set(this.xProperty.get() + newX);
		this.yProperty.set(this.yProperty.get() + newY);
	}

	abstract public boolean possibiliteAttaque(Personnage p);

	// Retourne s'il y a collision à l'endroit où le personnage se trouve + nouveaux
	// X / Y.
	abstract public boolean collision(int newX, int newY);

	public int calculationIndex(double x, double y) {
		return (int) (((int) (y / TAILLE_BLOC)) * collisionMap.getMapWidth() + (x / TAILLE_BLOC));
	}

	abstract public void attaque(Personnage ennemi);

	public void perteVie(int attaque) {
		this.pv.set(pv.get()-attaque);
	}

	public void gainVie(int gain) {
		this.pv.set(pv.get()+gain);
	}

	// Getter//
	public int getPV() {
		return this.pv.get();
	}
	
	public IntegerProperty pvProperty() {
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

	public boolean mort() {
		return this.pv.get() <= 0;
	}

	public Map getMap() {
		return this.collisionMap;
	}

	public String getImgDroite() {
		return imgdroite;
	}

	public String getImgGauche() {
		return imgGauche;
	}

	// Setter//
	public void setX(double x) {
		this.xProperty.set(x);
	}

	public void setPV(int gainOuPerte) {
		this.pv.set(pv.get()+gainOuPerte);
	}

	public void setY(double y) {
		this.yProperty.set(y);
	}

	public void setImgDroite(String imgdroite) {
		this.imgdroite = imgdroite;
	}

	public void setImgGauche(String imgGauche) {
		this.imgGauche = imgGauche;
	}
}
