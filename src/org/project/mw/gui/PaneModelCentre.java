package org.project.mw.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
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

	private JPanel container;

	protected ArrayList<JButton> btnArrayListTemp = new ArrayList<JButton>();

	PaneModelCentre(int size, int dimenisionX, int dimenisionY, boolean restore) {
		System.out.println("Created PanelModelCentre");
		JButton button;
		gb = new GridBagLayout();
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		// Util.getInstance().map = new LinkedHashMap<Point, JButton>();
		// Container container = getContentPane();
		container = new JPanel();
		container.setLayout(gb);
		int x = 0, y = -1;
		for (int i = 0; i < size; i++) {
			btnArrayListTemp.add(new JButton());
			btnArrayListTemp.get(i).setBackground(Color.WHITE);
			btnArrayListTemp.get(i).setName("DOWN");
			if (size > 100) {
				if (i % 20 == 0) {
					x = 0;
					y = y + 1;
				}
			} else {
				if (i % 10 == 0) {
					x = 0;
					y = y + 1;
				}
			}
			gc.gridx = x++;
			gc.gridy = y;

			btnArrayListTemp.get(i).setPreferredSize(new Dimension(dimenisionX, dimenisionY));
			btnArrayListTemp.get(i).setTransferHandler(new TransferHandler("icon"));

			gb.setConstraints(btnArrayListTemp.get(i), gc);
			container.add(btnArrayListTemp.get(i));
			if (restore == true) {

				button = Util.getInstance().map.get(new Point(x, y));
				if (button != null) {
					if (button.getIcon() != null) {
						Image image = Util.getInstance().iconToImage(button.getIcon());

						btnArrayListTemp.get(i).setIcon(new ImageIcon(Util.getInstance().getScaledImage(image, dimenisionX, dimenisionY)));
						
					}
				}

				Util.getInstance().map.put(new Point(x, y), btnArrayListTemp.get(i));

			} else {

				Util.getInstance().map.put(new Point(x, y), btnArrayListTemp.get(i));

			}
			btnArrayListTemp.get(i).setActionCommand(x + "," + y);
			btnArrayListTemp.get(i).addActionListener(this);

		}
		container.repaint();

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// resetAll();
		//System.out.println(btnArrayListTemp.get(0).getIcon().toString());
		JButton source = (JButton) evt.getSource();
		String command = source.getActionCommand();
		System.out.println(command);
		String[] arr = command.split(",");
		int x = Integer.parseInt(arr[0]);
		int y = Integer.parseInt(arr[1]);
		JButton button = Util.getInstance().map.get(new Point(x, y));
		 System.out.println("****" + button.getName());
		// TODO impement action

		if (EditorWindow.removeEnbKey == true) {// TODO just testing change
													// this to true
			Util.getInstance().map.remove(new Point(x, y));
			EditorWindow editorWindowInstanze = EditorWindow.getEditWindowInstanze();
			editorWindowInstanze.editWindowContentPane.remove(editorWindowInstanze.scrollpane);
			editorWindowInstanze.paneModelCentre = new PaneModelCentre(editorWindowInstanze.n * editorWindowInstanze.scalFactor, editorWindowInstanze.modelDemension, editorWindowInstanze.modelDemension, true);// FIXME
			editorWindowInstanze.paneModelCentre.getContainer().repaint();
			editorWindowInstanze.scrollpane = new JScrollPane(editorWindowInstanze.paneModelCentre.getContainer());
			editorWindowInstanze.editWindowContentPane.add(editorWindowInstanze.scrollpane, BorderLayout.CENTER);

			editorWindowInstanze.pack();

		}
		
		if (EditorWindow.rotEnbledKey == false) {
			
			Util.getInstance().rotElemt(new Point(x,y));

	}}

	public JPanel getContainer() {
		return container;
	}
}
