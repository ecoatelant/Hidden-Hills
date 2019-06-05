package modeles;

import java.util.ArrayList;

import javafx.collections.ObservableList;

public abstract class Item {
	private String id;
	private int nb;	//Nombres d'utilisation
	private ObservableList<Item> ressources;
	
	public Item(String id, int nb,ObservableList<Item> res) { 
		this.id=id;
		this.nb=nb;
		this.ressources = res;
	}
	
	public Item(String id, int nb) { 
		this.id=id;
		this.nb=nb;
	}
	
	public String getId() {
		return this.id;
	}
	public int getNb() {
		return this.nb;
	}
	public boolean used() {
		nb--;
		if(this.nb<=0) { 
			return true;
			}
		return false;
		}	
	public abstract void useItem(int indice, Map map);
	public abstract String getItemURI();
	
	public void addUse() {
		this.nb++;
	}
	
	public boolean ressourcesPresentes(ObservableList<Item> inv) {
		boolean present = true;
		int i = 0;
		while(i < ressources.size() && present==true) {
			present=false;
			int j = 0;
			while(j < inv.size() && present==false) {
				if(inv.get(j)==ressources.get(i)) {
					present=true;
				}
				j++;
			}
			i++;
		}
		return present;
	}

}
