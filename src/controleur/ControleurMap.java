package controleur;

//Importation des classes vue modèle
import modeles.Personnage;
import modeles.Block;
import modeles.BlockItem;
import modeles.Inventaire;
import modeles.Item;
import modeles.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.net.URL;
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
    @FXML
    private TilePane inventoryBar;
    @FXML
    private Pane scrollingPane;
    @FXML
    private Pane fixedPane;
    
    private long lastUpdateTime;
    
    private Block airBlock;

    private Map map;

	private Timeline gameLoop;

	@SuppressWarnings("unused")
	private int temps;
	
    private AnimationTimer timer;
    
    private boolean south,east,west,north,jump;
    
    private Inventaire inventory;
    
  //Déclaration du PP
    private Personnage p;

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
  			case Z:  north = false; break;
  			case S:  south = false; break;
          	case Q:  west  = false; break;
          	case D:  east  = false; break;
          	case SPACE:	if(!jump) {
          					jump = true;
          	}
          	break;
          	default: 	break;
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
		scrollingPane.setTranslateX(-p.getX()+640);
		scrollingPane.setTranslateY(-p.getY()+700);
	}
	
/*	public boolean bordureMap() {
		if(mainPane.getTranslateX()==0) {
			return true;
		}
	}*/
	
	public void createPerso() {
			this.inventory=new Inventaire();
			p = new Personnage(this.map, this.inventory);
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
		        	 System.out.println("Trop loin pour placer/casser");
		         }
		         else {         
			         if (event.getButton()==MouseButton.PRIMARY) {//Casser blocs
			        	 if(map.getBlock(y*60+x).getId().equals(airBlock.getId())) {}//Si on essaye de casser la ou il y a un bloc d'air
			        	 Block workBlock=map.getBlock(y*60+x);//bloc qui a été retiré
			        	 map.setBlock(y*60+x,airBlock);
			        	 int i=0;
			        	 boolean done=false;
			        	 if(inventory.getInventory().size()>1) {
				        	 while(done==false && i<inventory.getInventory().size()) {//cas ou le bloc est présent dans l'inventaire, on augmente son compteur
				         			if(inventory.getInventory().get(i).getId().equals(workBlock.getId())) {
						         		inventory.getInventory().get(i).addUse();
						         		done=true;
				         			}
				         			i++;
					         } 
			        	 }
		         		 if(done==false){//cas ou le bloc n'est pas présent, on ajoute le bloc à l'inventaire
		         			Item adBlock=new BlockItem(workBlock.getId());
		         			inventory.add(adBlock);
		         		}
		         		 
			         } 
			         else if(event.getButton()==MouseButton.SECONDARY) {//Poser bloc en main
			        	 if(map.getBlock(y*60+x).getIndice()==0) {}
			        	 else if(map.getBlock(y*60+x).getId().equals(airBlock.getId())) {
			        		 if(inventory.getInventory().size()<=1) {
			        		 }//inventaire vide
			        		 else {
					        	 Item item=inventory.getItemInHand();
					        	 item.useItem(y*60+x,map);
					        	 if (inventory.getItemInHand().used()) //Dernier bloc utilisé -> Main vide
									inventory.emptyHand();
			        		 }
			        	 }
			         }            
			     }	
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
	public void handlerInventory() {
		inventory.getInventory().addListener(new ListChangeListener< Item>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends Item> c) {
					while(c.next()) {
						int indice=c.getFrom();
						inventoryBar.getChildren().add(indice-1, new ImageView(new Image(inventory.getInventory().get(indice).getItemURI())));
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
						if (north) dy -= 0;
						if (south) dy += 8;
						if (east) {
							dx += 8;
						    imgVi.setImage(new Image("file:src/img/perso-right.png"));
						}
						
						if (west) {
						    dx -= 8;
						    imgVi.setImage(new Image("file:src/img/persoMod.png"));
						}
						int hauteurSaut = 128;
						//Système de saut
						if (jump) {
							p.move(0, -128);
							jump=false;
						}
						
						//Pour gérer les colisions
						handlerColision(dx, dy);
						//Pour gérer la gravité
						handlerGravity(dx);
					}
					affichageMap();
				}
		};
	}
	
	private void handlerColision (int dx , int dy) {
		if (!p.colision(dx, dy)) {
			p.move(dx, dy);
		}
	}
	
	private void handlerGravity (int dx) {
		if (!p.colision(dx, 8)) {
			p.move(dx, 8);
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		createMap();
		createPerso();   
		initAnimation();
		airBlock=map.getBlock(0);
		breakBlock();
		gameLoop.play();
		timer.start();
		inventoryBar.setMouseTransparent(false);
		handlerInventory();
		paneVueJoueur.setMouseTransparent(true);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		        map.sauvegarderMap();
		    }
		}));
	}

}
