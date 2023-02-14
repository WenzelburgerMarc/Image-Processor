package Actions.Commands;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextArea;

import Actions.Command;
import Actions.CommandNames;
import GUI.FrameStatics;
import MenuBarActions.FileMenu;

//FrameStatics.lblAsciiImagePreview
public class RenderAsciiArt implements Command {
	public static String filePath;

	@Override
	public Image execute(Image img) {
		BufferedImage image = FileMenu.toBufferedImage(img);

		int width = image.getWidth();
		int height = image.getHeight();

		// ASCII-Zeichen für Helligkeitswerte definieren
		char[] asciiChars = { ' ', '.', '*', ':', 'o', '&', '8', '#', '@' };

		try {

			// Pfad zum temporären Ordner
			String tempDirectory = System.getProperty("java.io.tmpdir");
			// Name der Textdatei
			String fileName = "ascii_art_temp.txt";
			// Vollständiger Pfad zur Textdatei
			filePath = tempDirectory + File.separator + fileName;
			System.out.println(filePath);
			FileWriter writer = new FileWriter(filePath);

			// ASCII-Art generieren und in Textdatei schreiben
			try {
				for (int y = 0; y < height; y+=2) {
					for (int x = 0; x < width; x++) {
						int rgb = image.getRGB(x, y);
						int gray = (int) ((0.3 * ((rgb >> 16) & 0xff)) + (0.59 * ((rgb >> 8) & 0xff))
								+ (0.11 * (rgb & 0xff)));
						int index = gray * (asciiChars.length - 1) / 255;
						writer.write(asciiChars[index]);
						int percentage = (int) ((double) (y * width + x + 1) / (height*width) * 100);
						
						if(FrameStatics.liquidProgress != null)
				        FrameStatics.liquidProgress.setValue((int)percentage);
					}
					writer.write("\n");
				}
			} catch (Exception e) {

			}

			writer.close();

			String asciiArt = readAsciiArtFromFile();
			int fontSize = determineFontSize(asciiArt, FrameStatics.txtAreaAsciiImagePreview);
			FrameStatics.txtAreaAsciiImagePreview.setFont(new Font("Courier", Font.PLAIN, fontSize));
			FrameStatics.txtAreaAsciiImagePreview.setText(asciiArt);

		} catch (IOException e) {
			System.out.println("Fehler beim Schreiben der Textdatei");
		}
		return image;
	}

	private int determineFontSize(String asciiArt, JTextArea textArea) {
		int fontSize = 1;
		boolean fontSizeIsCorrect = false;
		Font font = new Font("Courier", Font.PLAIN, fontSize);
		textArea.setFont(font);

		while (!fontSizeIsCorrect) {
			if (textArea.getPreferredSize().height > textArea.getHeight()
					|| textArea.getPreferredSize().width > textArea.getWidth()) {
				fontSize--;
				font = new Font("Courier", Font.PLAIN, fontSize);
				textArea.setFont(font);
			} else {
				fontSizeIsCorrect = true;
			}
		}

		return fontSize;
	}

	private String readAsciiArtFromFile() {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		return sb.toString();
	}

	@Override
	public CommandNames getName() {
		return CommandNames.RenderAsciiArt;
	}

}
