package game_stuff;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class GameTile {
	
	private IntegerProperty x_cord = new SimpleIntegerProperty();
    private IntegerProperty y_cord = new SimpleIntegerProperty();
    private final Image DEFAULT_IMAGE;
    private TestManager gameManager;
    private ImageView image;
	private Map map;
	private boolean passable;
	private boolean interact;
	private String interactText;
	public GameTile(Map map, Image IMAGE, boolean passable,
			boolean interact){
		DEFAULT_IMAGE = IMAGE;
		interactText = "Override.";
		image = new ImageView(DEFAULT_IMAGE);
		image.setFitHeight(TestBoard.TILE_SIZE);
		image.setFitWidth(TestBoard.TILE_SIZE);
		this.map = map;
		map.getChildren().add(image);
		this.passable = passable;
		this.interact = interact;
		image.xProperty().bind(x_cord.multiply(TestBoard.TILE_SIZE));
        image.yProperty().bind(y_cord.multiply(TestBoard.TILE_SIZE));
	}
	public void setVisible(boolean visible){
		image.setVisible(visible);
	}
	public int getX(){
		return x_cord.get();
	}
	public int getY(){
		return y_cord.get();
	}
	public boolean canInteract(){
		return interact;
	}
	
	public void setText(String text){
		interactText = text;
	}
	
	public String getText(){
		return interactText;
	}
	
	public void interact(){
		map.addDialogueBox(interactText);
		
		//PopupText text = new PopupText(interactText);
		/*InGameMenu box = new InGameMenu(map, interactText);
		map.getChildren().add(box);
		System.out.println(interactText);*/
	}
	
	public boolean getPassable(){
		return passable;
	}
	public ImageView getImage(){
		return image;
	}
	public void setLocation(int x, int y) {
        x_cord.unbind();
        y_cord.unbind();
        x_cord.set(x); // due to binding, moves to x*SQUARE_SIZE, y*SQUARE_SIZE
        y_cord.set(y);
    }
	private class PopupText extends StackPane{
		private String text;
		public PopupText(String input){
			text = input;
			Rectangle rectangle = new Rectangle(Map.TILE_SIZE * 
					Map.X_DIM_TILES, 50);
			rectangle.setFill(Color.WHITE);
			
			Text bar = new Text(input);
			bar.setFont(Font.font("Courier New", 20));
			this.setAlignment(Pos.CENTER);
			//this.setLayoutX(200);
			this.setLayoutY(Map.TILE_SIZE * 
					Map.Y_DIM_TILES-60);
			getChildren().addAll(rectangle, bar);
			
	}
	}

}
