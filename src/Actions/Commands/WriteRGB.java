package Actions.Commands;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Actions.Command;
import Actions.CommandNames;
import GUI.FrameStatics;
import MenuBarActions.FileMenu;

public class WriteRGB implements Command {

	@Override
	public Image execute(Image img) {
		try {

			BufferedImage image = FileMenu.toBufferedImage(img);
			int width = image.getWidth();
			int height = image.getHeight();
			int textIndex = 0;
			int r = 0;
			int b = 0;
			int g = 0;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {

					if (textIndex >= FrameStatics.txtAreaWrite.getText().length()) {
						break;
					}

					 r = 0;
					 b = 0;
					 g = 0;

					if (FrameStatics.chbxRedWrite.isSelected()
							&& textIndex < FrameStatics.txtAreaWrite.getText().length()) {
						r = (int) FrameStatics.txtAreaWrite.getText().charAt(textIndex);
						textIndex++;
					}

					if (FrameStatics.chbxGreenWrite.isSelected()
							&& textIndex < FrameStatics.txtAreaWrite.getText().length()) {
						g = (int) FrameStatics.txtAreaWrite.getText().charAt(textIndex);
						textIndex++;
					}

					if (FrameStatics.chbxBlueWrite.isSelected()
							&& textIndex < FrameStatics.txtAreaWrite.getText().length()) {
						b = (int) FrameStatics.txtAreaWrite.getText().charAt(textIndex);
						textIndex++;
					}
					int rgb = (r << 16) | (g << 8) | b;

					image.setRGB(x, y, rgb);
					
					if (FrameStatics.cmdHandler.startWorking.interrupted()) {
                        return null;
                        
                    }
					
					int percentage = (int) (100.0 * textIndex / FrameStatics.txtAreaWrite.getText().length());

					if (FrameStatics.liquidProgress != null)
						FrameStatics.liquidProgress.setValue((int) percentage);
				}
			}

			return image;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error, please make sure that the text isnt too long!");
			return null;
		}

	}

	@Override
	public CommandNames getName() {
		return CommandNames.WriteRGB;
	}

}
