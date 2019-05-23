package modeles;

public class Block {
	private String id;
	private String uRI;
	private boolean collision;

	public Block (String id) {
        this.id = id;
        String chemin = "file:src/img/";
        this.collision=true;
        switch (id) {
            case "-1":
                chemin = chemin + "fond.png";
                this.collision=false;
                break; 
            case "0":
                chemin = chemin + "charcoal.png";
                this.collision=true;
                break;
            case "13":
                chemin = chemin + "dirt.jpg";

                this.collision=true;
                break;
            case "6":
                chemin = chemin + "titanium.png";
                //this.collision=true;
                break;
            case "15":
                chemin = chemin + "grass.jpg";
                this.collision=true;
                break;
            case "14":
                chemin = chemin + "stone.jpg";
                this.collision=true;
                break;
            case "":
                chemin = chemin + "wood.jpg";
                this.collision=false;
                break;
            case "2":
                chemin = chemin + "four.gif";
                this.collision=false;
                break;
            default:
                chemin = chemin + "air.png";
                this.collision=false;
                break;
        }
        this.uRI = chemin;
	}

	//Getter
	public String getId () {
		return this.id;
	}
	
	public String getuRI() {
		return this.uRI;
	}
	
	
	public boolean getCollision() {
		return this.collision;
	}
	
}
