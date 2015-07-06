package org.project.mw.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;

import javax.swing.*;

import org.lwjgl.util.Point;
import org.project.mw.threeD.DisplayManager;
import org.project.mw.util.Mlogger;
import org.project.mw.util.Util;

/**
 * @author Yuri Kalinin MainWindow for 2D constaraction
 * 
 */

public class EditorWindow extends JFrame {
	private Mlogger log = new Mlogger();// for logging write inf ito output.txt
	private static EditorWindow editWindowInstance = null;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem newItemMenu;
	private JSeparator separator;
	private JMenuItem openItemMenu;
	private JMenuItem saveDefaultItemMenu;
	private JMenuItem menuItemSave;
	private JSeparator separatorTwo;
	private JMenuItem menuItemClose;
	private JMenu menuHelp;
	private JMenuItem menuItemHelp;
	private JPanel panelSouth;
	private JButton buttonMoreZoom;
	private AbstractButton buttonLessZoom;
	private AbstractButton buttonAdd;
	private AbstractButton buttonRemove;
	protected int modelDemension;
	protected PaneModelCentre paneModelCentre;
	protected int scalFactor = 1;
	public JScrollPane scrollpane;
	private JPanel panelEast;

	private final String FILE_IMAGE_PATH = "./Resources/Images/";
	private MouseListener listener;
	private JButton buttonOk;
	protected static boolean rotEnbledKey = false;
	protected static boolean removeEnbKey = false;
	protected Container editWindowContentPane;
	protected int n = 40;
	private boolean newWindow;

	public EditorWindow(boolean newWindow) {
		this.newWindow = newWindow;
		menuBar = new JMenuBar();
		menuFile = new JMenu();
		newItemMenu = new JMenuItem();
		separator = new JSeparator();
		openItemMenu = new JMenuItem();
		saveDefaultItemMenu = new JMenuItem();
		menuItemSave = new JMenuItem();
		separatorTwo = new JSeparator();
		menuItemClose = new JMenuItem();
		menuHelp = new JMenu();
		menuItemHelp = new JMenuItem();
		panelSouth = new JPanel();
		buttonMoreZoom = new JButton();
		buttonLessZoom = new JButton();
		buttonAdd = new JButton();
		buttonRemove = new JButton();
		modelDemension = 50;
		paneModelCentre = new PaneModelCentre(n, modelDemension, modelDemension, false);
		scrollpane = new JScrollPane(paneModelCentre.getContainer());
		scrollpane.setMinimumSize(new Dimension(150, 150));
		listener = new DragMouseAdapter();
		panelEast = new JPanel();
		buttonOk = new JButton();
		// ======== this ========
		setIconImage(((ImageIcon) UIManager.getIcon("FileView.computerIcon")).getImage());
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				if (Util.getInstance().checkFileDefaultSaving() == true & newWindow == false) {
					Util.getInstance().openModel();
					editWindowContentPane.remove(scrollpane);
					paneModelCentre = new PaneModelCentre(n * scalFactor, modelDemension, modelDemension, true);
					paneModelCentre.getContainer().repaint();
					scrollpane = new JScrollPane(paneModelCentre.getContainer());
					editWindowContentPane.add(scrollpane, BorderLayout.CENTER);

					pack();

				} else {
					Util.getInstance().revomeDefaultSaveFile();
				}
			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				Util.getInstance().saveModel();
				dispose();

			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}
		});
		setMinimumSize(new Dimension(800, 600));
		editWindowContentPane = getContentPane();
		editWindowContentPane.setLayout(new BorderLayout());

		// ======== menuBar ========
		{

			// ======== menu ========
			{
				menuFile.setText("Datei");

				// ---- newItemMenu ----
				newItemMenu.setText("Neu");
				newItemMenu.setIcon(UIManager.getIcon("FileView.fileIcon"));
				newItemMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
				newItemMenu.addActionListener(e -> newItemMenuActionPerformed(e));

				menuFile.add(newItemMenu);
				menuFile.add(separator);

				// ---- openItemMenu ----
				openItemMenu.setText("\u00d6ffnen...");
				openItemMenu.setIcon(UIManager.getIcon("FileChooser.upFolderIcon"));
				openItemMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
				openItemMenu.addActionListener(e -> openItemMenuActionPerformed(e));
				menuFile.add(openItemMenu);

				// ---- saveDefaultItemMenu ----
				saveDefaultItemMenu.setText("Speichern");
				saveDefaultItemMenu.setIcon(UIManager.getIcon("FileView.floppyDriveIcon"));
				saveDefaultItemMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
				saveDefaultItemMenu.addActionListener(e -> saveDefaultItemMenuActionPerformed(e));
				menuFile.add(saveDefaultItemMenu);

				// ---- menuItem Save ----
				menuItemSave.setText("Speichern als...");
				menuItemSave.setIcon(UIManager.getIcon("FileView.floppyDriveIcon"));
				menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK | KeyEvent.ALT_MASK));
				menuItemSave.addActionListener(e -> saveFileItemActionPerformed(e));
				menuFile.add(menuItemSave);
				menuFile.add(separatorTwo);

				// ---- menuItem Exit ----
				menuItemClose.setText("Beenden");
				menuItemClose.setIcon(null);
				menuItemClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
				menuItemClose.addActionListener(e -> closeItemMenuActionPerformed(e));
				menuFile.add(menuItemClose);
			}
			menuBar.add(menuFile);

			// ======== menu Help ========
			{
				menuHelp.setText("Hilfe");

				// ---- menuItem Help ----
				menuItemHelp.setText("\u00dcber das Programm");
				menuItemHelp.setIcon(null);
				menuItemHelp.setSelected(true);
				menuItemHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
				menuHelp.add(menuItemHelp);
			}
			menuBar.add(menuHelp);
		}
		setJMenuBar(menuBar);
		// ======== toolbar Right ========
		JToolBar toolbarRight = new JToolBar(JToolBar.VERTICAL);
		// T-Part on toolbar
		JLabel tPart = new JLabel(new ImageIcon(FILE_IMAGE_PATH + "tPartImage_50.png"));
		tPart.addMouseListener(listener);
		tPart.setToolTipText("T-Bauteil");
		tPart.setTransferHandler(new TransferHandler("icon"));
		tPart.setDropTarget(null);
		toolbarRight.add(tPart);

		// L-Part on toolbar
		JLabel lPart = new JLabel(new ImageIcon(FILE_IMAGE_PATH + "LPartImage_50.png"));
		lPart.setToolTipText("L-Bauteil");
		lPart.addMouseListener(listener);
		lPart.setTransferHandler(new TransferHandler("icon"));
		lPart.setDropTarget(null);
		toolbarRight.add(lPart);

		// Straight-Part on toolbar
		JLabel sPart = new JLabel(new ImageIcon(FILE_IMAGE_PATH + "IPartImage_1_50.png"));
		sPart.addMouseListener(listener);
		sPart.setTransferHandler(new TransferHandler("icon"));
		sPart.setDropTarget(null);
		sPart.setToolTipText("Gerades Bauteil");
		toolbarRight.add(sPart);

		// Pump on toolbar
		JLabel pump = new JLabel(new ImageIcon(FILE_IMAGE_PATH + "pumpPartImage_50.png"));
		pump.addMouseListener(listener);
		pump.setTransferHandler(new TransferHandler("icon"));
		pump.setDropTarget(null);
		pump.setToolTipText("Pumpe");
		toolbarRight.add(pump);
		panelEast.add(toolbarRight);

		// sensor on toolbar
		JLabel sensor = new JLabel(new ImageIcon(FILE_IMAGE_PATH + "sensorPartImage.png"));
		sensor.addMouseListener(listener);
		sensor.setTransferHandler(new TransferHandler("icon"));
		sensor.setDropTarget(null);
		sensor.setToolTipText("Sensor");
		toolbarRight.add(sensor);
		panelEast.add(toolbarRight);

		// ventilr on toolbar
		JLabel fauc = new JLabel(new ImageIcon(FILE_IMAGE_PATH + "faucPartImage_50.png"));
		fauc.addMouseListener(listener);
		fauc.setTransferHandler(new TransferHandler("icon"));
		fauc.setDropTarget(null);
		fauc.setToolTipText("Ventile");
		toolbarRight.add(fauc);
		panelEast.add(toolbarRight);
		// ======== panes ========
		{

			// ---- button MoreZoom ----
			buttonMoreZoom.setText("+");
			buttonMoreZoom.addActionListener(e -> moreZoomActionPerformend(e));
			buttonMoreZoom.setMnemonic(KeyEvent.VK_UP);
			panelSouth.add(buttonMoreZoom);

			// ---- button Less Zoom ----
			buttonLessZoom.setText("-");
			buttonLessZoom.addActionListener(e -> lessZoomActionPerformed(e));
			buttonLessZoom.setMnemonic(KeyEvent.VK_DOWN);
			panelSouth.add(buttonLessZoom);

			// ---- button add new col and rows ----
			buttonAdd.setText("Hinzufügen");
			buttonAdd.setMnemonic(KeyEvent.VK_RIGHT);
			buttonAdd.addActionListener(e -> addColsRowsActionPerformend(e));
			panelSouth.add(buttonAdd);

			// ---- button remove cols and rows----
			buttonRemove.setText("Löschen");
			buttonRemove.addActionListener(e -> removeColsRowsActionPerformend(e));
			buttonRemove.setMnemonic(KeyEvent.VK_LEFT);
			panelSouth.add(buttonRemove);
			// ---- button GoTO 3D Model----
			buttonOk.setText("Fertig");
			buttonOk.setBackground(new Color(135, 206, 235));
			buttonOk.addActionListener(e -> goTo3DModelActionPerformed(e));
			panelSouth.add(buttonOk);

			editWindowContentPane.add(panelSouth, BorderLayout.SOUTH);
			editWindowContentPane.add(panelEast, BorderLayout.EAST);
			editWindowContentPane.add(scrollpane, BorderLayout.CENTER);
			// editWindowContentPane.add(paneModelCentre.getContainer(),
			// BorderLayout.CENTER);
			pack();
			setLocationRelativeTo(null);
		}
	}

	// ======== OnClicListners ========
	private void closeItemMenuActionPerformed(ActionEvent e) {
		editWindowInstance.dispose();
		if (Util.getInstance().checkFileDefaultSaving() == true) {
			Util.getInstance().revomeDefaultSaveFile();
		}

	}

	private void goTo3DModelActionPerformed(ActionEvent e) {
		paneModelCentre.putElementsToMap();
		// just testing
		for(Map.Entry e1 : Util.getInstance().getElementsCollection().entrySet()){
			Element element =(Element) e1.getValue();
			  System.out.println(e1.getKey() + " = " + element.getNameElement()+" Rot: "+element.getRotation()+ " Sensor ID "+ element.getSensorID());
		}
		new DisplayManager().start();
	}

	private void newItemMenuActionPerformed(ActionEvent e) {
		if (editWindowInstance != null) {
			editWindowInstance.setVisible(false);
			editWindowInstance.dispose();

			editWindowInstance = new EditorWindow(true);
			editWindowInstance.setVisible(true);
		}
	}

	private void saveFileItemActionPerformed(ActionEvent e) {
		Util.getInstance().fileChooser(editWindowInstance, "save");

	}

	private void removeColsRowsActionPerformend(ActionEvent e) {
		if (scalFactor > 1) {
			scalFactor = scalFactor - 2;
			paneModelCentre.putElementsToMap();
			editWindowContentPane.remove(scrollpane);
			paneModelCentre = new PaneModelCentre(n * scalFactor, modelDemension, modelDemension, true);
			paneModelCentre.getContainer().repaint();
			scrollpane = new JScrollPane(paneModelCentre.getContainer());
			editWindowContentPane.add(scrollpane, BorderLayout.CENTER);

			pack();

		}

	}

	private void addColsRowsActionPerformend(ActionEvent e) {

		if (scalFactor < 10) {
			scalFactor = scalFactor + 2;
			paneModelCentre.putElementsToMap();
			editWindowContentPane.remove(scrollpane);
			paneModelCentre = new PaneModelCentre(n * scalFactor, modelDemension, modelDemension, true);
			paneModelCentre.getContainer().repaint();
			scrollpane = new JScrollPane(paneModelCentre.getContainer());
			editWindowContentPane.add(scrollpane, BorderLayout.CENTER);

			pack();

		}

	}

	private void moreZoomActionPerformend(ActionEvent e) {

		modelDemension = modelDemension + 5;
		paneModelCentre.putElementsToMap();
		editWindowContentPane.remove(scrollpane);
		paneModelCentre = new PaneModelCentre(n * scalFactor, modelDemension, modelDemension, true);
		scrollpane = new JScrollPane(paneModelCentre.getContainer());
		editWindowContentPane.add(scrollpane, BorderLayout.CENTER);
		pack();

	}

	private void lessZoomActionPerformed(ActionEvent e) {
		if (modelDemension != 50) {
			modelDemension = modelDemension - 5;
			paneModelCentre.putElementsToMap();
			editWindowContentPane.remove(scrollpane);
			paneModelCentre = new PaneModelCentre(n * scalFactor, modelDemension, modelDemension, true);
			paneModelCentre.getContainer().repaint();
			scrollpane = new JScrollPane(paneModelCentre.getContainer());
			editWindowContentPane.add(scrollpane, BorderLayout.CENTER);
			pack();

		}

	}

	// action listner open model
	private void openItemMenuActionPerformed(ActionEvent e) {
		// file chooser dialog and reading of file into Util.map
		Util.getInstance().fileChooser(editWindowInstance, "open");

		editWindowContentPane.remove(scrollpane);
		paneModelCentre = new PaneModelCentre(n * scalFactor, modelDemension, modelDemension, true);
		paneModelCentre.getContainer().repaint();
		scrollpane = new JScrollPane(paneModelCentre.getContainer());
		editWindowContentPane.add(scrollpane, BorderLayout.CENTER);

		pack();
	}

	private void saveDefaultItemMenuActionPerformed(ActionEvent e) {
		Util.getInstance().saveModel();

	}

	// ======== End of OnClickListners ========

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				EditorWindow edit = EditorWindow.getEditWindowInstance();
				edit.setVisible(true);
			}
		});

	}

	public static EditorWindow getEditWindowInstance() {
		if (editWindowInstance == null) {
			editWindowInstance = new EditorWindow(false);
		}
		return editWindowInstance;
	}
	public PaneModelCentre getPaneModelCentreInstance(){
		return paneModelCentre;
	}
	public  int getModelDemension() {
		return modelDemension;
	}
}
