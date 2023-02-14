package OwnSwingComponents.CustomProgressBar;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class CustomProgressBarUI extends BasicProgressBarUI {

	private CustomProgressBar progressBar;
	private Thread thread;
	private boolean started;
	private float location1 = -1f;
	private float location2;

	public CustomProgressBarUI() {
		init();
	}

	public CustomProgressBarUI(CustomProgressBar progressBar) {
		this.progressBar = progressBar;
		init();
	}

	private void init() {
		start();
	}

	public synchronized void start() {
		if (!started) {
			started = true;
			thread = new Thread(() -> {
				while (started) {
					location1 += 0.01f;
					location2 += 0.01f;
					if (location1 > 1f) {
						location1 = -1f;
					}
					if (location2 > 1f) {
						location2 = -1f;
					}
					progressBar.repaint();
					sleep();
				}
			});
			thread.start();
		}
	}

	public void stop() {
		started = false;
	}

	private void sleep() {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			Logger.getLogger(CustomProgressBarUI.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	@Override
	public void paint(Graphics g, JComponent jc) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int width = jc.getWidth();
		int height = jc.getHeight();
		int size = Math.min(width, height);
		int x = (width - size) / 2;
		int y = (height - size) / 2;

		g2d.setColor(progressBar.getBorderColor());
		g2d.fillOval(x, y, size, size);

		int borderSize = progressBar.getBorderSize();
		size -= borderSize * 2;
		g2d.setColor(progressBar.getBackground());
		g2d.fillOval(x + borderSize, y + borderSize, size, size);

		int spaceSize = progressBar.getSpaceSize();
		borderSize += spaceSize;
		size -= spaceSize * 2;
		createAnimation(g2d, x + borderSize, y + borderSize, size);

		if (progressBar.isStringPainted()) {
			paintString(g);
		}

		g2d.dispose();

	}

	private void createAnimation(Graphics2D g, int x, int y, int size) {
		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Ellipse2D circle = new Ellipse2D.Double(0, 0, size, size);
		g2.setColor(progressBar.getBackground());
		g2.fill(circle);
		g2.setComposite(AlphaComposite.SrcIn);
		int value = (int) (size * progressBar.getPercentComplete());
		int waterStyleHeight = (int) (size * 0.07f);
		g2.setColor(progressBar.getAnimateColor());
		g2.fillRect(0, size - value, size, size);
		int location1 = (int) (this.location1 * size);
		int location2 = (int) (this.location2 * size);
		g2.fill(new CustomProgressBarShape(
				new Rectangle(location1, size - value - waterStyleHeight, size, waterStyleHeight)).createProgressArt());
		g2.fill(new CustomProgressBarShape(
				new Rectangle(location2, size - value - waterStyleHeight, size, waterStyleHeight)).createProgressArt());
		g2.dispose();
		g.drawImage(img, x, y, null);
	}

	private void paintString(Graphics g) {
		Insets insects = progressBar.getInsets();
		int barRectWidth = progressBar.getWidth() - insects.right - insects.left;
		int barRectHeight = progressBar.getHeight() - insects.top - insects.bottom;
		
		g.setColor(progressBar.getBackground());
		paintString(g, insects.left, insects.top, barRectWidth, barRectHeight, 0, insects);
	}
}