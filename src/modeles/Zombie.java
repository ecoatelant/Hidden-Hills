package modeles;


public class Zombie extends Ennemis {

	public Zombie(Map collisionMap) {
		super(collisionMap, "file:src/img/zombie-left.gif", "file:src/img/zombie.gif", 20, 1, 5, true);
	}

	//https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra

	@Override
	// Permet de d�terminer si on est assez proche pour attaquer
	public boolean possibiliteAttaque(Personnage p) {
		// TO-DO
		if (p.getX() <= this.getX() + (Personnage.TAILLE_BLOC * super.getRangeAttack())
				|| p.getX() >= this.getX() - (Personnage.TAILLE_BLOC * super.getRangeAttack())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void attaque(Personnage ennemi) {
		if (ennemi.getPV() > this.getDegats()) {
			ennemi.perteVie(this.getDegats());
		} else {
			ennemi.setPV(-ennemi.getPV());
		}
	}

	@Override
	// Retourne s'il y a collision à l'endroit où le personnage se trouve + nouveaux
	// X / Y.
	public boolean collision(int newX, int newY) {
		// Regard angle gauche haut
		if (this.getMap().getBlock(calculationIndex((getX() + newX), (getY() + newY))).getCollision()) {
			return true;
		}
		// Regard angle gauche bas
		else if (this.getMap().getBlock(calculationIndex((getX() + newX), (getY() + newY) + 56)).getCollision()) {
			return true;
		}
		// Regard angle droit haut
		else if (this.getMap().getBlock(calculationIndex((getX() + newX) + 24, (getY() + newY))).getCollision()) {
			return true;
		}
		// Regard angle droit bas
		else if (this.getMap().getBlock(calculationIndex((getX() + newX) + 24, (getY() + newY) + 56)).getCollision()) {
			return true;
		}
		// Return
		else {
			return false;
		}

	}
	// Setter//

}
