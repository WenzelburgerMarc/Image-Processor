package MenuBarActions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Actions.CommandHandler;
import Actions.Commands.RenderAsciiArt;
import GUI.FrameStatics;
import OwnSwingComponents.Listener.MaxWriteChangeListener;

public class FileMenu {

	private String home = System.getProperty("user.home");
	private final String[] okFileExtensions = new String[] { "jpg", "jpeg", "png" };

	// Open File
	public void createNewImage(Color c) {

		int width = 512;
		int height = 512;

		// Create a new BufferedImage
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Get a Graphics2D object for the image
		Graphics2D g2d = image.createGraphics();

		// Fill the image with a solid color
		g2d.setColor(c);
		g2d.fillRect(0, 0, width, height);

		FrameStatics.originalInputImage = image;
		displayLoadedImageInJLabel(FrameStatics.originalInputImage);
		CommandHandler.imgHistory.clear();

	}

	public void openImageFromPath() {
		JFileChooser fc = new JFileChooser(home);
		int res = fc.showOpenDialog(null);
		// We have an image!
		try {
			if (res == JFileChooser.APPROVE_OPTION) {
				java.io.File file = fc.getSelectedFile();
				if (accept(file)) {
					FrameStatics.originalInputImage = ImageIO.read(file);
					displayLoadedImageInJLabel(FrameStatics.originalInputImage);
					CommandHandler.imgHistory.clear();
				} else {
					JOptionPane.showMessageDialog(null, "Wrong File-Format!", "Error...", JOptionPane.WARNING_MESSAGE);
				}

			}
		} catch (Exception iOException) {
		}
	}

	public void openImageFromURL() {

		try {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

			// Get the URL string from the clipboard
			String urlString = (String) clipboard.getData(DataFlavor.stringFlavor);
			URL imageURL = new URL(urlString);

			// Get the image from the URL
			Image image = Toolkit.getDefaultToolkit().getImage(imageURL);

			FrameStatics.originalInputImage = image;
			displayLoadedImageInJLabel(FrameStatics.originalInputImage);
			CommandHandler.imgHistory.clear();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Wrong File-Format!", "Error...", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void openImageFromClipBoard() {

		try {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

			if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {

				FrameStatics.originalInputImage = (Image) clipboard.getData(DataFlavor.imageFlavor);
				displayLoadedImageInJLabel(FrameStatics.originalInputImage);
				CommandHandler.imgHistory.clear();
			} else {
				JOptionPane.showMessageDialog(null, "Wrong File-Format!", "Error...", JOptionPane.WARNING_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Wrong File-Format!", "Error...", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void displayLoadedImageInJLabel(Image image) {

		ImageIcon imageIcon = new ImageIcon(image);
		FrameStatics.lblImgInputFile.setIcon(new ImageIcon(getScaledImage(image,
				FrameStatics.lblImgInputFile.getWidth(), FrameStatics.lblImgInputFile.getHeight())));
		MaxWriteChangeListener.change();
	}

	public static Image getScaledImage(Image image, int labelWidth, int labelHeight) {
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);

		double scaleFactor = Math.min((double) labelWidth / imageWidth, (double) labelHeight / imageHeight);

		int scaledWidth = (int) (scaleFactor * imageWidth);
		int scaledHeight = (int) (scaleFactor * imageHeight);

		FrameStatics.widthOriginalImage = imageWidth;
		FrameStatics.heightOriginalImage = imageHeight;
		FrameStatics.txtResizeWidth.setText("" + FrameStatics.widthOriginalImage);
		FrameStatics.txtResizeHeight.setText("" + FrameStatics.heightOriginalImage);

		BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);

		scaledImage.getGraphics().drawImage(image, 0, 0, scaledWidth, scaledHeight, null);

		return scaledImage;
	}

	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);

		bufferedImage.getGraphics().drawImage(image, 0, 0, null);

		return bufferedImage;
	}

	public boolean accept(File file) {
		for (String extension : okFileExtensions) {
			if (file.getName().toLowerCase().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}

	// Save Image
	public void saveImageAs() {
		if (FrameStatics.originalInputImage != null) {
			JFileChooser fileChooser = new JFileChooser();
			FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
			fileChooser.setFileFilter(imageFilter);
			int returnValue = fileChooser.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				String filePath = selectedFile.getAbsolutePath();
				if (!filePath.endsWith(".png") && !filePath.endsWith(".jpg")) {
					selectedFile = new File(filePath + ".png");
				}
				try {
					ImageIO.write(toBufferedImage(FrameStatics.originalInputImage), "png", selectedFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "An Error Occured", "Error...", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void saveAsciiArtAs() {

		try {
			File sourceFile = new File(RenderAsciiArt.filePath);

			// File Chooser öffnen
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        fileChooser.setAcceptAllFileFilterUsed(false);
	        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
	        int result = fileChooser.showSaveDialog(null);

	        // Wenn der Benutzer einen Speicherort ausgewählt hat
	        if (result == JFileChooser.APPROVE_OPTION) {
	            File destFile = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".txt");
	            try {
	                // Datei kopieren
	                copyFile(sourceFile, destFile);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "An Error Occured", "Error...", JOptionPane.WARNING_MESSAGE);
		}

	}
	
	public void saveReadTextAs() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
		fileChooser.setAcceptAllFileFilterUsed(false);
	    int returnValue = fileChooser.showSaveDialog(null);
	    if (returnValue == JFileChooser.APPROVE_OPTION) {
	      File selectedFile = fileChooser.getSelectedFile();
	      try (FileWriter fileWriter = new FileWriter(selectedFile)) {
	        fileWriter.write(FrameStatics.txtAreaRead.getText());
	      } catch (IOException ex) {
	        ex.printStackTrace();
	      }
	    }
	}
	
    // Funktion zum Kopieren einer Datei
    private void copyFile(File sourceFile, File destFile) throws IOException {
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }
}
