package org.project.mw.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.project.mw.util.Util;

public class MouseListenerSensorId extends MouseAdapter {
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {

			JButton source = (JButton) e.getSource();
			String command = source.getActionCommand();
			System.out.println(command);
			String[] arr = command.split(",");
			int x = Integer.parseInt(arr[0]);
			int y = Integer.parseInt(arr[1]);
			JButton button = Util.getInstance().getElementsCollection().get(new Point(x, y)).getIconButton();
			EditorWindow.getEditWindowInstance().getPaneModelCentreInstance().putElementsToMap();
			if (Util.getInstance().getElementsCollection().get(new Point(x, y)).isSensor() == true) {
				System.out.println("Sensor");

				dialog(x, y);

			}
		}
	}

	private void dialog(int x, int y) {
		JPanel pan = new JPanel(new BorderLayout());
		final JTextField sensorIdField = new JTextField(5);
		final JButton ok = new JButton("OK");
		ok.setEnabled(false);

		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = sensorIdField.getText();
				System.out.println("The input is: " + input);
				int id = Integer.parseInt(input);
				Util.getInstance().getElementsCollection().get(new Point(x, y)).setSensorID(id);

				/* close the dialog */
				Window w = SwingUtilities.getWindowAncestor(ok);
				if (w != null)
					w.setVisible(false);
			}
		});

		sensorIdField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if (sensorIdField.getText().matches("\\d+") == false) {
					ok.setEnabled(false);
				} else {
					ok.setEnabled(true);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {

				if (sensorIdField.getText().matches("\\d+") == true) {
					ok.setEnabled(true);
				} else {
					ok.setEnabled(false);
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (sensorIdField.getText().matches("\\d+") == true) {
					ok.setEnabled(true);
				} else {
					ok.setEnabled(false);
				}
			}
		});

		pan.add(sensorIdField, BorderLayout.NORTH);
		JOptionPane.showOptionDialog(null, pan, "Sensor ID", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new JButton[] { ok }, ok);

	}
}
