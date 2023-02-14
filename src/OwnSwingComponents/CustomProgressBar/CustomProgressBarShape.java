package OwnSwingComponents.CustomProgressBar;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

public class CustomProgressBarShape {

	public Rectangle getSize() {
		return size;
	}

	public void setSize(Rectangle size) {
		this.size = size;
	}

	public CustomProgressBarShape(Rectangle size) {
		this.size = size;
	}

	public CustomProgressBarShape() {
	}

	private Rectangle size;

	public Shape createProgressArt() {
		double width = size.getWidth();
	    double height = size.getHeight();
	    double space = width / 4;
	    double x = size.getX();
	    double y = size.getY();

	    GeneralPath gp1 = new GeneralPath();
	    gp1.moveTo(x, y + height);
	    gp1.curveTo(x + space, y + height, x + space, y, x + width / 2, y);
	    gp1.lineTo(x + width / 2, y + height);

	    GeneralPath gp2 = new GeneralPath();
	    gp2.moveTo(x + width / 2, y);
	    gp2.curveTo(x + width - space, y, x + width - space, y + height, x + width, y + height);
	    gp2.lineTo(x + width / 2, y + height);

	    gp1.append(gp2, false);

	    return gp1;
	}
}
