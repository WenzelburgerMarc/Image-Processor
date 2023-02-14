package Actions.Commands;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import Actions.Command;
import Actions.CommandNames;
import GUI.FrameStatics;
import MenuBarActions.FileMenu;

public class Resize implements Command {

	@Override
	public Image execute(Image img) {
			BufferedImage image = FileMenu.toBufferedImage(img);
			int width = image.getWidth();
		    int height = image.getHeight();
		    BufferedImage scaledImage = new BufferedImage(Integer.parseInt(FrameStatics.txtResizeWidth.getText()), Integer.parseInt(FrameStatics.txtResizeHeight.getText()), image.getType());
		    
		    for (int x = 0; x < Integer.parseInt(FrameStatics.txtResizeWidth.getText()); x++) {
		      for (int y = 0; y < Integer.parseInt(FrameStatics.txtResizeHeight.getText()); y++) {
		        int originalX = (int) (x * width / (double) Integer.parseInt(FrameStatics.txtResizeWidth.getText()));
		        int originalY = (int) (y * height / (double) Integer.parseInt(FrameStatics.txtResizeHeight.getText()));
		        Color color = new Color(image.getRGB(originalX, originalY));
		        scaledImage.setRGB(x, y, color.getRGB());
				int percentage = (int) ((double) (x * Integer.parseInt(FrameStatics.txtResizeHeight.getText()) + y + 1) / (Integer.parseInt(FrameStatics.txtResizeWidth.getText())*Integer.parseInt(FrameStatics.txtResizeHeight.getText())) * 100);
				
				if(FrameStatics.liquidProgress != null)
		        FrameStatics.liquidProgress.setValue((int)percentage);
		      }
		    }
		    return scaledImage;

	}
	
	@Override
	public CommandNames getName() {
		return CommandNames.Resize;
	}

}
