package windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import components.GroupGUI;
import gui.Main.AddGroupListener;
import gui.Main.AddPersonListener;
import gui.Window;
public class GroupScreen extends Window{

	private AddPersonListener l;
	private AddGroupListener gl;
	
	public GroupScreen(AddPersonListener l, AddGroupListener gl) {
		this.l = l;
		this.gl = gl;
		init();
	}

	@Override
	public void init() {
		GroupGUI g = new GroupGUI(this,l, gl);
//		BorderPane.setAlignment(g, Pos.CENTER);
		BorderPane.setMargin(g, new Insets(20));
		borderPane.setCenter(g);
	}

}
