package org.project.mw.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class CellPane extends JPanel {
	private Color ACTIVE_COLOR = Color.decode("#56fca9"); // green color
	private Color defaultBackground;
	private int dimension1;
	private int dimension2;
	private int row, col, index;

	public CellPane(int dimension1, int dimension2, int row, int col, int index) {
		this.dimension1 = dimension1;
		this.dimension2 = dimension2;
		this.index=index;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				//defaultBackground = getBackground();
				//setBackground(ACTIVE_COLOR);
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//setBackground(defaultBackground);
			}
		});
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(dimension1, dimension2); // size of matrix
	}
	public int getIndex () {
		return index;
		
	}
}
