package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarculaLaf;

import OwnSwingComponents.CustomProgressBar.CustomProgressBar;

public class ProgressBarFrame extends JFrame {
	public ProgressBarFrame() {
		super("ProgressBar");

		FlatDarculaLaf.setup();

		try {
			UIManager.setLookAndFeel(new FlatDarculaLaf());
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// You can set the content pane of the frame to your custom class.
		setUndecorated(true);
		setIconImage(Frame.icon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);

		FrameStatics.liquidProgress = new CustomProgressBar();

		FrameStatics.liquidProgress.setBackground(Color.DARK_GRAY);
		FrameStatics.liquidProgress.setForeground(Color.DARK_GRAY.darker());
		FrameStatics.liquidProgress.setValue(0);
		FrameStatics.liquidProgress.setAnimateColor(new java.awt.Color(255, 255, 255));
		FrameStatics.liquidProgress.setBorderColor(Color.DARK_GRAY.darker().darker());
		FrameStatics.liquidProgress.setBorderSize(8);
		FrameStatics.liquidProgress.setSpaceSize(10);

		JPanel FixedPanel = new JPanel(new GridBagLayout());
		FixedPanel.setSize(1280, 720);

		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(120, 160));
		myPanel.setBackground(null);
		myPanel.add(FrameStatics.liquidProgress);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(100, 30));
		cancelButton.setFocusable(false);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//FrameStatics.cmdHandler.canceled = true;
				
				if (FrameStatics.cmdHandler.startWorking != null && FrameStatics.cmdHandler.startWorking.isAlive()) {
					FrameStatics.cmdHandler.canceled = true;
					FrameStatics.cmdHandler.startWorking.interrupt();
				}

			}
		});

		myPanel.add(cancelButton);

		FixedPanel.add(myPanel);
		add(FixedPanel);

		setVisible(true);
		Frame.frame.setVisible(false);

	}
}
