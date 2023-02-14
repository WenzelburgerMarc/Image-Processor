package Actions.Commands;

import java.awt.Image;
import java.awt.image.BufferedImage;

import Actions.Command;
import Actions.CommandNames;
import GUI.FrameStatics;
import MenuBarActions.FileMenu;

public class RotateRight implements Command{

	@Override
	public Image execute(Image img) {
		
		
	    BufferedImage image = FileMenu.toBufferedImage(img);
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        
        BufferedImage rotatedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int newX = height - y - 1;
                int newY = x;
                rotatedImage.setRGB(newX, newY, image.getRGB(x, y));
				int percentage = (int) ((double) (x * height + y + 1) / (height*width) * 100);
				
				if(FrameStatics.liquidProgress != null)
		        FrameStatics.liquidProgress.setValue((int)percentage);
            }
        }
        
        return rotatedImage;
	}
	
	@Override
	public CommandNames getName() {
		return CommandNames.RotateRight;
	}

}
