package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.formdev.flatlaf.FlatDarculaLaf;

public class ColorPickerFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JSlider redSlider, greenSlider, blueSlider;
	private JLabel redLabel, greenLabel, blueLabel;
	private JPanel previewPanel;

	public ColorPickerFrame() {
		FlatDarculaLaf.setup();

		try {
			UIManager.setLookAndFeel(new FlatDarculaLaf());

		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		setTitle("Color Picker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		setUndecorated(true);

		redLabel = new JLabel("Red: 255");
		greenLabel = new JLabel("Green: 255");
		blueLabel = new JLabel("Blue: 255");

		redSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
		greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
		blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);

		redSlider.addChangeListener(new SliderListener());
		greenSlider.addChangeListener(new SliderListener());
		blueSlider.addChangeListener(new SliderListener());

		redSlider.setPreferredSize(new Dimension(200, 50));
		greenSlider.setPreferredSize(new Dimension(200, 50));
		blueSlider.setPreferredSize(new Dimension(200, 50));

		previewPanel = new JPanel();
		previewPanel.setBackground(Color.white);
		previewPanel.setPreferredSize(new Dimension(100, 100));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(Box.createVerticalStrut(10));

		JPanel redPanel = new JPanel();
		redPanel.setLayout(new BoxLayout(redPanel, BoxLayout.X_AXIS));
		redPanel.add(Box.createHorizontalStrut(10));
		redPanel.add(redLabel);
		mainPanel.add(redPanel);

		mainPanel.add(Box.createVerticalStrut(5));
		mainPanel.add(redSlider);
		mainPanel.add(Box.createVerticalStrut(5));

		JPanel greenPanel = new JPanel();
		greenPanel.setLayout(new BoxLayout(greenPanel, BoxLayout.X_AXIS));
		greenPanel.add(Box.createHorizontalStrut(10));
		greenPanel.add(greenLabel);
		mainPanel.add(greenPanel);

		mainPanel.add(Box.createVerticalStrut(5));
		mainPanel.add(greenSlider);
		mainPanel.add(Box.createVerticalStrut(5));

		JPanel bluePanel = new JPanel();
		bluePanel.setLayout(new BoxLayout(bluePanel, BoxLayout.X_AXIS));
		bluePanel.add(Box.createHorizontalStrut(10));
		bluePanel.add(blueLabel);
		mainPanel.add(bluePanel);

		mainPanel.add(Box.createVerticalStrut(5));
		mainPanel.add(blueSlider);
		mainPanel.add(Box.createVerticalStrut(5));

		mainPanel.add(previewPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton cancelButton = new JButton("Cancel");
		JButton applyButton = new JButton("Apply");

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Frame.frame.setEnabled(true);
				dispose();
			}
		});

		applyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Frame.fileManager
						.createNewImage(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
				Frame.frame.setEnabled(true);
				dispose();
			}
		});

		buttonPanel.add(cancelButton);
		buttonPanel.add(applyButton);

		mainPanel.add(Box.createVerticalStrut(5));
		mainPanel.add(buttonPanel);

		add(mainPanel);
		setVisible(true);

	}

	private class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {

			int red = redSlider.getValue();
			int green = greenSlider.getValue();
			int blue = blueSlider.getValue();

			redLabel.setText("Red: " + red);
			greenLabel.setText("Green: " + green);
			blueLabel.setText("Blue: " + blue);

			previewPanel.setBackground(new Color(red, green, blue));
		}
	}
}
