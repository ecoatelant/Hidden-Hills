package modeles;

import java.util.ArrayList;
import modeles.BlockItem;

public class Ironsword extends Tool {
	
	public Ironsword() {
		super("2",20);
	}
	
	@Override
	public ArrayList<Item> ressourcesNeeded(){
		BlockItem pierre = new BlockItem("13");
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(pierre);
		return list;
	}
	
}