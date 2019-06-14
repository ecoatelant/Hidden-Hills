package modeles;

import java.util.ArrayList;

public abstract class Tool extends Item{
	private int maxNB;
	private String URI;
	private String URICraft;
	
	public Tool(String id, int durability) {
		super(id, durability);
		this.maxNB=1;
		String chemin = "file:src/img/";
		switch (id) {
        case "1":
            this.URI = chemin + "axe.png";
            this.URICraft = chemin + "AxeCraft.gif";
            break; 
        case "2":
        	this.URI = chemin + "ironsword.png";
        	this.URICraft = chemin + "IronswordCraft.gif";
            break;
        case "50":
        	this.URI = chemin + "pickaxe.png";
        	this.URICraft = chemin + "pickaxeCraft.gif";
            break;
		}
	}

	@Override
	public String getItemURI() {
		return this.URI;
	}
	
	@Override
	public String getItemURICraft() {
		return this.URICraft;
	}
	
	public int getMaxNB() {
		return maxNB;
	}
	
	public abstract ArrayList<Item> ressourcesNeeded();
	
}
