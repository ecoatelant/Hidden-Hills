package modeles;

import java.util.ArrayList;
import modeles.BlockItem;

public class Axe extends Tool {
	
	public Axe() {
		super("1",8);
	}
	
	@Override
	public ArrayList<Item> ressourcesNeeded(){
		BlockItem bois = new BlockItem("5");
		BlockItem pierre = new BlockItem("13");
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(bois);
		list.add(pierre);
		return list;
	}
	
}