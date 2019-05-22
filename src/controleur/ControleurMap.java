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
	
	private int temps;
	
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
	
	public void affichageMap() {
		//TO-DO
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
	
    public void handlerColision () {
    	aabb.setFill(Color.BLACK);
    	aabb.setOpacity(0.3);
		aabb.translateXProperty().bind(this.p.xProperty());
		aabb.translateYProperty().bind(this.p.yProperty());
		persoPane.getChildren().add(aabb);
    }
	
	private void initAnimation() {
		lastUpdateTime=1;
		gameLoop = new Timeline();
		temps=0;
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
			
					if (!p.colision(dx, dy)) {
						p.move(dx, dy);
					}
				}
			}
		};
	}
	
	public void handlerGravity() {
		while (!p.colision(0,8)) {
			this.p.move(0,8);
		}
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
