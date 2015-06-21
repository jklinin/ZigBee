package org.project.mw.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.project.mw.util.RotatedIcon;
import org.project.mw.util.RotatedIcon.Rotate;
import org.project.mw.util.Util;

public class ClickListner implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton component = (JButton) e.getSource();
		CellPane parentComp = (CellPane) component.getParent();

		System.out.println("Parent index " + parentComp.getIndex());
		if (EditorWindow.getEditWindowInstanze().rotEnabled == false) {
			removeElement(parentComp.getIndex());
		} else {
			rotElemt(component, parentComp.getIndex());
		}
	}

	public void removeElement(int indexComp) {
		PaneModelCentre paneModelCentre = EditorWindow.getEditWindowInstanze().paneModelCentre;
		String icon;
		paneModelCentre.getLabelArray().get(indexComp).setIcon(null);
		int modelDemension = EditorWindow.getEditWindowInstanze().modelDemension;
		int scalFactor = EditorWindow.getEditWindowInstanze().scalFactor;
		Util.getInstance().getElementsArray().get(indexComp).setFileIconName("");

		paneModelCentre.update(scalFactor, modelDemension, modelDemension);// FIXME
		EditorWindow.getEditWindowInstanze().scrollpane.revalidate();
		EditorWindow.getEditWindowInstanze().scrollpane.repaint();

	}

	public void rotElemt(JButton component, int compIndex) {
		String icon = component.getIcon().toString();

		Util.getInstance().getElementsArray().get(compIndex).setFileIconName(icon);
		Rotate roateElemt = Util.getInstance().getElementsArray().get(compIndex).getRotation();
		RotatedIcon ri;
		if (roateElemt != null) {
			switch (roateElemt) {
			case DOWN:
				ri = new RotatedIcon((component.getIcon()), RotatedIcon.Rotate.UP);
				component.setIcon(ri);
				System.out.println("Rotated Icon-> " + ri.toString());
				Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.UP);
				break;
			case UP:
				ri = new RotatedIcon((component.getIcon()), RotatedIcon.Rotate.UPSIDE_DOWN);
				component.setIcon(ri);
				System.out.println("Rotated Icon-> " + ri.toString());
				Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.UPSIDE_DOWN);
				break;

			case UPSIDE_DOWN:
				ri = new RotatedIcon((component.getIcon()), RotatedIcon.Rotate.ABOUT_CENTER);
				component.setIcon(ri);
				System.out.println("Rotated Icon-> " + ri.toString());
				Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.ABOUT_CENTER);
				break;
			}
		} else {

			ri = new RotatedIcon((component.getIcon()), RotatedIcon.Rotate.DOWN);
			Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.DOWN);
			component.setIcon(ri);
			System.out.println("Rotated Icon-> " + ri.toString());

		}
		
		EditorWindow.getEditWindowInstanze().scrollpane.revalidate();
		EditorWindow.getEditWindowInstanze().scrollpane.repaint();
	}
}