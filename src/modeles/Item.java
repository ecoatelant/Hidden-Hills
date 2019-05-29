package modeles;

public abstract class Item {
	private String id;
	private int nb;
	
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
	
	public void addUse() {
		this.nb++;
	}

}
