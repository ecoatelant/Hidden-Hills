package modeles;

import java.util.ArrayList;

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
	
	public void crafting(Item craft) {
		if(craft.ressourcesPresentes(this.inventory)) {
			ArrayList<Item> itemToRM = craft.ressourcesNeeded();
			for(int i = 0; i<itemToRM.size();i++) {
				for(int j = 0; j<this.inventory.size();j++) {
					if(this.inventory.get(j)==itemToRM.get(i)) {
						this.inventory.remove(j);
						itemToRM.remove(i);
					}
				}
			}
			inventory.add(craft);
		}
		
	}
	
}
