package modeles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventaire {
	private ObservableList<Item> inventory;
	private boolean handIsEmpty= true;//détermine si le personnage tient un objet ou pas
	
	public Inventaire() {
		this.inventory=FXCollections.observableArrayList();
		this.inventory.add(new BlockItem("-1"));
	}
	public Item getItemInHand() {
		return this.inventory.get(1); 
	} 
	public ObservableList<Item> getInventory(){
		return this.inventory;
	}
	public void emptyHand() {
		this.inventory.remove(1);
		handIsEmpty=true;
	}
	public void add(Item Item) {
		if(this.getInventory().size()==40)
			System.out.println("Erreur : inventaire plein");
		else {
			this.inventory.add(Item);
			handIsEmpty=false;
		}

	}
	public boolean isHandEmpty() {
		return this.handIsEmpty;		
	}
	
}
