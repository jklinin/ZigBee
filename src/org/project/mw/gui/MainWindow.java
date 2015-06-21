package org.project.mw.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


import org.project.mw.util.Util;

public class MainWindow extends JFrame {
	private static MainWindow mainWindowInstance =null;
	

	public MainWindow() {
		initUI();
	}

	public static MainWindow getInstance() {
		if (mainWindowInstance == null) {
			mainWindowInstance = new MainWindow();
		}
		return mainWindowInstance;
	}
	private void initUI() {
		JMenuBar menubar = new JMenuBar();

		// File menu
		JMenu file = new JMenu("Datei");
		file.setMnemonic(KeyEvent.VK_F);

		// New button in File menu
		ImageIcon newMenuIcon = new ImageIcon("newSmall.png");
		JMenuItem newMenuItem = new JMenuItem("Neu", newMenuIcon);
		newMenuItem.setMnemonic(KeyEvent.VK_N);
		newMenuItem.setToolTipText("Öffnet einen leeren Grundriss");
		newMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Util.getInstance().removeAllElementsArray();
				EditorWindow.getEditWindowInstanze().dispose();
				EditorWindow.getEditWindowInstanze().setVisible(true);
		
				mainWindowInstance.setVisible(false);
				
			}
		});

		// Open button in File menu
		ImageIcon openMenuIcon = new ImageIcon("refreshSmall.png");
		JMenuItem openMenuItem = new JMenuItem("Öffnen", openMenuIcon);
		openMenuItem.setMnemonic(KeyEvent.VK_R);
		openMenuItem.setToolTipText("Öffnet ein gespeichterten Grundriss");
		openMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Util.getInstance().fileChooser(mainWindowInstance, "open");

			}
		});

		// Open recent button in File menu
		ImageIcon openRecentMenuIcon = new ImageIcon("contactsSmall.png");
		JMenuItem openRecentMenuItem = new JMenuItem("Zuletzt verwendet", openRecentMenuIcon);
		openRecentMenuItem.setMnemonic(KeyEvent.VK_C);
		openRecentMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Util.getInstance().openModel();

			}
		});

		

		// Exit button in File menu
		ImageIcon exitMenuIcon = new ImageIcon("exitSmall.png");
		JMenuItem exitMenuItem = new JMenuItem("Beenden", exitMenuIcon);
		exitMenuItem.setMnemonic(KeyEvent.VK_E);
		exitMenuItem.setToolTipText("Beendet die Anwendung");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		// Help menu
		JMenu help = new JMenu("Hilfe");

		// Help Button in Help menu
		JMenuItem help_button = new JMenuItem("Hilfe");
		help_button.setMnemonic(KeyEvent.VK_H);
		help_button.setToolTipText("Click here for further information");
		help_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}
		});

		// About Button in Help menu
		JMenuItem about_button = new JMenuItem("Über Prpgramm");
		about_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AboutDialog ad = new AboutDialog();
				ad.setVisible(true);
			}
		});
		about_button.setMnemonic(KeyEvent.VK_A);
		about_button.setToolTipText("Click for information about the Authors");

		// Toolbar
		JToolBar toolbar = new JToolBar();

		// New button on toolbar
		ImageIcon newIcon = new ImageIcon("new.png");
		JButton newButton = new JButton(newIcon);
		newButton.setToolTipText("Erstellt einen leeren Grundriss");
		toolbar.add(newButton);
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		
				EditorWindow.getEditWindowInstanze().setVisible(true);
				mainWindowInstance.setVisible(false);
			
			}
		});

		// Open button on toolbar
		ImageIcon openIcon = new ImageIcon("open.png");
		JButton openButton = new JButton(openIcon);
		openButton.setToolTipText("Öffnet einen gespeicherten Grundriss");
		toolbar.add(openButton);
		openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Util.getInstance().fileChooser(mainWindowInstance, "open");
				EditorWindow.getEditWindowInstanze().setVisible(true);

			}
		});

		
	/*	ImageIcon saveIcon = new ImageIcon("save.png");
		JButton saveButton = new JButton(saveIcon);
		saveButton.setToolTipText("Speichert den aktuellen Grundriss");
		toolbar.add(saveButton);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Util.getInstance().fileChooser(mainWindowInstance, "save");

			}
		});*/

		add(toolbar, BorderLayout.NORTH);
		file.add(newMenuItem);
		file.add(openMenuItem);
		file.add(openRecentMenuItem);
		//file.add(saveMenuItem);
		//file.add(saveAsMenuItem);
		file.add(exitMenuItem);
		menubar.add(file);
		//menubar.add(Box.createHorizontalGlue());
		help.add(help_button);
		help.add(about_button);
		menubar.add(help);

		setJMenuBar(menubar);
		setTitle("Projekt");
		setSize(1280, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void openWebPage(String url) {
		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
		} catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void changeVisiableMainWindow(boolean vis) {
		mainWindowInstance.setVisible(vis);
	}

	public static void main(String[] args) {
		//Für die 3D-Demo nachfolgenden Code auskommentieren
		//new DisplayManager().start();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainWindow ex=MainWindow.getInstance();
				ex.setVisible(true);
			}
		});
	}

}