package modeles;

import java.util.ArrayList;
import modeles.BlockItem;

public class Kiln extends Furniture{
			
	public Kiln() {
			super("17",1);
	}
		
	@Override
	public ArrayList<Item> ressourcesNeeded(){
		BlockItem bois = new BlockItem("5");
		for(int i=0;i<3;i++)
			bois.addUse();
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(bois);
		return list;
	}

	@Override
	public void useItem(int indice, Map map, Inventaire inventory) {
			if(map.getBlock(indice).getIndice()==0) {}
			 else if(map.getBlock(indice).getId().equals("-1")) {
	        	 if(inventory.getInventoryList().size()<=1 || inventory.getItemInHand().getClass().getName().equals("modeles.Tool")) {
	        	 }//inventaire vide
	        	 if (inventory.getItemInHand().used()) //Dernier bloc utilisé -> Main vide
				inventory.emptyHand();
				map.setBlock(indice,new Block(this.getId(),indice));
			 }
	}

	@Override
	public String getNom() {
		return "Four";
	}

	@Override
	public String getItemURICraft() {
		return "four";
	}

}
