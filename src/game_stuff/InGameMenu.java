package game_stuff;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class InGameMenu extends Parent{
	
	private Map map;
	private Hero hero;
	
	public static double x_factor = 3;
	
	public InGameMenu(Map map){
		this.setMap(map);
		hero = map.getHero();
	}
	public void setDisplay(StackPane box){
		this.getChildren().add(box);
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public Hero getHero(){
		return hero;
	}
	
	public abstract void enter();
}
