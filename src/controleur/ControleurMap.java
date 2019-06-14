package controleur;

//Importation des classes vue modèle
import modeles.Joueur;
import modeles.Pickaxe;
import modeles.Block;
import modeles.ChauveSouris;
import modeles.Craft;
import modeles.Ennemis;
import modeles.Inventaire;
import modeles.Item;
import modeles.Map;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    
    @FXML
    private Label craftLabel;

    private Map map;

	private Timeline gameLoop;
    
    private boolean east,west,jump;
    
    private Inventaire inventory;
    
    private Ennemis e;
    
    private Label lab;
    private int frame;
    private Craft crafting;
    private MediaPlayer player;
    private int fightCompteur=0;
    private ImageView imgEnnemi,imgPerso;
    private Label pvEnnemi, pvPerso;
    
    //private Map <(String perso), (Image img)> dictionnaire = new Map <> ();
    
    
  //Déclaration du PP
    private Joueur p;

    //Déplacement en ZQSD
  	public void handlePressed(KeyEvent e) {

  		switch (e.getCode()) {
          	case Q:           		
          		west=true;
          		break;
          	case D:  
          		east=true;
          		break;
          	case SPACE:
          		if(p.onGround())
          		jump=true;
          	default:
          		break;
  		}
  	}
  	
  	public void handleRelease(KeyEvent e) {
  		switch (e.getCode()) {
          	case Q:  west  = false; break;
          	case D:  east  = false; break;
          	default: 	break;
		}
  	}

	public void createMap() {
		map = new Map();
		mainPane.setPrefSize(map.getMapWidthPX(), map.getMapHeightPX());
		scrollingPane.setPrefSize(map.getMapWidthPX(), map.getMapHeightPX());
		tilePaneMap.setPrefSize(map.getMapWidthPX(), map.getMapHeightPX());
		for(int j=0; j<map.getMapHeight()-1;j++) {
		    for(int i = 0; i < map.getMapWidth();i++) {
			    Image img = new Image(this.map.getBlock(i+(j*map.getMapWidth())).getuRI());
			    ImageView imgV=new ImageView(img);
				imgV.setFitHeight(32);
				imgV.setFitWidth(32);
				tilePaneMap.getChildren().add(imgV);
		    }
		       
		}
	}
	
	public boolean bordWidth(int x) {
		return p.getX()+(map.getMapWidthPX()-x)<=map.getMapWidthPX() && p.getX()-x>=0;
	}
	
	public boolean bordHeight(int y) {
		//TO-DO: trouver à quoi correspond 378 -> map.getMapHeightPX()-p.getY() au départ? NON
		return p.getY()+378<=map.getMapHeightPX() && p.getY()-y>=0;
	}
	
	public void displayMap() {
		final int placementPersoX = 640;
		final int placementPersoY = 700;
		//Gestion des bordures en largeur
		//if(bordWidth(placementPersoX)) {
			scrollingPane.setTranslateX(-p.getX()+placementPersoX);
		//}
		//Gestion des bordures en hauteur
		//TO-DO: trouver à quoi correspond 378 -> map.getMapHeightPX()-p.getY() au départ? NON

		//if(bordHeight(placementPersoY)){
			scrollingPane.setTranslateY(-p.getY()+placementPersoY);
		//}
	}
	
	public void createPerso() {
		this.inventory=new Inventaire();
		p = new Joueur(this.map, this.inventory);	
		addInventoryListener();
		p.getInventory().add(new Pickaxe());
		pvPerso = new Label();
		pvPerso.textProperty().bind(p.pvProperty().asString());
		pvPerso.translateXProperty().bind(p.xProperty().add(3));
		pvPerso.translateYProperty().bind(p.yProperty().subtract(20));
	  	imgPerso = new ImageView (p.getImgGauche());
	  	imgPerso.translateXProperty().bind(this.p.xProperty());
		imgPerso.translateYProperty().bind(this.p.yProperty());
		p.setX(100);
		p.setY(600);
		imgPerso.setFocusTraversable(true);
		imgPerso.setFitHeight(64);
		imgPerso.setFitWidth(32);
		persoPane.getChildren().add(imgPerso);
		persoPane.getChildren().add(pvPerso);
		persoPane.setMouseTransparent(true);
		persoPane.setFocusTraversable(true);
		persoPane.setOnKeyPressed(e -> handlePressed(e));
		persoPane.setOnKeyReleased(e -> handleRelease(e));
	}	
	
	public void createEnnemi() {
		e = new ChauveSouris(map);
		imgEnnemi = new ImageView (new Image("file:src/img/Bat.gif"));
		pvEnnemi = new Label();
		pvEnnemi.textProperty().bind(e.pvProperty().asString());
		pvEnnemi.translateXProperty().bind(e.xProperty().add(10));
		pvEnnemi.translateYProperty().bind(e.yProperty().subtract(5));
		e.setX(900);
		e.setY(350);
		imgEnnemi.setFocusTraversable(true);
		imgEnnemi.setFitHeight(48);
		imgEnnemi.setFitWidth(48);
		imgEnnemi.translateXProperty().bind(e.xProperty());
		imgEnnemi.translateYProperty().bind(e.yProperty());
		persoPane.getChildren().add(imgEnnemi);
		persoPane.getChildren().add(pvEnnemi);
	}
	

	public void breakBlock() {
		tilePaneMap.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {// à mettre dans personnage
		     @Override
		     public void handle(MouseEvent event) {
                 int x=((int)(event.getX())/32); 
		         int y=((int)(event.getY())/32);
		         if (event.getButton()==MouseButton.PRIMARY) {//Casser blocs
		        	 	p.casserBlock(x, y);
	    		 }
			     if(event.getButton()==MouseButton.SECONDARY) {//Poser bloc en main
			    	 if(!p.getInventory().isHandEmpty())
			    	 p.placerBlock(x,y);
			     }	
	         }
	      });	
	}
	private void createCraft() {
		crafting = new Craft();
		int column = 0;
		int row = 0;
		for(int j = 0;j<crafting.sizeTableCraft();j++) {
			if(column == 3) {
				row++;
				column = 0;
			}
			Image img;
			ImageView imgV;
			try{
				
				img = new Image(this.crafting.getItem(j).getItemURICraft());
				imgV = new ImageView(img);
				imgV.setFitHeight(64);
				imgV.setFitWidth(64);
				paneCraft.add(imgV, column, row);
				column++;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		paneCraft.setFocusTraversable(true);
	}
	
	
	public void crafting() {
		paneCraft.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
		     public void handle(MouseEvent click) {
				Node clickNode = click.getPickResult().getIntersectedNode();
				Item itemSelect=null;
				Integer colIndex = GridPane.getColumnIndex(clickNode);
			    Integer rowIndex = GridPane.getRowIndex(clickNode);
				try{itemSelect = crafting.getItem(rowIndex*3+colIndex);
					}catch(Exception e) {}
				if(!crafting.ressourcesPresentes(inventory.getInventoryList(), itemSelect)) {
					craftLabel.setText("Ressources nécessaires: "+itemSelect.ressourcesNeededToString());
				}
				if(crafting.ressourcesPresentes(inventory.getInventoryList(), itemSelect)) {
				crafting.crafting(inventory.getInventoryList(),itemSelect);
				craftLabel.setText("");
				}
		     }
		  });
	}
	public void addInventoryListener() {
		p.getInventory().getInventoryList().addListener(new ListChangeListener<Item>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends Item> c) {
					while(c.next()) { 
							inventoryBar.getChildren().clear();
							for(Item i:p.getInventory().getInventoryList()) {
								if(i.getId()=="-1") {}
								else {
									Pane itemPane=new Pane();
									lab=new Label();
									lab.textProperty().bind(Bindings.convert(i.nbProperty()));          
									lab.setLayoutX(20);
									lab.setLayoutY(20);
									Image img=new Image(i.getItemURI());
									ImageView imgV=new ImageView(img);
									itemPane.getChildren().add(imgV);
									itemPane.getChildren().add(lab);
									inventoryBar.getChildren().add(itemPane);
								}
							}
						}
					}
			});
		}
	
	public void chooseItem() {
		inventoryBar.addEventFilter(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 int x=((int)(event.getX())/32); 
		         if(x!=0)
		         p.getInventory().swapItem(x);
			}
		});
	}
	
	public void addMapListener() {
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
	public void fights () {
		if (e.possibiliteAttaque(p) && !e.mort()) {
			e.attaque(p);
			if (p.mort()) {
				persoPane.setDisable(true);
				persoPane.toBack();
				persoPane.getChildren().remove(imgPerso);
				Button quitter = new Button ();
				Button recommencer = new Button ();
				recommencer.setText("Nouvelle Aventure");
				recommencer.setTranslateX(585);
				recommencer.setTranslateY(840);
				quitter.setText("Quitter");
				quitter.setTranslateX(620);
				quitter.setTranslateY(880);
				mainPane.getChildren().add(quitter);
				mainPane.getChildren().add(recommencer);
				quitter.setOnAction(e -> quitter(e));
				recommencer.setOnAction(ev -> recommencer(ev));
			}
		}
		if (p.possibiliteAttaque(e) && !p.mort()) {
			p.attaque(e);
			if (e.mort()) {
				persoPane.getChildren().remove(imgEnnemi);
				persoPane.getChildren().remove(pvEnnemi);
			}
		}
	}
	
	public void quitter (ActionEvent e) {
		System.exit(0);
	}
	
	public void recommencer (ActionEvent ev) {
		//Cela réouvre une nouvelle fenêtre
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Hidden Hills");
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../vue/Sample.fxml"));
			Scene scene = new Scene(root,1920,1280);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	private void initAnimationPerso() {
		Image left=new Image(p.getImgGauche());
		Image right=new Image(p.getImgDroite());
		int jumpHeight=-160;
		frame=0;
		gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.017), (ev ->{
				int dx = 0, dy = 0;
				if (east) {
					dx += 8;
				    if (!imgPerso.getImage().equals(right)) {
				    	 imgPerso.setImage(right);
				    }
				   
				}
				if (west) {
				    dx -= 8;
					if (!imgPerso.getImage().equals(left)) {
						 imgPerso.setImage(left);
				    }
				}
				if(!jump)
					frame=0;
				if(jump) {
					if(frame<10) {
						frame++;
						if(!p.plafond(jumpHeight*0.3/10)){
						p.move(0,(jumpHeight*0.3/10));
						}
						else
							frame=40;
					}
					else if(frame<20) {
						frame++;
						if(!p.plafond(jumpHeight*0.5/10)){
						p.move(0,(jumpHeight*0.5)/10);
						}
						else
							frame=40;
					}
					else if(frame<30) {
						frame++;
						if(!p.plafond(jumpHeight*0.2/10)){
						p.move(0,(jumpHeight*0.2)/10);
						}
						else 
							frame=40;
					}
				}
				if(frame<40 && frame >=30) {
					frame++;
					if(!p.onGround())
					p.move(0,(1));
					
				}
				if(frame<50 && frame >=40) {
					frame++;
					if(!p.onGround())
					p.move(0,(3));
				}
				if(frame<60 && frame >=50) {
					frame++;
					if(!p.onGround())
					p.move(0,(6));
				}
				if(frame>=60)
					jump=false;
				//Système de saut
				
				//Pour gérer les collisions
			handlercollision(dx, dy);
				//Pour gérer la gravité
			handlerGravity(dx);		
			displayMap();
			fightCompteur++;
			if (fightCompteur == 60) {
				fights();
				fightCompteur=0;
			}
			
			if (e.detectionPersonnageDroite(p)) {
				if (e.getX() != p.getX()) {
					e.move(4, 0);
				}
			}
			else {
				if (e.detectionPersonnageGauche(p)) {
					if (e.getX() != p.getX()) {
						e.move(-4, 0);
					}
				}
				}
			
			if (e.detectionPersonnageBas(p)) {
				if (e.getY() != p.getY()) {
					e.move(0, 4);
				}
			}
			
			if (e.detectionPersonnageHaut(p)) {
				if (e.getY() != p.getY()) {
					e.move(0, -4);
				}
			}
		}));
		gameLoop.getKeyFrames().add(kf);
		}
	
	private void handlercollision (int dx , int dy) {
		if (!p.collision(dx, dy)) {
			p.move(dx, dy);
		}
	}
	
	private void handlerGravity (int dx) {
		if (!p.onGround() && !jump) {
			p.move(0, 8);
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		createMap();
		createPerso();
		chooseItem();
		createEnnemi();
		initAnimationPerso();
		breakBlock();
		addMapListener();
		createCraft();
		crafting();
		gameLoop.play();
		fixedPane.toFront();
		fixedPane.setPickOnBounds(false);
		fixedPane.setFocusTraversable(true);
		paneVueJoueur.setMouseTransparent(true);
		paneVueJoueur.setFocusTraversable(true);	
		inventoryBar.setPickOnBounds(true);
		paneCraft.setPickOnBounds(true);
		craftLabel.setFocusTraversable(true);
		craftLabel.setMouseTransparent(true);
		String music=new File("src/music/music.mp3").toURI().toString();
		player=new MediaPlayer(new Media(music));
		player.setOnEndOfMedia(new Runnable() {
	        @Override
	        public void run() {
	            player.seek(Duration.ZERO);
	            player.play();
	        }
	    }); 
		player.play();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		        map.sauvegarderMap();
		    }
		}));
	}

}
