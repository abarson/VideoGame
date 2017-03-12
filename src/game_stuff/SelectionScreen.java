package game_stuff;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SelectionScreen extends Pane{
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private GameManager gameManager;
	private Menu menu;
	private boolean canStart = false;
	
	public SelectionScreen(GameManager gameManager){
		this.gameManager = gameManager;
		Image BACKGROUND_IMAGE2 = new Image (Game.class.getResource("sword_background.jpg").toString());
		ImageView background2 = new ImageView(BACKGROUND_IMAGE2);
		
		menu = new Menu();
		background2.setFitWidth(WIDTH);
		background2.setFitHeight(HEIGHT);
		getChildren().addAll(background2, menu);
	}
	
	private class Menu extends Parent{
		private MenuButton start;
		public Menu(){
			//Menu options
			VBox menu0 = new VBox(10);
			
			menu0.setTranslateX(100);
			menu0.setTranslateY(100);
			
			Text text = new Text("Enter Your Name:");
			text.setFont(Font.font("Courier New", 20));
			text.setTranslateX(28);
			
			NameInput input = new NameInput();
			
			start = new MenuButton("START");
			start.setTranslateX(28);
			start.setDisable(true);
			start.setOnMouseClicked(e -> gameManager.startNewGame());
			
			menu0.getChildren().addAll(text, input, start);
			
			getChildren().addAll(menu0);
		}
		public void enableStart(){
			start.setDisable(false);
		}
		public void disableStart(){
			start.setDisable(true);
		}
	}
	
	/*
	 * Pane that contains name check/x picture, textfield for name, and button
	 * Button field also checks automatically that textfield has between 1 and 9 chars
	 * not yet included: stats (maybe seperate object?)
	 */
	private class NameInput extends StackPane{
		private Text name;
		public NameInput(){
			HBox nameField = new HBox(8);
			Image CHECK = new Image (Game.class.getResource("greencheck.png").toString());
			Image X = new Image (Game.class.getResource("xmark.png").toString());
			ImageView result = new ImageView(CHECK);
			result.setFitWidth(20);
			result.setFitHeight(20);
			result.setOpacity(0);
			
			TextField input = new TextField();
			Button button = new Button("Check");
			button.setDisable(true);
			
			input.textProperty().addListener((v, oldValue, newValue) -> {
				if (input.getText().length() > 0 && input.getText().length() < 10){
					button.setDisable(false);
				}
				else{
					button.setDisable(true);
					
				}
				result.setOpacity(0);
			});
			
			button.setOnMouseClicked(e ->{
				if (validateName(input.getText())){
					result.setImage(CHECK);
					result.setOpacity(1);
					menu.enableStart();
				}
				else{
					result.setImage(X);
					result.setOpacity(1);
					menu.disableStart();
				}
			});
			nameField.getChildren().addAll(result, input, button);
			getChildren().addAll(nameField);
		}
	}
	/*
	 * Eventually add a Tokenizer to check for special characters.
	 */
	private boolean validateName(String name){
		return true;
	}
	
	private class MenuButton extends StackPane{
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
				//bg.setTranslateX(10);
				//text.setTranslateX(10);
				bg.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});
			
			this.setOnMouseExited(e -> {
				//bg.setTranslateX(0);
				//text.setTranslateX(0);
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
