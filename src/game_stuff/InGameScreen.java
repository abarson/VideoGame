package game_stuff;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class InGameScreen extends Parent{
	
	protected Map map;
	protected Hero hero;
	public static double x_factor = 3;
	
	public InGameScreen(Map map, Hero hero){
		this.map = map;
		this.hero = hero;
	}
	
	public void setDisplay(StackPane box){
		this.getChildren().add(box);
	}
	
	public Map getMap() {
		return map;
	}
	
	public abstract boolean enter();
	
	public abstract void up();
	
	public abstract void down();
	
	public abstract boolean click();
}
