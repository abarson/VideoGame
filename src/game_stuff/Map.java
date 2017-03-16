package game_stuff;

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

/*
 * TO DO:
 * Clean up all of these classes. Make actual classes for Board and Game.
 * Figure out why trees make the screen lag
 * Make the camera less jerky
 * 3/16 Not bad
 */
public class Map extends Pane{
	
	private GameApplication game;
	private InGameScreen menu;
	public static final int TILE_SIZE = 40;
	public static final int X_DIM_TILES = 20;
    public static final int Y_DIM_TILES = 20;
    
	//hero placement in screen relative x and y position
	public final static int CAMERA_X = 4;
	public final static int CAMERA_Y = 4;
	
	//size of the visible screen
	public final static int WINDOW_X = 9;
	public final static int WINDOW_Y = 8;
	
	//distance of character from bottom/right of screen
	public final static int DELTA_X = WINDOW_X - CAMERA_X;
	public final static int DELTA_Y = WINDOW_Y - CAMERA_Y;
	
    private Image TREE = new Image(getClass().getClassLoader()
    		.getResource("res/ScenerySprite/tree2.png").toString());
    private Image GRASS = new Image(getClass().getClassLoader()
    		.getResource("res/ScenerySprite/grasssquare.png").toString());
    
    private GameTile[][] tiles = new GameTile[X_DIM_TILES][Y_DIM_TILES];
    
	public Map(GameApplication game){
		this.game = game;
		menu = null;
		
		this.setPrefWidth(TILE_SIZE * X_DIM_TILES);
		this.setPrefHeight(TILE_SIZE * Y_DIM_TILES);
		
		setUpScreen();
	}
	
	public InGameScreen getCurrentMenu(){
		return menu;
	}
	
	public void addDialogue(String dialogue, Hero hero){
		menu = new Dialogue(this, dialogue, hero);
		getChildren().add(menu);
	}
	
	public void openMenu(Hero hero){
		menu = new Menu(this, hero);
	}
	
	public void removeScreen(){
		getChildren().remove(menu);
		menu = null;
	}
	
	public GameApplication getGame(){
		return game;
	}
	public boolean checkTile(double x, double y){
		return (x < X_DIM_TILES && x >= 0 && y < Y_DIM_TILES && y >= 0 && tiles[(int)x][(int)y].getPassable());
	}
	
	public GameTile getTile(int x, int y){
		return tiles[x][y];
	}
	
	
	
	/*
	 * this needs some work
	 * 3/16 good to go
	 */
	public void initVisible(Hero hero){
		int x = (int)(hero.getX());
		int y = (int)(hero.getY());
		int start_y = y - Map.CAMERA_Y - hero.getRelY();
		int end_y = y + Map.DELTA_Y - hero.getRelY();
		int start_x = x - Map.CAMERA_X - hero.getRelX();
		int end_x = x + Map.DELTA_X - hero.getRelX();
		for (int i = start_x; i < end_x; i++){
			for (int j = start_y; j < end_y; j++){
				tiles[i][j].setVisible(true);
			}
		}
	}
	
	/*
	 * xClean up! Also delay row/column deletion until after Hero moves
	 * Update 1/1 looks quite good
	 * 3/14 cleaned up a bit
	 */
	public void visible(int code, Hero hero){
		Timeline timeline = new Timeline();
		KeyFrame keyframe;
		
		int x = (int)(hero.getX());
		int y = (int)(hero.getY());
		int start_y = y - Map.CAMERA_Y - hero.getRelY();
		int end_y = y + Map.DELTA_Y - hero.getRelY();
		int start_x = x - Map.CAMERA_X - hero.getRelX();
		int end_x = x + Map.DELTA_X - hero.getRelX();
		switch (code){
		case(Hero.LEFT):
		if (x > Map.CAMERA_X && x + Map.DELTA_X <= Map.X_DIM_TILES 
			&& start_y >= 0 && start_y < Map.Y_DIM_TILES){
			for (int i = start_y; i < end_y; i++){
				tiles[x - Map.CAMERA_X - 1][i].setVisible(true);
			};
			timeline.setOnFinished(e -> {
				for (int i = start_y; i < end_y; i++){
					tiles[x + Map.DELTA_X - 1][i].setVisible(false);
			}
			});
			keyframe = new KeyFrame(Duration.millis(Hero.moveTime),
					new KeyValue(game.getGroup().translateXProperty(),
							game.getGroup().getTranslateX()+TILE_SIZE));
			timeline.getKeyFrames().add(keyframe);
		}
		break;
		
		case(Hero.RIGHT):
		if (x >= Map.CAMERA_X && x + Map.DELTA_X < Map.X_DIM_TILES
			&& start_y >= 0 && start_y < Map.Y_DIM_TILES){
			for (int i = start_y; i < end_y; i++){
					tiles[x + Map.DELTA_X][i].setVisible(true);
			};
			timeline.setOnFinished(e -> {
				for (int i = start_y; i < end_y; i++){
					tiles[x - Map.CAMERA_X][i].setVisible(false);
			}
			});
			keyframe = new KeyFrame(Duration.millis(Hero.moveTime),
					new KeyValue(game.getGroup().translateXProperty(),
							game.getGroup().getTranslateX()-TILE_SIZE));
			timeline.getKeyFrames().add(keyframe);	
		}
			
		break;
		case(Hero.UP):
		if (y + Map.DELTA_Y - 1 < Map.Y_DIM_TILES && y - Map.CAMERA_Y - 1 >= 0 
			&& start_x >= 0 && start_x < Map.Y_DIM_TILES){
			for (int i = start_x; i < end_x; i++){
					tiles[i][y - Map.CAMERA_Y - 1].setVisible(true);
			}
			timeline.setOnFinished(e -> {
				for (int i = start_x; i < end_x; i++){
					tiles[i][y + Map.DELTA_Y - 1].setVisible(false);
			}
			});
			keyframe = new KeyFrame(Duration.millis(Hero.moveTime),
				new KeyValue(game.getGroup().translateYProperty(),
						game.getGroup().getTranslateY()+TILE_SIZE));
			timeline.getKeyFrames().add(keyframe);	
		}
		break;
		case(Hero.DOWN):
			if (y >= Map.CAMERA_Y && y + Map.DELTA_Y < Map.Y_DIM_TILES
			&& start_x >= 0 && start_x < Map.X_DIM_TILES){
			for (int i = start_x; i < end_x; i++){
					tiles[i][y + Map.DELTA_Y].setVisible(true);
			}
			timeline.setOnFinished(e -> {
				for (int i = start_x; i < end_x; i++){
					tiles[i][y - Map.CAMERA_Y].setVisible(false);
			}
			});
			keyframe = new KeyFrame(Duration.millis(Hero.moveTime),
					new KeyValue(game.getGroup().translateYProperty(),
							game.getGroup().getTranslateY()-TILE_SIZE));
			timeline.getKeyFrames().add(keyframe);	
		break;
		}
		}
		timeline.play();
	}
	

	public void visibleDebug(int code, Hero hero){
		int x = (int)(hero.getX());
		int y = (int)(hero.getY());
		int start_y = y - Map.CAMERA_Y - hero.getRelY();
		int end_y = y + Map.DELTA_Y - hero.getRelY();
		int start_x = x - Map.CAMERA_X - hero.getRelX();
		int end_x = x + Map.DELTA_X - hero.getRelX();
		switch (code){
		case(Hero.LEFT):
		if (x > Map.CAMERA_X && x + Map.DELTA_X <= Map.X_DIM_TILES 
			&& start_y >= 0 && start_y < Map.Y_DIM_TILES){
			for (int i = start_y; i < end_y; i++){
				tiles[x - Map.CAMERA_X - 1][i].setVisible(true);
				tiles[x + Map.DELTA_X - 1][i].setVisible(false);
			}
		}
		break;
		
		case(Hero.RIGHT):
		if (x >= Map.CAMERA_X && x + Map.DELTA_X < Map.X_DIM_TILES
			&& start_y >= 0 && start_y < Map.Y_DIM_TILES){
			for (int i = start_y; i < end_y; i++){
					tiles[x + Map.DELTA_X][i].setVisible(true);
					tiles[x - Map.CAMERA_X][i].setVisible(false);
			}
		}
			
		break;
		case(Hero.UP):
		if (y + Map.DELTA_Y - 1 < Map.Y_DIM_TILES && y - Map.CAMERA_Y - 1 >= 0 
			&& start_x >= 0 && start_x < Map.Y_DIM_TILES){
			for (int i = start_x; i < end_x; i++){
					tiles[i][y - Map.CAMERA_Y - 1].setVisible(true);
					tiles[i][y + Map.DELTA_Y - 1].setVisible(false);
			}	
		}
		break;
		case(Hero.DOWN):
			if (y >= Map.CAMERA_Y && y + Map.DELTA_Y < Map.Y_DIM_TILES
			&& start_x >= 0 && start_x < Map.X_DIM_TILES){
			for (int i = start_x; i < end_x; i++){
					tiles[i][y + Map.DELTA_Y].setVisible(true);
					tiles[i][y - Map.CAMERA_Y].setVisible(false);
			}
		break;
		}
		}
	}
	public void setUpScreen(){
			for (int i = 0; i < X_DIM_TILES; i++){
				for (int j = 0; j < Y_DIM_TILES; j++){
					addScenery(i, j, GRASS, true);
				}
			}
			/*for (int i = 0; i < X_DIM_TILES; i++){
				addTile(0, i, TREE, false, false);
			}
			for (int i = 1; i < Y_DIM_TILES; i++){
				addTile(i, 0, TREE, false, false);
			}
			for (int i = 0; i < Y_DIM_TILES-1; i++){
				addTile(X_DIM_TILES-1, i+1, TREE, false, false);
			}*/
			
			addTile(5, 5, TREE, false, true);
			addText(5, 5, "I'm a tree! \nHow are you doing today?" 
					+ "_I love you.");
			
			addTile(7, 5, TREE, false, true);
			addText(7, 5, "I'm also a tree!");
			
			addTile(1, 5, TREE, false, true);
			addText(1, 5, "I'm not a tree! _What are you looking at?");
			
			addTile(3, 5, TREE, false, true);
			addText(3, 5, "Hello World. \nI'm a tree. _Love me.");
			
			addTile(3, 6, TREE, false, true);
			addTile(4, 7, TREE, false, true);
			
			for (int i = 1; i < Map.Y_DIM_TILES - 2; i ++){
				if (i != 6){
					addScenery(8, i, TREE, false);
					addScenery(6, i, TREE, false);
				}
			}
			
			addTile(18, 18, TREE, false, true);
			addTile(18, 17, TREE, false, true);
			addTile(18, 1, TREE, false, true);
			addTile(18, 0, TREE, false, true);
			addTile(1, 18, TREE, false, true);
			addTile(1, 17, TREE, false, true);
			addTile(1, 1, TREE, false, true);
			addTile(1, 0, TREE, false, true);
			((InteractTile)(tiles[4][7])).setText("Hi");
			for (int i = 0; i < X_DIM_TILES; i++){
				for (int j = 0; j < Y_DIM_TILES; j++){
					tiles[i][j].setVisible(false);
				}
			}
			
				
	}
	public void addScenery(int xCord, int yCord, Image image, boolean passable){
		SceneryTile tile = new SceneryTile(this, image, passable);
		tile.setLocation(xCord, yCord);
		tiles[xCord][yCord] = tile;
	}
	
	public void addTile(int xCord, int yCord, Image image, boolean passable,
			boolean interact){
		InteractTile tile = new InteractTile(this, image, passable);
		tile.setLocation(xCord, yCord);
		tiles[xCord][yCord] = tile; 
	}
	
	public void addText(int xCord, int yCord, String text){
		if (tiles[xCord][yCord] instanceof InteractTile){
			InteractTile tile = ((InteractTile)(tiles[xCord][yCord]));
			tile.setText(text);
		}
	}
}