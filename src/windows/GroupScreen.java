package windows;

import components.GroupGUI;
import gui.Main.AddPersonListener;
import gui.Window;
public class GroupScreen extends Window{

	private AddPersonListener l;
	
	public GroupScreen(AddPersonListener l) {
		this.l = l;
		init();
	}

	@Override
	public void init() {
		GroupGUI g = new GroupGUI(this, l);
		getChildren().add(g);
	}

}
