package org.project.mw.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.project.mw.util.Util;

public class ClickListner implements ActionListener {
private int compIndex;
private JButton component;
	@Override
	public void actionPerformed(ActionEvent e) {
		 component = (JButton) e.getSource();
		CellPane parentComp = (CellPane) component.getParent();
		compIndex=parentComp.getIndex();
		System.out.println("Parent index " + parentComp.getIndex());
		

	}
	private void removeImage(){
		Util.getInstance().getElementsArray().remove( compIndex);
		PaneModelCentre.getPaneModelCentreInstanze().labelCompArrayTemp.remove(compIndex);
		String icon;
			for (int i = 0; i < PaneModelCentre.getPaneModelCentreInstanze().getLabelArray().size(); i++) {
				if ( PaneModelCentre.getPaneModelCentreInstanze().getLabelArray().get(i).getIcon() != null) {
					icon = PaneModelCentre.getPaneModelCentreInstanze().getLabelArray().get(i).getIcon().toString();
					if (icon.contains("@") == false) {
						Util.getInstance().getElementsArray().get(i).setFileIconName(icon);
					}
				}

			}
			 PaneModelCentre.getPaneModelCentreInstanze().update(1,50, 50, -1); // FIXME
			EditorWindow.getEditWindowInstanze().revalidate();
			EditorWindow.getEditWindowInstanze().repaint();
		
	}

}
