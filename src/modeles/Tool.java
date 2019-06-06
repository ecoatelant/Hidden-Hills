package modeles;

import java.util.ArrayList;

public abstract class Tool extends Item{
	private int durability; //nombre d'utilisations
	private String uRI;
	
	public Tool(String id, int durability) {
		super(id, 1,1);
		this.durability=durability;
		String chemin = "file:src/img/";
		switch (id) {
        case "1":
            chemin += "axe.png";
            break; 
        case "2":
            chemin += "ironsword.png";
            break;
        case "3":
            chemin += "pickaxe.png";
            break;
		}
		this.uRI=chemin;
	}

	@Override
	public void useItem(int indice, Map map) {
		durability--;
	}

	@Override
	public String getItemURI() {
		return this.uRI;
	}
	
	public abstract ArrayList<Item> ressourcesNeeded();
	
}
