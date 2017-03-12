package game_stuff;

import javafx.event.EventHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;

import com.sun.javafx.iio.ImageLoader;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
public class Hero {
	private DoubleProperty x_cord = new SimpleDoubleProperty();
    private DoubleProperty y_cord = new SimpleDoubleProperty();
    
    //all directions the hero can face
    private final Image LEFT1 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/left1.png").toString());
    private final Image LEFT2 =new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/left2.png").toString());
    private final Image LEFT3 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/left3.png").toString());
    private final Image LEFT4 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/left4.png").toString());
    private final Image RIGHT1 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/right1.png").toString());
    private final Image RIGHT2 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/right2.png").toString());
    private final Image RIGHT3 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/right3.png").toString());
    private final Image RIGHT4 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/right4.png").toString());
    private final Image FORWARD1 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/forward1.png").toString());
    private final Image FORWARD2 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/forward2.png").toString());
    private final Image FORWARD3 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/forward3.png").toString());
    private final Image FORWARD4 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/forward4.png").toString());
    private final Image DOWN1 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/down1.png").toString());
    private final Image DOWN2 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/down2.png").toString());
    private final Image DOWN3 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/down3.png").toString());
    private final Image DOWN4 = new Image(getClass().getClassLoader()
    		.getResource("res/HeroSprite/down4.png").toString());
    
    private ImageView image;
	private Map map;
	
	private int facing;
	private boolean moving;
	private int relativeX;
	private int relativeY;
	
	public final static int LEFT = 1;
	public final static int RIGHT = 2;
	public final static int UP = 3;
	public final static int DOWN = 4;
	public final static double moveTime = 600;
	
	public Hero(Map map){
		moving = false;
		
		//These depend on game design, careful
		facing = DOWN;
		relativeX = 0;
		relativeY = 0;
		
		//By default, character facing down. Also depends on game design.
		image = new ImageView(DOWN1);
		
		this.map = map;
		image.setFitHeight(Map.TILE_SIZE);
		image.setFitWidth(Map.TILE_SIZE);
		map.getChildren().add(image);
		
		image.xProperty().bind(x_cord.multiply(Map.TILE_SIZE));
        image.yProperty().bind(y_cord.multiply(Map.TILE_SIZE));
        
	}
	
	public int getRelX(){
		return relativeX;
	}
	public int getRelY(){
		return relativeY;
	}
	
	/*
	 * In case the real x or y position is needed
	 */
	public double imageX(){
		return image.xProperty().get();
	}
	public double imageY(){
		return image.yProperty().get();
	}
	
	/*
	 * The x and y position relative to the board
	 */
	public double getX(){
		return x_cord.get();
	}
	public double getY(){
		return y_cord.get();
	}
	
	public DoubleProperty getxcord(){
		return x_cord;
	}
	public DoubleProperty getycord(){
		return y_cord;
	}
	
	public ImageView getImage(){
		return image;
	}
	public int getFacing(){
		return facing;
	}
	public void setLocation(double x, double y) {
        x_cord.set(x);
        y_cord.set(y);
    }
	
	/*
	 * Will attempt to interact with whatever tile the character is facing
	 */
	public void interactWith(){
	if (!moving){
		GameTile tile = null;
		int x;
		int y;
		switch(facing){
		case(LEFT):
			if (x_cord.get() > 0){
				x = (int) (x_cord.get()-1);
				y = (int) (y_cord.get());
				tile = map.getTile(x, y);
			}
			break;
		case(RIGHT):
			if (x_cord.get() < Map.X_DIM_TILES-1){
				x = (int) (x_cord.get()+1);
				y = (int) (y_cord.get());
				tile = map.getTile(x, y);
			}
			break;
		case(UP):
			if (y_cord.get() > 0){
				x = (int) (x_cord.get());
				y = (int) (y_cord.get()-1);
				tile = map.getTile(x, y);
			}
			break;
		case(DOWN):
			if (y_cord.get() < Map.Y_DIM_TILES-1){
				x = (int) (x_cord.get());
				y = (int) (y_cord.get()+1);
				tile = map.getTile(x, y);
			}
			break;
		default:
			tile = null;
			break;
		}
		if (tile != null && tile.canInteract()){
			tile.interact();
		}
	}
	}
	
	/*
	 * method that handles move animations for all directions, depending
	 * on the given code integer.
	 */
	public void moveAnimation(int code, double time){
		Image frame1;
		Image frame2;
		Image frame3;
		Image frame4;
		Image frame5;
		switch (code){
		case LEFT:
			frame1 = LEFT2;
			frame2 = LEFT3;
			frame3 = LEFT2;
			frame4 = LEFT4;
			frame5 = LEFT1;
			break;
		case RIGHT:
			frame1 = RIGHT2;
			frame2 = RIGHT3;
			frame3 = RIGHT2;
			frame4 = RIGHT4;
			frame5 = RIGHT1;
			break;
		case UP:
			frame1 = FORWARD2;
			frame2 = FORWARD3;
			frame3 = FORWARD2;
			frame4 = FORWARD4;
			frame5 = FORWARD1;
			break;
		case DOWN:
			frame1 = DOWN2;
			frame2 = DOWN3;
			frame3 = DOWN2;
			frame4 = DOWN4;
			frame5 = DOWN1;
			break;
		default:
			frame1 = LEFT2;
			frame2 = LEFT3;
			frame3 = LEFT2;
			frame4 = LEFT4;
			frame5 = LEFT1;
			break;
		}
		
		Timeline timeline = new Timeline();
		KeyFrame keyFrame1 = new KeyFrame(Duration.millis(time * 0), 
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event){
						image.setImage(frame1);
					}
		});
		KeyFrame keyFrame2 = new KeyFrame(Duration.millis(time * 0.25), 
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event){
						image.setImage(frame2);
					}
		});
		KeyFrame keyFrame3 = new KeyFrame(Duration.millis(time * 0.5), 
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event){
						image.setImage(frame3);
					}
		});
		KeyFrame keyFrame4 = new KeyFrame(Duration.millis(time * 0.75), 
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event){
						image.setImage(frame4);
					}
		});
		KeyFrame keyFrame5 = new KeyFrame(Duration.millis(time*.98), 
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event){
						image.setImage(frame5);
					}
		});
		timeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3, 
				keyFrame4, keyFrame5);
		
		timeline.setOnFinished(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				image.setImage(frame5);
			}
		});
		timeline.play();
	}

	public void computeRelatives(int code){
		switch(code){
		case(LEFT):
			if (getX() - 1 < Map.CAMERA_X){
				relativeX = (int) (getX() - Map.CAMERA_X - 1);
			}
			else if (relativeX != 0){
				relativeX--;
			}
		break;
		case(RIGHT):
			if (getX() + 1 + Map.DELTA_X > Map.X_DIM_TILES || relativeX != 0){
				relativeX++;
			}
		break;
		case(UP):
			if (getY() - 1 < Map.CAMERA_Y){
				relativeY = (int) (getY() - Map.CAMERA_Y - 1);
			}
			else if (relativeY != 0){
				relativeY--;
			}
		break;
		case(DOWN):
			if (getY() + 1 + Map.DELTA_Y > Map.Y_DIM_TILES || relativeY != 0){
				relativeY++;
			}
		break;
		}
	}
	
	/*
	 * All move methods first check to make sure the Character can move,
	 * then will shift the character to the given direction in
	 * moveTime
	 * Relatives work for some reason
	 */
	public void moveLeft(){
		if(map.checkTile(x_cord.get()-1, y_cord.get()) && !moving){
			moving = true;
			if (getX() > Map.CAMERA_X){
				if (!GameApplication.DEBUG)
					map.visible(LEFT, relativeX, relativeY);
				else
					map.visibleDebug(LEFT, relativeX, relativeY);
			}
			computeRelatives(LEFT);
			moveAnimation(LEFT, moveTime);
			Timeline timeline = new Timeline();
			KeyValue keyvalue = new KeyValue(x_cord,x_cord.get()-1);
			KeyFrame keyframe = new KeyFrame(Duration.millis(moveTime), 
					keyvalue);
			timeline.getKeyFrames().addAll(keyframe);
			timeline.setOnFinished(new EventHandler<ActionEvent>() {

			    @Override
			    public void handle(ActionEvent event) {
			    	moving = false;
			    	
			    }
			});
			timeline.play();
			facing = LEFT;
			
		}
		else if (moving == false){
			image.setImage(LEFT1);
			facing = LEFT;
		}
		
	}
	public void moveRight(){
		if(map.checkTile(x_cord.get()+1, y_cord.get()) && !moving){
			moving = true;
			if (getX() + Map.DELTA_X < Map.X_DIM_TILES){
				if (!GameApplication.DEBUG)
					map.visible(RIGHT, relativeX, relativeY);
				else
					map.visibleDebug(RIGHT, relativeX, relativeY);
			}
			computeRelatives(RIGHT);
			moveAnimation(RIGHT, moveTime);
			Timeline timeline = new Timeline();
			KeyValue keyvalue = new KeyValue(x_cord,x_cord.get()+1);
			KeyFrame keyframe = new KeyFrame(Duration.millis(moveTime), keyvalue);
			timeline.getKeyFrames().add(keyframe);
			timeline.setOnFinished(new EventHandler<ActionEvent>() {

			    @Override
			    public void handle(ActionEvent event) {
			    	moving = false;
			    	
			    }
			});
			timeline.play();
			facing = RIGHT;
		}
		else if (moving == false){
			image.setImage(RIGHT1);
			facing = RIGHT;
		}
		
	}
	public void moveUp(){
		if(map.checkTile(x_cord.get(), y_cord.get()-1) && !moving){
			moving = true;
			if (getY() > Map.CAMERA_Y){
				if (!GameApplication.DEBUG)
					map.visible(UP, relativeX, relativeY);
				else
					map.visibleDebug(UP, relativeX, relativeY);
			}
			computeRelatives(UP);
			moveAnimation(UP, moveTime);
			Timeline timeline = new Timeline();
			KeyValue keyvalue = new KeyValue(y_cord,y_cord.get()-1);
			KeyFrame keyframe = new KeyFrame(Duration.millis(moveTime), keyvalue);
			timeline.getKeyFrames().add(keyframe);
			timeline.setOnFinished(new EventHandler<ActionEvent>() {

			    @Override
			    public void handle(ActionEvent event) {
			    	moving = false;
			    }
			});
			timeline.play();
			facing = UP;
		}
		else if (moving == false){
			image.setImage(FORWARD1);
			facing = UP;
		}
		
	}
	public void moveDown(){
		if(map.checkTile(x_cord.get(), y_cord.get()+1) && !moving){
			moving = true;
			if (getY() + Map.DELTA_Y < Map.Y_DIM_TILES){
				if (!GameApplication.DEBUG)
					map.visible(DOWN, relativeX, relativeY);
				else
					map.visibleDebug(DOWN, relativeX, relativeY);
			}
			computeRelatives(DOWN);
			moveAnimation(DOWN, moveTime);
			Timeline timeline = new Timeline();
			KeyValue keyvalue = new KeyValue(y_cord,y_cord.get()+1);
			KeyFrame keyframe = new KeyFrame(Duration.millis(moveTime), keyvalue);
			timeline.getKeyFrames().add(keyframe);
			timeline.setOnFinished(new EventHandler<ActionEvent>() {

			    @Override
			    public void handle(ActionEvent event) {
			    	moving = false;
			    }
			});
			timeline.play();
			facing = DOWN;
		}
		else if (moving == false){
			image.setImage(DOWN1);
			facing = DOWN;
		}
		
	}
}
