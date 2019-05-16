package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Hidden Hills");
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../vue/Sample.fxml"));
			Scene scene = new Scene(root,80*32,90*32);
			scene.getStylesheets().add(getClass().getResource("../vue/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
