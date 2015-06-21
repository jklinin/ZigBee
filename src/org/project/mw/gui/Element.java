/**
 * 
 */
package org.project.mw.gui;

import java.io.Serializable;

import org.project.mw.util.RotatedIcon;
import org.project.mw.util.RotatedIcon.Rotate;

/**
 * @author yuri
 *
 */
public class Element implements Serializable{
	private String fileNameIcon;
	private int xPosition;
	private int yPosition;
	private Rotate rotate;
	
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
	public void setRotation (Rotate rotation) {
		this.rotate=rotation;
		
	}
	public Rotate getRotation () {
		return rotate;
	}

}
