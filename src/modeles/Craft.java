package modeles;

import java.util.ArrayList;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class Craft {
	
	private ObservableList<Item> tableCraft;
	
	public Craft() {
		this.tableCraft = FXCollections.observableArrayList( new Callback<Item, Observable[]>() {
                    @Override
                    public Observable[] call(Item craft) {
                        return new Observable[]{
                                craft.getIndexProperty()
                        };
                    }
                }
        );
		Pickaxe pioche = new Pickaxe();
		Axe hache = new Axe();
		Ironsword epee = new Ironsword();
		tableCraft.add(0, pioche);
		tableCraft.add(1, hache);
		tableCraft.add(2, epee);
	}
	
	//Getter
	public int sizeTableCraft() {
		return tableCraft.size();
	}
	
	public Item getItem(int index) {
		return tableCraft.get(index);
	}
	
	//Méthodes
	public boolean ressourcesPresentes(ObservableList<Item> inventory, Item craft) {
		boolean present = true;
		int i = 0;
		while(i < craft.ressourcesNeeded().size() && present==true) {
			present = false;
			int j = 0;
			while(j < inventory.size() && present==false) {
				if(inventory.get(j).getId().equals(craft.ressourcesNeeded().get(i).getId())) {
					present=true;
					System.out.println("1 ressource présente");
				}
				j++;
			}
			i++;
			
		}
		
		//Indications dans la console
		if(present) {
			System.out.println("Les ressources dans l'inventaire sont présentes !");
		}
		else {
			System.out.println("Il manque des ressources dans l'inventaire : RESSOURCES NECESSAIRES : ");
			for(int k = 0; k<craft.ressourcesNeeded().size();k++) {
				System.out.println(craft.ressourcesNeeded().get(k).getId());
			}
		}
		
		return present;
	}
	
	public void crafting(ObservableList<Item> inventory, Item craft) {
		if(ressourcesPresentes(inventory,craft)) {
			ArrayList<Item> itemToRM = craft.ressourcesNeeded();
			for(int i = 0; i<itemToRM.size();i++) {
				for(int j = 0; j<inventory.size();j++) {
					if(inventory.get(j)==itemToRM.get(i)) {
						inventory.remove(j);
						itemToRM.remove(i);
						System.out.println("1 ressource supprimé");
					}
				}
			}
			inventory.add(craft);
		}
	}

}
