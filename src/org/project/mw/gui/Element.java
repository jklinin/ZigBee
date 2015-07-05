package org.project.mw.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Element implements Serializable {
	private String nameElement;
	private JButton iconButton = new JButton();
	private int rotation;
	private int sensorID;

	public void setNameElement(String nameIcon) {
		nameElement = nameIcon;
	}

	public String getNameElement() {
		return nameElement;
	}

	public void setIconElement(String icon) {
		iconButton.setIcon(new ImageIcon(icon));
	}

	public void setImageIconElement(Image image) {

		iconButton.setIcon(new ImageIcon(image));
	}

	public JButton getIconButton() {
		return iconButton;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getRotation() {
		return rotation;

	}
	
	public int getRotationCounterClockWise() {
		return 360-rotation;
	}

	public void setSensorID(int id) {
		sensorID = id;
	}

	public int getSensorID() {
		return sensorID;
	}

	public boolean isSensor() {
		
			if (nameElement.contains("sensor") == true) {
				return true;

			}else{
				return false;
			}
		
		
	}
}
