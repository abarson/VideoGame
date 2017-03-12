package game_stuff;

import javafx.geometry.Pos;
import javafx.scene.Parent;
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

public class MainMenu extends Pane{
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private GameMenu gameMenu;
	private static GameManager gameManager;
	
	public MainMenu(GameManager gameManager){
		MainMenu.gameManager = gameManager;
		setPrefSize(WIDTH, HEIGHT);
		Image BACKGROUND_IMAGE1 = new Image (Game.class.getResource("Main_Background.jpg").toString());
		ImageView background1 = new ImageView(BACKGROUND_IMAGE1);
		background1.setFitWidth(WIDTH);
		background1.setFitHeight(HEIGHT);
		gameMenu = new GameMenu();
		getChildren().addAll(background1, gameMenu);
	}
	/*
	 * GameMenu class acts as a container for all MenuButton objects.
	 * Stores MenuButton elements inside VBox.
	 */
	private static class GameMenu extends Parent{
		public GameMenu(){
			//Main menu options
			VBox menu0 = new VBox(10);
			//Secondary menu options (not yet implemented)
			VBox menu1 = new VBox(10);
			
			menu0.setTranslateX(100);
			menu0.setTranslateY(100);
			
			menu1.setTranslateX(100);
			menu1.setTranslateY(100);
			
			final int offset = 400;
			
			MenuButton newGame = new MenuButton("NEW GAME");
			newGame.setOnMouseClicked(e -> gameManager.startSelectionScreen());
			
			MenuButton options = new MenuButton("OPTIONS");
			
			MenuButton exit = new MenuButton("EXIT");
			exit.setOnMouseClicked(e -> System.exit(0));
			
			menu0.getChildren().addAll(newGame, options, exit);
			
			getChildren().addAll(menu0);
		}
	}
	/*
	 * MenuButton is an instance of one of the MainMenu buttons. Every MenuButton
	 * comprises a text field and a rectangle background.
	 */
	private static class MenuButton extends StackPane{
		private Text text;
		
		public MenuButton(String name){
			text = new Text(name);
			text.setFont(Font.font("Courier New", 20));
			text.setFill(Color.WHITE);
			Rectangle bg = new Rectangle(200, 30);
			bg.setOpacity(0.6);
			bg.setFill(Color.BLACK);
			bg.setEffect(new GaussianBlur(3.5));
			
			setAlignment(Pos.CENTER_LEFT);
			getChildren().addAll(bg, text);
			
			this.setOnMouseEntered(e -> {
				bg.setTranslateX(10);
				text.setTranslateX(10);
				bg.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});
			
			this.setOnMouseExited(e -> {
				bg.setTranslateX(0);
				text.setTranslateX(0);
				bg.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});
			DropShadow drop = new DropShadow(50, Color.WHITE);
			drop.setInput(new Glow());
			
			this.setOnMousePressed(e -> setEffect(drop));
			this.setOnMouseReleased(e -> setEffect(null));
		}
	}
}
