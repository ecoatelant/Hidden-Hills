package controleur;

//Importation des classes vue modèle
import modeles.Personnage;
import modeles.Block;
import modeles.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
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
    private ImageView imgVi;
    
    @FXML
    private Pane paneVueJoueur;
    
    //Déclaration du PP
    private Personnage p = new Personnage();
    
    private long lastUpdateTime;
	
    private Map map;
    
	private Timeline gameLoop;
	
    private AnimationTimer timer;
    
    private boolean south,east,west,north;
    
    private int temps;

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
          	default:
          		break;
  		}
  	}
  	public void handleRelease(KeyEvent e) {
  		switch (e.getCode()) {
      	case Z:  
      		north=false;
      		break;
      	case S:  
      		south=false;
      		break;
      	case Q:           		
      		west=false;
      		break;
      	case D:  
      		east=false;
      		break;
      	default:
      		break;
		}
  	}

	public void createMap() {
		map = new Map();
		for(int j=0; j<map.getMapHeight()-1;j++) {
		        for(int i = 0; i < map.getMapWidth();i++) {
			            Image img = new Image(this.map.getBlock(i+(j*60)).getuRI());
						ImageView imgV=new ImageView(img);
						imgV.setFitHeight(32);
						imgV.setFitWidth(32);
						tilePaneMap.getChildren().add(imgV);
		        }
		       
		}
	}
	
	public void affichageMap() {
		mainPane.setTranslateX(-p.getX()+640);
		mainPane.setTranslateY(-p.getY());
	}
	
/*	public boolean bordureMap() {
		if(mainPane.getTranslateX()==0) {
			return true;
		}
	}*/
	
	public void createPerso() {
		  	imgVi = new ImageView ("file:src/img/persoMod.png");
		  	imgVi.translateXProperty().bind(this.p.xProperty());
			imgVi.translateYProperty().bind(this.p.yProperty());
			imgVi.setFocusTraversable(true);
			imgVi.setFitHeight(64);
			imgVi.setFitWidth(32);
			persoPane.getChildren().add(imgVi);
			persoPane.setMouseTransparent(true);
			persoPane.setFocusTraversable(true);
			persoPane.setOnKeyPressed(e -> handlePressed(e));
			persoPane.setOnKeyReleased(e -> handleRelease(e));
			
	}

	public void breakBlock() {
		tilePaneMap.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 int x=((int)(event.getX())/32);
		         int y=((int)(event.getY())/32);
		         if (x<p.xProperty().get()/32-5 || x>p.xProperty().get()/32+5 || y<p.yProperty().get()/32-5 || y>p.yProperty().get()/32+6) {
		         }
		         else
		        	 map.setBlock(y*60+x,new Block("-1",y*60+x));
		     }
		});
		 map.getMap().addListener(new ListChangeListener<Block>(){
	        	@Override
				public void onChanged(ListChangeListener.Change<? extends Block> c){
				        while (c.next()) {	
				        	if (c.wasReplaced()){
				        		int indice=c.getFrom();
					        		tilePaneMap.getChildren().set(indice,new ImageView(new Image(map.getBlock(indice).getuRI())));
				        		}
				        	}
	                    }
	        	});
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
						imgVi.setImage(new Image("file:src/img/perso-right.png"));
					}
					if (west) {
						dx -= 8;
						imgVi.setImage(new Image("file:src/img/persoMod.png"));
					}
			
					if (!p.colision(dx, dy)) {
						p.move(dx, dy);
					}
				}
				affichageMap();
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
		gameLoop.play();
		timer.start();
	}

}


