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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
public class Character {
	private DoubleProperty x_cord = new SimpleDoubleProperty();
    private DoubleProperty y_cord = new SimpleDoubleProperty();
    private final Image LEFT1 = new Image(Game.class.getResource("left1.png").toString());
    private final Image LEFT2 = new Image(Game.class.getResource("left2.png").toString());
    private final Image LEFT3 = new Image(Game.class.getResource("left3.png").toString());
    private final Image LEFT4 = new Image(Game.class.getResource("left4.png").toString());
    private final Image RIGHT1 = new Image(Game.class.getResource("right1.png").toString());
    private final Image RIGHT2 = new Image(Game.class.getResource("right2.png").toString());
    private final Image RIGHT3 = new Image(Game.class.getResource("right3.png").toString());
    private final Image RIGHT4 = new Image(Game.class.getResource("right4.png").toString());
    private final Image FORWARD1 = new Image(Game.class.getResource("forward1.png").toString());
    private final Image FORWARD2 = new Image(Game.class.getResource("forward2.png").toString());
    private final Image FORWARD3 = new Image(Game.class.getResource("forward3.png").toString());
    private final Image FORWARD4 = new Image(Game.class.getResource("forward4.png").toString());
    private final Image FORWARD5 = new Image(Game.class.getResource("forward5.png").toString());
    private final Image DOWN1 = new Image(Game.class.getResource("down1.png").toString());
    private final Image DOWN2 = new Image(Game.class.getResource("down2.png").toString());
    private final Image DOWN3 = new Image(Game.class.getResource("down3.png").toString());
    private final Image DOWN4 = new Image(Game.class.getResource("down4.png").toString());
    private final Image DOWN5 = new Image(Game.class.getResource("down5.png").toString());
    
    private GameTile facingTile;
    private ImageView image;
	private TestBoard testBoard;
	
	private int facing;
	private boolean moving;
	private int relativeX;
	private int relativeY;
	
	public final static int LEFT = 1;
	public final static int RIGHT = 2;
	public final static int UP = 3;
	public final static int DOWN = 4;
	public final static double moveTime = 600;
	
	public Character(TestBoard testBoard){
		moving = false;
		
		//These depend on game design, careful
		facing = DOWN;
		relativeX = 0;
		relativeY = 0;
		
		image = new ImageView(DOWN1);
		this.testBoard = testBoard;
		image.setFitHeight(TestBoard.TILE_SIZE);
		image.setFitWidth(TestBoard.TILE_SIZE);
		testBoard.getChildren().add(image);
		image.xProperty().bind(x_cord.multiply(TestBoard.TILE_SIZE));
        image.yProperty().bind(y_cord.multiply(TestBoard.TILE_SIZE));
        
	}
	
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
				tile = testBoard.getTile(x, y);
			}
			break;
		case(RIGHT):
			if (x_cord.get() < testBoard.X_DIM_TILES-1){
				x = (int) (x_cord.get()+1);
				y = (int) (y_cord.get());
				
				tile = testBoard.getTile(x, y);
			}
			break;
		case(UP):
			if (y_cord.get() > 0){
				tile = testBoard.getTile((int)x_cord.get(), (int)(y_cord.get()-1));
			}
			break;
		case(DOWN):
			if (y_cord.get() < testBoard.Y_DIM_TILES-1){
				tile = testBoard.getTile((int)x_cord.get(), (int)(y_cord.get()+1));
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
	 * on the given code.
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
				//testBoard.shiftBoard(code);
			}
		});
		timeline.play();
	}
	
	/*
	 * All move methods first check to make sure the Character can move,
	 * then will shift the character to the given direction in
	 * moveTime
	 */
	public void moveLeft(){
		if(testBoard.checkTile(x_cord.get()-1, y_cord.get()) && !moving){
			moving = true;
			if (getX() - 2 > 0){
				testBoard.visible(LEFT, relativeX, relativeY);
				if (relativeX > 1)
					relativeX--;
				else{
					relativeX = 0;
				}
			}
			else{
				if (getX() == 2){
					relativeX = -1;
				}
				if (getX() == 1){
					relativeX = -2;
				}
			}
			//testBoard.shiftBoard(LEFT);
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
			
		}
		else{
			if (moving == false)
				image.setImage(LEFT1);
		}
		facing = LEFT;
		
	}
	public void moveRight(){
		if(testBoard.checkTile(x_cord.get()+1, y_cord.get()) && !moving){
			moving = true;
			if (getX() + 5 < TestBoard.X_DIM_TILES){
				testBoard.visible(RIGHT, relativeX, relativeY);
				if (relativeX < -1)
					relativeX++;
				else{
					relativeX = 0;
				}
			}
			else{
				if ((int)(TestBoard.X_DIM_TILES - getX()) == 5){
					relativeX = 1;
				}
				if ((int)(TestBoard.X_DIM_TILES - getX()) == 4){
					relativeX = 2;
				}
				if ((int)(TestBoard.X_DIM_TILES - getX()) == 3){
					relativeX = 3;
				}
				if ((int)(TestBoard.X_DIM_TILES - getX()) == 2){
					relativeX = 4;
				}
			}
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
		}
		else{
			if (moving == false)
				image.setImage(RIGHT1);
		}
		facing = RIGHT;
		
	}
	public void moveUp(){
		if(testBoard.checkTile(x_cord.get(), y_cord.get()-1) && !moving){
			moving = true;
			if (getY() - 2 > 0){
				testBoard.visible(UP, relativeX, relativeY);
				if (relativeY > 1)
					relativeY--;
				else{
					relativeY = 0;
				}
			}
			else{
				if ((int)(getY()) == 2){
					relativeY = -1;
				}
				if ((int)(getY()) == 1){
					relativeY = -
							2;
				}
				
			}
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
		}
		else{
			if (moving == false)
				image.setImage(FORWARD1);
		}
		facing = UP;
	}
	public void moveDown(){
		if(testBoard.checkTile(x_cord.get(), y_cord.get()+1) && !moving){
			moving = true;
			if (getY() + 5 < TestBoard.Y_DIM_TILES){
				testBoard.visible(DOWN, relativeX, relativeY);
				if (relativeY > 1)
					relativeY--;
				else{
					relativeY = 0;
				}
			}
			else{
				if ((int)(TestBoard.Y_DIM_TILES - getY()) == 5){
					relativeY = 1;
				}
				if ((int)(TestBoard.Y_DIM_TILES - getY()) == 4){
					relativeY = 2;
				}
				if ((int)(TestBoard.Y_DIM_TILES - getY()) == 3){
					relativeY = 3;
				}
				if ((int)(TestBoard.Y_DIM_TILES - getY()) == 2){
					relativeY = 4;
				}
				//relativeX = (int)(TestBoard.X_DIM_TILES - getX());
			}
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
		}
		else{
			if (moving == false)
				image.setImage(DOWN1);
		}
		facing = DOWN;
	}
}
