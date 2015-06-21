package org.project.mw.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.project.mw.util.RotatedIcon;
import org.project.mw.util.Util;

public class ClickListner implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton component = (JButton) e.getSource();
		CellPane parentComp = (CellPane) component.getParent();

		System.out.println("Parent index " + parentComp.getIndex());
		if (EditorWindow.getEditWindowInstanze().rotEnabled == false) {
			removeElement(parentComp.getIndex());
		}else{
			rotElemt(component);
		}
	}

	public void removeElement(int indexComp) {
		PaneModelCentre paneModelCentre = EditorWindow.getEditWindowInstanze().paneModelCentre;
		String icon;
		paneModelCentre.getLabelArray().get(indexComp).setIcon(null);
		int modelDemension = EditorWindow.getEditWindowInstanze().modelDemension;
		int scalFactor = EditorWindow.getEditWindowInstanze().scalFactor;
		Util.getInstance().getElementsArray().get(indexComp).setFileIconName("");
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
		paneModelCentre.update(scalFactor, modelDemension, modelDemension);// FIXME
		EditorWindow.getEditWindowInstanze().scrollpane.revalidate();
		EditorWindow.getEditWindowInstanze().scrollpane.repaint();

	}

	public void rotElemt(JButton component) {
		RotatedIcon ri = new RotatedIcon((component.getIcon()), RotatedIcon.Rotate.UPSIDE_DOWN);
		component.setIcon( ri );
		
		EditorWindow.getEditWindowInstanze().scrollpane.revalidate();
		EditorWindow.getEditWindowInstanze().scrollpane.repaint();
	}
}