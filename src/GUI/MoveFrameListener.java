package GUI;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MoveFrameListener implements MouseListener, MouseMotionListener {

	private Point pressedPoint;
	private Rectangle frameBounds;

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
		this.frameBounds = Frame.frame.getBounds();
		this.pressedPoint = event.getPoint();
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		moveJFrame(event);
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		moveJFrame(event);
	}

	@Override
	public void mouseMoved(MouseEvent event) {
	}

	private void moveJFrame(MouseEvent event) {
		Point endPoint = event.getPoint();

		int xDiff = endPoint.x - pressedPoint.x;
		int yDiff = endPoint.y - pressedPoint.y;
		frameBounds.x += xDiff;
		frameBounds.y += yDiff;
		Frame.frame.setBounds(frameBounds);
		Frame.xPos = frameBounds.x;
		Frame.yPos = frameBounds.y;
		
	}

}