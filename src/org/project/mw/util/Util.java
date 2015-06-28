package org.project.mw.util;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.project.mw.gui.EditorWindow;
import org.project.mw.gui.Element;
import org.project.mw.gui.PaneModelCentre;
import org.project.mw.util.RotatedIcon.Rotate;
import org.project.mw.util.RotatedIcon;
import org.project.mw.util.RotatedIcon.Rotate;
/**
 * @author yuri
 *
 */
public class Util {
	private static Util utilInstance = null;
	private String FILENAME_DEFAULT = "./test.zb";
	public Map<Point, JButton> map;

	Util() {
		map = new LinkedHashMap<Point, JButton>();
	}

	public static Util getInstance() {
		if (utilInstance == null) {
			utilInstance = new Util();
		}
		return utilInstance;
	}

	/*
	 * public void saveModel() { if (new File(FILENAME_DEFAULT).exists()) { new
	 * File(FILENAME_DEFAULT).delete(); } try { FileOutputStream fileoutput =
	 * new FileOutputStream(FILENAME_DEFAULT); ObjectOutputStream outputstream =
	 * new ObjectOutputStream(fileoutput);
	 * outputstream.writeObject(elementsArray);
	 * System.out.println("file saved"); outputstream.flush();
	 * outputstream.close(); } catch (Exception e) { e.printStackTrace(); } }
	 */

	/**
	 * Two methondes for the saving files and opening file with the using the
	 * file choosers
	 */
	/*
	 * public void openModel() {
	 * 
	 * try { ObjectInputStream input = new ObjectInputStream(new
	 * FileInputStream(FILENAME_DEFAULT)); elementsArray = (ArrayList<Element>)
	 * input.readObject(); input.close(); } catch (Exception e) {
	 * JOptionPane.showMessageDialog(null, Util.getInstance().FILENAME_DEFAULT +
	 * " Konnte nicht gefunden werden.", "Warnung",
	 * JOptionPane.WARNING_MESSAGE); e.printStackTrace(); } }
	 */
	/*
	 * protected void saveModel(String fileName) { if (new
	 * File(FILENAME_DEFAULT).exists()) { new File(FILENAME_DEFAULT).delete(); }
	 * try { FileOutputStream fileoutput = new FileOutputStream(fileName);
	 * ObjectOutputStream outputstream = new ObjectOutputStream(fileoutput);
	 * outputstream.writeObject(elementsArray);
	 * 
	 * outputstream.flush(); outputstream.close(); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	/*
	 * private void openModel(String fileName) {
	 * 
	 * try { ObjectInputStream input = new ObjectInputStream(new
	 * FileInputStream(fileName)); elementsArray = (ArrayList<Element>)
	 * input.readObject(); input.close(); } catch (Exception e) {
	 * JOptionPane.showMessageDialog(null, fileName +
	 * " Konnte nicht gefunden werden.", "Warnung",
	 * JOptionPane.WARNING_MESSAGE); e.printStackTrace(); } }
	 */

	/*
	 * public void fileChooser(Component component, String option) {
	 * JFileChooser fc = new JFileChooser(); fc.addChoosableFileFilter(new
	 * FileFilter() {
	 * 
	 * @Override public String getDescription() { return "ZigBee"; }
	 * 
	 * @Override public boolean accept(File f) { return
	 * f.getName().endsWith(".zb"); } }); if (option.equals("save")) {
	 * fc.showSaveDialog(component); if (fc.getSelectedFile() != null) {
	 * saveModel(fc.getSelectedFile().getAbsolutePath() + ".zb");
	 * System.out.println(fc.getSelectedFile().getAbsolutePath()); } } else if
	 * (option.equals("open")) { fc.showOpenDialog(component); if
	 * (fc.getSelectedFile() != null) {
	 * openModel(fc.getSelectedFile().getAbsolutePath());
	 * 
	 * } }
	 * 
	 * }
	 */
	public Map<Point, JButton> getElementsArray() {
		return map;

	}

	/**
	 * The method make scalation of Image
	 * 
	 * @param srcImg
	 *            source image
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @return resized image icon
	 */
	public ImageIcon getScaledImage(String imageName, int w, int h) {
		Toolkit t = Toolkit.getDefaultToolkit();
		Image srcImg = t.getImage(imageName);
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return new ImageIcon(resizedImg);
	}

	public Image getScaledImage(Image srcImg, int w, int h) {

		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	public Image iconToImage(Icon icon) {
		if (icon instanceof ImageIcon) {
			return ((ImageIcon) icon).getImage();
		} else {
			int w = icon.getIconWidth();
			int h = icon.getIconHeight();
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gd.getDefaultConfiguration();
			BufferedImage image = gc.createCompatibleImage(w, h);
			Graphics2D g = image.createGraphics();
			icon.paintIcon(null, g, 0, 0);
			g.dispose();
			return image;
		}
	}
	
	
	public void rotElemt(Point locationElement) {
		//Rotate roateElemt = Util.getInstance().getElementsArray().get(compIndex).getRotation();
		RotatedIcon ri;
		Icon icon;
		JButton button=map.get(locationElement);
		String roateElemt=button.getName();
		icon=button.getIcon();
			System.out.println("Rotated Icon-> " + icon.toString());
			System.out.println("-> " + button.getName());
		
			int modelDemension = EditorWindow.getEditWindowInstanze().modelDemension;
			JButton btnTemp = new JButton();
			btnTemp.setIcon(Util.getInstance().getScaledImage(icon.toString(), modelDemension, modelDemension));
		//	ri = new RotatedIcon(btnTemp.getIcon(), roateElemt);
			//component.setIcon(ri);

		
			// Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.UP);
		
		Image image = Util.getInstance().iconToImage(icon);
	
	
		button.setIcon(new ImageIcon(Util.getInstance().getScaledImage(image, modelDemension, modelDemension)));
		icon = button.getIcon();
		if (roateElemt != null) {
			System.out.println("Rotation is not null");

			switch (roateElemt) {
			case "DOWN":
				ri = new RotatedIcon(icon, RotatedIcon.Rotate.UP);
				button.setIcon(ri);
				System.out.println("Rotated Icon-> " + icon.toString());
				button.setName("UP");
				break;
			case "UP":
				ri = new RotatedIcon(icon, RotatedIcon.Rotate.UPSIDE_DOWN);
				button.setIcon(ri);
				System.out.println("Rotated Icon-> " + icon.toString());
				//Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.UPSIDE_DOWN);
				break;

			case "UPSIDE_DOWN":
				ri = new RotatedIcon(icon, RotatedIcon.Rotate.ABOUT_CENTER);
				button.setIcon(ri);
				System.out.println("Rotated Icon-> " + icon.toString());
				//Util.getInstance().getElementsArray().get(compIndex).setRotation(RotatedIcon.Rotate.ABOUT_CENTER);
				break;

			case "ABOUT_CENTER":
				ri = new RotatedIcon(icon, RotatedIcon.Rotate.DOWN);
				button.setIcon(ri);
				System.out.println("Rotated Icon-> " + icon.toString());
				button.setName("DOWN");
				break;
			}
		} else {
			System.out.println("Rotation is null from array");
			ri = new RotatedIcon(icon, RotatedIcon.Rotate.DOWN);
			button.setName("DOWN");
			button.setIcon(ri);
			System.out.println("Rotated Icon-> " + icon.toString());

		}

		EditorWindow.getEditWindowInstanze().scrollpane.revalidate();
		EditorWindow.getEditWindowInstanze().scrollpane.repaint();
	}

}