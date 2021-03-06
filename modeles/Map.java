package modeles;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import modeles.Block;

public class Map {
											//Attributs//
	private ObservableList<Block> map;
	private int mapwidth;
	private int mapheight;
	
	//Déclaration des tailles sur la map
	private static String nomFichierMap = "MapEmilie";
    public final static int TAILLE_BLOC = 32; //Les blocs sont carrés en 32 pixels
    											//Constructeur//
	
	public Map () {
		this.map = FXCollections.observableArrayList();
		BufferedReader file;
		try {
			file = new BufferedReader(new FileReader("src/"+nomFichierMap+".csv"));
			try {
				while (file.ready()) {
					try {
						//On récupère l'id des blocks en les découpants à partir des virgules 
						mapheight++;
						String line=file.readLine();
						String[] parts= line.split(",");
						int j=0;
						if(parts.length!=1) {
							for(int i=0; i<parts.length;i++) {
							this.map.add(new Block(parts[i],j));
							j++;
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
            File file = new File("src/test.csv");
            FileWriter fileWriter = new FileWriter(file, false);
            String changements = "";
            int i = 1;
            for (Block t : map) {
                changements = changements + t.getId()+",";
                if (i%this.mapwidth ==0)
                if (i%this.mapwidth==0)
                    changements = changements + "\n";
                i++;
            }
            fileWriter.write(changements);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
											//Setter//
	public void setBlock(int indice, Block block) {
		this.map.set(indice, block);
											//Getter//
	}
	public ObservableList<Block> getMap() {
	        return this.map;
	}
	
	public Block getBlock (int indice) {
        return this.map.get(indice);
	}
	
	//Retourne la taille en largeur de la map en nombre de blocs
	public int getMapWidth() {
		return this.mapwidth;
	}
	
	//Retourne la taille en hauteur de la map en nombre de blocs
	public int getMapHeight() {
		return this.mapheight;
	}
	
	//Retourne la taille en largeur de la map en pixels
	public int getMapWidthPX() {
		return this.mapwidth*32;
	}
		
	//Retourne la taille en hauteur de la map en pixels
	public int getMapHeightPX() {
		return this.mapheight*32;
	}
	
	public int calculationIndex(double x, double y) {
		return (int) (((int)(y/TAILLE_BLOC))*mapwidth+(x/TAILLE_BLOC));
	}

}

