package modeles;

import modeles.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Personnage {
											//Attributs//
	private int pv;
	private DoubleProperty xProperty;
	private DoubleProperty yProperty;
	private Map collisionMap;
	private Inventaire inventory;
    final static int PERSO_LARGEUR = 32;
    final static int PERSO_HAUTEUR = 64;
    
    //Taille de la map
    public final static int NBR_BLOC_LARGEUR = 60;
    public final static int NBR_BLOC_HAUTEUR = 40;
    public final static int TAILLE_BLOC = 32; //Les blocs sont carrés en 32 pixels
    
  //Saut
    private static double VITESSE_SAUT = -16;
											//Constructeur//
	public Personnage(Map collisionMap , Inventaire inventory) {
		this.pv=100;
		this.xProperty = new SimpleDoubleProperty(640);
		this.yProperty = new SimpleDoubleProperty(150);
		this.collisionMap = collisionMap;
		this.inventory = inventory;
	}
	
	public void move(double newX, double newY) {
		this.xProperty.set(this.xProperty.get() + newX);
		this.yProperty.set(this.yProperty.get() + newY);
	}
	
	//Retourne s'il y a colision à l'endroit où le personnage se trouve + nouveaux X / Y.
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
	
/*	public void saut (int hauteurSaut , int dy) {
		int timer = 2;
		move(0, VITESSE_SAUT);
		timer--;
		if(timer <= 0) {
			jump=false;
			timer = 2;
		}
	}*/
	
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
	
	public int getIndex() {
		return (int) (((int) (getY()/TAILLE_BLOC))*NBR_BLOC_LARGEUR+(getX()/TAILLE_BLOC));
	}
	
	public boolean mort() {
		return this.pv<=0;
	}
										//Setter//
	public void setX (double x) {
		this.xProperty.set(x);
	}

	public void setY  (double y) {
		this.yProperty.set(y);
	}
	
	public int calculationIndex(double x, double y) {
		return (int) (((int)(y/TAILLE_BLOC))*NBR_BLOC_LARGEUR+(x/TAILLE_BLOC));
	}

	
}
