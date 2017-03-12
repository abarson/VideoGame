package game_stuff;

import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class GameManager {
	
	private Game game;
	public GameManager(Game game){
		this.game = game;
	}
	public void startSelectionScreen(){
		game.setScene(new SelectionScreen(this));
	}
	public void startNewGame(){
		game.setScene(new TestLevel(this));
	}
	
	//figure it out later
	/*public void fadeToBlack(){
		FadeTransition ft = new FadeTransition(Duration.seconds(2));
		ft.play();
	}*/
}
