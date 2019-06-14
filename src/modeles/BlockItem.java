package modeles;

import java.util.ArrayList;

public class BlockItem extends Item{
	
	private int maxNB=64;
	private String name;
	
	public BlockItem(String id) { 
		super(id,1);
			switch (id) {
			case "-1":
				this.name = "Fond";
				break;  
			case "1":
				this.name = "Charbon";
				break;
			case "2":
                this.name = "terre";
                break;
            case "4":
                this.name = "titanium";
                break;
            case "7":
                this.name = "herbe";
                break;
            case "8":
                this.name = "pierre";
                break;
            case "5":
                this.name = "bois";
                break;
            case "6":
                this.name = "four";
                break;
		}
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
	public String getItemURI() {
		return new Block(this.getId(),0).getuRI();
	}
	
	@Override
	public String getItemURICraft() {
		return new Block(this.getId(),0).getuRI();
	}

	
	@Override
	public int getMaxNB() {
		return this.maxNB;
	}
	@Override
	public ArrayList<Item> ressourcesNeeded() {
		return null;
	}
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
}
