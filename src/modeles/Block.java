package modeles;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Block {
	//L'id correspond au nombre attribué aux blocs dans le fichier csv
	//Il faut par ailleurs utiliser le même tileset pour que les nombres attribués aux blocs soient identiques entre les diffèrents fichiers csv
	//Ce qui permet un décodage de la map optimal
	private String id;
	private String uRI;
	private IntegerProperty indice=new SimpleIntegerProperty();
	private boolean collision;

	public Block(String id, int ind) {
        this.id = id;
        this.indice.set(ind);
        String chemin = "file:src/img/";
        this.collision=true;
        switch (id) {
            case "-1":
                chemin = chemin + "fond.png";
                this.collision=false;
                break; 
            case "7":
                chemin = chemin + "charcoal.png";
                break;
            case "13":
                chemin = chemin + "dirt.jpg";
                break;
            case "6":
                break; 
            case "5":
                chemin = chemin + "titanium.png";
                break;
            case "15":
                chemin = chemin + "grass.jpg";
                break;
            case "14":
                chemin = chemin + "stone.jpg";
                break;
            case "3":
                chemin = chemin + "wood.jpg";
                this.collision=false;
                break;
            case "2":
                chemin = chemin + "four.gif";
                this.collision=false;
                break;
            default:
                chemin = chemin + "air.png";
                this.collision=false;
                break;
        }
        this.uRI = chemin;
    }

	//Getter
	public String getId () {
		return this.id;
	}
	
	public int getIndice() {
		return this.indice.get();
	}
	
	public String getuRI() {
		return this.uRI;
	}
	
	public IntegerProperty indiceProperty() {
		return this.indice;
	}
	
	public boolean getCollision() {
		return this.collision;
	}
	
}
