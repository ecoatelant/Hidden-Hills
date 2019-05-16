package controleur;

import modeles.*;
import img.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SampleController implements Initializable {
	
	private static File Terre = new File("src/img/dirt.jpg");
	private static File Titane = new File("src/img/titanium.png");
	private static File Herbe = new File("src/img/grass.jpg");
	private static File Charbon = new File("src/img/charcoal.png");
	private static File Roche = new File("src/img/stone.jpg");
	private static File Four = new File("src/img/four.png");
	private static File Bois = new File ("src/img/wood.jpg");
	private static File Lit = new File ("src/img/bed.png");
	private ArrayList<File> listFiles = new ArrayList<>();
	@SuppressWarnings("unused")
	private void ajouterFiles () {
		this.listFiles.add(Terre);
		this.listFiles.add(Titane);
		this.listFiles.add(Herbe);
		this.listFiles.add(Charbon);
		this.listFiles.add(Roche);
		this.listFiles.add(Four);
		this.listFiles.add(Bois);
		this.listFiles.add(Lit);
	}
	private ArrayList<Image> listImages;
	private Pane pane;
	private Map map;
	
	
	
	
	
	
	
	public void initialize(URL location, ResourceBundle resources) {
		pane = new Pane();
		map = new Map();
		listImages= new ArrayList<>();
		
		
		for(int x=0;x<=30;x++) {
			for(int y=0;y<=100;y++) {
				switch (map.getBlock(x)) {
				case "-1":
					
				}
			}
		}
		
		
		
		
		
		
		
	}
}
