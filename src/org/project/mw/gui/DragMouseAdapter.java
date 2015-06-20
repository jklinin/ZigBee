package org.project.mw.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;







import org.project.mw.util.Util;

class DragMouseAdapter extends MouseAdapter {
	Logger log = Logger.getLogger(DragMouseAdapter.class.getName());
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			log.info("Rechte Taste  ");
		
			 
			JComponent c = (JComponent) e.getSource();
			System.out.println(c.getName());
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, e, TransferHandler.COPY);

		}
	}
}
