package controleur;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
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
        if (direction=='E') dx += 5;
        if (direction=='W') dx -= 5;
        if (direction=='J') dy -= 20;
        
        p.move(dx, dy);
    }
      
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		map = new Map();
        for(int i = 0; i < this.map.getMap().size(); i++) {
            Image img = new Image (this.map.getBlock(i).getuRI());
			ImageView imgV = new ImageView (img);
			imgV.setFitHeight(32);
			imgV.setFitWidth(32);
			imgV.setTranslateX(0);
			imgV.setTranslateY(-500);
			tilePaneMap.getChildren().add(imgV);
        }
        imgVi = new ImageView ("file:Hidden Hills/src/img/persoMod.png");
		imgVi.translateXProperty().bind(this.p.xProperty());
		imgVi.translateYProperty().bind(this.p.yProperty());
		imgVi.setFocusTraversable(true);
		persoPane.getChildren().add(imgVi);
		persoPane.setOnKeyPressed(e -> handlePressed(e));
		persoPane.setOnKeyReleased(e -> handleReleased(e));
		
	}
}