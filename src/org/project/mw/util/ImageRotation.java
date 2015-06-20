package org.project.mw.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.project.mw.gui.Element;

public class ImageRotation {
	JLabel comp;
	ImageIcon icon;
	int angle1 = 90;
	int angle2 = 180;
	int angle3 = 270;

	private static ImageRotation utilInstance = null;

	public static ImageRotation getInstance() {
		if (utilInstance == null) {
			utilInstance = new ImageRotation();
		}
		return utilInstance;
	}

	public Icon preRotation(int indexComp, JLabel comp) {
		this.comp = comp;
		icon = new ImageIcon(comp.getIcon().toString());
		System.out.println("Rotation icon name "+icon);
		return rotateIcon(90).getIcon();
	
		

	}
	private  JLabel rotateIcon(int rotation) {
		 JLabel labelComp = new JLabel(icon) {
		        protected void paintComponent(Graphics g) {			        	
		            Graphics2D g2 = (Graphics2D)g;
		            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                                RenderingHints.VALUE_ANTIALIAS_ON);
		            AffineTransform aT = g2.getTransform();
		            Shape oldshape = g2.getClip();
		            double x = getWidth()/2.0;
		            double y = getHeight()/2.0;
		            aT.rotate(Math.toRadians(90), x, y);//FIXME Rotation factor in panelModel
//		            g2.transform(aT);
		            g2.setTransform(aT);
		            g2.setClip(oldshape);
		            super.paintComponent(g);
		        }
		    };
		   
		    return labelComp;
	}
	
}
