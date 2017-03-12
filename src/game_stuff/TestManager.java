package game_stuff;

public class TestManager {
	private final GameApplication game;
	private Hero hero;
	private Map map;
	//current menu
	private InGameMenu menu;
	
	public final static int ROAM = 0;
	public final static int DIALOGUE = 1;
	
	//By default, the character is in ROAM state.
	private int gameState = ROAM;
	public TestManager(GameApplication game, Hero hero){
		menu = null;
		this.game = game;
		this.hero = hero;
		//this.map = map;
	}
	
	public void setMenu(InGameMenu menu){
		this.menu = menu;
	}
	
	public void setState(int code){
		gameState = code;
	}
	public int getState(){
		return gameState;
	}
	
	public void left(){
		if (gameState == ROAM){
			hero.moveLeft();
		}
	}
	public void right(){
		if (gameState == ROAM){
			hero.moveRight();
		}
	}
	public void up(){
		if (gameState == ROAM){
			hero.moveUp();
		}
	}
	public void down(){
		if (gameState == ROAM){
			hero.moveDown();
		}
	}
	public void enter(){
		if (gameState == ROAM){
			hero.interactWith();
		}
		else if (gameState == DIALOGUE){
			menu.enter();
		}
	}
	public void e(){
		if (gameState == ROAM){
			hero.interactWith();
		}
	}
	
	void update(){
		//testBoard.getCharacter().swapImage();
	}
}
