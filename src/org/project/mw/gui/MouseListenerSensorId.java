package org.project.mw.gui;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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
			JButton button = Util.getInstance().map.get(new Point(x, y)).getIconButton();
			EditorWindow.getEditWindowInstanze().getPaneModelCentreInstance().putElementsToMap();
			if (Util.getInstance().getElementsCollection().get(new Point(x, y)).isSensor() == true) {
				System.out.println("Sensor");
				
				JTextField sensorIDField = new JTextField(5);
			   

			      JPanel myPanel = new JPanel();
			      myPanel.add(new JLabel("Sensor ID"));
			      myPanel.add(sensorIDField);
			      myPanel.add(Box.createHorizontalStrut(15)); 
			  
			      int result = JOptionPane.showConfirmDialog(null, myPanel, 
			               "Sensor ID", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			         System.out.println("id value: " + sensorIDField.getText());
			         int id=Integer.parseInt(sensorIDField.getText());
			         Util.getInstance().map.get(new Point(x,y)).setSensorID(id);
			      
			      }}
		}
	}
}
