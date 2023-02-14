package Actions;

import java.awt.Image;
import java.io.File;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import GUI.Frame;
import GUI.FrameStatics;
import GUI.ProgressBarFrame;
import MenuBarActions.FileMenu;

public class CommandHandler {

	private Image img;
	public static Stack<Image> imgHistory = new Stack<Image>();
	public static boolean canceled = false;
	public static Thread startWorking;
	ProgressBarFrame frameProgress;

	public void executeCommand(Command c) {
		canceled = false;
		if (FrameStatics.originalInputImage != null) {

			try {
				Thread initThread = new Thread(new Runnable() {

					@Override
					public void run() {
						if (CommandHandler.imgHistory.isEmpty()) {
							System.out.println("imgHistory is empty");
							img = FrameStatics.originalInputImage;
							CommandHandler.imgHistory.push(img);
							
						}

					}

				});
				initThread.start();

				Thread showProgressThread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							initThread.join();
						} catch (InterruptedException e) {

						}
						System.out.println("Initiating ProgressBar");
						frameProgress = new ProgressBarFrame();
						frameProgress.setBounds(Frame.xPos, Frame.yPos, 1280, 720);

					}
				});
				showProgressThread.start();

				startWorking = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							showProgressThread.join();
						} catch (InterruptedException e) {

						}
						System.out.println("Start Executing");
						try {
							img = c.execute(img);
						} catch (Exception n) {

						}

						if (img == null) {
							img = FrameStatics.originalInputImage;
							System.out.println("null");
							//Frame.frame.setContentPane(new JLabel(new ImageIcon(FrameStatics.originalInputImage)));
						}

					}
				});
				startWorking.start();

				Thread outputThread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							startWorking.join();
						} catch (InterruptedException e) {

						}
						System.out.println("Start output");
						Frame.frame.setVisible(true);
						if (!canceled) {
							System.out.println("Canceled = false");
							CommandHandler.imgHistory.push(img);

							FrameStatics.lblImgInputFile.setIcon(
									new ImageIcon(FileMenu.getScaledImage(img, FrameStatics.lblImgInputFile.getWidth(),
											FrameStatics.lblImgInputFile.getHeight())));

						} else {
							System.out.println("Canceled = true");
						}
						frameProgress.dispose();

					}
				});
				outputThread.start();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Make Sure to load an Image first!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Make Sure to load an Image first!");

		}

	}

	public void executeCommandNoUndo(Command c) {
		canceled = false;
		if (FrameStatics.originalInputImage != null) {

			try {
				Thread initThread = new Thread(new Runnable() {

					@Override
					public void run() {
						if (CommandHandler.imgHistory.isEmpty()) {
							System.out.println("imgHistory is empty");
							img = FrameStatics.originalInputImage;
							CommandHandler.imgHistory.push(img);
							
						}

					}

				});
				initThread.start();

				Thread showProgressThread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							initThread.join();
						} catch (InterruptedException e) {

						}
						System.out.println("Initiating ProgressBar");
						frameProgress = new ProgressBarFrame();
						frameProgress.setBounds(Frame.xPos, Frame.yPos, 1280, 720);

					}
				});
				showProgressThread.start();

				startWorking = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							showProgressThread.join();
						} catch (InterruptedException e) {

						}
						System.out.println("Start Executing");
						try {
							img = c.execute(img);
						} catch (Exception n) {

						}

						if (img == null) {
							img = FrameStatics.originalInputImage;
							System.out.println("null");
							//Frame.frame.setContentPane(new JLabel(new ImageIcon(FrameStatics.originalInputImage)));
						}

					}
				});
				startWorking.start();

				Thread outputThread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							startWorking.join();
						} catch (InterruptedException e) {

						}
						System.out.println("Start output");
						Frame.frame.setVisible(true);
						if (!canceled) {
							System.out.println("Canceled = false");

							FrameStatics.lblImgInputFile.setIcon(
									new ImageIcon(FileMenu.getScaledImage(img, FrameStatics.lblImgInputFile.getWidth(),
											FrameStatics.lblImgInputFile.getHeight())));

						} else {
							System.out.println("Canceled = true");
						}
						frameProgress.dispose();

					}
				});
				outputThread.start();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Make Sure to load an Image first!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Make Sure to load an Image first!");

		}

	}

	public void undoCommand() {
		try {
			if (!CommandHandler.imgHistory.isEmpty()) {

				this.img = CommandHandler.imgHistory.pop();
				FrameStatics.lblImgInputFile.setIcon(new ImageIcon(FileMenu.getScaledImage(this.img,
						FrameStatics.lblImgInputFile.getWidth(), FrameStatics.lblImgInputFile.getHeight())));
				FrameStatics.lblImgInputFile.repaint();
				Frame.frame.repaint();

			} else {
				JOptionPane.showMessageDialog(null, "Undo-History Is Empty!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while Undoing!");
		}

	}

}
