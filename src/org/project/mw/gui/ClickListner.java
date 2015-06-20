package org.project.mw.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class ClickListner implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton component = (JButton) e.getSource();
		CellPane parentComp = (CellPane) component.getParent();
		System.out.println("Parent index " + parentComp.getIndex());

	}

}
