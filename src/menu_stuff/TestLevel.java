package menu_stuff;

import game_stuff.GameManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TestLevel extends Pane{
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private GameManager gameManager;
	
	public TestLevel(GameManager gameManager){
		
		this.gameManager = gameManager;
		Image BACKGROUND_IMAGE2 = new Image (Game.class.getResource("sword_background.jpg").toString());
		ImageView background2 = new ImageView(BACKGROUND_IMAGE2);
		 
		background2.setFitWidth(WIDTH);
		background2.setFitHeight(HEIGHT);
		getChildren().addAll(background2);
	}
}
