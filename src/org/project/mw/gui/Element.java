package org.project.mw.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;


/**
 * @author Yuri Kalinin Class contains information about component in 2D Model
 *         nameElement name of icon file iconButton icon rotation information
 *         about rotation(0, 90, 180, 270 degrees) sensorID sendorID for
 *         identification of sensor
 */
public class Element implements Serializable {
	private String nameElement;
	private JButton iconButton = new JButton();
	private int rotation;
	private int sensorID;
	private String currentSensorValue = null;
	
	

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

	public String getCurrentSensorValue() {
		return currentSensorValue;
	}

	public void setCurrentSensorValue(String currentSensorValue) {
		this.currentSensorValue = currentSensorValue;
	}

	/**
	 * @return true if the component is sensor
	 */
	public boolean isSensor() {
		
			if (nameElement.contains("sensor") == true) {
				return true;

			}else{
				return false;
			}
		
		
	}
}
