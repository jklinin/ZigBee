package org.project.mw.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;

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

	protected ArrayList<Element> btnArrayListTemp = new ArrayList<Element>();

	PaneModelCentre(int size, int dimenisionX, int dimenisionY, boolean restore) {
		System.out.println("Created PanelModelCentre");
		JButton button;
		Element element;
		gb = new GridBagLayout();
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		// Util.getInstance().map = new LinkedHashMap<Point, JButton>();
		// Container container = getContentPane();
		container = new JPanel();
		container.setLayout(gb);
		int x = 0, y = -1;
		for (int i = 0; i < size; i++) {
			element = new Element();
			element.setRotation("DOWN");
			btnArrayListTemp.add(element);
			btnArrayListTemp.get(i).getIconButton().setBackground(Color.WHITE);

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
			btnArrayListTemp.get(i).getIconButton().setPreferredSize(new Dimension(dimenisionX, dimenisionY));

			btnArrayListTemp.get(i).getIconButton().setTransferHandler(new TransferHandler("icon"));
			
			gb.setConstraints(btnArrayListTemp.get(i).getIconButton(), gc);
			container.add(btnArrayListTemp.get(i).getIconButton());
			if (restore == true) {

				if (Util.getInstance().map.get(new Point(x, y))!= null) {
				button = Util.getInstance().map.get(new Point(x, y)).getIconButton();

					if (button.getIcon() != null) {
						Image image = Util.getInstance().iconToImage(button.getIcon());
					
						btnArrayListTemp.get(i).setImageIconElement((Util.getInstance().getScaledImage(image, dimenisionX, dimenisionY)));

					}
				}
			
				Util.getInstance().map.put(new Point(x, y), btnArrayListTemp.get(i));

			} else {

				Util.getInstance().map.put(new Point(x, y), btnArrayListTemp.get(i));

			}

			btnArrayListTemp.get(i).getIconButton().setActionCommand(x + "," + y);
			btnArrayListTemp.get(i).getIconButton().addActionListener(this);

		}
		container.repaint();

	}

	@Override
	public void actionPerformed(ActionEvent evt) {

		JButton source = (JButton) evt.getSource();
		String command = source.getActionCommand();
		System.out.println(command);
		String[] arr = command.split(",");
		int x = Integer.parseInt(arr[0]);
		int y = Integer.parseInt(arr[1]);
		JButton button = Util.getInstance().map.get(new Point(x, y)).getIconButton();
		System.out.println("****" + button.getIcon().toString());
		// TODO impement action
		System.out.println("+++++"+Util.getInstance().getElementsCollection().get(new Point(x, y)).getNameElement());

		if (EditorWindow.removeEnbKey == true) {
			Util.getInstance().map.remove(new Point(x, y));
			EditorWindow editorWindowInstanze = EditorWindow.getEditWindowInstanze();
			editorWindowInstanze.editWindowContentPane.remove(editorWindowInstanze.scrollpane);
			editorWindowInstanze.paneModelCentre = new PaneModelCentre(editorWindowInstanze.n * editorWindowInstanze.scalFactor, editorWindowInstanze.modelDemension, editorWindowInstanze.modelDemension, true);// FIXME
			editorWindowInstanze.paneModelCentre.getContainer().repaint();
			editorWindowInstanze.scrollpane = new JScrollPane(editorWindowInstanze.paneModelCentre.getContainer());
			editorWindowInstanze.editWindowContentPane.add(editorWindowInstanze.scrollpane, BorderLayout.CENTER);

			editorWindowInstanze.pack();

		}

		if (EditorWindow.rotEnbledKey == true) {
		
			Element element =Util.getInstance().getElementsCollection().get(new Point(x, y));
			element.setNameElement(button.getIcon().toString());
			Util.getInstance().map.replace(new Point(x, y), element);
			
			Util.getInstance().rotElemt(new Point(x, y));

		}
	}

	public JPanel getContainer() {
		return container;
	}
}
