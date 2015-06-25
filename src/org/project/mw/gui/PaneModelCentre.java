package org.project.mw.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import java.util.Map;

import org.project.mw.util.Util;

/**
 * Class of centre model in the editor window default matrix (gridbag) 5x5
 * dafault size 50px
 * 
 * @author Yuri Kalinin
 *
 */
public class PaneModelCentre implements ActionListener {
	GridBagLayout gb;
	GridBagConstraints gc;
	Map<Point, JButton> map;
//	final int SIZE = 20;
	private JPanel container;
	//JButton[] button = new JButton[SIZE];
	ArrayList <JButton>btnArrayListTemp= new ArrayList<JButton>();

	PaneModelCentre(int size, int dimenisionX, int dimenisionY ) {
		System.out.println("Created PanelModelCentre");
		gb = new GridBagLayout();
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		map = new LinkedHashMap<Point, JButton>();
		// Container container = getContentPane();
		container = new JPanel();
		container.setLayout(gb);
		int x = 0, y = -1;
		for (int i = 0; i < size; i++) {
			btnArrayListTemp.add( new JButton());
			btnArrayListTemp.get(i).setBackground(Color.WHITE);
			System.out.println(btnArrayListTemp.size());
			
			if(size>100){
				if (i % 20 == 0) {
					x = 0;
					y = y + 1;
				}
			}else{
				if (i % 10 == 0) {
					x = 0;
					y = y + 1;
				}
			}
			gc.gridx = x++;
			gc.gridy = y;
			//button[i].setMinimumSize(new Dimension(100, 100));
			btnArrayListTemp.get(i).setPreferredSize(new Dimension(dimenisionX, dimenisionY));
			btnArrayListTemp.get(i).setTransferHandler(new TransferHandler("icon"));
			gb.setConstraints(btnArrayListTemp.get(i), gc);
			container.add(btnArrayListTemp.get(i));
			map.put(new Point(x, y), btnArrayListTemp.get(i));
			btnArrayListTemp.get(i).setActionCommand(x + "," + y);
			btnArrayListTemp.get(i).addActionListener(this);
		}
		container.repaint();

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// resetAll();
		JButton source = (JButton) evt.getSource();
		String command = source.getActionCommand();
		System.out.println(command);
		String[] arr = command.split(",");
		int x = Integer.parseInt(arr[0]);
		int y = Integer.parseInt(arr[1]);
		JButton button = map.get(new Point(x, y));
		// TODO impement action

	}

	public JPanel getContainer() {
		return container;
	}
}
