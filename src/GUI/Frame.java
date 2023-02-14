package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import com.formdev.flatlaf.FlatDarculaLaf;

import Actions.CommandHandler;
import Actions.Commands.Blur;
import Actions.Commands.Contrast;
import Actions.Commands.Invert;
import Actions.Commands.Monochrome;
import Actions.Commands.ReadRGB;
import Actions.Commands.RenderAsciiArt;
import Actions.Commands.Resize;
import Actions.Commands.RotateLeft;
import Actions.Commands.RotateRight;
import Actions.Commands.WriteRGB;
import MenuBarActions.FileMenu;
import OwnSwingComponents.CustomJMenu;
import OwnSwingComponents.Filter.NumberFilter;
import OwnSwingComponents.Listener.MaxWriteChangeListener;

public class Frame {

	public static JFrame frame;
	public static Image icon;
	public static FileMenu fileManager;
	public static int xPos, yPos;
	private int frameWidth = 1280, frameHeight = 720;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame window = new Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame() {
		FlatDarculaLaf.setup();

		try {
			UIManager.setLookAndFeel(new FlatDarculaLaf());
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		FrameStatics.cmdHandler = new CommandHandler();
		SliderValueUpdater sliderUpdater = new SliderValueUpdater();
		fileManager = new FileMenu();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();

		icon = Toolkit.getDefaultToolkit().getImage("icon/icon.png");

		frame.setIconImage(icon);

		frame.setFocusable(true);
		frame.setBounds((size.width / 2) - frameWidth / 2, (size.height / 2) - frameHeight / 2, frameWidth,
				frameHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		Frame.xPos = frame.getBounds().x;
		Frame.yPos = frame.getBounds().y;

		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(10, 10, (frameWidth / 2) - 10, frameHeight - 45);
		frame.getContentPane().add(leftPanel);
		leftPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelInputImage = new JPanel();
		panelInputImage.setBorder(
				new TitledBorder(null, "Your Image File", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leftPanel.add(panelInputImage);
		panelInputImage.setLayout(new BorderLayout(0, 0));

		FrameStatics.lblImgInputFile = new JLabel();

		panelInputImage.add(FrameStatics.lblImgInputFile);
		FrameStatics.lblImgInputFile.setHorizontalAlignment(SwingConstants.CENTER);
		FrameStatics.lblImgInputFile.setBounds(0, 0, leftPanel.getWidth(), leftPanel.getHeight() / 2 - 5);

		FrameStatics.panelAsciiPreview = new JPanel();
		FrameStatics.panelAsciiPreview.setBorder(
				new TitledBorder(null, "Ascii-Art Preview", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leftPanel.add(FrameStatics.panelAsciiPreview);
		FrameStatics.panelAsciiPreview.setLayout(new BorderLayout(0, 0));

		FrameStatics.txtAreaAsciiImagePreview = new JTextArea("");
		FrameStatics.txtAreaAsciiImagePreview.setEditable(false);
		FrameStatics.txtAreaAsciiImagePreview.setLineWrap(false);
		FrameStatics.txtAreaAsciiImagePreview.setWrapStyleWord(false);
		FrameStatics.txtAreaAsciiImagePreview.setForeground(Color.LIGHT_GRAY);

		JScrollPane scrollAscii = new JScrollPane(FrameStatics.txtAreaAsciiImagePreview);
		FrameStatics.panelAsciiPreview.add(scrollAscii);

		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(frameWidth / 2 + 10, 10, (frameWidth / 2) - 20, 350);
		rightPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Image Processing",
				TitledBorder.LEADING, TitledBorder.TOP, null, FrameStatics.lblImgInputFile.getForeground()));
		frame.getContentPane().add(rightPanel);

		JPanel ToolsPanel = new JPanel();
		rightPanel.add(ToolsPanel);

		JPanel panelRotateTitle = new JPanel();
		ToolsPanel.add(panelRotateTitle);
		panelRotateTitle.setLayout(new GridLayout(0, 1, 0, 0));

		FrameStatics.btnRotateImageLeft = new JButton("Rotate Left");

		FrameStatics.btnRotateImageLeft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameStatics.cmdHandler.executeCommand(new RotateLeft());

			}
		});

		FrameStatics.btnRotateImageRight = new JButton("Rotate Right");
		FrameStatics.btnRotateImageRight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameStatics.cmdHandler.executeCommand(new RotateRight());

			}
		});

		FrameStatics.btnImageInvert = new JButton("Invert Image");
		FrameStatics.btnImageInvert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameStatics.cmdHandler.executeCommand(new Invert());

			}
		});

		FrameStatics.btnImageMonochrome = new JButton("Monochrome Image");
		FrameStatics.btnImageMonochrome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameStatics.cmdHandler.executeCommand(new Monochrome());

			}
		});

		JPanel panel_2 = new JPanel();
		panelRotateTitle.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

		JPanel panelRotateInputs = new JPanel();

		panelRotateInputs.setLayout(new GridLayout(0, 2, 100, 0));

		JPanel panelRotate = new JPanel();
		panelRotate.setLayout(new GridLayout(0, 1, 0, 0));
		panelRotate.add(FrameStatics.btnRotateImageLeft);
		panelRotate.add(FrameStatics.btnRotateImageRight);

		panelRotateInputs.add(panelRotate);

		JPanel panelEffects = new JPanel();
		panelEffects.setLayout(new GridLayout(0, 1, 0, 0));
		panelEffects.add(FrameStatics.btnImageInvert);
		panelEffects.add(FrameStatics.btnImageMonochrome);
		panelRotateInputs.add(panelEffects);

		panel_2.add(panelRotateInputs);

		JPanel panelResizeTitle = new JPanel();
		ToolsPanel.add(panelResizeTitle);
		panelResizeTitle.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel labelResize = new JLabel("Resize");
		labelResize.setHorizontalAlignment(SwingConstants.CENTER);
		panelResizeTitle.add(labelResize);

		JPanel panelResizeInput = new JPanel();
		panelResizeTitle.add(panelResizeInput);
		panelResizeInput.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		FrameStatics.txtResizeWidth = new JTextField();
		FrameStatics.txtResizeWidth.setText("" + FrameStatics.widthOriginalImage);
		panelResizeInput.add(FrameStatics.txtResizeWidth);
		FrameStatics.txtResizeWidth.setToolTipText("Image Width");
		FrameStatics.txtResizeWidth.setColumns(10);
		((AbstractDocument) FrameStatics.txtResizeWidth.getDocument()).setDocumentFilter(new NumberFilter());

		FrameStatics.txtResizeHeight = new JTextField();
		FrameStatics.txtResizeHeight.setText("" + FrameStatics.heightOriginalImage);
		panelResizeInput.add(FrameStatics.txtResizeHeight);
		FrameStatics.txtResizeHeight.setToolTipText("Image Height");
		FrameStatics.txtResizeHeight.setColumns(10);
		((AbstractDocument) FrameStatics.txtResizeHeight.getDocument()).setDocumentFilter(new NumberFilter());
		FrameStatics.btnApplyResize = new JButton("Apply");
		FrameStatics.btnApplyResize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FrameStatics.cmdHandler.executeCommand(new Resize());
			}
		});
		panelResizeInput.add(FrameStatics.btnApplyResize);

		JPanel panelBlurInfo = new JPanel();
		ToolsPanel.add(panelBlurInfo);
		panelBlurInfo.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblBlurTitle = new JLabel("Blur");
		lblBlurTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelBlurInfo.add(lblBlurTitle);

		JPanel panelBlurTools = new JPanel();
		panelBlurInfo.add(panelBlurTools);

		FrameStatics.lblBlurValue = new JLabel("1");
		panelBlurTools.add(FrameStatics.lblBlurValue);

		FrameStatics.sliderBlur = new JSlider();
		FrameStatics.sliderBlur.setValue(1);
		FrameStatics.sliderBlur.setMinimum(1);
		FrameStatics.sliderBlur.setMaximum(10);
		FrameStatics.sliderBlur.setSnapToTicks(true);
		FrameStatics.sliderBlur.setPaintTicks(true);
		FrameStatics.sliderBlur.setPaintLabels(true);
		FrameStatics.sliderBlur.setMinorTickSpacing(1);
		sliderUpdater.updateSliderLabel(FrameStatics.sliderBlur, FrameStatics.lblBlurValue);
//		FrameStatics.sliderBlur.addChangeListener(new ChangeListener() {
//
//			@Override
//			public void stateChanged(ChangeEvent e) {
//				FrameStatics.lblBlurValue.setText("" + FrameStatics.sliderBlur.getValue());
//
//			}
//		});
		panelBlurTools.add(FrameStatics.sliderBlur);

		FrameStatics.btnApplyBlur = new JButton("Apply");
		FrameStatics.btnApplyBlur.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameStatics.cmdHandler.executeCommand(new Blur());

			}
		});
		panelBlurTools.add(FrameStatics.btnApplyBlur);

		JPanel panelContrast = new JPanel();
		ToolsPanel.add(panelContrast);
		panelContrast.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblContrast = new JLabel("Contrast");
		lblContrast.setHorizontalAlignment(SwingConstants.CENTER);
		panelContrast.add(lblContrast);

		JPanel panel = new JPanel();
		panelContrast.add(panel);

		FrameStatics.lblContrastValue = new JLabel("1");
		panel.add(FrameStatics.lblContrastValue);

		FrameStatics.sliderContrast = new JSlider();
		FrameStatics.sliderContrast.setValue(1);
		panel.add(FrameStatics.sliderContrast);
		FrameStatics.sliderContrast.setMinorTickSpacing(1);
		FrameStatics.sliderContrast.setMaximum(10);
		FrameStatics.sliderContrast.setMinimum(1);
		FrameStatics.sliderContrast.setSnapToTicks(true);
		FrameStatics.sliderContrast.setPaintLabels(true);
		FrameStatics.sliderContrast.setPaintTicks(true);
		sliderUpdater.updateSliderLabel(FrameStatics.sliderContrast, FrameStatics.lblContrastValue);
//		FrameStatics.sliderContrast.addChangeListener(new ChangeListener() {
//
//			@Override
//			public void stateChanged(ChangeEvent e) {
//				FrameStatics.lblContrastValue.setText("" + FrameStatics.sliderContrast.getValue());
//
//			}
//		});

		FrameStatics.btnApplyContrast = new JButton("Apply");
		FrameStatics.btnApplyContrast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FrameStatics.cmdHandler.executeCommand(new Contrast());
			}
		});
		panel.add(FrameStatics.btnApplyContrast);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, frameWidth, 50);
		menuBar.setPreferredSize(new Dimension(frameWidth, 30));
		MoveFrameListener listener = new MoveFrameListener();
		menuBar.addMouseListener(listener);
		menuBar.addMouseMotionListener(listener);

		CustomJMenu MenuProgramDropdown = new CustomJMenu(FrameStatics.strProgramName, Color.lightGray);
		CustomJMenu MenuFileDropdown = new CustomJMenu("File", Color.lightGray);
		CustomJMenu ExportFileDropdown = new CustomJMenu("Export", Color.lightGray);

		// JMenuItem FrameStatics.miCreateNewImage, FrameStatics.miOpenFileFromFile,
		// FrameStatics.miOpenFileUrl, FrameStatics.miOpenFileClippboard;
		FrameStatics.miCreateNewImage = new JMenuItem("Create New Image");
		FrameStatics.miCreateNewImage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		FrameStatics.miCreateNewImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ColorPickerFrame colorPicker = new ColorPickerFrame();
				frame.setEnabled(false);
			}
		});
		FrameStatics.miOpenFileFromFile = new JMenuItem("Open Image From File");
		FrameStatics.miOpenFileFromFile
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		FrameStatics.miOpenFileFromFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				fileManager.openImageFromPath();

			}
		});

		FrameStatics.miOpenFileUrl = new JMenuItem("Open Image From URL (ClipBoard)");
		FrameStatics.miOpenFileUrl.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
		FrameStatics.miOpenFileUrl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				fileManager.openImageFromURL();
			}
		});

		FrameStatics.miOpenFileClippboard = new JMenuItem("Open Image From ClipBoard");
		FrameStatics.miOpenFileClippboard
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
		FrameStatics.miOpenFileClippboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				fileManager.openImageFromClipBoard();
			}
		});

		// JMenuItem FrameStatics.miSaveImages, FrameStatics.miSaveAsciiArt;
		FrameStatics.miSaveImages = new JMenuItem("Save Image As");
		FrameStatics.miSaveImages.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
		FrameStatics.miSaveImages.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				fileManager.saveImageAs();
			}
		});

		FrameStatics.miSaveAsciiArt = new JMenuItem("Save Ascii-Art As");
		FrameStatics.miSaveAsciiArt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		FrameStatics.miSaveAsciiArt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				fileManager.saveAsciiArtAs();
			}
		});

		FrameStatics.miSaveReadRGBText = new JMenuItem("Save Read Text As");
		FrameStatics.miSaveReadRGBText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		FrameStatics.miSaveReadRGBText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fileManager.saveReadTextAs();

			}
		});

		JMenuItem closeProgramItem;
		closeProgramItem = new JMenuItem("Quit");
		closeProgramItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		closeProgramItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		MenuProgramDropdown.add(closeProgramItem);

		MenuFileDropdown.add(FrameStatics.miCreateNewImage);
		MenuFileDropdown.add(FrameStatics.miOpenFileFromFile);
		MenuFileDropdown.add(FrameStatics.miOpenFileUrl);
		MenuFileDropdown.add(FrameStatics.miOpenFileClippboard);

		// ExportFileDropdown.add(clipBoardItem);
		ExportFileDropdown.add(FrameStatics.miSaveImages);
		ExportFileDropdown.add(FrameStatics.miSaveAsciiArt);
		ExportFileDropdown.add(FrameStatics.miSaveReadRGBText);

		menuBar.add(MenuProgramDropdown);
		menuBar.add(MenuFileDropdown);
		menuBar.add(ExportFileDropdown);

		ToolsPanel.setLayout(new GridLayout(ToolsPanel.getComponentCount(), 1, 0, 0));

		rightPanel.setLayout(new GridLayout(0, rightPanel.getComponentCount(), 0, 0));

		JPanel rightPanelMiddle = new JPanel();
		rightPanelMiddle.setBounds(frameWidth / 2 + 10, 370, (frameWidth / 2) - 20, 250);
		frame.getContentPane().add(rightPanelMiddle);
		rightPanelMiddle.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane
				.setBorder(new TitledBorder(null, "Cryptography", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.setSize(rightPanelMiddle.getWidth(), rightPanelMiddle.getHeight());
		rightPanelMiddle.add(tabbedPane);

		JPanel panelRead = new JPanel();
		tabbedPane.addTab("Read RGB", null, panelRead, null);
		panelRead.setLayout(new BoxLayout(panelRead, BoxLayout.X_AXIS));

		JPanel panelCheckboxesRead = new JPanel();
		panelRead.add(panelCheckboxesRead);
		panelCheckboxesRead.setLayout(new BoxLayout(panelCheckboxesRead, BoxLayout.Y_AXIS));

		FrameStatics.chbxRedRead = new JCheckBox("Red");
		FrameStatics.chbxRedRead.setSelected(true);
		FrameStatics.chbxRedRead.setForeground(new Color(255, 2, 0));
		panelCheckboxesRead.add(FrameStatics.chbxRedRead);

		FrameStatics.chbxGreenRead = new JCheckBox("Green");
		FrameStatics.chbxGreenRead.setSelected(true);
		FrameStatics.chbxGreenRead.setForeground(new Color(4, 255, 67));
		panelCheckboxesRead.add(FrameStatics.chbxGreenRead);

		FrameStatics.chbxBlueRead = new JCheckBox("Blue");
		FrameStatics.chbxBlueRead.setSelected(true);
		FrameStatics.chbxBlueRead.setForeground(new Color(0, 247, 255));
		panelCheckboxesRead.add(FrameStatics.chbxBlueRead);

		JPanel panelReadField = new JPanel();
		panelRead.add(panelReadField);
		panelReadField.setLayout(new BoxLayout(panelReadField, BoxLayout.X_AXIS));

		FrameStatics.txtAreaRead = new JTextArea();
		FrameStatics.txtAreaRead.setLineWrap(true);
		// panelReadField.add(textArea);

		JScrollPane scrollPane = new JScrollPane(FrameStatics.txtAreaRead);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		panelReadField.add(scrollPane);

		JPanel panelWrite = new JPanel();
		tabbedPane.addTab("Write RGB", null, panelWrite, null);
		panelWrite.setLayout(new BoxLayout(panelWrite, BoxLayout.X_AXIS));

		JPanel panelCheckboxesWrite = new JPanel();
		panelWrite.add(panelCheckboxesWrite);
		panelCheckboxesWrite.setLayout(new BoxLayout(panelCheckboxesWrite, BoxLayout.Y_AXIS));

		FrameStatics.lblMaxChars = new JLabel("Max Chars To Add: 0");

		FrameStatics.chbxRedWrite = new JCheckBox("Red");
		FrameStatics.chbxRedWrite.setSelected(true);
		FrameStatics.chbxRedWrite.setForeground(new Color(255, 0, 0));
		FrameStatics.chbxRedWrite.addChangeListener(new MaxWriteChangeListener());
		panelCheckboxesWrite.add(FrameStatics.chbxRedWrite);

		FrameStatics.chbxGreenWrite = new JCheckBox("Green");
		FrameStatics.chbxGreenWrite.setSelected(true);
		FrameStatics.chbxGreenWrite.setForeground(new Color(0, 255, 80));
		FrameStatics.chbxGreenWrite.addChangeListener(new MaxWriteChangeListener());
		panelCheckboxesWrite.add(FrameStatics.chbxGreenWrite);

		FrameStatics.chbxBlueWrite = new JCheckBox("Blue");
		FrameStatics.chbxBlueWrite.setSelected(true);
		FrameStatics.chbxBlueWrite.setForeground(new Color(0, 247, 255));
		FrameStatics.chbxBlueWrite.addChangeListener(new MaxWriteChangeListener());
		panelCheckboxesWrite.add(FrameStatics.chbxBlueWrite);

		JPanel panelWriteField = new JPanel();
		panelWrite.add(panelWriteField);

		panelWriteField.setLayout(new BoxLayout(panelWriteField, BoxLayout.X_AXIS));

		FrameStatics.txtAreaWrite = new JTextArea();
		FrameStatics.txtAreaWrite.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				try {
					if ((MaxWriteChangeListener.value) - FrameStatics.txtAreaWrite.getText().length() <= 0) {
						e.consume();
					}
				} catch (Exception ex) {

				}

			}
		});

		JScrollPane scrollPane_1 = new JScrollPane(FrameStatics.txtAreaWrite);
		scrollPane_1.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		panelWriteField.add(scrollPane_1);

		scrollPane_1.setColumnHeaderView(FrameStatics.lblMaxChars);

		FrameStatics.txtAreaWrite.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {

				MaxWriteChangeListener.change();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {

				MaxWriteChangeListener.change();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {

				MaxWriteChangeListener.change();

			}
		});
		FrameStatics.txtAreaWrite.setLineWrap(true);
		// panelWriteField.add(textArea);

		JPanel panelCredits = new JPanel();
		tabbedPane.addTab("Credits", null, panelCredits, null);
		panelCredits.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblCredits = new JLabel("Made With 🖤");

		Thread tLblCredits = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Random r = new Random();
					int random = r.nextInt(4);
					String[] hearts = { "🖤", "💜", "💙", "💚" };
					for (String s : hearts) {
						lblCredits.setText(lblCredits.getText().replace(s, hearts[random]));
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		tLblCredits.start();

		lblCredits.setForeground(Color.GRAY);
		lblCredits.setSize(panelCredits.getWidth(), panelCredits.getHeight());
		lblCredits.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredits.setVerticalAlignment(SwingConstants.BOTTOM);
		panelCredits.add(lblCredits);

		JLabel lblNewLabel = new JLabel(" by Marc Wenzelburger");
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		panelCredits.add(lblNewLabel);

		JPanel rightPanelBottom = new JPanel();
		rightPanelBottom
				.setBorder(new TitledBorder(null, "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		rightPanelBottom.setBounds(frameWidth / 2 + 10, 624, (frameWidth / 2) - 20, 60);
		frame.getContentPane().add(rightPanelBottom);
		rightPanelBottom.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelActions = new JPanel();
		rightPanelBottom.add(panelActions);
		panelActions.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		FrameStatics.btnRenderAsciiArt = new JButton("Render Ascii-Art");
		FrameStatics.btnRenderAsciiArt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameStatics.cmdHandler.executeCommandNoUndo(new RenderAsciiArt());

			}
		});
		FrameStatics.btnRenderAsciiArt.setBackground(new Color(47, 81, 103));
		panelActions.add(FrameStatics.btnRenderAsciiArt);

		FrameStatics.btnWriteRGB = new JButton("Write Text via RGB");
		FrameStatics.btnWriteRGB.setBackground(new Color(47, 81, 103));
		FrameStatics.btnWriteRGB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameStatics.cmdHandler.executeCommand(new WriteRGB());

			}
		});
		panelActions.add(FrameStatics.btnWriteRGB);

		FrameStatics.btnReadRGB = new JButton("Read Text via RGB");
		FrameStatics.btnReadRGB.setBackground(new Color(47, 81, 103));
		FrameStatics.btnReadRGB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameStatics.cmdHandler.executeCommandNoUndo(new ReadRGB());

			}
		});
		panelActions.add(FrameStatics.btnReadRGB);

		FrameStatics.btnImageUndo = new JButton("Undo");
		FrameStatics.btnImageUndo.setBackground(new Color(47, 81, 103));
		FrameStatics.btnImageUndo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FrameStatics.cmdHandler.undoCommand();
			}
		});
		panelActions.add(FrameStatics.btnImageUndo);

		frame.setJMenuBar(menuBar);

	}
}
