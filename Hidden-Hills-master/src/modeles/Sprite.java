package modeles;

import modeles.Map;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

public abstract class Sprite {
	
	final static int SPRITE_HEIGHT = 64;
    final static int SPRITE_WIDTH = 32;
	private DoubleProperty xProperty;
	private DoubleProperty yProperty;
	@SuppressWarnings("unused")
	private ImageView img;
	private Map collisionMap;
	private int pv;
	//Taille de la map
    public final static int NBR_BLOC_LARGEUR = 60;
    public final static int NBR_BLOC_HAUTEUR = 40;
    public final static int TAILLE_BLOC = 32; //Les blocs sont carrés en 32 pixels
	
    public Sprite (Map collisionMap) {
    	this.pv=100;
		this.xProperty = new SimpleDoubleProperty(0);
		this.yProperty = new SimpleDoubleProperty(0);
		this.collisionMap = collisionMap;
    }
	
	public void move(int newX, int newY) {
		this.xProperty.set(this.xProperty.get() + newX);
		this.yProperty.set(this.yProperty.get() + newY);
	}
	
	abstract public boolean possibiliteAttaque (Sprite p) ;
	
	//Retourne s'il y a collision à l'endroit où le personnage se trouve + nouveaux X / Y.
	public boolean colision(int newX, int newY) {
			//Regard angle gauche haut
			if(collisionMap.getBlock(calculationIndex((getX()+newX),(getY()+newY))).getColision()) {
				return true;
			}
			//Regard angle gauche bas
			else if (collisionMap.getBlock(calculationIndex((getX()+newX),(getY()+newY)+56)).getColision()) {
				return true;
			}
			//Regard angle droit haut
			else if (collisionMap.getBlock(calculationIndex((getX()+newX)+24,(getY()+newY))).getColision()) {
				return true;
			}
			//Regard angle droit bas
			else if (collisionMap.getBlock(calculationIndex((getX()+newX)+24,(getY()+newY)+56)).getColision()) {
				return true;
			}
			//Return
			else {
				return false;
			}
			
	}
	
	public void perteVie(int attaque) {
		this.pv=-attaque;
	}
	
	public void gainVie(int gain) {
		this.pv=+gain;
	}
											//Getter//
	public int getPV() {
		return this.pv;
	}
	
	public void setPV(int gainOuPerte) {
		this.pv += gainOuPerte;
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
	
	public void setX (double x) {
		this.xProperty.set(x);
	}
	
	public void setY (double y) {
		this.yProperty.set(y);
	}
	
	public int calculationIndex(double x, double y) {
		return (int) (((int)(y/TAILLE_BLOC))*NBR_BLOC_LARGEUR+(x/TAILLE_BLOC));
	}
	
	public boolean mort() {
		return this.pv<=0;
	}
	
	public Map getMap () {
		return this.collisionMap;
	}

	abstract public void attaque(Sprite ennemi) ;
}