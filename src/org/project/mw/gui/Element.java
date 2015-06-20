/**
 * 
 */
package org.project.mw.gui;

import java.io.Serializable;

/**
 * @author yuri
 *
 */
public class Element implements Serializable{
	private String fileNameIcon;
	private int xPosition;
	private int yPosition;
	private int rotation=0;
	
	public Element() {

	}

	public void setPosition(int y, int x) {
		this.xPosition = x;
		this.yPosition = y;
		
	}

	public void setFileIconName(String iconName) {
		fileNameIcon = iconName;
	}

	public int getPositionX() {
		return xPosition;

	}

	public int getPositionY() {
		return yPosition;

	}

	public String getFileIconName() {
		return fileNameIcon;

	}
	public void setRotation (int rotation) {
		this.rotation=rotation;
		
	}
	public int getRotation () {
		return rotation;
	}

}
