package modeles;

public class ChauveSouris extends Ennemis {

	public ChauveSouris(Map collisionMap) {
		// la chauve-souris n'a qu'une image étant donnée qu'on la crée de face
		super(collisionMap, null, "file:src/img/Bat.gif", 10, 2, 24, false);
	}

	// https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra

	@Override
	// Permet de d�terminer si on est assez proche pour attaquer
	public boolean possibiliteAttaque(Personnage p) {
		// TO-DO
		if (p.getX() >= this.getX() - (Personnage.TAILLE_BLOC*super.getRangeAttack()) && p.getX() <= this.getX() + (Personnage.TAILLE_BLOC*super.getRangeAttack())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void attaque(Personnage p) {
		if (p.getPV() > this.getDegats()) {
			p.perteVie(this.getDegats());
		} else {
			p.setPV(-p.getPV());
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
		else if (this.getMap().getBlock(calculationIndex((getX() + newX), (getY() + newY) + 40)).getCollision()) {
			return true;
		}
		// Regard angle droit haut
		else if (this.getMap().getBlock(calculationIndex((getX() + newX) + 40, (getY() + newY))).getCollision()) {
			return true;
		}
		// Regard angle droit bas
		else if (this.getMap().getBlock(calculationIndex((getX() + newX) + 40, (getY() + newY) + 40)).getCollision()) {
			return true;
		}
		// Return
		else {
			return false;
		}

	}
	// Setter//
}
