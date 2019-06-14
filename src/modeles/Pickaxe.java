package modeles;

import java.util.ArrayList;
import modeles.BlockItem;

public class Pickaxe extends Tool {
	
	public Pickaxe() {
		super("50",50);
	}
	
	@Override
	public ArrayList<Item> ressourcesNeeded(){
		BlockItem bois = new BlockItem("5");
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(bois);
		return list;
	}

	@Override
	public void useItem(int indice, Map map, Inventaire inventory) {
		
   	 if(map.getBlock(indice).getId().equals("-1")) {}//Si on essaye de casser la ou il y a un bloc d'air
   	 else {
   		 Block workBlock;
		 workBlock=map.getBlock(indice);//bloc qui a été retiré
		 boolean done=false;
			 map.setBlock(indice,new Block("-1",indice));
		 if (inventory.getItemInHand().used()) //Dernière utilisation de la pioche
			inventory.emptyHand();
		 if(inventory.getInventoryList().size()>1) {	 
	    	 int i=0;
	    	 while(done==false && i<inventory.getInventoryList().size()) {//cas ou le bloc est présent dans l'inventaire, on augmente son compteur
	     			if(inventory.getInventoryList().get(i).getId().equals(workBlock.getId()) && inventory.getInventoryList().get(i).getMaxNB()>inventory.getInventoryList().get(i).getNb()) {
		         		inventory.getInventoryList().get(i).addUse();
		         		done=true;
	     			}
	     			i++;
	         } 
		 }
		 if(done==false){//cas ou le bloc n'est pas présent, on ajoute le bloc à l'inventaire
			Item adBlock=new BlockItem(workBlock.getId());
			inventory.add(adBlock);
		 }
		 }
	 }
	
	@Override
	public int getMaxNB() {
		return 1;
	}

	@Override
	public String getNom() {
		return "Pioche";
	}
	
}