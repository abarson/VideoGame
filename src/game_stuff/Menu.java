package game_stuff;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * InGameScreen that appears whenever the user hits the E key.
 * The Menu has four buttons that the user can select from.
 * @author adambarson
 *
 */
public class Menu extends InGameScreen {
	public static int VERTICAL_DISTANCE = 10;
	
	private static MenuButton[] buttons = new MenuButton[4];
	private static int activeButton = 0;
	
	public final static int CHAR_COMMAND = 0;
	public final static int INV_COMMAND = 1;
	public final static int CONT_COMMAND = 2;
	public final static int QUIT_COMMAND = 3;
	MenuButton charButton;
	MenuButton invButton;
	MenuButton contButton;
	MenuButton quitButton;
	
	/**
	 * Creates a Menu and adds it to the current Map, based on the Hero's coordinates.
	 * @param map The current Map
	 * @param hero The Hero
	 */
	public Menu(Map map, Hero hero) {
		super(map, hero);
		VBox menu = new VBox(VERTICAL_DISTANCE);
		
		BorderStroke stroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, 
				new CornerRadii(10), BorderWidths.DEFAULT);
		Border border = new Border(stroke);
		menu.setBorder(border);
		BackgroundFill background = new BackgroundFill(Color.BLUE, new CornerRadii(10), null);
		BackgroundFill[] backgrounds = {background};
		menu.setBackground(new Background(backgrounds));
		
		menu.setTranslateX(hero.imageX() + (Map.DELTA_X * Map.TILE_SIZE) - (Map.TILE_SIZE * MenuButton.MENU_X) - hero.getRelX() * Map.TILE_SIZE);
		menu.setTranslateY(hero.imageY() - (Map.CAMERA_Y * Map.TILE_SIZE) - hero.getRelY() * Map.TILE_SIZE);
		
		createButtons();
		
		menu.getChildren().addAll(charButton, invButton, contButton, quitButton);
		getChildren().addAll(menu);
		map.getChildren().add(this);
	}
	
	/**
	 * Initialize all buttons, and update selected array upon clicking.
	 */
	public void createButtons(){
		charButton = new MenuButton("CHARACTER");
		invButton = new MenuButton("INVENTORY");
		contButton = new MenuButton("CONTINUE");
		quitButton = new MenuButton("QUIT");
		buttons[0] = charButton; buttons[1] = invButton; 
		buttons[2] = contButton; buttons[3] = quitButton;
		buttons[activeButton].activateEffect();
	}
	
	/**
	 * Action resulting from user clicking a button.
	 */
	public boolean click() {
		switch(activeButton){
		case(CHAR_COMMAND):
			if (buttons[CHAR_COMMAND].getClicked()){
				return false;
			}
		case(INV_COMMAND):
			if (buttons[INV_COMMAND].getClicked()){
				return false;
			}
		case(CONT_COMMAND):
			if (buttons[CONT_COMMAND].getClicked()){
				map.removeScreen();
				activeButton = 0;
				MenuButton.reset();
				return true;
			}
		case(QUIT_COMMAND):
			if (buttons[QUIT_COMMAND].getClicked()){
				System.exit(0); //this will be handled more gracefully in future
				return true; 
			}
		}
		return false;
	}
	/**
	 * Action resulting from user hitting enter.
	 */
	public boolean enter() {
		switch(activeButton){
		case(CHAR_COMMAND):
			return false;
		case(INV_COMMAND):
			return false;
		case(CONT_COMMAND):
			map.removeScreen();
			activeButton = 0;
			MenuButton.reset();
			return true;
		case(QUIT_COMMAND):
			System.exit(0);
		}
		return false;
	}
	
	/**
	 * Moves the currently selected button up.
	 */
	public void up(){
		if (activeButton > 0){
			buttons[activeButton--].deactivateEffect();
			buttons[activeButton].activateEffect();
		}
	}
	
	/**
	 * Moves the currently selected button down.
	 */
	public void down(){
		if (activeButton < buttons.length-1){
			buttons[activeButton++].deactivateEffect();
			buttons[activeButton].activateEffect();
		}
	}
	
	/**
	 * A MenuButton has a background rectangle with text over it.
	 * Each button will respond to mouse hovering and pressing enter.
	 */
	private static class MenuButton extends StackPane{
		public static int MENU_X = 3;
		public static int FONT_SIZE = 20;
		
		private static int counter = 0;
		private int ID;
		
		private Text text;
		private Rectangle bg;
		private boolean clicked = false;
		
		public MenuButton(String name){
			ID = counter++;
			text = new Text(name);
			text.setFont(Font.font("Courier New", FONT_SIZE));
			text.setFill(Color.WHITE);
			bg = new Rectangle(Map.TILE_SIZE * MENU_X, FONT_SIZE);
			bg.setOpacity(0.6);
			bg.setFill(Color.BLACK);
			bg.setEffect(new GaussianBlur(3.5));
			
			setAlignment(Pos.CENTER_LEFT);
			getChildren().addAll(bg, text);
			
			this.setOnMouseEntered(e -> {
				for (int i = 0; i < buttons.length; i++){
					buttons[i].deactivateEffect();
				}
				bg.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
				activeButton = ID;
			});
			
			DropShadow drop = new DropShadow(50, Color.WHITE);
			drop.setInput(new Glow());
			
			this.setOnMousePressed(e -> {setEffect(drop); clicked = true;});
			this.setOnMouseReleased(e -> setEffect(null));
		}
		
		/**
		 * Returns whether or not the button has been clicked.
		 * @return clicked
		 */
		public boolean getClicked(){
			return clicked;
		}
		
		/**
		 * Highlights the button.
		 */
		public void activateEffect(){
			bg.setFill(Color.WHITE);
			text.setFill(Color.BLACK);
		}
		
		/**
		 * Returns the button to normal.
		 */
		public void deactivateEffect(){
			bg.setFill(Color.BLACK);
			text.setFill(Color.WHITE);
		}
		
		/**
		 * When the menu is removed from screen, reset the counter of buttons to 0.
		 */
		public static void reset(){
			counter = 0;
		}
	}
}
