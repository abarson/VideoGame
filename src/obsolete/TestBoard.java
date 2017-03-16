package obsolete;

import game_stuff.GameTile;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import menu_stuff.Game;

/*
 * TO DO:
 * Clean up all of these classes. Make actual classes for Board and Game.
 * Figure out why trees make the screen lag
 * Make the camera less jerky
 */
public class TestBoard extends Pane{
	
	private TEST game;
	
	private Group group;
	private double delta;
	
	public static final int TILE_SIZE = 40;
	public static final int X_DIM_TILES = 20;
    public static final int Y_DIM_TILES = 20;
    private Image TREE = new Image(Game.class.getResource("tree.png").toString());
    private Image GRASS = new Image(Game.class.getResource("grasssquare.png").toString());
    private Character character;
    private GameTile[][] tiles = new GameTile[X_DIM_TILES][Y_DIM_TILES];
	public TestBoard(TEST game){
		this.game = game;
		
		this.setPrefWidth(TILE_SIZE * X_DIM_TILES);
		this.setPrefHeight(TILE_SIZE * Y_DIM_TILES);
		
		setUpScreen();
		character = new Character(this);
		addCharacter();
		updateVisible();
		
		

	}
	public void setGroup(Group group){
		this.group = group;
	}
	public boolean checkTile(double x, double y){
		return (x < X_DIM_TILES && x >= 0 && y < Y_DIM_TILES && y >= 0 && tiles[(int)x][(int)y].getPassable());
	}
	public Character getCharacter(){
		return character;
	}
	public void addCharacter(){
		character.setLocation(4, 4);
	}
	public GameTile getTile(int x, int y){
		return tiles[x][y];
	}
	
	/*
	 * Obsolete**************************
	 */
	public void shiftBoard(int code){
		delta = 0;
		switch (code){
		case(Character.LEFT):
			delta = -1 * 40;
			group.setTranslateX(group.getTranslateX() - delta);
			break;
		case(Character.RIGHT):
			delta = 1 * 40;
			group.setTranslateX(group.getTranslateX() - delta);
			break;
		case(Character.UP):
			delta = -1 * 40;
			group.setTranslateY(group.getTranslateY() - delta);
			break;
		case(Character.DOWN):
			delta = 1 * 40;
			group.setTranslateY(group.getTranslateY() - delta);
			break;
		}
		
		//group.setTranslateX(group.getTranslateX() - delta);
		/*
		//KeyValue keyvalue = new KeyValue(group,group.getTranslateX() - 5);
		KeyFrame keyFrame1 = new KeyFrame(Duration.millis(Character.moveTime), 
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event){
						group.setTranslateX(group.getTranslateX() - delta);
					}
		});
		timeline.getKeyFrames().add(keyFrame1);
		timeline.play();*/
	}
	public void updateVisible(){
		int x = (int)character.getX() - 2;
		int y = (int)(character.getY() - 2);
		for (int i = x; i < x + 7; i++){
			for (int j = y; j < y + 7; j++){
				tiles[i][j].setVisible(true);
			}
		}
	}
	
	public void activateVisible(int code, int relativeX, int relativeY){
		Timeline timeline = new Timeline();
		KeyFrame keyframe = new KeyFrame(Duration.millis(Character.moveTime), 
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				visible(code, relativeX, relativeY);
			}
		});
		timeline.getKeyFrames().add(keyframe);
		timeline.play();
	}
	/*
	 * Clean up!
	 */
	public void visible(int code, int relativeX, int relativeY){
		int x = (int)character.getX();
		int y = (int)(character.getY());
		switch (code){
		case(Character.LEFT):
			for (int i = y-2-relativeY; i < y+5-relativeY; i++){
				if (x-3 >= 0 && i >= 0 && i < TestBoard.Y_DIM_TILES)
					tiles[x-3][i].setVisible(true);
				if (x+4 < TestBoard.X_DIM_TILES 
						&& i >= 0 && i < TestBoard.Y_DIM_TILES)
					
					tiles[x+4][i].setVisible(false);
				
			}
		break;
		case(Character.RIGHT):
			for (int i = y-2-relativeY; i < y+5-relativeY; i++){
				if (x-2 >= 0 && i >= 0 && i < TestBoard.Y_DIM_TILES)
					tiles[x-2][i].setVisible(false);
				if (x+5 < TestBoard.X_DIM_TILES 
						&& i >= 0 && i < TestBoard.Y_DIM_TILES)
					tiles[x+5][i].setVisible(true);
			}
		break;
		case(Character.UP):
			for (int i = x-2-relativeX; i < x+5-relativeX; i++){
				if (y+4 < TestBoard.Y_DIM_TILES && i >= 0 &&
						i < TestBoard.X_DIM_TILES)
					tiles[i][y+4].setVisible(false);
				if (y-3 >= 0 && i >= 0 &&
						i < TestBoard.X_DIM_TILES)
					tiles[i][y-3].setVisible(true);
			}
		break;
		case(Character.DOWN):
			for (int i = x-2-relativeX; i < x+5-relativeX; i++){
				if (y-2 >= 0 && i >= 0 &&
						i < TestBoard.X_DIM_TILES)
					tiles[i][y-2].setVisible(false);
				if (y+5 < TestBoard.Y_DIM_TILES && i >= 0 &&
						i < TestBoard.X_DIM_TILES)
					tiles[i][y+5].setVisible(true);
			}
		break;
		}
		
		
	}
	public void setUpScreen(){
			for (int i = 0; i < X_DIM_TILES; i++){
				for (int j = 0; j < Y_DIM_TILES; j++){
					addTile(i, j, GRASS, true, false);
				}
			}
			/*for (int j = 1; j < Y_DIM_TILES-1; j++){
				addTile(1, j, TREE, false, false);
			}*/
			//addTile(5, 5, TREE, false, true);
			//tiles[5][5].setText("I'm a tree!");
			
			//addTile(7, 5, TREE, false, true);
			//tiles[7][5].setText("I'm also a tree!");
			
			for (int i = 0; i < X_DIM_TILES; i++){
				for (int j = 0; j < Y_DIM_TILES; j++){
					tiles[i][j].setVisible(false);
				}
			}
			
				
	}
	public void addTile(int xCord, int yCord, Image image, boolean passable,
			boolean interact){
		/*
		SceneryTile tile = new SceneryTile(this, image, passable, interact);
		tile.setLocation(xCord, yCord);
		tiles[xCord][yCord] = tile; */
	}
	
}
