package Actions.Commands;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import Actions.Command;
import Actions.CommandNames;
import GUI.FrameStatics;
import MenuBarActions.FileMenu;

public class Invert implements Command{

	@Override
	public Image execute(Image img) {
		BufferedImage image = FileMenu.toBufferedImage(img);
	    int width = image.getWidth();
	    int height = image.getHeight();
	    BufferedImage invertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    
	    for (int i = 0; i < width; i++) {
	      for (int j = 0; j < height; j++) {
	        Color color = new Color(image.getRGB(i, j));
	        int r = 255 - color.getRed();
	        int g = 255 - color.getGreen();
	        int b = 255 - color.getBlue();
	        invertedImage.setRGB(i, j, new Color(r, g, b).getRGB());
			int percentage = (int) ((double) (i * height + j + 1) / (height*width) * 100);
			
			if(FrameStatics.liquidProgress != null)
	        FrameStatics.liquidProgress.setValue((int)percentage);
	      }
	    }
	    
	    return invertedImage;
	}

	@Override
	public CommandNames getName() {
		return CommandNames.Invert;
	}

}
