package modeles;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modeles.Block;

public class Map {
	
	 private ObservableList<Block> map;
	 
	public Map () {
		
		this.map = FXCollections.observableArrayList();
		BufferedReader file;
		try {
			file = new BufferedReader(new FileReader("Hidden Hills/src/Map.csv"));
			try {
				while (file.ready()) {
					try {
						String[] parts= file.readLine().split(",");
						for(int i=0; i<parts.length;i++) {
						this.map.add(new Block(parts[i]));
						}
					}catch (IOException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
				System.out.println("Fichier introuvable");
				e1.printStackTrace();
		}
	}	
	
	/*public void sauvegarderMap() {
        try {
            File file = new File("src/vue/Map.csv");
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