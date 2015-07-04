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
import java.io.Serializable;
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
import org.project.mw.util.RotatedIcon;

/**
 * @author Yuri Kalinin
 *
 */
public class Util implements Serializable {
	private static Util utilInstance = null;
	private String FILENAME_DEFAULT = "./dafaultModel.zb";
	private Map<Point, Element> map;

	Util() {
		map = new LinkedHashMap<Point, Element>();
	}

	public static Util getInstance() {
		if (utilInstance == null) {
			utilInstance = new Util();
		}
		return utilInstance;
	}

	public void saveModel() {
		if (new File(FILENAME_DEFAULT).exists()) {
			new File(FILENAME_DEFAULT).delete();
		}
		try {
			FileOutputStream fileoutput = new FileOutputStream(FILENAME_DEFAULT);
			ObjectOutputStream outputstream = new ObjectOutputStream(fileoutput);
			outputstream.writeObject(map);
			System.out.println("file saved");
			outputstream.flush();
			outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Two method for the saving files and opening file with the using the file
	 * choosers
	 */

	public void openModel() {

		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILENAME_DEFAULT));
			map = (Map<Point, Element>) input.readObject();
			input.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Util.getInstance().FILENAME_DEFAULT + " Konnte nicht gefunden werden.", "Warnung", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * @return true if the default file exists
	 */
	public boolean checkFileDefaultSaving() {
		if (new File(FILENAME_DEFAULT).exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * remove default file
	 */
	public void revomeDefaultSaveFile() {
		if (new File(FILENAME_DEFAULT).exists()) {
			new File(FILENAME_DEFAULT).delete();
		}

	}

	protected void saveModel(String fileName) {
		if (new File(fileName).exists()) {
			new File(fileName).delete();
		}
		try {
			FileOutputStream fileoutput = new FileOutputStream(fileName);
			ObjectOutputStream outputstream = new ObjectOutputStream(fileoutput);
			outputstream.writeObject(map);

			outputstream.flush();
			outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openModel(String fileName) {

		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName));
			map = (Map<Point, Element>) input.readObject();
			input.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, fileName + " Konnte nicht gefunden werden.", "Warnung", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}

	public void fileChooser(Component component, String option) {
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "ZigBee";
			}

			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(".zb");
			}

		});
		if (option.equals("save")) {
			fc.showSaveDialog(component);
			if (fc.getSelectedFile() != null) {
				saveModel(fc.getSelectedFile().getAbsolutePath() + ".zb");
				System.out.println(fc.getSelectedFile().getAbsolutePath());
			}
		} else if (option.equals("open")) {
			fc.showOpenDialog(component);
			if (fc.getSelectedFile() != null) {
				openModel(fc.getSelectedFile().getAbsolutePath());

			}
		}

	}

	/**
	 * @return Map of all elements on GridBagLayout
	 */
	public Map<Point, Element> getElementsCollection() {
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
		RotatedIcon ri;
		Icon icon;
		Element element = map.get(locationElement);
		System.out.println("Rotation " + element.getNameElement());// hier ist
																	// noch null
		System.out.println("Rotation Icon Name->" + element.getIconButton().getIcon().toString());// hier
																									// ist
																									// richtige
																									// name
		if (element.getIconButton().getIcon().toString().contains("@") == false) {
			element.setNameElement(element.getIconButton().getIcon().toString());
			// map.replace(locationElement, element);
		}
		System.out.println("Rotation Check2" + element.getNameElement());// prüfen
																			// wieder
		JButton button = element.getIconButton();
		int roateElemt = element.getRotation();
		icon = button.getIcon();

		int modelDemension = EditorWindow.getEditWindowInstance().getModelDemension();
		JButton btnTemp = new JButton();
		btnTemp.setIcon(Util.getInstance().getScaledImage(icon.toString(), modelDemension, modelDemension));

		Image image = Util.getInstance().iconToImage(icon);

		button.setIcon(new ImageIcon(Util.getInstance().getScaledImage(image, modelDemension, modelDemension)));
		icon = button.getIcon();
			switch (roateElemt) {
			case 0:
				ri = new RotatedIcon(icon, 90.0);
				button.setIcon(ri);
				System.out.println("Rotated Icon-> " + icon.toString());
				element.setRotation(90);

				break;
			case 90:
				ri = new RotatedIcon(icon, 90.0);
				button.setIcon(ri);
				System.out.println("Rotated Icon-> " + icon.toString());
				element.setRotation(180);

				break;

			case 180:
				ri = new RotatedIcon(icon, 90.0);
				button.setIcon(ri);
				System.out.println("Rotated Icon-> " + icon.toString());
				element.setRotation(270);

				break;

			case 270:
				ri = new RotatedIcon(icon, 90.0);
				button.setIcon(ri);
				element.setRotation(0);

				break;
			}
		
		map.replace(locationElement, element);
		EditorWindow.getEditWindowInstance().scrollpane.revalidate();
		EditorWindow.getEditWindowInstance().scrollpane.repaint();
	}

}