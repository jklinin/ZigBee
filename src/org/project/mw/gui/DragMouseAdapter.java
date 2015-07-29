package org.project.mw.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

/**
 * @author Yuri Kalinin
 * DnD 
 * copy image from source to destination 
 *
 */
class DragMouseAdapter extends MouseAdapter {
	Logger log = Logger.getLogger(DragMouseAdapter.class.getName());

	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			
			JComponent c = (JComponent) e.getSource();
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, e, TransferHandler.COPY);

		}
	}
}
