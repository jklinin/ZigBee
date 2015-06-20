package org.project.mw.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;




import org.project.mw.util.ImageRotation;
import org.project.mw.util.Util;

class DragMouseAdapter extends MouseAdapter {
	Logger log = Logger.getLogger(DragMouseAdapter.class.getName());
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)&EditorWindow.getEditWindowInstanze().rotEnabled==true) {
			log.info("Rechte Taste  ");
			JLabel component = (JLabel) e.getSource();
			CellPane parentComp = (CellPane) component.getParent();
			System.out.println("Parent index " + parentComp.getIndex());
			
			EditorWindow.getEditWindowInstanze().rotate(parentComp.getIndex());
			
/*
			// component=
			// ImageRotation.getInstance().preRotation(parentComp.getIndex(),
			// component); //FIXME Rotation method is not working. This bug wil

			component.setIcon(ImageRotation.getInstance().preRotation(parentComp.getIndex(), component));
			System.out.println("Component Icon Name->" + component.getIcon().toString());
			component.revalidate();
			component.repaint();
			EditorWindow.getEditWindowInstanze().updateWindow();

			// ImageRotation.getInstance().preRotation(parentComp.getIndex(),component);
			// component.getParent().repaint();
*/		} else {
			JLabel c = (JLabel) e.getSource();
			log.info("Dimenison of component -> " + c.getSize(new Dimension()));
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, e, TransferHandler.COPY);

		}
	}
}
