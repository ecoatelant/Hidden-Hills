package modeles;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modeles.Block;

public class Map {
	
	 private ObservableList<Block> map;
	 private int largeur;
	 
	@SuppressWarnings("resource")
	public Map () {
		
		this.map = FXCollections.observableArrayList();
		 try {
	            File file = new File("view/map.csv");
	            Scanner sc = new Scanner(file);

	            String line = sc.nextLine();
	            this.largeur = line.length();

	            sc = new Scanner(file);
	            while(sc.hasNextLine()) {
	                line = sc.nextLine();
	                int i = 0;
	                while (i < this.largeur) {
	                    this.map.add(new Block(String.valueOf(line.charAt(i))));
	                    i++;
	                }
	            }
	            sc.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	}	
	
	/*public void sauvegarderMap() {
        try {
            File file = new File("src/view/map.csv");
            FileWriter fileWriter = new FileWriter(file, false);
            String changements = "";
            int i = 1;
            for(Block t : map) {
                changements = changements + t.getId();
                if (i%this.largeur == 0)
                    changements = changements + "\n";
                i++;
            }
            fileWriter.write(changements);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
	
	public Block getBlock (int index) {
	        return this.map.get(index);
	}
	
	 public ObservableList<Block> getMap() {
	        return this.map;
	 }


}