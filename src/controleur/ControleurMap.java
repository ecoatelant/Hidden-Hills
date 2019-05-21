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
        	imgVi.setImage(new Image("file:src/img/perso-right.png"));
        }
        if (direction=='W') {
        	dx -= 5;
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
		return map.getBlock(calculIndice((p.getX()+newX),(p.getY()+newY))).getCollision();
	}
	
	//Dans la map, il y a 40 blocs de hauteur et 60 blocs de largeur
	public int calculIndice(double x, double y) {
		int ind;
		if(y == 0) {
			ind= (int) x;
		}
		else {
			ind = (int) ((y*59)+x+1);
		}
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
<<<<<<< HEAD
    	imgVi = new ImageView ("file:src/img/persoMod.png");
=======
    	imgVi = new ImageView (new Image("file:Hidden Hills/src/img/persoMod.png"));
>>>>>>> 234c68f164ea75a6d5904802a58534a6df714892
		imgVi.translateXProperty().bind(this.p.xProperty());
		imgVi.translateYProperty().bind(this.p.yProperty());
		imgVi.setFocusTraversable(true);
		persoPane.getChildren().add(imgVi);
		persoPane.setOnKeyPressed(e -> handlePressed(e));
		persoPane.setOnKeyReleased(e -> handleReleased(e));
    }
    
    public void gestionCollision () {
    	Rectangle test = new Rectangle(26,46);
    	test.setFill(Color.BLACK);
		test.translateXProperty().bind(this.p.xProperty());
		test.translateYProperty().bind(this.p.yProperty());
		persoPane.getChildren().add(test);
		for (int i =0;i<this.map.getMap().size();i++) {
			if (test.getBoundsInParent().intersects(tilePaneMap.getChildren().get(i).getBoundsInParent())) {
				System.out.println("tg");
			}
		}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		créationMap();
		déplacementPersonnage();
		gestionCollision();
	}
}