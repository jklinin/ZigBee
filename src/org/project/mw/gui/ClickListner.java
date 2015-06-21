package org.project.mw.gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.project.mw.util.RotatedIcon;
import org.project.mw.util.RotatedIcon.Rotate;
import org.project.mw.util.Util;

public class ClickListner implements ActionListener {
	public static ClickListner clickListnerInstanze = null;

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton component = (JButton) e.getSource();
		CellPane parentComp = (CellPane) component.getParent();

		System.out.println("Parent index " + parentComp.getIndex());
		if (EditorWindow.getEditWindowInstanze().rotEnabled == false) {
			removeElement(parentComp.getIndex());
		} else {
			rotElemt(component, parentComp.getIndex(), false);
		}
	}

	public void removeElement(int indexComp) {
		PaneModelCentre paneModelCentre = EditorWindow.getEditWindowInstanze().paneModelCentre;
		String icon;
		paneModelCentre.getLabelArray().get(indexComp).setIcon(null);
		int modelDemension = EditorWindow.getEditWindowInstanze().modelDemension;
		int scalFactor = EditorWindow.getEditWindowInstanze().scalFactor;
		Util.getInstance().getElementsArray().get(indexComp).setFileIconName("");
		Util.getInstance().getElementsArray().get(indexComp).setRotation(null);

		paneModelCentre.update(scalFactor, modelDemension, modelDemension);// FIXME
		EditorWindow.getEditWindowInstanze().scrollpane.revalidate();
		EditorWindow.getEditWindowInstanze().scrollpane.repaint();

	}

	// if the state true update method
	public void rotElemt(JButton component, int compIndex, boolean state) {
		Rotate roateElemt = Util.getInstance().getElementsArray().get(compIndex).getRotation();
		RotatedIcon ri;
		Icon icon;
		if (state == false) {
			System.out.println("This is not update (Rotation)");
			icon = component.getIcon();
			
			if (icon.toString().contains("@") == false) {
				Util.getInstance().getElementsArray().get(compIndex).setFileIconName(icon.toString());
			}
			System.out.println("Rotated Icon-> " + icon.toString());
		} else {
			System.out.println("This is  update (Rotation)");
			icon = new ImageIcon(Util.getInstance().getElementsArray().get(compIndex).getFileIconName());
			
			if (icon.toString().contains("@") == false) {
				Util.getInstance().getElementsArray().get(compIndex).setFileIconName(icon.toString());
			}
			int modelDemension = EditorWindow.getEditWindowInstanze().modelDemension;
			JButton btnTemp=new JButton();
			btnTemp.setIcon(Util.getInstance().getScaledImage(icon.toString(), modelDemension, modelDemension));
			ri = new RotatedIcon(btnTemp.getIcon(), roateElemt);
			component.setIcon(ri);

			System.out.println("Rotated Icon-> " + icon.toString());
			// Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.UP);
			EditorWindow.getEditWindowInstanze().scrollpane.revalidate();
			EditorWindow.getEditWindowInstanze().scrollpane.repaint();
			return;
		}

		if (roateElemt != null) {
			System.out.println("Rotation is not null");
			Image image =Util.getInstance().iconToImage(icon);
			int modelDemension = EditorWindow.getEditWindowInstanze().modelDemension;
			JButton btnTemp =new JButton();
			btnTemp.setIcon(new ImageIcon(Util.getInstance().getScaledImage(image, modelDemension, modelDemension)));
			icon=btnTemp.getIcon();
			switch (roateElemt) {
			case DOWN:
				ri = new RotatedIcon(icon, RotatedIcon.Rotate.UP);
				component.setIcon(ri);
				System.out.println("Rotated Icon-> " + icon.toString());
				Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.UP);
				break;
			case UP:
				ri = new RotatedIcon(icon, RotatedIcon.Rotate.UPSIDE_DOWN);
				component.setIcon(ri);
				System.out.println("Rotated Icon-> " + icon.toString());
				Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.UPSIDE_DOWN);
				break;

			case UPSIDE_DOWN:
				ri = new RotatedIcon(icon, RotatedIcon.Rotate.ABOUT_CENTER);
				component.setIcon(ri);
				System.out.println("Rotated Icon-> " + icon.toString());
				Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.ABOUT_CENTER);
				break;

			case ABOUT_CENTER:
				ri = new RotatedIcon(icon, RotatedIcon.Rotate.DOWN);
				component.setIcon(ri);
				System.out.println("Rotated Icon-> " + icon.toString());
				Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.DOWN);
				break;
			}
		} else {
			System.out.println("Rotation is null from array");
			ri = new RotatedIcon(icon, RotatedIcon.Rotate.DOWN);
			Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.DOWN);
			component.setIcon(ri);
			System.out.println("Rotated Icon-> " + icon.toString());

		}

		EditorWindow.getEditWindowInstanze().scrollpane.revalidate();
		EditorWindow.getEditWindowInstanze().scrollpane.repaint();
	}

	public static ClickListner getClickListnerInstanze() {
		if (clickListnerInstanze == null) {
			clickListnerInstanze = new ClickListner();
		}
		return clickListnerInstanze;
	}
}