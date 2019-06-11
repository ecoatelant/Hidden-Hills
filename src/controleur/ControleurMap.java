package controleur;

//Importation des classes vue modèle
import modeles.Personnage;
import modeles.Block;
import modeles.BlockItem;
import modeles.Craft;
import modeles.Inventaire;
import modeles.Item;
import modeles.Map;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
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
    
    @FXML
    private GridPane paneCraft;
    
    private long lastUpdateTime;
    
    private Block airBlock;

    private Map map;

	private Timeline gameLoop;
	
    private AnimationTimer timer;
    
    private boolean south,east,west,north;
    
    private int compteurSaut = 0;
    
    private Inventaire inventory;
    
    private static int VITESSE_SAUT = -16;
    
    private Craft crafting;
    
  //Déclaration du PP
    private Personnage p;

    //Déplacement en ZQSD
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
          	case SPACE:
          			compteurSaut++;
              		System.out.println(compteurSaut);
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
          	case SPACE:	if (p.colision((int)p.getX(), 16)) compteurSaut = 0;
          				else while (!p.colision((int)p.getX(),16) && compteurSaut>0) compteurSaut--;
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
	
	private void createCraft() {
		this.crafting = new Craft();
		int column = 0;
		int row = 0;
		for(int i = 0;i<crafting.sizeTableCraft();i++) {
			if(column == 3) {
				row++;
				column = 0;
			}
			Image img = new Image(this.crafting.getItem(i).getItemURI());
			ImageView imgV = new ImageView(img);
			imgV.setFitHeight(80);
			imgV.setFitWidth(80);
			//paneCraft.getChildren().add(imgV);
			paneCraft.add(imgV, column, row);
			column++;
		}
		paneCraft.setFocusTraversable(true);
	}
	
	public void crafting() {
		paneCraft.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
		     public void handle(MouseEvent click) {
				Node clickNode = click.getPickResult().getIntersectedNode();
					Integer colIndex = GridPane.getColumnIndex(clickNode);
			        Integer rowIndex = GridPane.getRowIndex(clickNode);
					Item itemSelect = crafting.getItem(rowIndex*3+colIndex);
					System.out.println("Inventaire:");
					for(int i = 0; i < inventory.getInventory().size(); i++) {
						System.out.println(inventory.getInventory().get(i).getId());
					}
					crafting.crafting(inventory.getInventory(),itemSelect);
					System.out.println(itemSelect);
		     }
		  });
	}
	
	public boolean bordWidth(int x) {
		return p.getX()+(map.getMapWidthPX()-x)<=map.getMapWidthPX() && p.getX()-x>=0;
	}
	
	public boolean bordHeight(int y) {
		//TO-DO: trouver à quoi correspond 378 -> map.getMapHeightPX()-p.getY() au départ? NON
		return p.getY()+378<=map.getMapHeightPX()
				&& p.getY()-y>=0;
	}
	
	public void affichageMap() {
		final int placementPersoX = 640;
		final int placementPersoY = 700;
		//Gestion des bordures en largeur
		if(bordWidth(placementPersoX)) {
			mainPane.setTranslateX(-p.getX()+placementPersoX);
		}
		//Gestion des bordures en hauteur
		//TO-DO: trouver à quoi correspond 378 -> map.getMapHeightPX()-p.getY() au départ? NON

		if(p.getY()+378<=map.getMapHeightPX() && p.getY()-placementPersoY>=0){

		if(bordHeight(placementPersoY)){
			mainPane.setTranslateY(-p.getY()+placementPersoY);
		}
		}
	}
	
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
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		timer= new AnimationTimer() {

				@SuppressWarnings("deprecation")
				@Override
				public void handle(long l) {
					if(lastUpdateTime>0) {
						int dx = 0, dy = 0;
						if (north) dy -= 0;
						if (south) dy += 8;
						if (east) {
							dx += 8;
							if (!imgVi.getImage().impl_getUrl().equals("file:src/img/perso-right.png")) {
								imgVi.setImage(new Image("file:src/img/perso-right.png"));
						    }
						    
						}

						if (west) {
						    dx -= 8;
						    if (!imgVi.getImage().impl_getUrl().equals("file:src/img/persoMod.png")) {
						    	imgVi.setImage(new Image("file:src/img/persoMod.png"));
						    }
						}

						//Système de saut
						if (compteurSaut==1) {
							int timer = 2;
								p.move(0, VITESSE_SAUT);
								timer--;
							if(timer <= 0) {
								timer = 2;
								compteurSaut=0;
							}
						}
						
						//Pour gérer les collisions
						handlerColision(dx, dy);
						
						//Pour gérer la gravité
						handlerGravity(dx);
						
						//Pour gérer le scrolling
						affichageMap();
					} 
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
		createCraft();
		initAnimation();
		airBlock=map.getBlock(0);
		breakBlock();
		crafting();
		gameLoop.play();
		timer.start();
		paneVueJoueur.setMouseTransparent(true);
		paneVueJoueur.setFocusTraversable(true);
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
