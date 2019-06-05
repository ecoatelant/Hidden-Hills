package modeles;

import javafx.collections.ObservableList;

public class Tool extends Item{
	private int durability; //nombre d'utilisations
	public Tool(String id, int durability,ObservableList<Item> res) {
		super(id, 1,res);
		this.durability=durability;
	}
	
	public Tool(String id, int durability) {
		super(id, 1);
		this.durability=durability;
	}

	@Override
	public void useItem(int indice, Map map) {
		durability--;
	}

	@Override
	public String getItemURI() {
		return null;
	}	
	
}
