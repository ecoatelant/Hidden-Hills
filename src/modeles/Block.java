package modeles;

public class Block {
	private String id;
	private String uRI;

	public Block (String id) {
        this.id = id;
        String chemin = "file:src/img/";
        switch (id) {
            case "-1":
                chemin = chemin + "air.png";
                break;
            case "0":
                chemin = chemin + "charcoal.png";
                break;
            case "6":
                chemin = chemin + "dirt.jpg";
                break;
            case "5":
                chemin = chemin + "titanium.png";
                break;
            case "1":
                chemin = chemin + "grass.jpg";
                break;
            case "4":
                chemin = chemin + "stone.jpg";
                break;
            case "3":
                chemin = chemin + "wood.jpg";
                break;
            case "2":
                chemin = chemin + "four.gif";
                break;
            default:
                chemin = chemin + "air.png";
                break;
        }
        this.uRI = chemin;
    }

	public String getId () {
		return this.id;
	}
	
	public String getuRI() {
		return this.uRI;
	}
	
	
	
	
}
