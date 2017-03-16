package game_stuff;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameApplication extends Application{
	//for debugging
	public final static boolean DEBUG = false;
	
	private Map map;
	private Timeline animation;
	private int updateTime = 200;
	private Scene scene;
	private Group group;
	private TestManager gameManager;
	public static void main(String [] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		map = new Map(this);
		
		group = new Group(map);
		group.setManaged(false);
		Pane root = new Pane(group);
		
		if (!DEBUG){
		root.setPrefSize((Map.WINDOW_X) * Map.TILE_SIZE, 
				(Map.WINDOW_Y) * Map.TILE_SIZE);
			//updateCamera();
		}
		else{
			root.setPrefSize(Map.X_DIM_TILES * Map.TILE_SIZE,
					Map.Y_DIM_TILES * Map.TILE_SIZE);
		}
		gameManager = new TestManager(this, map);
		scene = new Scene(root);
		
		setUpKeyPresses();
		setUpAnimation();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/*
	 * creates animation that is updated every updateTime interval
	 */
	private void setUpAnimation() {
        EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
        	gameManager.animationLoop();
        };
        animation = new Timeline(new KeyFrame(Duration.millis(updateTime), 
        		eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }
	
	/**
	 * Initialize camera to be following hero.
	 * @param hero
	 */
	public void setUpCamera(Hero hero){
		group.setTranslateX(-hero.imageX()
				+ (Map.CAMERA_X) * Map.TILE_SIZE + hero.getRelX() * Map.TILE_SIZE);
		group.setTranslateY(-hero.imageY()
				+ (Map.CAMERA_Y) * Map.TILE_SIZE + hero.getRelY() * Map.TILE_SIZE);
	}
	public Group getGroup(){
		return group;
	}
	
	private void setUpKeyPresses(){
		scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    gameManager.left();
                    break;
                case RIGHT:
                	gameManager.right();
                    break;
                case DOWN:
                	gameManager.down();
                    break;
                case UP:
                	gameManager.up();
                    break;
                case ENTER:
                	gameManager.enter();
                    break;
                case E:
                	gameManager.e();
                    break;
                default:
                	break;
            }
        });
		scene.setOnMouseClicked(e -> {
			gameManager.click();
		});
	}
}