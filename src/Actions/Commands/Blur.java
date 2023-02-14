package Actions.Commands;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import Actions.Command;
import Actions.CommandNames;
import GUI.FrameStatics;
import MenuBarActions.FileMenu;

public class Blur implements Command {

	@Override
	public Image execute(Image img) {
		
			BufferedImage image = FileMenu.toBufferedImage(img);
			int width = image.getWidth();
			int height = image.getHeight();
			
			int strength = FrameStatics.sliderBlur.getValue();
			BufferedImage blurredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int r = 0, g = 0, b = 0;
					int count = 0;

					for (int i = -strength; i <= strength; i++) {
						for (int j = -strength; j <= strength; j++) {
							int yCoord = y + i;
							int xCoord = x + j;

							if (yCoord >= 0 && yCoord < height && xCoord >= 0 && xCoord < width) {
								int pixel = image.getRGB(xCoord, yCoord);
								r += (pixel >> 16 & 0xff);
								g += (pixel >> 8 & 0xff);
								b += (pixel & 0xff);
								count++;
							}
						}
					}

					r /= count;
					g /= count;
					b /= count;
					blurredImage.setRGB(x, y, (r << 16) | (g << 8) | b | 0xff000000);
					
					int percentage = (int) ((double) (y * width + x + 1) / (height*width) * 100);
					
					if(FrameStatics.liquidProgress != null)
			        FrameStatics.liquidProgress.setValue((int)percentage);
				}
			}

			return blurredImage;

	}

	@Override
	public CommandNames getName() {
		return CommandNames.Blur;
	}

}
