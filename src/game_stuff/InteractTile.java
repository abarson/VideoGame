package game_stuff;

import javafx.scene.image.Image;

public class InteractTile extends GameTile {
	
	private String interactText = "null";
	public InteractTile(Map map, Image IMAGE, boolean passable) {
		super(map, IMAGE, passable);
	}

	public void interact(Hero hero){
		map.addDialogue(interactText, hero);
	}
	
	public void setText(String text){
		interactText = text;
	}
	
	public String getText(){
		return interactText;
	}

	@Override
	public void animation() {
		// TODO Auto-generated method stub
		
	}
}
