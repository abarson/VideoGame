package game_stuff;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
	
	
	private GameManager gameManager;
	private Stage window;
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		gameManager = new GameManager(this);
		window = primaryStage;
		Pane root = new Pane();
		
		root = new MainMenu(gameManager);
		
		
		Scene scene = new Scene(root);
		window.setScene(scene);
		window.show();
	}
	
	public void setScene(Pane pane){
		Scene scene = new Scene(pane);
		window.setScene(scene);
	}
	
	public static void main(String [] args){
		launch(args);
	}

}
