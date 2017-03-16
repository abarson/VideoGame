package game_stuff;


import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Dialogue extends InGameScreen{
	
	public final static int FONT_SIZE = 15;
	private String dialogue;
	private String[] allString;
	private int current = 0;
	private Text dialogueText;
	public Dialogue(Map map, String dialogue, Hero hero){
		super(map, hero);
		this.dialogue = dialogue;
		setDisplay(new Box());
	}
	
	
	public boolean enter(){
		if (allString.length > 1 && current < allString.length){
			dialogueText.setText(allString[current]);
			current++;
			return false;
		}
		else {
			getMap().removeScreen();
			return true;
		}
	}
	
	public void up(){}
	public void down(){}
	public boolean click(){return false;}
	
	private class Box extends StackPane{
		
		
		public Box(){
			
			double xSize = Map.WINDOW_X-1;
			double ySize = 2;
			Rectangle bg = new Rectangle(
					(xSize * Map.TILE_SIZE),
					(ySize * Map.TILE_SIZE)
					);
			bg.setArcHeight(20);
			bg.setArcWidth(20);
			bg.setFill(Color.LIGHTGRAY);
			allString = dialogue.split("_");
			
			dialogueText = new Text(allString[0]);
			current = 1;
			dialogueText.setFont(Font.font("Courier New", FONT_SIZE));
			
			//bg.setFill(Color.WHITE);
			bg.setStroke(Color.BLACK);
			setAlignment(Pos.CENTER);
			
			double translateX = Map.TILE_SIZE / 2.0;
			double translateY = Map.TILE_SIZE / 2.0;
			setTranslateX(hero.imageX() + translateX - ((Map.CAMERA_X + hero.getRelX()) * Map.TILE_SIZE));
			
			if (hero.getRelY() <= 0){
				setTranslateY(hero.imageY() - translateY + ((Map.DELTA_Y) - hero.getRelY() - ySize) * Map.TILE_SIZE);
			}
			else {
				setTranslateY(hero.imageY() + translateY - (Map.CAMERA_Y * Map.TILE_SIZE) - hero.getRelY() * Map.TILE_SIZE);
			}
			getChildren().addAll(bg, dialogueText);
		}
	}
	
}
