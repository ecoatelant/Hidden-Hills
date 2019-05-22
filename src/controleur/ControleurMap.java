package controleur;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import modeles.Personnage;
import modeles.Map;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ControleurMap implements Initializable {

    private Map map = new Map();
    
    final static int BLOC_LARGEUR = 60;
    final static int BLOC_HAUTEUR = 40;
    final static int TAILLE_BLOC = 32; //Les blocs sont carrés
    
    @FXML
    private Pane mainPane;

    @FXML
    private TilePane tilePaneMap;

    @FXML
    private Pane persoPane;

    private Personnage p = new Personnage();
    
    private Rectangle aabb = new Rectangle(26,46);
  
    boolean running, goNorth, goSouth, goEast, goWest , jump;

    @FXML
    private ImageView imgVi;
    
    @FXML
    private ImageView imgV;

  	public void handlePressed(KeyEvent e) {
  		switch (e.getCode()) {
          	case Z:  
          		goNorth = true; 
          		handle('N');
          		break;
          	case S:  
          		goSouth = true;
          		handle('S');
          		break;
          	case Q:  
          		goWest  = true;         		
          		handle('W');
          		break;
          	case D:  
          		goEast  = true;
          		handle('E');
          		break;
          	case SPACE :
          		jump = true;
          		handle('J');
          	default:
          		break;
  		}
  	}
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
      
    public void handle(char direction) {
        int dx = 0, dy = 0;

        if (direction=='N') dy -= 6;
        if (direction=='S') dy += 6;
        if (direction=='E') {
        	dx += 6;
        	imgVi.setImage(new Image("file:src/img/perso-right.png"));
        }
        if (direction=='W') {
        	dx -= 8;
        	imgVi.setImage(new Image("file:src/img/persoMod.png"));
        }
        if (direction=='J') dy -= 20;
        
        if(!collision(dx, dy)) {
        	p.move(dx, dy);
        }
        else {
        	System.out.println("collision");
        }
        
    }
    
	public boolean collision(int newX, int newY) {
		//Regard angle gauche haut
		if(map.getBlock(calculIndice((aabb.getTranslateX()+newX),(aabb.getTranslateY()+newY))).getCollision()) {
			return true;
		}
		//Regard angle gauche bas
		else if (map.getBlock(calculIndice((aabb.getTranslateX()+newX),(aabb.getTranslateY()+newY)+35)).getCollision()) {
			return true;
		}
		//Regard angle droite haut
		else if (map.getBlock(calculIndice((aabb.getTranslateX()+newX)+26,(aabb.getTranslateY()+newY))).getCollision()) {
			return true;
		}
		//Regard angle droite bas
		else if (map.getBlock(calculIndice((aabb.getTranslateX()+newX)+26,(aabb.getTranslateY()+newY)+35)).getCollision()) {
			return true;
		}
		//return
		else {
			return false;
		}
	}
	
	//Dans la map, il y a 40 blocs de hauteur et 60 blocs de largeur
	public int calculIndice(double x, double y) {
		int ind;
		ind = (int) (((int)(y/TAILLE_BLOC))*BLOC_LARGEUR+(x/TAILLE_BLOC));
		
		//Vérification indice par rapport aux x et y
		System.out.println(x + " , " + y);
		System.out.println(ind);
		return ind;
	}
    
    public void créationMap () {
    	for(int i = 0; i < this.map.getMap().size(); i++) {
            Image img = new Image (this.map.getBlock(i).getuRI());
			imgV = new ImageView (img);
			imgV.setFitHeight(32);
			imgV.setFitWidth(32);
			imgV.setTranslateX(0);
			imgV.setTranslateY(0);
			tilePaneMap.getChildren().add(imgV);
        }
    }
    
    public void déplacementPersonnage () {
    	imgVi = new ImageView ("file:src/img/persoMod.png");
    	imgVi = new ImageView (new Image("file:Hidden Hills/src/img/persoMod.png"));
		imgVi.translateXProperty().bind(this.p.xProperty());
		imgVi.translateYProperty().bind(this.p.yProperty());
		imgVi.setFocusTraversable(true);
		persoPane.getChildren().add(imgVi);
		persoPane.setOnKeyPressed(e -> handlePressed(e));
		persoPane.setOnKeyReleased(e -> handleReleased(e));
    }
    
    public void gestionCollision () {
    	aabb.setFill(Color.BLACK);
    	aabb.setOpacity(0.2);
		aabb.translateXProperty().bind(this.p.xProperty());
		aabb.translateYProperty().bind(this.p.yProperty());
		persoPane.getChildren().add(aabb);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		créationMap();
		déplacementPersonnage();
		gestionCollision();
	}
}