package game_stuff;


import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Dialogue extends InGameMenu{
	public final static int FONT_SIZE = 15;
	private String dialogue;
	private String[] allString;
	private int current = 0;
	private Text dialogueText;
	public Dialogue(Map map, String dialogue){
		super(map);
		this.dialogue = dialogue;
		setDisplay(new Box());
	}
	
	
	public void enter(){
		if (allString.length > 1 && current < allString.length){
			dialogueText.setText(allString[current]);
			current++;
		}
		else	
			getMap().removeDialougeBox();
	}
	
	private class Box extends StackPane{
		
		
		public Box(){
			Rectangle bg = new Rectangle(
					Map.TILE_SIZE * (Map.WINDOW_X-x_factor),
					Map.TILE_SIZE * Map.WINDOW_Y/6.0
					);
			
			allString = dialogue.split("_");
			
			dialogueText = new Text(allString[0]);
			current = 1;
			dialogueText.setFont(Font.font("Courier New", FONT_SIZE));
			//dialogueText.setTranslateX(5);
			bg.setFill(Color.WHITE);
			bg.setStroke(Color.BLACK);
			setAlignment(Pos.CENTER);
			/*
			 * fix this up a bit
			 */
			setTranslateX(-getMap().getGame().getGroup().getTranslateX() + x_factor/2 *
					Map.TILE_SIZE);
			setTranslateY(-getMap().getGame().getGroup().getTranslateY() + 
					(Map.WINDOW_Y - Map.WINDOW_Y/4.0) * Map.TILE_SIZE
					- getHero().getRelY());
			getChildren().addAll(bg, dialogueText);
		}
	}
	
}
