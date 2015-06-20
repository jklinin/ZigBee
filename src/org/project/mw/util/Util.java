package org.project.mw.util;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.project.mw.gui.Element;


/**
 * @author yuri
 *
 */
public class Util {
	private static Util utilInstance = null;
	private String FILENAME_DEFAULT = "./test.zb";
	private ArrayList<Element> elementsArray = new ArrayList(); // ArrayList of
																// elements(element
																// name, x, y
																// position)
	

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
			outputstream.writeObject(elementsArray);
			System.out.println("file saved");
			outputstream.flush();
			outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Two methondes for the saving files and opening file with the using the
	 * file choosers
	 */
	public void openModel() {

		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILENAME_DEFAULT));
			elementsArray = (ArrayList<Element>) input.readObject();
			input.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Util.getInstance().FILENAME_DEFAULT + " Konnte nicht gefunden werden.", "Warnung", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}

	protected void saveModel(String fileName) {
		if (new File(FILENAME_DEFAULT).exists()) {
			new File(FILENAME_DEFAULT).delete();
		}
		try {
			FileOutputStream fileoutput = new FileOutputStream(fileName);
			ObjectOutputStream outputstream = new ObjectOutputStream(fileoutput);
			outputstream.writeObject(elementsArray);

			outputstream.flush();
			outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openModel(String fileName) {

		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName));
			elementsArray = (ArrayList<Element>) input.readObject();
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

	public ArrayList<Element> getElementsArray() {
		return elementsArray;

	}

	public void removeAllElementsArray() {
		elementsArray.removeAll(elementsArray);

	}


	/**
	 * The method make scalation of Image 
	 * @param srcImg source image 
	 * @param w width
	 * @param h height
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
		return  new ImageIcon(resizedImg);
	}
	public Image getScaledImage(Image srcImg, int w, int h) {		
		Toolkit t = Toolkit.getDefaultToolkit();
		
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return  resizedImg;
	}
}