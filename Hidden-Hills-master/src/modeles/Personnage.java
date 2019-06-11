package modeles;

import modeles.Map;
import javafx.beans.property.DoubleProperty;

public class Personnage extends Sprite {
											//Attributs//
	@SuppressWarnings("unused")
	private Inventaire inventory;
    final static int PERSO_LARGEUR = 32;
    final static int PERSO_HAUTEUR = 64;
  //Saut
    @SuppressWarnings("unused")
	private static double VITESSE_SAUT = -16;
											//Constructeur//
	public Personnage(Map collisionMap , Inventaire inventory) {
		super(collisionMap);
		this.inventory=inventory;
	}
	
	public void move(int newX, int newY) {
		this.xProperty().set(this.getX() + newX);
		this.yProperty().set(this.getY() + newY);
	}
	
	//Retourne s'il y a collision à l'endroit où le personnage se trouve + nouveaux X / Y.
	public boolean colision(int newX, int newY) {
			//Regard angle gauche haut
			if(this.getMap().getBlock(calculationIndex((getX()+newX),(getY()+newY))).getColision()) {
				return true;
			}
			//Regard angle gauche bas
			else if (this.getMap().getBlock(calculationIndex((getX()+newX),(getY()+newY)+56)).getColision()) {
				return true;
			}
			//Regard angle droit haut
			else if (this.getMap().getBlock(calculationIndex((getX()+newX)+24,(getY()+newY))).getColision()) {
				return true;
			}
			//Regard angle droit bas
			else if (this.getMap().getBlock(calculationIndex((getX()+newX)+24,(getY()+newY)+56)).getColision()) {
				return true;
			}
			//MANQUE COLISION MILIEU TO-DO
			//Return
			else {
				return false;
			}
			
	}
	
	@Override
	public void attaque(Sprite ennemi) {
		if (ennemi.getPV()>45) {
			ennemi.perteVie(45);
		} else {
			ennemi.setPV(-ennemi.getPV());
		}
	}
	
	public void perteVie(int attaque) {
		super.perteVie(attaque);
	}
	
	public void gainVie(int gain) {
		super.gainVie(gain);
	}
											//Getter//
	public int getPV() {
		return super.getPV();
	}
	
	public DoubleProperty xProperty() {
		return super.xProperty();
	}
	
	public DoubleProperty yProperty() {
		return super.yProperty();
	}
	
	public double getX() {
		return super.getX();
	}
	
	public double getY() {
		return super.getY();
	}
	
	public int getIndex() {
		return (int) (((int) (getY()/TAILLE_BLOC))*NBR_BLOC_LARGEUR+(getX()/TAILLE_BLOC));
	}
	
	public boolean mort() {
		return super.mort();
	}
										//Setter//
	
	public int calculationIndex(double x, double y) {
		return (int) (((int)(y/TAILLE_BLOC))*NBR_BLOC_LARGEUR+(x/TAILLE_BLOC));
	}

	@Override
	public boolean possibiliteAttaque(Sprite p) {
		if (this.getX() <= p.getX() + (Sprite.TAILLE_BLOC) || this.getX() >= p.getX() - (Sprite.TAILLE_BLOC)) {
			return true;
		}
		else {
			return false;
		}
	}

	
}
