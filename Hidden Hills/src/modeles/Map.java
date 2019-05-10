package modeles;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Map {
	
	private ArrayList<String> map;
	
	public Map () {
		this.map = new ArrayList<>();
		BufferedReader file;
		try {
			file = new BufferedReader(new FileReader("src/Map.csv"));
			try {
				while (file.ready()) {
					try {
						this.map.add(file.readLine());
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
		}
	}	
	
	public String getBlock(int indice) {
		return this.map.get(indice);
	}
		

}