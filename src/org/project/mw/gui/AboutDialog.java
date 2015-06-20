package org.project.mw.gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


class AboutDialog extends JDialog {
	/**
	 * Short information about the authors of the program.
	 * @author Kevin Fath
	 * @version 1.0
	 * @return nothing
	 */
	public AboutDialog() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(1, 5, 1, 5);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Erstellt bei:"), gbc);
		gbc.gridy = 1;
		add(new JLabel("Yury Kalinin"), gbc);
		gbc.gridy = 2;
		add(new JLabel("Philipp Se√üner"), gbc);
		gbc.gridy = 3;
		add(new JLabel("Kevin Fath"), gbc);
		gbc.gridy = 4;
		JButton closeButton = new JButton("Schliﬂen");
		gbc.insets = new Insets(5,5,5,5);
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();					
			}
		});
		add(closeButton, gbc);
		
		setTitle("‹ber das Programm");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(200, 170);
		setLocationRelativeTo(null);
	}
}