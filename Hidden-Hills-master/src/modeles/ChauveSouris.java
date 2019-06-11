package modeles;

import javafx.beans.property.DoubleProperty;

public class ChauveSouris extends Sprite{

		public ChauveSouris(Map collisionMap) {
			super(collisionMap);
		}
		
		//https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra
		
		public void move(int newX, int newY) {
			this.xProperty().set(this.getX() + newX);
			this.yProperty().set(this.getY() + newY);
		}
		//Permet de detecter si le personnage se tient dans la zone de la chauve souris
		public boolean detectionPersonnageDroite (Personnage p) {
			if(p.getX() <= this.getX()+ (Sprite.TAILLE_BLOC*10) && p.getX() > this.getX()) {
				return true;
			}
			else {
				return false;
			}
		}
		
		public boolean detectionPersonnageGauche (Personnage p) {
			if(p.getX() >= this.getX() - (Sprite.TAILLE_BLOC*10) && p.getX() < this.getX()) {
				return true;
			}
			else {
				return false;
			}
		}
		
		public boolean detectionPersonnageBas (Personnage p) {
			if(p.getY() > this.getY() - (Sprite.TAILLE_BLOC*10) && p.getY() > this.getY()) {
				return true;
			}
			else {
				return false;
			}
		}
		
		public boolean detectionPersonnageHaut (Personnage p) {
			if(p.getX() < this.getX()) {
				return true;
			}
			else {
				return false;
			}
		}
		//Permet de d�terminer si on est assez proche pour attaquer
		public boolean possibiliteAttaque (Sprite p) {
			//TO-DO
			if (p.getX() <= this.getX()+ (Sprite.TAILLE_BLOC) || p.getX() >= this.getX() - (Sprite.TAILLE_BLOC)) {
				return true;
			}
			else {
				return false;
			}
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
				//Return
				else {
					return false;
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
		public void attaque(Sprite ennemi) {
			if (ennemi.getPV()>20) {
				ennemi.perteVie(20);
			} else {
				ennemi.setPV(-ennemi.getPV());
			}
		}
}
	
	
