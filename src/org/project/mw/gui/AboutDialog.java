package org.project.mw.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

/**
 * @author Yuri Kalinin
 * About, help, version dialog
 *
 */
class AboutDialog extends JFrame {
	private JTabbedPane tabbedPane;
	private JPanel aboutPane;
	private JPanel hilfePane;
	private JPanel versionPane;

	public AboutDialog() {
		setIconImage(((ImageIcon) UIManager.getIcon("FileView.computerIcon")).getImage());
		setTitle("Über das Programm");
		setSize(789, 400);
		setBackground(Color.decode("#948b93"));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel);

		// Create the tab pages
		createPage1();
		createPage2();
		createPage3();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Über das Programm", aboutPane);
		tabbedPane.addTab("Hilfe", hilfePane);
		tabbedPane.addTab("Version", versionPane);
		tabbedPane.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			
			}

			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane pane = (JTabbedPane) e.getComponent();
				setTitle(pane.getTitleAt(pane.getSelectedIndex()));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				

			}

			@Override
			public void mouseEntered(MouseEvent e) {



			}

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		topPanel.add(tabbedPane, BorderLayout.CENTER);
	}

	public void createPage1() {
		aboutPane = new JPanel();
		aboutPane.setBackground(Color.decode("#948b93"));
		JLabel aboutLabel=new JLabel(new ImageIcon("./Resources/Images/hilfeImages/ueberProgramm.jpg"));
		
		aboutPane.add(aboutLabel);

	}

	public void createPage2() {
		hilfePane = new JPanel();	
		hilfePane.setBackground(Color.decode("#948b93"));
		JLabel hilfeLabel=new JLabel(new ImageIcon("./Resources/Images/hilfeImages/hotKey.jpg"));
		hilfePane.add(hilfeLabel);

	}

	public void createPage3() {
		versionPane = new JPanel();	
		versionPane.setBackground(Color.decode("#948b93"));
		JLabel versionLabel=new JLabel("Version 1.0.0");
		versionLabel.setForeground(Color.WHITE);
		versionLabel.setFont(versionLabel.getFont().deriveFont(20.0f));
		versionPane.add(versionLabel);

	}


}