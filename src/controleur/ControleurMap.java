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
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ControleurMap implements Initializable {
    
	//Déclaration des tailles sur la map
    final static int NBR_BLOC_LARGEUR = 60;
    final static int NBR_BLOC_HAUTEUR = 40;
    final static int TAILLE_BLOC = 32; //Les blocs sont carrés en 32 pixels
    final static int PERSO_LARGEUR = 32;
    final static int PERSO_HAUTEUR = 64;

    @FXML
    private Pane mainPane;

    @FXML
    private TilePane tilePaneMap;

    @FXML
    private Pane persoPane;
    
    @FXML
    private ImageView imgVPerso;

    //Déclaration du PP et de son aabb
    private Personnage p = new Personnage();
    private Rectangle aabb = new Rectangle(32,64);
    
    private long lastUpdateTime;
	
    private Map map;
    
	private Timeline gameLoop;
	
	//private int temps;
	
    private AnimationTimer timer;
    
    private boolean south,east,west,north,jump;
    
  	public void handlePressed(KeyEvent e) {
  		switch (e.getCode()) {
          	case Z:  
          		north=true;
          		break;
          	case S:  
          		south=true;
          		break;
          	case Q:           		
          		west=true;
          		break;
          	case D:  
          		east=true;
          		break;
          	case SPACE :
          		jump = true;
          	default:
          		break;
  		}
  	}

  	public void handleReleased(KeyEvent e) {
  		switch (e.getCode()) {
  			case Z:  north = false; break;
  			case S:  south = false; break;
          	case Q:  west  = false; break;
          	case D:  east  = false; break;
          	case SPACE: break;
          	default: break;
  		}
  	}
    public void handle(char direction) {
        int dx = 0, dy = 0;
        if (direction=='N') dy -= 8;
        if (direction=='S') dy += 8;
        if (direction=='E') {
        	dx += 8;
        	imgVPerso.setImage(new Image("file:src/img/perso-right.png"));
        }
        if (direction=='W') {
        	dx -= 8;
        	imgVPerso.setImage(new Image("file:src/img/persoMod.png"));
        }
        if (direction=='J') dy -= 20;
        
        if(!colision(dx, dy)) {
        	p.move(dx, dy);
        }
        else {
        	System.out.println("collision");
        }
        
    }

	public boolean colision(int newX, int newY) {
		//Regard angle gauche haut
		if(map.getBlock(calculationIndex((aabb.getTranslateX()+newX),(aabb.getTranslateY()+newY))).getCollision()) {
			return true;
		}
		//Regard angle gauche bas
		else if (map.getBlock(calculationIndex((aabb.getTranslateX()+newX),(aabb.getTranslateY()+newY)+56)).getCollision()) {
			return true;
		}
		//Regard angle droit haut
		else if (map.getBlock(calculationIndex((aabb.getTranslateX()+newX)+24,(aabb.getTranslateY()+newY))).getCollision()) {
			return true;
		}
		//Regard angle droit bas
		else if (map.getBlock(calculationIndex((aabb.getTranslateX()+newX)+24,(aabb.getTranslateY()+newY)+56)).getCollision()) {
			return true;
		}
		//Return
		else {
			return false;
		}
	}
	
	public int calculationIndex(double x, double y) {
		return (int) (((int)(y/TAILLE_BLOC))*NBR_BLOC_LARGEUR+(x/TAILLE_BLOC));
	}
    
    public void handlerColision () {
    	aabb.setFill(Color.BLACK);
    	aabb.setOpacity(0.0);
		aabb.translateXProperty().bind(this.p.xProperty());
		aabb.translateYProperty().bind(this.p.yProperty());
		persoPane.getChildren().add(aabb);
    }
     
	public void createMap() {
		map = new Map();
		for(int j=0; j<map.getMapHeight()-1;j++) {
		        for(int i = 0; i < map.getMapWidth();i++) {
			       Image img = new Image(this.map.getBlock(i+(j*60)).getuRI());
			       ImageView imgV = new ImageView (img);
			       imgV.setFitHeight(32);
			       imgV.setFitWidth(32);
			       tilePaneMap.getChildren().add(imgV);
		        }
			}
	}
	public void createPerso() {
		imgVPerso = new ImageView ("file:src/img/persoMod.png");
		imgVPerso.translateXProperty().bind(this.p.xProperty());
		imgVPerso.translateYProperty().bind(this.p.yProperty());
		imgVPerso.setFocusTraversable(true);
		imgVPerso.setFitHeight(64);
		imgVPerso.setFitWidth(32);
		persoPane.getChildren().add(imgVPerso);
		persoPane.setOnKeyPressed(e -> handlePressed(e));
		persoPane.setOnKeyReleased(e -> handleReleased(e));
	}
	
	private void initAnimation() {
		lastUpdateTime=1;
		gameLoop = new Timeline();
		//temps=0;
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		timer= new AnimationTimer() {
			
			@Override
			public void handle(long l) {
				if(lastUpdateTime>0) {
					int dx = 0, dy = 0;
					if (north) dy -= 8;
					if (south) dy += 8;
					if (east) {
						dx += 8;
						imgVPerso.setImage(new Image("file:src/img/perso-right.png"));
					}
					if (west) {
						dx -= 8;
						imgVPerso.setImage(new Image("file:src/img/persoMod.png"));
					}
			
					if (!colision(dx, dy)) {
						p.move(dx, dy);
					}
				}
			}
		};
	}
	
	public void handlerGravity() {
		while (!colision(0,8)) {
			this.p.move(0,8);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createMap();
		createPerso();   
		initAnimation();
		//handlerGravity();
		gameLoop.play();
		timer.start();
	}

}
