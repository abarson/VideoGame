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
import obsolete.TestBoard;

public abstract class GameTile {
	
	private IntegerProperty x_cord = new SimpleIntegerProperty();
    private IntegerProperty y_cord = new SimpleIntegerProperty();
    private final Image DEFAULT_IMAGE;
    private ImageView image;
	protected Map map;
	private boolean passable;
	public GameTile(Map map, Image IMAGE, boolean passable){
		DEFAULT_IMAGE = IMAGE;
		image = new ImageView(DEFAULT_IMAGE);
		image.setFitHeight(TestBoard.TILE_SIZE);
		image.setFitWidth(TestBoard.TILE_SIZE);
		this.map = map;
		map.getChildren().add(image);
		this.passable = passable;
		image.xProperty().bind(x_cord.multiply(TestBoard.TILE_SIZE));
        image.yProperty().bind(y_cord.multiply(TestBoard.TILE_SIZE));
	}
	
	public abstract void animation();
	
	public void setVisible(boolean visible){
		image.setVisible(visible);
	}
	public int getX(){
		return x_cord.get();
	}
	public int getY(){
		return y_cord.get();
	}
	
	public boolean getPassable(){
		return passable;
	}
	
	public void setLocation(int x, int y) {
        x_cord.unbind();
        y_cord.unbind();
        x_cord.set(x); 
        y_cord.set(y);
	}

}
