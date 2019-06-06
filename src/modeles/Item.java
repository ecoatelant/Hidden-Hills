package modeles;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Item {
	private String id;
	private int nb;	//Nombres d'utilisation
	private IntegerProperty index;
	
	
	public Item(String id,int nb,int ind) { 
		this.id=id;
		this.nb=nb;
        this.index = new SimpleIntegerProperty(ind);
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
	
	public abstract ArrayList<Item> ressourcesNeeded();

	public IntegerProperty getIndexProperty() {
		return this.index;
	}

}
