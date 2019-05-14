package modele;

public class Personnage extends TiledMap{
	
	//Attributs
	private int pv;
	private int emplacementPerso; //correspond à l'indice dans l'ArrayList de la map
	
	//Constructeur
	public Personnage() {
		this.pv=100;
		this.emplacementPerso=0;
	}
	
	//Getter
	public int getPV() {
		return this.pv;
	}
	
	public int getEmplacementPerso() {
		return this.emplacementPerso;
	}
	
	//Setter
	public void gainVie(int gain) {
		this.pv=+gain;
	}
	
	public void perteVie(int attaque) {
		this.pv=-attaque;
	}
	
	public void setEmplacement(int emplacement) {
		this.emplacementPerso=emplacement;
	}
	
	//Méthode
	public void deplacerPersonnage(int direction) {
        
		switch(direction) {
        case 3:
            this.emplacementPerso++;
            break;
             
        // Droite
        case 4:
            this.emplacementPerso--;
            break;
             
            default:
                System.out.println("Ceci n'est pas une direction valide.");
        }
    }
	
	public boolean mort() {
		return this.pv<=0;
	}
	
	
}
