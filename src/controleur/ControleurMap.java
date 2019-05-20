package controleur;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import modeles.Personnage;
import modeles.Map;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ControleurMap implements Initializable {

    private Map map;
    
    @FXML
    private Pane mainPane;

    @FXML
    private TilePane tilePaneMap;

    @FXML
    private Pane persoPane;

    private Personnage p = new Personnage();
<<<<<<< HEAD
=======
  
    boolean running, goNorth, goSouth, goEast, goWest , jump;
>>>>>>> 4a92afd3a33ea0cffa49d6057e63bacc9c86f521

    @FXML
    private ImageView imgVi;

  	public void handlePressed(KeyEvent e) {
  		switch (e.getCode()) {
          	case Z:  
          		handle('N');
          		break;
          	case S:  
          		handle('S');
          		break;
          	case Q:           		
          		handle('W');
          		break;
          	case D:  
          		handle('E');
          		break;
          	case SPACE :
<<<<<<< HEAD
=======
          		jump = true;
>>>>>>> 4a92afd3a33ea0cffa49d6057e63bacc9c86f521
          		handle('J');
          	default:
          		break;
  		}
  	}
<<<<<<< HEAD
=======
  	public void handleReleased(KeyEvent e) {
  		switch (e.getCode()) {
  			case Z:  goNorth = false; break;
  			case S:  goSouth = false; break;
          	case Q:  goWest  = false; break;
          	case D:  goEast  = false; break;
          	case SPACE: jump = false;break;
          	default: break;
  		}
  	}
>>>>>>> 4a92afd3a33ea0cffa49d6057e63bacc9c86f521
      
    public void handle(char direction) {
        int dx = 0, dy = 0;

<<<<<<< HEAD
        if (direction=='N') dy -= 32;
        if (direction=='S') dy += 32;
        if (direction=='E') {
        	dx += 32;
        	imgVi.setImage(new Image("file:src/img/perso-right.png"));
        }
        if (direction=='W') {
        	dx -= 32;
        	imgVi.setImage(new Image("file:src/img/persoMod.png"));
=======
        if (direction=='N') dy -= 5;
        if (direction=='S') dy += 5;
        if (direction=='E') {
        	dx += 5;
        	imgVi.setImage(new Image("file:Hidden Hills/src/img/perso-right.png"));
        }
        if (direction=='W') {
        	dx -= 5;
        	imgVi.setImage(new Image("file:Hidden Hills/src/img/persoMod.png"));
>>>>>>> 4a92afd3a33ea0cffa49d6057e63bacc9c86f521
        }
        if (direction=='J') dy -= 20;
        
        p.move(dx, dy);
    }
      
<<<<<<< HEAD
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createMap();
		createPerso();   
	}
	public void createMap() {
		map = new Map();
		for(int j=0; j<map.getMapHeight()-1;j++) {
		        for(int i = 0; i < map.getMapWidth();i++) {
			            Image img = new Image(this.map.getBlock(i+(j*60)).getuRI());
						ImageView imgV=new ImageView (img);
						imgV.setFitHeight(32);
						imgV.setFitWidth(32);
						tilePaneMap.getChildren().add(imgV);
		        	
		        }
			}
	}
	public void createPerso() {
		  	imgVi = new ImageView ("file:src/img/persoMod.png");
		  	imgVi.translateXProperty().bind(this.p.xProperty());
			imgVi.translateYProperty().bind(this.p.yProperty());
			imgVi.setFocusTraversable(true);
			imgVi.setFitHeight(64);
			imgVi.setFitWidth(32);
			persoPane.getChildren().add(imgVi);
			persoPane.setOnKeyPressed(e -> handlePressed(e));
=======
    public void créationMap () {
    	for(int i = 0; i < this.map.getMap().size(); i++) {
            Image img = new Image (this.map.getBlock(i).getuRI());
			ImageView imgV = new ImageView (img);
			imgV.setFitHeight(32);
			imgV.setFitWidth(32);
			imgV.setTranslateX(0);
			imgV.setTranslateY(0);
			tilePaneMap.getChildren().add(imgV);
        }
    }
    
    public void déplacementPersonnage () {
    	imgVi = new ImageView ("file:Hidden Hills/src/img/persoMod.png");
		imgVi.translateXProperty().bind(this.p.xProperty());
		imgVi.translateYProperty().bind(this.p.yProperty());
		imgVi.setFocusTraversable(true);
		persoPane.getChildren().add(imgVi);
		persoPane.setOnKeyPressed(e -> handlePressed(e));
		persoPane.setOnKeyReleased(e -> handleReleased(e));
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		map = new Map();
		créationMap();
		déplacementPersonnage();
>>>>>>> 4a92afd3a33ea0cffa49d6057e63bacc9c86f521
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}