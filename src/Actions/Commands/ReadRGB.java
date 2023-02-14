package Actions.Commands;

import java.awt.Image;
import java.awt.image.BufferedImage;

import Actions.Command;
import Actions.CommandNames;
import GUI.FrameStatics;
import MenuBarActions.FileMenu;

public class ReadRGB implements Command {

	@Override
	public Image execute(Image img) {
		BufferedImage image = FileMenu.toBufferedImage(FrameStatics.originalInputImage);
		int width = image.getWidth();
		int height = image.getHeight();
		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = image.getRGB(x, y);
				int r = (rgb >> 16) & 0xff;
				int g = (rgb >> 8) & 0xff;
				int b = rgb & 0xff;

				if (FrameStatics.chbxRedRead.isSelected()) {

					sb.append((char) r);
				}
				if (FrameStatics.chbxGreenRead.isSelected()) {

					sb.append((char) g);
				}
				if (FrameStatics.chbxBlueRead.isSelected()) {

					sb.append((char) b);
				}

				int percentage = (int) ((double) (y * width + x + 1) / (height * width) * 100);

				if (FrameStatics.liquidProgress != null)
					FrameStatics.liquidProgress.setValue((int) percentage);

			}
		}

		FrameStatics.txtAreaRead.setText(sb.toString());
		FrameStatics.txtAreaRead.setCaretPosition(0);
		return image;
	}

	@Override
	public CommandNames getName() {
		return CommandNames.ReadRGB;
	}

}
