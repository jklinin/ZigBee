package org.project.mw.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.project.mw.util.Util;

public class ClickListner implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton component = (JButton) e.getSource();
		CellPane parentComp = (CellPane) component.getParent();
		System.out.println("Parent index " + parentComp.getIndex());
		removeElement(parentComp.getIndex());
	}

	public void removeElement(int indexComp) {
		PaneModelCentre paneModelCentre=EditorWindow.getEditWindowInstanze().paneModelCentre;
		String icon;
		for (int i = 0; i < paneModelCentre.getLabelArray().size(); i++) {
			if (paneModelCentre.getLabelArray().get(i).getIcon() != null) {
				icon = paneModelCentre.getLabelArray().get(i).getIcon().toString();
				paneModelCentre.getLabelArray().get(i).setIcon(Util.getInstance().getScaledImage(icon, 50, 50));

				if (icon.contains("@") == false) {
					Util.getInstance().getElementsArray().get(i).setFileIconName(icon);
					paneModelCentre.getLabelArray().get(i).setIcon(Util.getInstance().getScaledImage(icon, 50, 50));
				}
			}
		}
		paneModelCentre.update(3, 60, 60, -1);// FIXME
		EditorWindow.getEditWindowInstanze().scrollpane.revalidate();
		EditorWindow.getEditWindowInstanze().scrollpane.repaint();

	}

}
