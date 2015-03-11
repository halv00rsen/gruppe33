package windows;

import components.GroupGUI;

import gui.Window;
public class GroupScreen extends Window{

	public GroupScreen() {
		init();
	}

	@Override
	public void init() {
		GroupGUI g = new GroupGUI(this);
		getChildren().add(g);
	}

}
