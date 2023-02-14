package OwnSwingComponents.CustomProgressBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JProgressBar;

public class CustomProgressBar extends JProgressBar {

	private static final long serialVersionUID = 1L;

	private final CustomProgressBarUI progressBarUI;
	private int borderSize = 4;
	private int spaceSize = 4;
	private Color animateColor = new Color(125, 216, 255);
	private Color borderColor = new Color(0, 178, 255);

	public CustomProgressBar() {
		progressBarUI = new CustomProgressBarUI(this);
		setOpaque(false);
		setFont(new Font(getFont().getFamily(), 1, 20));
		setPreferredSize(new Dimension(100, 100));
		setBackground(Color.WHITE);
		setForeground(new Color(0, 178, 255));
		setUI(progressBarUI);
		setStringPainted(true);
	}

	public int getBorderSize() {
		return borderSize;
	}

	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}

	public int getSpaceSize() {
		return spaceSize;
	}

	public void setSpaceSize(int spaceSize) {
		this.spaceSize = spaceSize;
	}

	public Color getAnimateColor() {
		return animateColor;
	}

	public void setAnimateColor(Color animateColor) {
		this.animateColor = animateColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void startAnimation() {
		progressBarUI.start();
	}

	public void stopAnimation() {
		progressBarUI.stop();
	}

}
