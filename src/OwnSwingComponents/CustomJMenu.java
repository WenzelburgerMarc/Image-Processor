package OwnSwingComponents;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JMenu;

public class CustomJMenu extends JMenu {

    private Color dotColor;
    private Point center;
    private int width;
    private int height;
    private int currentWidth = 0;
    private Timer animationTimer;

    public CustomJMenu(String text, Color dotColor) {
        super(text);
        this.dotColor = dotColor;
        addMouseListener(createMouseListener());
    }

    private MouseListener createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                //if (!isPopupMenuVisible()) {
                    startAnimation(true);
                //}
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //if (!isPopupMenuVisible()) {
                    startAnimation(false);
               // }
            }
        };
    }

    private void startAnimation(boolean expand) {
        width = getWidth();
        height = getHeight();
        center = new Point(width / 2, height / 2 + getFont().getSize() + 2);

        if (animationTimer != null) {
            animationTimer.cancel();
        }

        animationTimer = new Timer();
        animationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (expand) {
                    currentWidth += 5;
                } else {
                    currentWidth -= 5;
                }

                if (currentWidth < 0 || currentWidth+20 > width) {
                    animationTimer.cancel();
                }

                repaint();
            }
        }, 0, 10);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (center == null) {
            return;
        }
        g.setColor(dotColor);
        g.fillRect(center.x - currentWidth / 2, center.y - 1, currentWidth, 2);

    }
}
