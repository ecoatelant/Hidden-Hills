package modeles;

public class BlockItem extends Item{
	
	public BlockItem(String id) { 
		super(id,1);
	}  
	public void useItem(int indice, Map map) {
			map.setBlock(indice,new Block(this.getId(),indice));
	} 
}
