package controleur;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import modeles.Map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.imageio.ImageIO;

public class SampleController implements Initializable {
    private Map map;

    @FXML
    private ResourceBundle resources;

    @FXML 
    private URL location;

    @FXML
    private TilePane tilePaneMap; 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		map = new Map();
        for(int i = 0; i < this.map.getMap().size(); i++) {
            try {
                BufferedImage buffImg = ImageIO.read(new File(this.map.getBlock(i).getuRI()));
                Image img = SwingFXUtils.toFXImage(buffImg, null);
                ImageView imgV = new ImageView (img);
                imgV.setFitHeight(32);
                imgV.setFitWidth(32);
                imgV.setVisible(true);
                imgV.setTranslateX(0);;
                tilePaneMap.getChildren().add(imgV);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
	}
}