package modeles;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Item {
	private String id;
	private IntegerProperty nb;
	
	public Item(String id,int nb) { 
		this.id=id;
        this.nb = new SimpleIntegerProperty(nb);
	}
	
	public String getId() {
		return this.id;
	}
	public int getNb() {
		return this.nb.get();
	}
	public boolean used() {
		nb.set(nb.get()-1);
		if(this.nb.get()<=0) { 
			return true;
			}
		return false;
		}	
	
	public abstract String getItemURI();
	
	public abstract String getItemURICraft();
	
	public abstract ArrayList<Item> ressourcesNeeded();
	
	public String ressourcesNeededToString() {
		String affichage = "";
		for(int i = 0; i < ressourcesNeeded().size() ; i++) {
			affichage += ressourcesNeeded().get(i).getNom();
			if(i+1 != ressourcesNeeded().size()) {
				affichage += ", ";
			}
		}
		return affichage;
	}
	
	public abstract String getNom();

	public IntegerProperty getIndexProperty() {
		return this.nb;
	}
	public abstract void useItem(int indice, Map map, Inventaire inventaire);
	
	public abstract int getMaxNB();
	
	public IntegerProperty nbProperty() {
		return this.nb;
	}
	public boolean addUse() {
		if(nb.get()<this.getMaxNB()) {
		this.nb.set(nb.get()+1);
		return true;
		}
		return false;
	}
	public Tool getTool() {
		return (Tool)this;
	}
}
