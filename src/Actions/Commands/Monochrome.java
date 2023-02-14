package Actions.Commands;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import Actions.Command;
import Actions.CommandNames;
import GUI.FrameStatics;
import MenuBarActions.FileMenu;

public class Monochrome implements Command{

	@Override
	public Image execute(Image img) {
		BufferedImage image = FileMenu.toBufferedImage(img);
		int width = image.getWidth();
	    int height = image.getHeight();
	    BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	    
	    for (int i = 0; i < width; i++) {
	      for (int j = 0; j < height; j++) {
	        Color color = new Color(image.getRGB(i, j));
	        int gray = (int) (0.2989 * color.getRed() + 0.5870 * color.getGreen() + 0.1140 * color.getBlue());
	        grayscaleImage.setRGB(i, j, new Color(gray, gray, gray).getRGB());
			int percentage = (int) ((double) (i * height + j + 1) / (height*width) * 100);
			
			if(FrameStatics.liquidProgress != null)
	        FrameStatics.liquidProgress.setValue((int)percentage);
	      }
	    }
	    
	    return grayscaleImage;
	}
	
	@Override
	public CommandNames getName() {
		return CommandNames.Monochrome;
	}

}
