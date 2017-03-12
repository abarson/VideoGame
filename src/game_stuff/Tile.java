package game_stuff;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Tile {
	
	private IntegerProperty x_cord = new SimpleIntegerProperty();
    private IntegerProperty y_cord = new SimpleIntegerProperty();
    private final Image IMAGE;
    private ImageView image;
	private TestBoard testBoard;
	
	public Tile(TestBoard testBoard, Image IMAGE){
		this.testBoard = testBoard;
		this.IMAGE = IMAGE;
		image = new ImageView(IMAGE);
		image.setFitHeight(TestBoard.TILE_SIZE);
		image.setFitWidth(TestBoard.TILE_SIZE);
		testBoard.getChildren().add(image);
		image.xProperty().bind(x_cord.multiply(TestBoard.TILE_SIZE));
        image.yProperty().bind(y_cord.multiply(TestBoard.TILE_SIZE));
	}
	public void setLocation(int x, int y) {
        x_cord.unbind();
        y_cord.unbind();
        x_cord.set(x); // due to binding, moves to x*SQUARE_SIZE, y*SQUARE_SIZE
        y_cord.set(y);
    }
}
