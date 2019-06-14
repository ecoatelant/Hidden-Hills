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
		bois.addUse();
		BlockItem pierre = new BlockItem("8");
		for(int i=0;i<2;i++)
			pierre.addUse();
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(bois);
		list.add(pierre);
		for(Item i:list)
			System.out.println(i.getId());
		return list;
	}

	@Override
	public void useItem(int indice, Map map, Inventaire inventaire) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaxNB() {
		return 1;
	}

	@Override
	public String getNom() {
		return "Hache";
	}
	
}