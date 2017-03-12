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
 */
public class Map extends Pane{
	
	private GameApplication game;
	private TestManager gameManager;
	private InGameMenu menu;
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
    
    private Hero hero;
    private GameTile[][] tiles = new GameTile[X_DIM_TILES][Y_DIM_TILES];
    
	public Map(GameApplication game){
		this.game = game;
		menu = null;
		
		this.setPrefWidth(TILE_SIZE * X_DIM_TILES);
		this.setPrefHeight(TILE_SIZE * Y_DIM_TILES);
		
		setUpScreen();
		hero = new Hero(this);
		addHero();
		initVisible();
	}
	public void setManager(TestManager gameManager){
		this.gameManager = gameManager;
	}
	public void addDialogueBox(String dialogue){
		gameManager.setState(TestManager.DIALOGUE);
		menu = new Dialogue(this, dialogue);
		getChildren().add(menu);
		gameManager.setMenu(menu);
	}
	public void removeDialougeBox(){
		gameManager.setState(TestManager.ROAM);
		getChildren().remove(menu);
		gameManager.setMenu(null);
	}
	
	public GameApplication getGame(){
		return game;
	}
	public boolean checkTile(double x, double y){
		return (x < X_DIM_TILES && x >= 0 && y < Y_DIM_TILES && y >= 0 && tiles[(int)x][(int)y].getPassable());
	}
	public Hero getHero(){
		return hero;
	}
	
	public void addHero(){
		hero.setLocation(6, 6);
	}
	
	public GameTile getTile(int x, int y){
		return tiles[x][y];
	}
	
	/*
	 * this needs some work
	 */
	public void initVisible(){
		int x = (int)(hero.getX() - Map.CAMERA_X);
		int y = (int)(hero.getY() - Map.CAMERA_Y);
		for (int i = x; i < x + Map.WINDOW_X; i++){
			for (int j = y; j < y + Map.WINDOW_Y; j++){
				tiles[i][j].setVisible(true);
			}
		}
	}
	
	
	/*
	 * xClean up! Also delay row/column deletion until after Hero moves
	 * Update 1/1 looks quite good
	 */
	public void visible(int code, int relativeX, int relativeY){
		Timeline timeline = new Timeline();
		KeyFrame keyframe;
		
		int x = (int)(hero.getX());
		int y = (int)(hero.getY());
		int start_y = y - Map.CAMERA_Y - relativeY;
		int end_y = y + Map.DELTA_Y - relativeY;
		int start_x = x - Map.CAMERA_X - relativeX;
		int end_x = x + Map.DELTA_X - relativeX;
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
	public void visibleDebug(int code, int relativeX, int relativeY){
		int x = (int)(hero.getX());
		int y = (int)(hero.getY());
		int start_y = y - Map.CAMERA_Y - relativeY;
		int end_y = y + Map.DELTA_Y - relativeY;
		int start_x = x - Map.CAMERA_X - relativeX;
		int end_x = x + Map.DELTA_X - relativeX;
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
					addTile(i, j, GRASS, true, false);
				}
			}
			for (int i = 0; i < X_DIM_TILES; i++){
				addTile(0, i, TREE, false, false);
			}
			for (int i = 1; i < Y_DIM_TILES; i++){
				addTile(i, 0, TREE, false, false);
			}
			for (int i = 0; i < Y_DIM_TILES-1; i++){
				addTile(X_DIM_TILES-1, i+1, TREE, false, false);
			}
			
			addTile(5, 5, TREE, false, true);
			tiles[5][5].setText("I'm a tree! \nHow are you doing today?" 
					+ "_I love you.");
			
			addTile(7, 5, TREE, false, true);
			tiles[7][5].setText("I'm also a tree!");
			
			addTile(1, 5, TREE, false, true);
			tiles[1][5].setText("I'm not a tree! _What are you looking at?");
			
			addTile(3, 5, TREE, false, true);
			tiles[3][5].setText("Hello World. \nI'm a tree. _Love me.");
			
			addTile(3, 6, TREE, false, true);
			addTile(4, 7, TREE, false, true);
			tiles[4][7].setText("Hi");
			for (int i = 0; i < X_DIM_TILES; i++){
				for (int j = 0; j < Y_DIM_TILES; j++){
					tiles[i][j].setVisible(false);
				}
			}
			
				
	}
	public void addTile(int xCord, int yCord, Image image, boolean passable,
			boolean interact){
		SceneryTile tile = new SceneryTile(this, image, passable, interact);
		tile.setLocation(xCord, yCord);
		tiles[xCord][yCord] = tile; 
	}
}