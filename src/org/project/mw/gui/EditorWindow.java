package org.project.mw.gui;

/**
 *
 * @author Kevin Fath, Yuri Kalinin
 * @version 0.5

 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.TransferHandler;
import javax.swing.border.Border;

import org.project.mw.util.Util;

class EditorWindow extends JFrame {
	private final String FILE_IMAGE_PATH ="./Resources/Images/";
	
	private JButton buttonMoreZoom;
	private JButton buttonLessZoom;
	PaneModelCentre paneModelCentre = new PaneModelCentre();// GridBagLayout
	private static EditorWindow editorWindowInstanze=null;
	private int modelDemension = 50;

	JScrollPane scrollpane;
	protected JPanel contentPanel = new JPanel();
	private JPanel pannelSouth = new JPanel();
	private JButton buttonAdd;
	private JButton buttonRemove;
	private JButton buttonMainWindow;
	private int scalFactor = 1;
	private MouseListener listener = new DragMouseAdapter();
	private String icon;

	private JButton buttonRotation;
	protected boolean rotEnabled=false;
	public EditorWindow() {
		editorWindowInstanze = this;
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {

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
				dispose();
				MainWindow.getInstance().dispose();

			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}
		});
		JToolBar toolbarTop = new JToolBar();

		// Viewpoint top button on toolbar
		ImageIcon topIcon = new ImageIcon("save.png");
		JButton topButton = new JButton(topIcon);
		topButton.setToolTipText("Speichern");
		topButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Util.getInstance().saveModel();

			}
		});
		toolbarTop.add(topButton);

		JToolBar toolbarRight = new JToolBar(JToolBar.VERTICAL);
		// T-Part on toolbar
		JLabel tPart = new JLabel(new ImageIcon(FILE_IMAGE_PATH + "tPartImage_50.png"));
		tPart.addMouseListener(listener);
		tPart.setToolTipText("T-Bauteil");
		tPart.setTransferHandler(new TransferHandler("icon"));
		toolbarRight.add(tPart);

		// L-Part on toolbar
		JLabel lPart = new JLabel(new ImageIcon(FILE_IMAGE_PATH + "LPartImage_50.png"));
		lPart.setToolTipText("L-Bauteil");
		lPart.addMouseListener(listener);
		toolbarRight.add(lPart);

		// Straight-Part on toolbar
		JLabel sPart = new JLabel(new ImageIcon(FILE_IMAGE_PATH + "IPartImage_1_50.png"));
		sPart.addMouseListener(listener);
		sPart.setTransferHandler(new TransferHandler("icon"));
		sPart.setToolTipText("Gerades Bauteil");
		toolbarRight.add(sPart);

		// Pump on toolbar
		JLabel pump = new JLabel(new ImageIcon(FILE_IMAGE_PATH + "pumpPartImage_50.png"));
		pump.addMouseListener(listener);
		pump.setTransferHandler(new TransferHandler("icon"));
		pump.setToolTipText("Pumpe");
		toolbarRight.add(pump);

		// Faucet on toolbar
		// JLabel faucet = new JLabel(FILE_IMAGE_PATH +"faucPartImage.png");
		/*
		 * faucet.setToolTipText("Wasserhahn");
		 * faucet.addMouseListener(listener); faucet.setTransferHandler(new
		 * TransferHandler("icon")); toolbarRight.add(faucet);
		 */
		// Sensor on toolbar
		/*
		 * JLabel sensor = new JLabel(FILE_IMAGE_PATH +"sensorPartImage.png");
		 * sensor.addMouseListener(listener); sensor.setTransferHandler(new
		 * TransferHandler("icon")); sensor.setToolTipText("Sensor");
		 * toolbarRight.add(sensor);
		 */

		add(toolbarTop, BorderLayout.NORTH);
		add(toolbarRight, BorderLayout.EAST);
		setTitle("Grundriss");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1280, 800);
		setLocationRelativeTo(null);

		// --button more zoom---------------------------------------
		buttonMoreZoom = new JButton();
		buttonMoreZoom.setText("+");
		buttonMoreZoom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modelDemension = modelDemension + 5;
				for (int i = 0; i < paneModelCentre.getLabelArray().size(); i++) {
					if (paneModelCentre.getLabelArray().get(i).getIcon() != null) {
						icon = paneModelCentre.getLabelArray().get(i).getIcon().toString();
						paneModelCentre.getLabelArray().get(i).setIcon(Util.getInstance().getScaledImage(icon, modelDemension, modelDemension));

						if (icon.contains("@") == false) {
							Util.getInstance().getElementsArray().get(i).setFileIconName(icon);
							paneModelCentre.getLabelArray().get(i).setIcon(Util.getInstance().getScaledImage(icon, modelDemension, modelDemension));
						}
					}
				}
				paneModelCentre.update(scalFactor, modelDemension, modelDemension, -1);// FIXME
				

			}
		});
		// --button rotation---------------------------------------
				buttonRotation = new JButton();
				buttonRotation.setIcon(new ImageIcon(FILE_IMAGE_PATH+"Icon/iconRotation.png"));
				buttonRotation.setMaximumSize(new Dimension(5, 5));
				buttonRotation.setBackground(new Color(255, 248,151));
				buttonRotation.addActionListener(new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {
					/*
				 * modelDemension = modelDemension + 5; for (int i = 0; i <
				 * paneModelCentre.getLabelArray().size(); i++) { if
				 * (paneModelCentre.getLabelArray().get(i).getIcon() != null) {
				 * icon =
				 * paneModelCentre.getLabelArray().get(i).getIcon().toString();
				 * paneModelCentre
				 * .getLabelArray().get(i).setIcon(Util.getInstance
				 * ().getScaledImage(icon, modelDemension, modelDemension));
				 * 
				 * if (icon.contains("@") == false) {
				 * Util.getInstance().getElementsArray
				 * ().get(i).setFileIconName(icon);
				 * paneModelCentre.getLabelArray
				 * ().get(i).setIcon(Util.getInstance().getScaledImage(icon,
				 * modelDemension, modelDemension)); } } }
				 * paneModelCentre.update(scalFactor, modelDemension,
				 * modelDemension);// FIXME
				 */
						if (rotEnabled == true) {
					buttonRotation.setBackground(new Color(255, 248,151));
					rotEnabled = false;
				} else {
					buttonRotation.setBackground(new Color(255, 255, 255));
					rotEnabled = true;
				}
			}
				});
				
		// ---- button less zoom----------------------------------------
		buttonLessZoom = new JButton();
		buttonLessZoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (modelDemension != 50) {
					for (int i = 0; i < paneModelCentre.getLabelArray().size(); i++) {
						if (paneModelCentre.getLabelArray().get(i).getIcon() != null) {
							icon = paneModelCentre.getLabelArray().get(i).getIcon().toString();
							if (icon.contains("@") == false) {
								Util.getInstance().getElementsArray().get(i).setFileIconName(icon);
							}
						}

					}
					paneModelCentre.update(scalFactor, modelDemension, modelDemension, -1); // FIXME
					scrollpane.revalidate();
					scrollpane.repaint();
					modelDemension = modelDemension - 5;
				}
			}
		});
		buttonLessZoom.setText("-");

		// -------------button add new rows and cols------------------------
		buttonAdd = new JButton("Hinzufügen");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (scalFactor < 4) {
					for (int i = 0; i < paneModelCentre.getLabelArray().size(); i++) {
						if (paneModelCentre.getLabelArray().get(i).getIcon() != null) {
							icon = paneModelCentre.getLabelArray().get(i).getIcon().toString();
							if (icon.contains("@") == false) {
								Util.getInstance().getElementsArray().get(i).setFileIconName(icon);
							}
						}

					}
					scalFactor++;
					paneModelCentre.update(scalFactor, modelDemension, modelDemension, -1);// FIXME
					scrollpane.revalidate();
					scrollpane.repaint();

					
				}

			}

		});

		// ------------ button remove--------------------------------------
		buttonRemove = new JButton("Löschen");
		buttonRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (scalFactor > 1) {
					for (int i = 0; i < paneModelCentre.getLabelArray().size(); i++) {
						if (paneModelCentre.getLabelArray().get(i).getIcon() != null) {
							icon = paneModelCentre.getLabelArray().get(i).getIcon().toString();
							if (icon.contains("@") == false) {
								Util.getInstance().getElementsArray().get(i).setFileIconName(icon);
							}
						}

					}
					scalFactor--;
					paneModelCentre.update(scalFactor, modelDemension, modelDemension, -1); // FIXME
					scrollpane.revalidate();
					scrollpane.repaint();
					
				}

			}
		});
		// --------------buttonMainWindow Ok-----------------------------------------
		buttonMainWindow = new JButton();
		buttonMainWindow.setText("Fertig");
		buttonMainWindow.setBackground(new Color(135, 206, 235));
		buttonMainWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editorWindowInstanze.dispose();
				MainWindow.getInstance().changeVisiableMainWindow(true);
				// for testing
				for (int i = 0; i < Util.getInstance().getElementsArray().size(); i++) {
					System.out.println("Arrays with elemts contains " + Util.getInstance().getElementsArray().get(i).getFileIconName() + " X " + Util.getInstance().getElementsArray().get(i).getPositionX() + " Y " + Util.getInstance().getElementsArray().get(i).getPositionY());
				System.out.println("Rotation "+Util.getInstance().getElementsArray().get(i).getRotation());
				}

			}
		});

		// --------------------------------------------------------------------------------
		// open saved model

		/*
		 * if (flagOpend.equals("open")) { for (int i = 0; i <
		 * paneModelCentre.getLabelArray().size(); i++) {
		 * System.out.println("X " +
		 * Util.getInstance().getElementsArray().get(i).getPositionX());
		 * System.out.println("Y " +
		 * Util.getInstance().getElementsArray().get(i).getPositionY()); if
		 * (paneModelCentre.getLabelArray().get(i).getIcon() != null) { icon =
		 * paneModelCentre.getLabelArray().get(i).getIcon().toString();
		 * Util.getInstance().getElementsArray().get(i).setFileIconName(icon); }
		 * 
		 * } paneModelCentre.update(1, 50, 50);//FIXME scrollpane.revalidate();
		 * scrollpane.repaint(); }
		 */
		// ------------ pannel south----------------------------------------
		pannelSouth.add(buttonMoreZoom);
		pannelSouth.add(buttonLessZoom);
		pannelSouth.add(buttonAdd);
		pannelSouth.add(buttonRemove);
		pannelSouth.add(buttonMainWindow);
		pannelSouth.add(buttonRotation);
		getContentPane().add(pannelSouth, BorderLayout.SOUTH);
		scrollpane = new JScrollPane(paneModelCentre);
		getContentPane().add(scrollpane, BorderLayout.CENTER);

	}

	public void updateWindow() {
		editorWindowInstanze.revalidate();
		editorWindowInstanze.repaint();
	}

	public  static EditorWindow getEditWindowInstanze() {
		if (editorWindowInstanze == null) {
			editorWindowInstanze = new EditorWindow();
		}
		return editorWindowInstanze;
	}

	public void rotate(int compIndex) {
//		modelDemension = modelDemension + 5;
		for (int i = 0; i < paneModelCentre.getLabelArray().size(); i++) {
			if (paneModelCentre.getLabelArray().get(i).getIcon() != null) {
				icon = paneModelCentre.getLabelArray().get(i).getIcon().toString();
				paneModelCentre.getLabelArray().get(i).setIcon(Util.getInstance().getScaledImage(icon, modelDemension, modelDemension));

				if (icon.contains("@") == false) {
					Util.getInstance().getElementsArray().get(i).setFileIconName(icon);
					paneModelCentre.getLabelArray().get(i).setIcon(Util.getInstance().getScaledImage(icon, modelDemension, modelDemension));
				}
			}
		}
		paneModelCentre.update(scalFactor, modelDemension, modelDemension, compIndex);// FIXME
		scrollpane.revalidate();
		scrollpane.repaint();

	}

}
