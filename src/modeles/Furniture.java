package modeles;

import java.util.ArrayList;

public abstract class Furniture extends Item {
	private int maxNB=10;
	private String uRI;
	
	public Furniture(String id,int nb) {
		super(id,nb);
		String chemin = "file:src/img/";
		switch (id) {
        	case "79":
        		chemin += "bed.png";
        		break;
        	case "17":
        		chemin += "four.gif";
		}
        this.uRI=chemin;
	}

	@Override
	public String getItemURI() {
		return this.uRI;
	}
	
	@Override
	public String getItemURICraft() {
		return this.uRI;
	}
	
	@Override
	public int getMaxNB() {
		return maxNB;
	}
	
	public abstract ArrayList<Item> ressourcesNeeded();
	
}
