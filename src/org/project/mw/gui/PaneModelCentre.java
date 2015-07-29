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
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;

import org.project.mw.util.Util;

/**
 * Class of center model in the editor window default matrix (gridbag) 10x10 
 * Default size 50px
 * 
 * @author Yuri Kalinin
 *
 */
public class PaneModelCentre implements ActionListener {
	private GridBagLayout gb;
	private GridBagConstraints gc;

	private JPanel container;
	private int size;
	protected ArrayList<Element> btnArrayListTemp = new ArrayList<Element>();
	private MouseListenerSensorId mouseListner = new MouseListenerSensorId();
	private Map<Point, Element> map = Util.getInstance().getElementsCollection();

	PaneModelCentre(int size, int dimenisionX, int dimenisionY, boolean restore) {
		this.size = size;

		JButton button;
		Element element;
		gb = new GridBagLayout();
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		container = new JPanel();
		container.setLayout(gb);
		int x = 0, y = -1;
		for (int i = 0; i < size; i++) {
			element = new Element();
			element.setRotation(0);
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
			// set size for JButtons
			btnArrayListTemp.get(i).getIconButton().setPreferredSize(new Dimension(dimenisionX, dimenisionY));
			btnArrayListTemp.get(i).getIconButton().setTransferHandler(new TransferHandler("icon"));

			gb.setConstraints(btnArrayListTemp.get(i).getIconButton(), gc);
			container.add(btnArrayListTemp.get(i).getIconButton());
			// restore
			if (restore == true) {

				if (map.get(new Point(x, y)) != null) {

					int id = map.get(new Point(x, y)).getSensorID();
					int rotation = map.get(new Point(x, y)).getRotation();
					btnArrayListTemp.get(i).setNameElement(map.get(new Point(x, y)).getNameElement());
					btnArrayListTemp.get(i).setSensorID(id);
					btnArrayListTemp.get(i).setRotation(rotation);
					button = map.get(new Point(x, y)).getIconButton();
					if (button.getIcon() != null) {
						Image image = Util.getInstance().iconToImage(button.getIcon());
						btnArrayListTemp.get(i).setImageIconElement((Util.getInstance().getResizedImage(image, dimenisionX, dimenisionY)));

					}
				}

				map.put(new Point(x, y), btnArrayListTemp.get(i));

			} else {
				map.put(new Point(x, y), btnArrayListTemp.get(i));

			}

			btnArrayListTemp.get(i).getIconButton().setActionCommand(x + "," + y);
			btnArrayListTemp.get(i).getIconButton().addActionListener(this);
			btnArrayListTemp.get(i).getIconButton().addMouseListener(mouseListner);

		}
		container.repaint();

	}

	@Override
	public void actionPerformed(ActionEvent evt) {

		JButton source = (JButton) evt.getSource();
		String command = source.getActionCommand();

		String[] arr = command.split(",");
		int x = Integer.parseInt(arr[0]);
		int y = Integer.parseInt(arr[1]);
		JButton button = map.get(new Point(x, y)).getIconButton();

		putElementsToMap();

		EditorWindow editorWindowInstanze = EditorWindow.getEditWindowInstance();
		editorWindowInstanze.editWindowContentPane.remove(editorWindowInstanze.scrollpane);
		int scalFactor = Util.getInstance().getScalFactor();
		int modelDemension = Util.getInstance().getModelDemension();
		editorWindowInstanze.paneModelCentre = new PaneModelCentre(editorWindowInstanze.n * scalFactor, modelDemension, modelDemension, true);
		editorWindowInstanze.paneModelCentre.getContainer().repaint();
		editorWindowInstanze.scrollpane = new JScrollPane(editorWindowInstanze.paneModelCentre.getContainer());
		editorWindowInstanze.editWindowContentPane.add(editorWindowInstanze.scrollpane, BorderLayout.CENTER);

		editorWindowInstanze.pack();

		if (EditorWindow.removeEnbKey == true) {
			map.remove(new Point(x, y));
			editorWindowInstanze = EditorWindow.getEditWindowInstance();
			editorWindowInstanze.editWindowContentPane.remove(editorWindowInstanze.scrollpane);
			scalFactor = Util.getInstance().getScalFactor();
			modelDemension = Util.getInstance().getModelDemension();
			editorWindowInstanze.paneModelCentre = new PaneModelCentre(editorWindowInstanze.n * scalFactor, modelDemension, modelDemension, true);
			editorWindowInstanze.paneModelCentre.getContainer().repaint();
			editorWindowInstanze.scrollpane = new JScrollPane(editorWindowInstanze.paneModelCentre.getContainer());
			editorWindowInstanze.editWindowContentPane.add(editorWindowInstanze.scrollpane, BorderLayout.CENTER);
			editorWindowInstanze.pack();
		}

		if (EditorWindow.rotEnbledKey == true) {
			Util.getInstance().rotElemt(new Point(x, y));
		}
	}

	public JPanel getContainer() {
		return container;
	}

	public void putElementsToMap() {
		int x = 0, y = -1;
		for (int i = 0; i < size; i++) {

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
			if (btnArrayListTemp.get(i).getIconButton().getIcon() != null) {
				String iconName = btnArrayListTemp.get(i).getIconButton().getIcon().toString();
				if (iconName.contains("@") == false) {
					btnArrayListTemp.get(i).setNameElement(btnArrayListTemp.get(i).getIconButton().getIcon().toString());
					map.replace(new Point(x, y), btnArrayListTemp.get(i));
				}
			}

		}

	}
}
