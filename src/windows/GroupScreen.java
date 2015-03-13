package windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import components.GroupGUI;
import gui.Window;
public class GroupScreen extends Window{

	public GroupScreen() {
		init();
	}

	@Override
	public void init() {
		GroupGUI g = new GroupGUI(this);
//		BorderPane.setAlignment(g, Pos.CENTER);
		borderPane.setMargin(g, new Insets(20));
		borderPane.setCenter(g);
	}

}
