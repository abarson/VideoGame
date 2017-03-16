package menu_stuff;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Title_Content extends Pane{
	private Label label1;
	private Label label2;
	private Label label3;
	public Title_Content(){
		label1 = new Label("Start");
		label2 = new Label("Options");
		label3 = new Label("Exit");
	}
}
