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

    private Map map;
    
    @FXML
    private Pane mainPane;

    @FXML
    private TilePane tilePaneMap;

    @FXML
    private Pane persoPane;

    private Personnage p = new Personnage();
  
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

        if (direction=='N') dy -= 5;
        if (direction=='S') dy += 5;
        if (direction=='E') {
        	dx += 5;
        	imgVi.setImage(new Image("file:Hidden Hills/src/img/perso-right.png"));
        }
        if (direction=='W') {
        	dx -= 5;
        	imgVi.setImage(new Image("file:Hidden Hills/src/img/persoMod.png"));
        }
        if (direction=='J') dy -= 20;
        
        p.move(dx, dy);
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
    	imgVi = new ImageView (new Image("file:Hidden Hills/src/img/persoMod.png"));
		imgVi.translateXProperty().bind(this.p.xProperty());
		imgVi.translateYProperty().bind(this.p.yProperty());
		imgVi.setFocusTraversable(true);
		persoPane.getChildren().add(imgVi);
		persoPane.setOnKeyPressed(e -> handlePressed(e));
		persoPane.setOnKeyReleased(e -> handleReleased(e));
    }
    
    public void gestionCollision () {
    	Rectangle test = new Rectangle(26,46);
    	Rectangle test2 = new Rectangle (32,32);
    	test2.setFill(Color.RED);
    	test.setFill(Color.BLACK);
    	persoPane.getChildren().addAll(test,test2);
		test.translateXProperty().bind(this.p.xProperty());
		test.translateYProperty().bind(this.p.yProperty());
		if ()) {
			System.out.println("colision");
		}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		map = new Map();
		créationMap();
		déplacementPersonnage();
		gestionCollision();
	}
}