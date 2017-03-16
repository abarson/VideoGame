package obsolete;

import javafx.animation.Timeline;
import game_stuff.TestManager;
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

public class TEST extends Application{

	private TestBoard board;
	private Character character;
	private Timeline animation;
	private TestManager testManager;
	private Scene scene;
	private Group group;
	public static void main(String [] args){
		launch(args);
	}
	
	public TEST(){
		
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		board = new TestBoard(this);
		character = board.getCharacter();
		//testManager = new TestManager(this, board);
		group = new Group(board);
		group.setManaged(false);
		Pane root = new Pane(group);
		root.setPrefSize(280, 280);
		group.setTranslateX(-character.getImage().xProperty().get()+80);
		group.setTranslateY(-character.getImage().yProperty().get()+80);
		board.setGroup(group);
		scene = new Scene(root);
		setUpKeyPresses(1);
		setUpAnimation();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	private void setUpAnimation() {
        // Create a handler
        EventHandler<ActionEvent> eventHandler = (ActionEvent e) -> {
            //testManager.update();
        	updateCamera();
        };
        animation = new Timeline(new KeyFrame(Duration.millis(200), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }
	public void updateCamera(){
		if (character.getX() + 5 <= TestBoard.X_DIM_TILES && character.getX() - 2 >= 0)
			group.setTranslateX(-character.getImage().xProperty().get()+80);
		if (character.getY() - 2 >= 0 && character.getY() + 5
				<= TestBoard.Y_DIM_TILES)
			group.setTranslateY(-character.getImage().yProperty().get()+80);
	}
	public Group getGroup(){
		return group;
	}
	
	private void setUpKeyPresses(int x){
		scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    board.getCharacter().moveLeft();
                    break;
                case RIGHT:
                	board.getCharacter().moveRight();
                    break;
                case DOWN:
                	board.getCharacter().moveDown();
                    break;
                case UP:
                	board.getCharacter().moveUp();
                    break;
                case ENTER:
                	board.getCharacter().interactWith();
                    break;

            }
        });
	}
	/*private void setUpKeyPresses() {
        board.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    board.getCharacter().moveLeft();
                    break;
                case RIGHT:
                	board.getCharacter().moveRight();
                    break;
                case DOWN:
                	board.getCharacter().moveDown();
                    break;
                case UP:
                	board.getCharacter().moveUp();
                    break;
                case ENTER:
                	board.getCharacter().interactWith();
                    break;

            }
        });
        board.requestFocus(); // board is focused to receive key input
	 	*/
    //}
}
