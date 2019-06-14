package modeles;

import modeles.Map;

import java.io.File;

import javafx.beans.property.DoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Joueur extends Personnage{
											//Attributs//
	private Inventaire inventory;
    final static int PERSO_LARGEUR = 32;
    final static int PERSO_HAUTEUR = 64;
  //Saut
	private static double VITESSE_SAUT = -16;
											//Constructeur//
	public Joueur(Map collisionMap , Inventaire inventory) {
		super(collisionMap, "file:src/img/PPleft.gif", "file:src/img/PPright.gif");
		this.inventory=inventory;
	}
	
	public void move(double newX, double newY) {
		this.xProperty().set(this.getX() + newX);
		this.yProperty().set(this.getY() + newY);
	}
	public boolean onGround() {
		int y;
		if (this.getMap().getBlock(calculationIndex((getX()+10),(getY()+8)+56)).getCollision()) {
			y=(int)(getY())/32;
			if(getY()!=y)
				this.setY(y*32);
			return true;
		}
		else if (this.getMap().getBlock(calculationIndex((getX())+25,(getY()+8)+56)).getCollision()) {
			y=(int)(getY())/32;
			if(getY()!=y)
				this.setY(y*32);
			return true;
		}
		return false;
	}
	public boolean plafond(double newY) {
		//Regard angle droit haut
		if (this.getMap().getBlock(calculationIndex((getX())+10,(getY()+newY))).getCollision()) {
				return true;
			}
		else if(this.getMap().getBlock(calculationIndex((getX())+25,(getY()+newY))).getCollision()) {
			return true;
		}
		return false;
	}
	//Retourne s'il y a collision à l'endroit où le personnage se trouve + nouveaux X / Y.
	public boolean collision(int newX, int newY) {
			//Regard angle gauche haut
			if(this.getMap().getBlock(calculationIndex((getX()+newX+10),(getY()+newY))).getCollision()) {
				return true;
			}
			//Regard angle gauche bas
			else if (this.getMap().getBlock(calculationIndex((getX()+newX+10),(getY()+newY)+56)).getCollision()) {
				return true;
			}
			//Regard angle droit haut
			else if (this.getMap().getBlock(calculationIndex((getX()+newX)+25,(getY()+newY))).getCollision()) {
				return true;
			}
			//Regard angle droit bas
			else if (this.getMap().getBlock(calculationIndex((getX()+newX)+25,(getY()+newY)+56)).getCollision()) {
				return true;
			}
			//MANQUE COLISION MILIEU TO-DO
			else if (this.getMap().getBlock(calculationIndex((getX()+newX)+10,(getY()+newY)+28)).getCollision()) {
				return true;
			}
			//Regard angle droit bas
			else if (this.getMap().getBlock(calculationIndex((getX()+newX)+25,(getY()+newY)+28)).getCollision()) {
				return true;
			}
			//Return
			else {
				return false;
			}
			
	}
	public void casserBlock(int x, int y) {
		 if (x<this.xProperty().get()/32-5 || x>this.xProperty().get()/32+5 || y<this.yProperty().get()/32-5 || y>this.yProperty().get()/32+6) {
			 System.out.println("Trop loin pour placer/casser");
         } 
    	 if(this.getInventory().isHandEmpty()) {} // cas main vide
    	 else if(inventory.getInventoryList().size()<=1 || inventory.getItemInHand().getId()=="50") {
       	 inventory.getItemInHand().useItem(x+y*this.getMap().getMapWidth(), this.getMap(), inventory);
	     }
	}
	public void placerBlock(int x, int y) {
		 Item item=inventory.getItemInHand();
		 if(item.getClass().toString().equals("class modeles.BlockItem"))
			 item.useItem(x+y*this.getMap().getMapWidth(),this.getMap(),inventory);
   }
	
	public void perteVie(int attaque) {
		super.perteVie(attaque);
	 	String music=new File("src/music/damagetaken.mp3").toURI().toString();
		MediaPlayer player=new MediaPlayer(new Media(music));
		player.play();
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
		return (int) (((int) (getY()/TAILLE_BLOC))*this.getMap().getMapWidth()+(getX()/TAILLE_BLOC));
	}
	public Inventaire getInventory() {
		return this.inventory;
	}

	
	public boolean mort() {
		return super.mort();
	}
										//Setter//
	
	public int calculationIndex(double x, double y) {
		return (int) (((int)(y/TAILLE_BLOC))*this.getMap().getMapWidth()+(x/TAILLE_BLOC));
	}
	public boolean possibiliteAttaque(Personnage p) {
		// TO-DO
		if (p.getX() >= this.getX() - (Personnage.TAILLE_BLOC) && p.getX() <= this.getX() + (Personnage.TAILLE_BLOC)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void attaque(Personnage ennemi) {
		if (ennemi.getPV() > 25) {
			ennemi.perteVie(25);
		} else {
			ennemi.setPV(-ennemi.getPV());
		}
	}

	
}
