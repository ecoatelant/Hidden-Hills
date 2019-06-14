package modeles;

import java.util.ArrayList;
import modeles.BlockItem;

public class Ironsword extends Tool {
	
	public Ironsword() {
		super("2",20);
	}
	
	@Override
	public ArrayList<Item> ressourcesNeeded(){
		BlockItem pierre = new BlockItem("8");
		for(int i=0;i<3;i++)
			pierre.addUse();
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(pierre);
		return list;
	}
	
	@Override
	public String getNom() {
		return "Epée";
	}

	@Override
	public void useItem(int indice, Map map, Inventaire inventaire) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaxNB() {
		return 1;
	}
	
}