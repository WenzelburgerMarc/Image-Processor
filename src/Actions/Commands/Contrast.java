package Actions.Commands;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import Actions.Command;
import Actions.CommandNames;
import GUI.FrameStatics;
import MenuBarActions.FileMenu;

public class Contrast implements Command{

	@Override
	public Image execute(Image img) {
			BufferedImage image = FileMenu.toBufferedImage(img);
		    int contrast = FrameStatics.sliderContrast.getValue();
		    BufferedImage outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

		    for (int x = 0; x < image.getWidth(); x++) {
		      for (int y = 0; y < image.getHeight(); y++) {
		        int color = image.getRGB(x, y);
		        int r = (color >> 16) & 0xff;
		        int g = (color >> 8) & 0xff;
		        int b = color & 0xff;

		        r = (int) (r * contrast);
		        g = (int) (g * contrast);
		        b = (int) (b * contrast);

		        r = Math.min(Math.max(0, r), 255);
		        g = Math.min(Math.max(0, g), 255);
		        b = Math.min(Math.max(0, b), 255);

		        int newColor = (r << 16) | (g << 8) | b;
		        outputImage.setRGB(x, y, newColor);
				int percentage = (int) ((double) (x * image.getHeight() + x + 1) / (image.getHeight()*image.getWidth()) * 100);
				
				if(FrameStatics.liquidProgress != null)
		        FrameStatics.liquidProgress.setValue((int)percentage);
		      }
		    }
			

		    return outputImage;
			
	}

	@Override
	public CommandNames getName() {
		return CommandNames.Contrast;
	}

}
