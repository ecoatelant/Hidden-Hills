package modeles;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modeles.Block;

public class Map {
	
	private ObservableList<Block> map;
	private int mapwidth;
	private int mapheight;
	
	//Déclaration des tailles sur la map
    final static int NBR_BLOC_LARGEUR = 60;
    final static int NBR_BLOC_HAUTEUR = 40;
    final static int TAILLE_BLOC = 32; //Les blocs sont carrés en 32 pixels
	
	public Map () {
		this.map = FXCollections.observableArrayList();
		BufferedReader file;
		try {

			file = new BufferedReader(new FileReader("src/Map2.csv"));
			try {
				while (file.ready()) {
					try {
						//On récupère l'id des blocks en les découpants à partir des virgules 
						mapheight++;
						String line=file.readLine();
						String[] parts= line.split(",");
						if(parts.length!=1) {
							for(int i=0; i<parts.length;i++) {
							this.map.add(new Block(parts[i]));
							}
						}
						
						this.mapwidth=parts.length;
					}catch (IOException e) {
						e.printStackTrace(); 
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
				System.out.println("Fichier introuvable");
				e1.printStackTrace();
		}
	}	
	
	public void sauvegarderMap() {
        try {
        	//Dans le fichier csv, on place les ids des blocks et on place une virgule.
            File file = new File("src/vue/Map2.csv");
            FileWriter fileWriter = new FileWriter(file, false);
            String changements = "";
            int i = 1;
            for (Block t : map) {
                changements = changements + t.getId()+",";
                if (i%this.map.size() ==0)
                    changements = changements + "\n";
                i++;
            }
            fileWriter.write(changements);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public Block getBlock (int indice) {
	        return this.map.get(indice);
	}
	
	public ObservableList<Block> getMap() {
	        return this.map;
	}
	public int getMapWidth() {
		return this.mapwidth;
	}
	public int getMapHeight() {
		return this.mapheight;
	}
	
	public int calculationIndex(double x, double y) {
		return (int) (((int)(y/TAILLE_BLOC))*NBR_BLOC_LARGEUR+(x/TAILLE_BLOC));
	}

}

