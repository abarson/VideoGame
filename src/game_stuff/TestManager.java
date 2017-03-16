package game_stuff;

public class TestManager {
	private final GameApplication game;
	private Hero hero;
	private Map map;
	
	public final static int ROAM = 0; //indicates Hero is free to walk around.
	public final static int MENU = 1; //indicates a menu is open on screen
	
	//By default, the character is in ROAM state.
	private int gameState = ROAM;
	
	public TestManager(GameApplication game, Map map){
		this.map = map;
		this.game = game;
		hero = new Hero(map);
		hero.setLocation(7, 10);
		if (!GameApplication.DEBUG)
			game.setUpCamera(hero);
		map.initVisible(hero);
	}
	
	public int getState(){
		return gameState;
	}
	
	public void click(){
		if (gameState == MENU){
			if (map.getCurrentMenu().click()){
				gameState = ROAM;
				hero.thaw();
			}
		}
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
		else if(gameState == MENU){
			map.getCurrentMenu().up();
		}
	}
	public void down(){
		if (gameState == ROAM){
			hero.moveDown();
		}
		else if(gameState == MENU){
			map.getCurrentMenu().down();
		}
	}
	
	public void animationLoop(){
		hero.incrementIdle();
	}
	
	public void enter(){
		if (gameState == ROAM){
			if (hero.interactWith()){
				gameState = MENU;
				hero.freeze();
			}
		}
		else if (gameState == MENU){
			if (map.getCurrentMenu().enter()){
				gameState = ROAM;
				hero.thaw();
			}
		}
	}
	public void e(){
		if (gameState == ROAM && !hero.getMoving()){
			hero.createMenu();;
			gameState = MENU;
			hero.freeze();
		}
		else if (gameState == MENU){
			if (map.getCurrentMenu().enter()){
				gameState = ROAM;
				hero.thaw();
			}
		}
	}
}
