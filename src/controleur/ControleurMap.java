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
    
    final static int BLOC_LARGEUR = 60;
    final static int BLOC_HAUTEUR = 40;
    final static int TAILLE_BLOC = 32; //Les blocs sont carrés en 32 pixels

    @FXML
    private Pane mainPane;

    @FXML
    private TilePane tilePaneMap;

    @FXML
    private Pane persoPane;
    
    @FXML
    private ImageView imgVPerso;

    private Personnage p = new Personnage();
    
    private Rectangle rectangleColision = new Rectangle(32,64);
    
    private long lastUpdateTime;
	
    private Map map;
    
	private Timeline gameLoop;
	
	//private int temps;
	
    private AnimationTimer timer;
    
    private boolean south,east,west,north,jump;
    
    private int nbJump;
    
  	public void handlePressed(KeyEvent e) {
  		nbJump=0;
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
          		jump=true;
          		nbJump+=1;
          		handle('J');
          		break;
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
          	case SPACE: nbJump=0; 
          				jump = false; 
          				break;
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
        if (direction=='J') dy -= 32;
        
        if(!colision(dx, dy)) {
        	p.move(dx, dy);
        }
        else {
        	System.out.println("collision");
        }
    }

	public boolean colision(int newX, int newY) {
		//Regard angle gauche haut
		if(map.getBlock(calculationIndex((rectangleColision.getTranslateX()+newX),(rectangleColision.getTranslateY()+newY))).getCollision()) {
			return true;
		}
		//Regard angle gauche bas
		else if (map.getBlock(calculationIndex((rectangleColision.getTranslateX()+newX),(rectangleColision.getTranslateY()+newY)+32)).getCollision()) {
			return true;
		}
		//Regard angle droite haut
		else if (map.getBlock(calculationIndex((rectangleColision.getTranslateX()+newX)+64,(rectangleColision.getTranslateY()+newY))).getCollision()) {
			return true;
		}
		//Regard angle droite bas
		else if (map.getBlock(calculationIndex((rectangleColision.getTranslateX()+newX)+32,(rectangleColision.getTranslateY()+newY)+64)).getCollision()) {
			return true;
		}
		//return
		else {
			return false;
		}
	}
	
	//Dans la map, il y a 40 blocs de hauteur et 60 blocs de largeur
	public int calculationIndex(double x, double y) {
		int ind;
		ind = (int) (((int)(y/TAILLE_BLOC))*BLOC_LARGEUR+(x/TAILLE_BLOC));
		//Vérification indice par rapport aux x et y
		return ind;
	}
    
    public void handlerColision () {
    	rectangleColision.setFill(Color.BLACK);
    	rectangleColision.setOpacity(0.0);
		rectangleColision.translateXProperty().bind(this.p.xProperty());
		rectangleColision.translateYProperty().bind(this.p.yProperty());
		persoPane.getChildren().add(rectangleColision);
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
						if (north) dy -= 16;
						if (south) dy += 16;
						if (east) {
							dx += 16;
						    imgVPerso.setImage(new Image("file:src/img/perso-right.png"));
						}
						if (west) {
						    dx -= 16;
						    imgVPerso.setImage(new Image("file:src/img/persoMod.png"));
						}
						if (jump && nbJump==1) {
							dy -= 32;
						}
						if (!colision(dx, dy)) {
							p.move(dx, dy);
						}
					}
					lastUpdateTime=l;
				}
		};
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createMap();
		createPerso();
		initAnimation();
		handlerColision();
		gameLoop.play();
		timer.start();
	}

}
