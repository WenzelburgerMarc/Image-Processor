package GUI;

import java.awt.Image;
import javax.swing.Timer;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Actions.CommandHandler;
import OwnSwingComponents.CustomProgressBar.CustomProgressBar;

public class FrameStatics {
	// Menu Actions
	public static JMenuItem miCreateNewImage, miOpenFileUrl, miOpenFileFromFile, miOpenFileClippboard;
	public static JMenuItem miSaveImages, miSaveAsciiArt, miSaveReadRGBText;

	// LeftPanel - Loaded Image + Rendered Ascii Preview
	public static JLabel lblImgInputFile;
	public static JTextArea txtAreaAsciiImagePreview;

	// Rotate / Effects / Undo
	public static JButton btnRotateImageLeft, btnRotateImageRight, btnImageInvert, btnImageMonochrome, btnImageUndo;

	// Resize
	public static JTextField txtResizeWidth, txtResizeHeight;
	public static JButton btnApplyResize;

	// Blur
	public static JSlider sliderBlur;
	public static JButton btnApplyBlur;
	public static JLabel lblBlurValue;

	// Contrast
	public static JSlider sliderContrast;
	public static JButton btnApplyContrast;
	public static JLabel lblContrastValue;

	// Read RGB
	public static JCheckBox chbxRedRead, chbxGreenRead, chbxBlueRead, chbxAutoRead;
	public static JButton btnReadRGB;
	public static JTextArea txtAreaRead;

	// Write RGB
	public static JCheckBox chbxRedWrite, chbxGreenWrite, chbxBlueWrite;
	public static JButton btnWriteRGB;
	public static JTextArea txtAreaWrite;
	public static JLabel lblMaxChars;

	// Render Ascii Art
	public static JButton btnRenderAsciiArt;
	
	//Original Image
	public static Image originalInputImage;
	public static int widthOriginalImage, heightOriginalImage;
	
	//Generall
	public static String strProgramName="ImoceZ";
	public static CommandHandler cmdHandler;
	public static JPanel panelAsciiPreview;
	public static CustomProgressBar liquidProgress;
	

}
