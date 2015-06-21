package org.project.mw.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

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
		 paneModelCentre.getLabelArray().get(indexComp).setIcon(null);;
		// Util.getInstance().getElementsArray().remove(indexComp);
		 Util.getInstance().getElementsArray().get(indexComp).setFileIconName("");
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
		paneModelCentre.update(1, 50, 50, -1);// FIXME
		EditorWindow.getEditWindowInstanze().scrollpane.revalidate();
		EditorWindow.getEditWindowInstanze().scrollpane.repaint();

	}

}
