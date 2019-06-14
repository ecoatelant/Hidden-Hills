package modeles;


public abstract class Ennemis extends Personnage {

	private int rangeDetection;
	private int rangeAttack;
	private int degats;
	private boolean terrestre;

	public Ennemis(Map collisionMap, String imgDroite, String imgGauche, int rangeDetection, int rangeAttack, int degats, boolean terrestre) {
		super(collisionMap, imgDroite, imgGauche);
		this.rangeDetection = rangeDetection;
		this.rangeAttack = rangeAttack;
		this.degats = degats;
		this.terrestre = terrestre;
	}

	// https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra

	// Permet de detecter si le personnage se tient dans la zone de la chauve souris
	public boolean detectionPersonnageDroite(Joueur p) {
		if (p.getX() <= this.getX() + (Personnage.TAILLE_BLOC * getRangeDetection()) && p.getX() > this.getX()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean detectionPersonnageGauche(Joueur p) {
		if (p.getX() >= this.getX() - (Personnage.TAILLE_BLOC * getRangeDetection()) && p.getX() < this.getX()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean detectionPersonnageBas(Joueur p) {
		if ((detectionPersonnageDroite(p) || detectionPersonnageGauche(p) || p.getX() == this.getX()) && p.getY() <= this.getY() + (Personnage.TAILLE_BLOC * getRangeDetection()) && p.getY() > this.getY()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean detectionPersonnageHaut(Joueur p) {
		if ((detectionPersonnageDroite(p) || detectionPersonnageGauche(p) || p.getX() == this.getX()) && p.getX() >= this.getX() - (Personnage.TAILLE_BLOC * getRangeDetection()) && p.getY() < this.getY()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	abstract public void attaque(Personnage p);

	public int getRangeAttack() {
		return rangeAttack;
	}

	public int getRangeDetection() {
		return rangeDetection;
	}

	public int getDegats() {
		return degats;
	}

	public boolean getTerrestre() {
		return this.terrestre;
	}
	// Setter//

	public void setRangeDetection(int rangeDetection) {
		this.rangeDetection = rangeDetection;
	}

	public void setRangeAttack(int rangeAttack) {
		this.rangeAttack = rangeAttack;
	}

	public void setDegats(int degats) {
		this.degats = degats;
	}
}
