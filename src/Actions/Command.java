package Actions;

import java.awt.Image;

public interface Command {
	
	public Image execute(Image img);
	public CommandNames getName();

}
