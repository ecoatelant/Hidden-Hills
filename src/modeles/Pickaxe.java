package modeles;

import java.util.ArrayList;
import modeles.BlockItem;

public class Pickaxe extends Tool {
	
	public Pickaxe() {
		super("3",8);
	}
	
	@Override
	public ArrayList<Item> ressourcesNeeded(){
		BlockItem bois = new BlockItem("5");
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(bois);
		return list;
	}
	
}